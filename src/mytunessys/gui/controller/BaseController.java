package mytunessys.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.utilities.MusicPlayer;
import mytunessys.gui.models.PlaylistModel;
import mytunessys.gui.models.SongModel;

/**
 * @author Bálint, Matej & Tomas
 */

public class BaseController implements Initializable {

    //region FXML
    //TODO QUESTION: SHOULD BE ANNOTATED AS FXML ?

    public AnchorPane top;
    public AnchorPane contentWindow;
    @FXML
    public Button btnUp;
    @FXML
    public Button btnDown;

    // ----
    @FXML
    private TableView<Song> tbvContentTable;
    @FXML
    private TableColumn<Song, String> tbvCol1;
    @FXML
    private TableColumn<Song, String> tbvCol2;

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
    //endregion

    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();
    private SongController songCont;
    private PlaylistController playlistCont;
    private SongOnPlaylistController songOnPlaylistCont;
    private boolean songIsPlaying = false;

    MusicPlayer musicPlayer = MusicPlayer.getInstance();
    MediaPlayer player;

    public void updatePlayerUI(TableView<Song> songTableView) {
        lblNameOfSong.setText(songTableView.getItems().get(songTableView.getSelectionModel().getSelectedIndex()).getTitle());
        lblArtist.setText(songTableView.getItems().get(songTableView.getSelectionModel().getSelectedIndex()).getArtist());

        playSong(songTableView);

    }

    public void playSong(TableView<Song> songTableView) {

        musicPlayer.setSongs(songTableView);
        musicPlayer.setPath();

        if(!songIsPlaying){
            songIsPlaying = true;
            btnPlay.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Play.png")));//make pause button
            musicPlayer.play();
            musicPlayer.setRepeat(true);

            musicPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
                if (songTableView.getSelectionModel().getSelectedIndex() < songTableView.getItems().size() - 1) {
                    songTableView.getSelectionModel().clearAndSelect(songTableView.getSelectionModel().getSelectedIndex() + 1);

                    lblNameOfSong.setText(songTableView.getSelectionModel().getSelectedItem().getTitle());
                    lblArtist.setText(songTableView.getSelectionModel().getSelectedItem().getArtist());
                    musicPlayer.play();
                } else {
                    musicPlayer.stop();
                    songIsPlaying = false;
                }
            });
        }
        else
        {
            musicPlayer.stop();
            songIsPlaying = false;
            playSong(songTableView);
        }


    }

    @FXML
    private void switchToSongInterface(ActionEvent actionEvent) throws ApplicationException {
        ShowInterface(actionEvent, "Songs");
        showSearchBar();
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists.png")));
        songCont.Show(centerContent);

    }

    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent) throws ApplicationException {
        ShowInterface(actionEvent, "Playlists");
        showSearchBar();
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs2.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists2.png")));
        playlistCont.Show(centerContent);
    }

    public void switchToSongOnPlaylistInterface(ActionEvent actionEvent, Playlist playlist) throws ApplicationException {
        ShowInterface(actionEvent, playlist.getPlaylistName());//implement playlist.getName() edited CSS, mention word-break and overflow-wrap
        hideSearchBar();
        songOnPlaylistCont.Show(centerContent, playlist);
    }

    public void CleanCenterContent() {
        centerContent.getChildren().removeAll(centerContent.getChildren());
    }

    public void ShowInterface(ActionEvent actionEvent, String name) {
        CleanCenterContent();
        lblCurrentLocation.setText(name);
    }

    public void showSearchBar() {
        txfSearchBar.setVisible(true);
        btnDown.setVisible(false);
        btnUp.setVisible(false);
        btnAdd.setVisible(true);
    }

    public void hideSearchBar() {
        txfSearchBar.setVisible(false);
        btnDown.setVisible(true);
        btnUp.setVisible(true);
        btnAdd.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songCont = new SongController(contentWindow, songModel, this);
        playlistCont = new PlaylistController(contentWindow, playlistModel, this);
        songOnPlaylistCont = new SongOnPlaylistController(contentWindow, playlistModel, this);
        btnPlay.setOnAction(this::listener);
        btnNext.setOnAction(this::nextSong);
        btnPrevious.setOnAction(this::previousSong);
        btnGoBack.setVisible(false);
        setSearch();
        try {
            switchToSongInterface(new ActionEvent());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private void previousSong(ActionEvent actionEvent) {
        // do previous song
    }

    private void nextSong(ActionEvent actionEvent) {
        // do next song
    }

    public void listener(ActionEvent actionEvent) {
        player = musicPlayer.getMediaPlayer();
        if (player != null){
            if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                player.pause();
                // we pause and change the button
                btnPlay.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Close.png")));
            }
            if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                // if pause we play again and return the button
                player.play();
                btnPlay.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Play.png")));
            }
        }
        else
        {
            //TODO add play selected row from the tableView
        }

    }

    private void setSearch() {
        txfSearchBar.textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                if (lblCurrentLocation.getText().equals("Playlists")) { // not so type safe but works for now
                    playlistModel.searchPlaylist(newValue);
                } else {
                    songModel.searchSongs(newValue);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }

    public void NewItem(ActionEvent actionEvent) {
        if (lblCurrentLocation.getText().equals("Songs"))
            songCont.NewSong();
        else
            playlistCont.NewPlaylist();
    }

    public void btnUpAction(ActionEvent actionEvent) {
        songOnPlaylistCont.moveUp();
    }

    public void btnDownAction(ActionEvent actionEvent) {
        songOnPlaylistCont.moveDown();
    }
}
