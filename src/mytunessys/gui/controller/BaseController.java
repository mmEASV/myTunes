package mytunessys.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import mytunessys.be.Song;

public class BaseController {

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
        ShowInterface(actionEvent,"Songs");
        //TODO switch the ui to song with btnSongs

        //change list to display songs



        //change the btnGoBack



        //change the btnAdd



    }
    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent){
        ShowInterface(actionEvent,"Playlists");
        //TODO switch the ui to playlist with btnPlaylists
    }

    public void ShowInterface(ActionEvent actionEvent,String name) {
        btnGoBack.setVisible(false);
        lblCurrentLocation.setText(name);

    }
}
