package mytunessys.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.gui.models.PlaylistModel;

/**
 * @author Bálint & Matej
 */

public class PlaylistController {

    TextField playlistName;
    PlaylistModel playlistModel = new PlaylistModel();

    public void Show(AnchorPane centerContent){
        TableView<Playlist> table = new TableView<>();

        TableColumn<Playlist, String> NameColumn = new TableColumn<>();
        NameColumn.setText("Playlist Name");
        NameColumn.prefWidthProperty().set(126);
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));

        TableColumn<Playlist, String> CurrentlyPlayingColumn = new TableColumn<>();
        CurrentlyPlayingColumn.setText("Currently Playing");
        CurrentlyPlayingColumn.prefWidthProperty().set(107);
        CurrentlyPlayingColumn.setCellValueFactory(new PropertyValueFactory<>("currentlyPlaying"));

        TableColumn<Playlist, Integer> NumberOfSongsColumn = new TableColumn<>();
        NumberOfSongsColumn.setText("Number of Songs");
        NumberOfSongsColumn.prefWidthProperty().set(64);
        NumberOfSongsColumn.setCellValueFactory(new PropertyValueFactory<>("NumberOfSongs"));

        TableColumn<Playlist, Button> OptionsColumn = new TableColumn<>();
        OptionsColumn.setText("Options");
        OptionsColumn.prefWidthProperty().set(47);
        OptionsColumn.setCellValueFactory(new PropertyValueFactory<>("button")); //change this later

        table.editableProperty().set(false);

        table.getColumns().addAll(NameColumn,CurrentlyPlayingColumn,NumberOfSongsColumn,OptionsColumn);
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
        playlistName = new TextField();
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
