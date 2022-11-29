package mytunessys.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;

public class BaseController implements Initializable {

    public Label lblNameOfSong;
    public Label lblArtist;
    public Button btnPrevious;
    public Button btnPlay;
    public Button btnNext;
    public Button btnSongs;
    public Button btnPlaylists;
    public Button btnAdd;
    public Button btnGoBack;
    public Label lblCurrentLocation;
    public TextField txfSearchBar;
    public AnchorPane centerContent;
    public ListView contentList;


    private void updateCurrentSongNameLabel(){
        //TODO display the song that is played currently on lblNameOfSong
        //update
    }

    private void updateArtistLabel(){
        //TODO display the artist for the song on lblArtist
    }

    private void goBackToMainMenu(){
        //TODO go back to the main menu with btnPrevious
    }

    @FXML
    private void switchToSongInterface(ActionEvent actionEvent){
        //TODO switch the ui to song with btnSongs

        List<Object> items = new ArrayList<>();
        items.add(new Song(1,"Song 1","","","",new Genre(1,"Name")));
        items.add(new Song(2,"Song 2","","","",new Genre(1,"Name")));
        items.add(new Song(3,"Song 3","","","",new Genre(1,"Name")));
        items.add(new Song(4,"Song 4","","","",new Genre(1,"Name")));
        ShowInterface(actionEvent,"Songs", items);
        //change list to display songs



        //change the btnGoBack



        //change the btnAdd



    }
    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent){
        List<Object> items = new ArrayList<>();
        items.add(new Playlist());
        ShowInterface(actionEvent,"Playlists",items);
        //TODO switch the ui to playlist with btnPlaylists
    }

    public void ShowInterface(ActionEvent actionEvent, String name, List<Object> items) {
        btnGoBack.setVisible(false);
        lblCurrentLocation.setText(name);
        ObservableList<Object> songs = FXCollections.observableArrayList();
        if(items != null)
            songs.addAll(items);
        contentList.setItems(songs);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        switchToSongInterface(new ActionEvent());
    }
}
