package mytunessys.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.util.Callback;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.utilities.AlertNotification;
import mytunessys.gui.models.PlaylistModel;

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistController {
    // TODO: do not write inst var with upper case letter first please
    private AnchorPane Window;
    private final PlaylistModel playlistModel;
    BaseController baseController;
    public PlaylistController(AnchorPane contentWindow,PlaylistModel playlistModel,BaseController baseController){
        this.Window = contentWindow; // refer to this. instead of just the name  :)
        this.playlistModel = playlistModel;
        this.baseController = baseController;
    }

    public void Show(AnchorPane centerContent) throws ApplicationException {
        TableView<Playlist> table = new TableView<>();

        TableColumn<Playlist, String> NameColumn = new TableColumn<>();
        NameColumn.setText("Playlist Name");
        NameColumn.prefWidthProperty().set(233);
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));

        TableColumn<Playlist, Integer> NumberOfSongsColumn = new TableColumn<>();
        NumberOfSongsColumn.setText("Number of Songs");
        NumberOfSongsColumn.prefWidthProperty().set(64);
        NumberOfSongsColumn.setCellValueFactory(new PropertyValueFactory<>("songAmount"));

        TableColumn<Playlist, String> OptionsColumn = new TableColumn<>();
        OptionsColumn.setText("Options");

        OptionsColumn.prefWidthProperty().set(47);

        MenuItem editItem = new MenuItem("edit playlist");
        MenuItem deletePlaylist = new MenuItem("delete playlist");
        var menu = new ContextMenu(editItem, deletePlaylist);

        Callback<TableColumn<Playlist, String>, TableCell<Playlist, String>> cellFactory
            = //
            new Callback<TableColumn<Playlist, String>, TableCell<Playlist, String>>() {
                @Override
                public TableCell call(final TableColumn<Playlist, String> param) {
                    final TableCell<Playlist, String> cell = new TableCell<Playlist, String>() {

                        final Button btn = new Button("...");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                editItem.setOnAction(event -> {
                                    EditPlaylist(getTableRow().getItem());
                                    event.consume();
                                });
                                deletePlaylist.setOnAction(event -> {
                                    DeletePlaylist(getTableRow().getItem());
                                    event.consume();
                                });
                                btn.setOnAction(event -> {
                                    menu.show(btn, Side.BOTTOM,0,0);
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

        OptionsColumn.setCellFactory(cellFactory);

        // this is sketchy but works for now
        table.setRowFactory(new Callback<TableView<Playlist>, TableRow<Playlist>>() {
            @Override
            public TableRow<Playlist> call(TableView<Playlist> param) {
                TableRow<Playlist> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount() == 2 && (!row.isEmpty())){
                            Playlist serialData = row.getItem();
                            try {
                                baseController.switchToSongOnPlaylistInterface(new ActionEvent(),serialData);
                            } catch (ApplicationException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });
                return row;
            }
        });

        table.editableProperty().set(false);

        table.getColumns().addAll(NameColumn,NumberOfSongsColumn,OptionsColumn);
        table.setFocusTraversable(false);
        centerContent.getChildren().add(table);
        table.setItems(playlistModel.getAllPlaylists());
    }
    public void NewPlaylist(){
        DisplayPlaylistPopUp(null);
    }
    public void EditPlaylist(Playlist playlist) {
        DisplayPlaylistPopUp(playlist);
    }
    public void DeletePlaylist(Playlist playlist){
        DisplayedDeleteConfirmation(playlist);
    }
    public void DisplayedDeleteConfirmation(Playlist playlistToDelete){
        var confirm = AlertNotification.showAlertWindow("Delete Song", "You are about to delete this playlist.",
                Alert.AlertType.CONFIRMATION);
        if(confirm.get().equals(ButtonType.OK)){
            try {
                playlistModel.deletePlaylist(playlistToDelete);
            } catch (ApplicationException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void DisplayPlaylistPopUp(Playlist content){
        var anchorPane = new AnchorPane();
        anchorPane.setMinWidth(400);
        anchorPane.setMinHeight(470);
        anchorPane.getStyleClass().add("new");
        Window.getChildren().add(anchorPane);

        var FormHolder = new AnchorPane();
        FormHolder.setLayoutX(36);
        FormHolder.setLayoutY(100);
        FormHolder.setMinWidth(300);
        FormHolder.setMinHeight(75);
        FormHolder.getStyleClass().add("form");
        anchorPane.getChildren().add(FormHolder);

        var vBoxHolder = new VBox();
        vBoxHolder.setPadding(new Insets(10));
        FormHolder.getChildren().add(vBoxHolder);

        var TopRow = new HBox();
        vBoxHolder.getChildren().add(TopRow);


        var CloseButton = new Button();
        CloseButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Close.png")));
        var playlistLabel = new Label("Add new Playlist");
        var Space = new Region();
        HBox.setHgrow(Space, Priority.ALWAYS);
        CloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.visibleProperty().set(false);

            }
        });
        TopRow.getChildren().addAll(playlistLabel, Space, CloseButton);


        var playlistRow = new HBox();
        var playlistNameLabel = new Label("Playlist Name");
        var playlistName = new TextField();
        playlistName.setPromptText("Playlist Name...");


        playlistRow.getChildren().addAll(playlistNameLabel, playlistName);
        vBoxHolder.getChildren().addAll(playlistRow);



        var addPlaylistButton = new Button("Add Playlist");
        addPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO add a playlist to the lists
                anchorPane.visibleProperty().set(false);
            }
        });
        vBoxHolder.getChildren().addAll(addPlaylistButton);

        if(content != null){
            playlistName.setText(content.getPlaylistName());
        }
    }



}
