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
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.utilities.AlertNotification;
import mytunessys.gui.models.PlaylistModel;

import java.util.HashMap;

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistController {
    // TODO: do not write inst var with upper case letter first please
    private AnchorPane popUpContent;
    private AnchorPane window;
    private final PlaylistModel playlistModel;

    private BaseController baseController;
    private TableView<Playlist> table;

    public PlaylistController(AnchorPane contentWindow,PlaylistModel playlistModel,BaseController baseController){
        this.window = contentWindow; // refer to this. instead of just the name  :)
        this.playlistModel = playlistModel;
        this.baseController = baseController;
    }
    public void fillTable() throws ApplicationException{
        table.setItems(playlistModel.getAllPlaylists());
    }
    public void show(AnchorPane centerContent) throws ApplicationException {
        table = new TableView<>();


    public TableView<Playlist> getTable(){
        return table;
    }

    public void Show(AnchorPane centerContent) throws ApplicationException {
        table = new TableView<>();

        TableColumn<Playlist, String> NameColumn = new TableColumn<>();
        NameColumn.setText("Playlist Name");
        NameColumn.prefWidthProperty().set(233);
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));


        TableColumn<Playlist, Integer> NumberOfSongsColumn = new TableColumn<>();
        NumberOfSongsColumn.setText("Number of Songs");
        NumberOfSongsColumn.prefWidthProperty().set(64);
        NumberOfSongsColumn.setCellValueFactory(new PropertyValueFactory<>("songAmount"));

        TableColumn<Playlist, String> optionsColumn = new TableColumn<>();
        optionsColumn.setText("Options");

        OptionsColumn.prefWidthProperty().set(47);

        MenuItem editItem = new MenuItem("Edit Playlist");
        var menu = new ContextMenu(editItem);

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
                                MenuItem editItem = new MenuItem("edit playlist");
                                MenuItem deletePlaylist = new MenuItem("delete playlist");
                                var menu = new ContextMenu(editItem,deletePlaylist);
                                editItem.setOnAction(event -> {
                                    editPlaylist(getTableRow().getItem());
                                    event.consume();
                                });
                                deletePlaylist.setOnAction(event -> {
                                    deletePlaylist(getTableRow().getItem());
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

        optionsColumn.setCellFactory(cellFactory);

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

        table.getColumns().addAll(nameColumn,NumberOfSongsColumn,optionsColumn);
        table.setFocusTraversable(false);
        centerContent.getChildren().add(table);
        fillTable();
    }
    public void newPlaylist(){
        displayPlaylistPopUp(null);
    }
    public void editPlaylist(Playlist playlist) {
        displayPlaylistPopUp(playlist);
    }
    public void deletePlaylist(Playlist playlist){
        displayedDeleteConfirmation(playlist);
    }
    public void displayedDeleteConfirmation(Playlist playlistToDelete){
        var confirm = AlertNotification.showAlertWindow("You are about to delete this playlist.", Alert.AlertType.CONFIRMATION);
        if(confirm.get().equals(ButtonType.OK)){
            try {
                playlistModel.deletePlaylist(playlistToDelete);
                fillTable();
            } catch (ApplicationException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void displayPlaylistPopUp(Playlist content){
        popUpContent = new AnchorPane();
        popUpContent.setMinWidth(400);
        popUpContent.setMinHeight(470);
        popUpContent.getStyleClass().add("new");
        window.getChildren().add(popUpContent);

        var formHolder = new AnchorPane();
        formHolder.setLayoutX(36);
        formHolder.setLayoutY(100);
        formHolder.setMinWidth(300);
        formHolder.setMinHeight(75);
        formHolder.getStyleClass().add("form");
        popUpContent.getChildren().add(formHolder);

        var vBoxHolder = new VBox();
        vBoxHolder.setPadding(new Insets(10));
        formHolder.getChildren().add(vBoxHolder);

        var topRow = new HBox();
        vBoxHolder.getChildren().add(topRow);


        var closeButton = new Button();
        closeButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Close.png")));
        var playlistLabel = new Label("Add new Playlist");
        var space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.getChildren().remove(popUpContent);

            }
        });
        topRow.getChildren().addAll(playlistLabel, space, closeButton);


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
                try {
                    playlistModel.createPlaylist(new Playlist(1, playlistName.getText(0,playlistName.getLength()),0));
                    fillTable();
                } catch (ApplicationException e) {
                    throw new RuntimeException(e);
                }
                popUpContent.visibleProperty().set(false);
            }
        });
        vBoxHolder.getChildren().addAll(addPlaylistButton);

        if(content != null){
            playlistName.setText(content.getPlaylistName());
        }
    }



}
