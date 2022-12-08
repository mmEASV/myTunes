package mytunessys.bll.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunessys.be.Song;

public class MusicPlayer {
    private static MusicPlayer instance = null;
    private MediaPlayer mediaPlayer;
    private double volume;
    private String path;

    private TableView<Song> songTable;

    private int row;


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

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public void play() {

        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.pause();
        }
        try {
            Song song = null;
            if(songTable != null){

                row = songTable.getSelectionModel().getSelectedIndex(); // 3
                song = songTable.getItems().get(row);
                this.mediaPlayer = new MediaPlayer(new Media(song.getAbsolutePath()));
                mediaPlayer.play();
                mediaPlayer.setVolume(volume);


                // needs to be tested
                mediaPlayer.setOnEndOfMedia(() -> {
                    if(songTable.getItems().size() >= row){
                        songTable.getSelectionModel().clearAndSelect(row + 1);
                        mediaPlayer.play();
                    } else {
                        mediaPlayer.stop();
                    }
                });
                // once song is done playing check if size is not out of bound and stop playing

            }
        } catch (UnsupportedOperationException exception) {
            AlertNotification.showAlertWindow(exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void setPath(String absolutePath) {
        this.path = absolutePath;
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
