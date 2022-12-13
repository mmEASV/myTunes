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

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistController {
    private AnchorPane popUpContent;
    private AnchorPane window;
    private final PlaylistModel playlistModel;
    private BaseController baseController;
    private TableView<Playlist> table;

    public PlaylistController(AnchorPane contentWindow, PlaylistModel playlistModel, BaseController baseController) {
        this.window = contentWindow;
        this.playlistModel = playlistModel;
        this.baseController = baseController;
    }

    public void fillTable() throws Exception {
        table.setItems(playlistModel.getAllPlaylists());
    }

    public TableView<Playlist> getTable(){
        return this.table;
    }
        public void show (AnchorPane centerContent) throws Exception {
            table = new TableView<>();

            TableColumn<Playlist, String> nameColumn = new TableColumn<>();
            nameColumn.setText("Playlist Name");
            nameColumn.prefWidthProperty().set(233);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));

            TableColumn<Playlist, Integer> NumberOfSongsColumn = new TableColumn<>();
            NumberOfSongsColumn.setText("Number of Songs");
            NumberOfSongsColumn.prefWidthProperty().set(64);
            NumberOfSongsColumn.setCellValueFactory(new PropertyValueFactory<>("songAmount"));

            TableColumn<Playlist, String> optionsColumn = new TableColumn<>();
            optionsColumn.setText("Options");

            optionsColumn.prefWidthProperty().set(47);

            MenuItem editItem = new MenuItem("Edit Playlist");
            var menu = new ContextMenu(editItem);
            ImageView editImage = new ImageView("mytunessys/gui/icons/Edit.png");
            ImageView removeImage = new ImageView("mytunessys/gui/icons/Remove.png");

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
                                    MenuItem editItem = new MenuItem("edit playlist", editImage);
                                    MenuItem deletePlaylist = new MenuItem("delete playlist", removeImage);
                                    var menu = new ContextMenu(editItem, deletePlaylist);
                                    editItem.setOnAction(event -> {
                                        editPlaylist(getTableRow().getItem());
                                        event.consume();
                                    });
                                    deletePlaylist.setOnAction(event -> {
                                        deletePlaylist(getTableRow().getItem());
                                        event.consume();
                                    });
                                    btn.setOnAction(event -> {
                                        menu.show(btn, Side.BOTTOM, -75, 0);
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
                            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                                Playlist serialData = row.getItem();
                                try {
                                    baseController.switchToSongOnPlaylistInterface(new ActionEvent(), serialData);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }
                    });
                    return row;
                }
            });

            table.editableProperty().set(false);

            table.getColumns().addAll(nameColumn, NumberOfSongsColumn, optionsColumn);
            table.setFocusTraversable(false);
            centerContent.getChildren().add(table);
            fillTable();
            table.getSelectionModel().clearAndSelect(0);
        }
        public void newPlaylist () {
            displayPlaylistPopUp(null);
        }
        public void editPlaylist (Playlist playlist){
            displayPlaylistPopUp(playlist);
        }
        public void deletePlaylist (Playlist playlist){
            displayedDeleteConfirmation(playlist);
        }

    /**
     * method with void as return type that tries to delete playlist from its model in not confirmed by user action this playlist wont be deleted
     * @param playlistToDelete selected playlist that must carry its id in order to be deleted
     */
    public void displayedDeleteConfirmation (Playlist playlistToDelete){
            var confirm = AlertNotification.showAlertWindow("You are about to delete this playlist.", Alert.AlertType.CONFIRMATION);
            if (confirm.get().equals(ButtonType.OK)) {
                try {
                    playlistModel.deletePlaylist(playlistToDelete);
                    fillTable();
                    AlertNotification.showAlertWindow("Playlist with id : " + playlistToDelete.getId() + " was successfully updated !", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        public void displayPlaylistPopUp (Playlist content){
            popUpContent = new AnchorPane();
            popUpContent.setMinSize(400,470);
            popUpContent.getStyleClass().add("new");
            window.getChildren().add(popUpContent);

            var formHolder = new AnchorPane();
            formHolder.setLayoutX(36);
            formHolder.setLayoutY(100);
            formHolder.setMinSize(300,75);
            formHolder.getStyleClass().add("form");
            popUpContent.getChildren().add(formHolder);

            var vBoxHolder = new VBox();
            vBoxHolder.setPadding(new Insets(10));
            formHolder.getChildren().add(vBoxHolder);

            var topRow = new HBox();
            topRow.setMinWidth(280);
            var playlistLabel = new Label("Add new Playlist");
            var closeButton = new Button();
            closeButton.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Close.png")));
            var space = new Region();
            HBox.setHgrow(space, Priority.ALWAYS);
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    window.getChildren().remove(popUpContent);
                }
            });
            topRow.getChildren().addAll(playlistLabel, space, closeButton);
            vBoxHolder.getChildren().add(topRow);

            var playlistRow = new AnchorPane();
            VBox.setMargin(playlistRow,new Insets(0,0,4,0));
            var playlistNameLabel = new Label("Playlist Name");
            playlistNameLabel.getStyleClass().add("form-label");
            var playlistName = new TextField();
            playlistName.getStyleClass().add("form-input");
            playlistRow.getChildren().addAll( playlistName,playlistNameLabel);
            vBoxHolder.getChildren().addAll(playlistRow);

            var submitRow = new HBox();
            var leftSpace = new Region();
            var rightSpace = new Region();
            HBox.setHgrow(leftSpace, Priority.ALWAYS);
            HBox.setHgrow(rightSpace, Priority.ALWAYS);
            var submitButton = new Button("Submit");
            submitButton.getStyleClass().add("submit-Button");
            submitRow.getChildren().addAll(leftSpace,submitButton,rightSpace);
            vBoxHolder.getChildren().add(submitRow);
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        playlistModel.createPlaylist(new Playlist(playlistName.getText(0, playlistName.getLength())));
                        fillTable();
                        AlertNotification.showAlertWindow("Successfully created playlist with id : " + playlistName.getId() + " !", Alert.AlertType.INFORMATION);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    popUpContent.visibleProperty().set(false);
                }
            });

            if (content != null) {
                playlistName.setText(content.getPlaylistName());
            }
        }

    }
