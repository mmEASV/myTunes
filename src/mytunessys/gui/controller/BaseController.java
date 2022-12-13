package mytunessys.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.utilities.AlertNotification;
import mytunessys.bll.utilities.MusicPlayer;
import mytunessys.gui.models.PlaylistModel;
import mytunessys.gui.models.SongModel;

/**
 * @author Bálint, Matej, Julian & Tomas
 */

public class BaseController implements Initializable {

    //region FXML
    @FXML
    public Button btnStartPlaylist;
    @FXML
    public Button btnShuffle;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Slider sldrVolume;
    @FXML
    private AnchorPane top;
    @FXML
    private AnchorPane contentWindow;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;

    // ----
    @FXML
    private TableView<Song> tbvContentTable;

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
    //region Variables
    private SongModel songModel;
    private PlaylistModel playlistModel;
    private SongController songCont;
    private PlaylistController playlistCont;
    private SongOnPlaylistController songOnPlaylistCont;
    private boolean songIsPlaying = false;
    private Timer timer;
    private TimerTask task;
    private boolean timerIsRunning;

    private final MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private MediaPlayer player;

    //endregion
    //region Interface Control
    public void updatePlayerUI(TableView<Song> songTableView) {
        lblNameOfSong.setText(songTableView.getItems().get(songTableView.getSelectionModel().getSelectedIndex()).getTitle());
        lblArtist.setText(songTableView.getItems().get(songTableView.getSelectionModel().getSelectedIndex()).getArtist());

        playSong(songTableView);

    }

    @FXML
    private void switchToSongInterface(ActionEvent actionEvent) throws Exception {
        ShowInterface(actionEvent, "Songs");
        showSearchBar();
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists.png")));
        btnStartPlaylist.setVisible(false);
        btnShuffle.setVisible(false);
        songCont.show(centerContent);

    }

    @FXML
    private void switchToPlaylistInterface(ActionEvent actionEvent) throws Exception {
        ShowInterface(actionEvent, "Playlists");
        showSearchBar();
        btnSongs.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Songs2.png")));
        btnPlaylists.setGraphic(new ImageView(new Image("mytunessys/gui/icons/Playlists2.png")));
        btnStartPlaylist.setVisible(true);
        btnShuffle.setVisible(true);
        btnShuffle.setDisable(true);
        playlistCont.show(centerContent);
    }

    public void switchToSongOnPlaylistInterface(ActionEvent actionEvent, Playlist playlist) throws Exception {
        ShowInterface(actionEvent, playlist.getPlaylistName());//implement playlist.getName() edited CSS, mention word-break and overflow-wrap
        hideSearchBar();
        btnStartPlaylist.setVisible(true);
        btnShuffle.setVisible(true);
        btnShuffle.setDisable(false);
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

    //endregion
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            this.songModel = new SongModel();
            this.playlistModel = new PlaylistModel();
            songCont = new SongController(contentWindow, songModel, this, playlistModel);
            playlistCont = new PlaylistController(contentWindow, playlistModel, this);
            songOnPlaylistCont = new SongOnPlaylistController(contentWindow, playlistModel, this);
            switchToSongInterface(new ActionEvent());
        } catch (Exception e) {
            AlertNotification.showAlertWindow(e.getMessage(), Alert.AlertType.ERROR);
        }
        btnGoBack.setVisible(false);

        btnPlay.setStyle("-fx-background-image: url('mytunessys/gui/icons/Play.png')");

        btnPlay.setOnAction(this::listener);
        btnNext.setOnAction(this::nextSong);
        btnPrevious.setOnAction(this::previousSong);
        btnGoBack.setVisible(false);
        setSearch();
        sldrVolume.setValue(musicPlayer.getVolume() * 100);
        sldrVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicPlayer.setVolume(sldrVolume.getValue() / 100);
            }
        });
    }

    /**
     * Starts timer to begin filling of progressbar to see song progress
     * boolean running is set to true
     */
    public void beginTimer() {
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                timerIsRunning = true;
                double current = musicPlayer.getMediaPlayer().getCurrentTime().toSeconds();
                double end = musicPlayer.getMediaPlayer().getTotalDuration().toSeconds();
                progressBar.setProgress(current / end);
                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * stops timer, used if song is finished or song is changed
     * boolean running is set to false
     */
    public void cancelTimer() {
        timerIsRunning = false;
        timer.cancel();
    }

    //region Play Controls
    public void playSong(TableView<Song> songTableView) {
        tbvContentTable = songTableView;

        musicPlayer.setSongs(songTableView);
        musicPlayer.setPath();

        if (!songIsPlaying) {
            songIsPlaying = true;

            btnPlay.setStyle("-fx-background-image: url('mytunessys/gui/icons/Pause.png')");

            musicPlayer.play();
            beginTimer();
            musicPlayer.setRepeat(true);

            musicPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
                if (songTableView.getSelectionModel().getSelectedIndex() < songTableView.getItems().size() - 1) {
                    songTableView.getSelectionModel().clearAndSelect(songTableView.getSelectionModel().getSelectedIndex() + 1);

                    lblNameOfSong.setText(songTableView.getSelectionModel().getSelectedItem().getTitle());
                    lblArtist.setText(songTableView.getSelectionModel().getSelectedItem().getArtist());
                    musicPlayer.play();
                } else {
                    musicPlayer.stop();
                    btnPlay.setStyle("-fx-background-image: url('mytunessys/gui/icons/Play.png')");
                    songIsPlaying = false;
                }
            });
        } else {
            musicPlayer.stop();
            songIsPlaying = false;
            playSong(songTableView);
        }
    }

    private void previousSong(ActionEvent actionEvent) {
        if (tbvContentTable.getSelectionModel().getSelectedIndex() > 0) {
            tbvContentTable.getSelectionModel().clearAndSelect(tbvContentTable.getSelectionModel().getSelectedIndex() - 1);

            lblNameOfSong.setText(tbvContentTable.getSelectionModel().getSelectedItem().getTitle());
            lblArtist.setText(tbvContentTable.getSelectionModel().getSelectedItem().getArtist());
        }
        playSong(tbvContentTable);
    }

    private void nextSong(ActionEvent actionEvent) {
        if (tbvContentTable.getSelectionModel().getSelectedIndex() < tbvContentTable.getItems().size() - 1) {
            tbvContentTable.getSelectionModel().clearAndSelect(tbvContentTable.getSelectionModel().getSelectedIndex() + 1);

            lblNameOfSong.setText(tbvContentTable.getSelectionModel().getSelectedItem().getTitle());
            lblArtist.setText(tbvContentTable.getSelectionModel().getSelectedItem().getArtist());
        }
        playSong(tbvContentTable);
    }
    //endregion

    public void listener(ActionEvent actionEvent) {
        player = musicPlayer.getMediaPlayer();

        if (player != null) {
            if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                player.pause();
                // we pause and change the button
                btnPlay.setStyle("-fx-background-image: url('mytunessys/gui/icons/Play.png')");
            }
            if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                // if pause we play again and return the button
                player.play();

                btnPlay.setStyle("-fx-background-image: url('mytunessys/gui/icons/Pause.png')");

            }
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
            songCont.newSong();

        else
            playlistCont.newPlaylist();
    }

    public void btnUpAction(ActionEvent actionEvent) {
        songOnPlaylistCont.moveUp();
    }

    public void btnDownAction(ActionEvent actionEvent) {
        songOnPlaylistCont.moveDown();
    }

    public void startPlaylist(ActionEvent actionEvent) {
        if (lblCurrentLocation.getText().equalsIgnoreCase("playlists")) {
            songOnPlaylistCont = new SongOnPlaylistController(contentWindow, playlistModel, this);
            int selectedRow = 0;

            try {
                songOnPlaylistCont.Show(centerContent, playlistCont.getTable().getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                playlistCont.show(centerContent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tbvContentTable = songOnPlaylistCont.getTable();
            tbvContentTable.getSelectionModel().clearAndSelect(selectedRow);
            lblArtist.setText(tbvContentTable.getSelectionModel().getSelectedItem().getArtist());
            lblNameOfSong.setText(tbvContentTable.getSelectionModel().getSelectedItem().getTitle());
            playSong(tbvContentTable);
        }
        else{
            playSong(songOnPlaylistCont.getTable());
        }

    }

    public void shuffleSongs(ActionEvent actionEvent) {
        songOnPlaylistCont.shuffleSongs();
    }
}
