package mytunessys.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.gui.models.PlaylistModel;

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistController {

    PlaylistModel playlistModel = new PlaylistModel();

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


        Callback<TableColumn<Playlist, String>, TableCell<Playlist, String>> cellFactory
            = //
            new Callback<TableColumn<Playlist, String>, TableCell<Playlist, String>>() {
                @Override
                public TableCell call(final TableColumn<Playlist, String> param) {
                    final TableCell<Playlist, String> cell = new TableCell<Playlist, String>() {

                        final Button btn = new Button("...");
                        final ContextMenu menu = new ContextMenu(new MenuItem("edit Playlist"),new MenuItem("add to playlist"));
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
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



        table.editableProperty().set(false);

        table.getColumns().addAll(NameColumn,NumberOfSongsColumn,OptionsColumn);
        table.setFocusTraversable(false);
        centerContent.getChildren().add(table);


    }


    public void NewPlaylist(AnchorPane pageContent){
        var anchorPane = new AnchorPane();
        anchorPane.setMinWidth(400);
        anchorPane.setMinHeight(470);
        anchorPane.getStyleClass().add("new");
        pageContent.getChildren().add(anchorPane);

        var FormHolder = new AnchorPane();
        FormHolder.setLayoutX(36);
        FormHolder.setLayoutY(100);
        FormHolder.setMinWidth(300);
        FormHolder.setMinHeight(75);
        FormHolder.getStyleClass().add("form");
        anchorPane.getChildren().add(FormHolder);

        var vBoxHolder = new VBox();
        FormHolder.getChildren().add(vBoxHolder);

        var TopRow = new HBox();
        vBoxHolder.getChildren().add(TopRow);


        var CloseButton = new Button("X");
        var playlistLabel = new Label("Add new Playlist");
        CloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.visibleProperty().set(false);

            }
        });
        TopRow.getChildren().addAll(playlistLabel, CloseButton);


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
    }



}
