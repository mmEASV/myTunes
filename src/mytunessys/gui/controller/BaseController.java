package mytunessys.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.gui.models.PlaylistModel;
import mytunessys.gui.models.SongModel;

/**
 * @author Bálint & Matej
 */

public class BaseController {

    @FXML
    private Label lblNameOfSong;
    @FXML
    private Label lblArtist;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnSongs;
    @FXML
    private Button btnPlaylists;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnGoBack;
    @FXML
    private Label lblCurrentLocation;
    @FXML
    private TextField txfSearchBar;
    @FXML
    private AnchorPane centerContent;
    @FXML
    protected ListView contentList;

    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();

    private void getAllPlaylists(){ //waiting on backend to implement Playlist
        //playlists = logicManager.getAllPlaylists();
    }

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

        contentList.setItems(songModel.getAllSongs());

        //change the btnGoBack



        //change the btnAdd



    }
    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent){
        ShowInterface(actionEvent,"Playlists");
        //TODO switch the ui to playlist with btnPlaylists

        contentList.setItems(playlistModel.getAllPlaylists());
    }

    public void ShowInterface(ActionEvent actionEvent,String name) {
        btnGoBack.setVisible(false);
        lblCurrentLocation.setText(name);

    }


}
