package mytunessys.bll.utilities;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunessys.be.Song;
import mytunessys.gui.controller.BaseController;

public class MusicPlayer {
    private static MusicPlayer instance = null;
    private MediaPlayer mediaPlayer;
    private double volume;
    private String path;

    private TableView<Song> songTable;

    private int row;
    private boolean repeat = false;

    //    private final int PREVIOUS_SONG = -1;
    //    private final int CURRENT_SONG = 0;
    //    private final int NEXT_SONG = 1;
    //    public final int NORMAL_MODE = 0;

    private MusicPlayer() {
        this.volume = 20;
        this.path = "";
        this.songTable = new TableView<>();

    }

    public static MusicPlayer getInstance() {
        MusicPlayer result = instance;
        if (instance == null) {
            synchronized (MusicPlayer.class) {
                result = instance;
                if (result == null) {
                    instance = result = new MusicPlayer();
                }
            }
        }
        return instance;
    }

    public boolean getRepeat(){
        return repeat;
    }

    public void setRepeat(boolean repeat){
        this.repeat = repeat;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public void play() {

        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.pause();
        }
        try {
            Song song = null;

            row = songTable.getSelectionModel().getSelectedIndex();
            song = songTable.getItems().get(row);
            this.mediaPlayer = new MediaPlayer(new Media(song.getAbsolutePath()));
            mediaPlayer.play();
            mediaPlayer.setVolume(volume);


        } catch (UnsupportedOperationException exception) {
            AlertNotification.showAlertWindow(exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void setPath() {
        this.path = songTable.getSelectionModel().getSelectedItem().getAbsolutePath();
    }

    public void pause() {
        if (mediaPlayer.getMedia() != null)
            this.mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer.getMedia() != null)
            this.mediaPlayer.stop();
    }

    public MediaPlayer.Status getState() {
        if (mediaPlayer.getMedia() != null) {
            return this.mediaPlayer.getStatus();
        }
        return MediaPlayer.Status.STOPPED;
    }

    public void setSongs(TableView<Song> songTableView) {
        this.songTable = songTableView;
    }
}
