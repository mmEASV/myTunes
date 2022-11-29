package mytunessys.gui.controller;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mytunessys.be.Playlist;
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

    private List<Song> songs;
    private List<Playlist> playlists;

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

    private void switchToSongInterface(){
        //TODO switch the ui to song with btnSongs

        //change list to display songs



        //change the btnGoBack



        //change the btnAdd



    }

    private void switchToPlaylistInterface(){
        //TODO switch the ui to playlist with btnPlaylists
    }

}
