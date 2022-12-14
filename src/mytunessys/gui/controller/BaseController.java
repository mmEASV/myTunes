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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
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
    private Slider progressBar;
    @FXML
    private Label lblCurrentDuration;
    @FXML
    private Label lblTotalDuration;
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
    private Duration duration;
    private boolean isDragging;

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

        if(songOnPlaylistCont.getPlaylistChanged()){
            songOnPlaylistCont.savePlaylistState();
            songOnPlaylistCont.setPlaylistChanged(false);
        }


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

        if(songOnPlaylistCont.getPlaylistChanged()){
            songOnPlaylistCont.savePlaylistState();
            songOnPlaylistCont.setPlaylistChanged(false);
        }

        playlistCont.show(centerContent);
    }

    public void switchToSongOnPlaylistInterface(ActionEvent actionEvent, Playlist playlist) throws Exception {
        ShowInterface(actionEvent, playlist.getPlaylistName());
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
            btnShuffle.getStyleClass().add("submit-Button");
            btnStartPlaylist.getStyleClass().add("submit-Button");
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
     * change listener to update progress bar's max value to the total duration of the song
     * and its current value to the current time in the song
     *
     * also updates the current time label to show user where they are in the song in mm:ss
     */
    public void updateProgressBar(){
        musicPlayer.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {

            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                double current = musicPlayer.getMediaPlayer().getCurrentTime().toSeconds();
                double end = musicPlayer.getMediaPlayer().getTotalDuration().toSeconds();

                progressBar.setMax(end);
                if(!isDragging) {
                    progressBar.setValue(current);
                }
                String currentTime = formatTime(current);
                lblCurrentDuration.setText(currentTime);
                lblTotalDuration.setText(formatTime(end));
            }
        });
    }

    /**
     * formats double value of seconds to a mm:ss format
     *
     * @param time double value given from media's current time or total duration
     * @return
     */
    public String formatTime(Double time){
        Double minutes;
        Double seconds;
        minutes = time/60;
        seconds = time%60;
        String stringSeconds = "" + seconds;

        stringSeconds = stringSeconds.substring(0,2);


        if(stringSeconds.contains(".")){
            stringSeconds = "0" +stringSeconds.substring(0,1);
        }
        return minutes.intValue() + ":" + stringSeconds;
    }

    /**
     * sets volume of media to value of volume slider
     * formats total duration label to total duration of song in mm:ss
     */

    public void setVolumeAndTotalDuration(){
        Double duration = musicPlayer.getMediaPlayer().getTotalDuration().toSeconds();
        String stringDuration = formatTime(duration);
        musicPlayer.setVolume(sldrVolume.getValue()/100);
    }

    /**
     * switches isDragging boolean to true to stop auto-updating of progress bar
     * @param mouseEvent
     */
    public void onMousePressed(MouseEvent mouseEvent){
        if(!isDragging) {
            isDragging = true;
        }
    }

    /**
     * updates current song position to value of progress bar that the mouse released its click on
     * changes isDragging boolean back to false
     * @param mouseEvent
     */
    public void onMouseRelease(MouseEvent mouseEvent) {
        if(isDragging){
            updateSongTime();
            isDragging = false;
        }
    }

    /**
     * updates the time that the song is playing at to the value that the progress bar is at
     */
    public void updateSongTime(){
        duration = Duration.seconds(progressBar.getValue());
        musicPlayer.getMediaPlayer().seek(duration);
    }


    //region Play Controls

    /**
     * Plays all the songs on the current TableView until it reaches the end or gets a new table.
     * @param songTableView Must be a Song TableView.
     */
    public void playSong(TableView<Song> songTableView) {
        tbvContentTable = songTableView;

        musicPlayer.setSongs(songTableView);
        musicPlayer.setPath();


        if (!songIsPlaying) {
            songIsPlaying = true;

            btnPlay.setStyle("-fx-background-image: url('mytunessys/gui/icons/Pause.png')");

            musicPlayer.play();
            musicPlayer.setRepeat(true);
                if(!isDragging) {
                    updateProgressBar();
                }
            setVolumeAndTotalDuration();
            musicPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
                if (songTableView.getSelectionModel().getSelectedIndex() < songTableView.getItems().size() - 1) {
                    songTableView.getSelectionModel().clearAndSelect(songTableView.getSelectionModel().getSelectedIndex() + 1);

                    lblNameOfSong.setText(songTableView.getSelectionModel().getSelectedItem().getTitle());
                    lblArtist.setText(songTableView.getSelectionModel().getSelectedItem().getArtist());
                    musicPlayer.play();
                    if(!isDragging) {
                        updateProgressBar();
                    }
                    setVolumeAndTotalDuration();
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
            updateProgressBar();
        }
    }

    /**
     * Plays the previous song on the active playlist (not the displayed one).
     * @param actionEvent
     */
    private void previousSong(ActionEvent actionEvent) {
        if (tbvContentTable.getSelectionModel().getSelectedIndex() > 0) {
            tbvContentTable.getSelectionModel().clearAndSelect(tbvContentTable.getSelectionModel().getSelectedIndex() - 1);

            lblNameOfSong.setText(tbvContentTable.getSelectionModel().getSelectedItem().getTitle());
            lblArtist.setText(tbvContentTable.getSelectionModel().getSelectedItem().getArtist());
        }

        playSong(tbvContentTable);
    }

    /**
     * Plays the next song on the active playlist (not the displayed one).
     * @param actionEvent
     */
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

    /**
     * Starts playing the playlist without displaying the playlist for the user.
     * @param actionEvent
     */
    public void startPlaylist(ActionEvent actionEvent) {
        if (lblCurrentLocation.getText().equalsIgnoreCase("playlists")) {
            songOnPlaylistCont = new SongOnPlaylistController(contentWindow, playlistModel, this);
            int selectedRow = playlistCont.getTable().getSelectionModel().getSelectedIndex();;

            try {
                songOnPlaylistCont.Show(centerContent, playlistCont.getTable().getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                playlistCont.show(centerContent);
                playlistCont.getTable().getSelectionModel().clearAndSelect(selectedRow);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tbvContentTable = songOnPlaylistCont.getTable();
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
