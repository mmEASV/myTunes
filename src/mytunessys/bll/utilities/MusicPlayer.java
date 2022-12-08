package mytunessys.bll.utilities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
    private static MusicPlayer instance = null;
    private MediaPlayer mediaPlayer;
    private Media media;

    private double volume;

    private MusicPlayer(){
        this.mediaPlayer = null;
        this.media = null;
        this.volume = 30;
    }

    public static MusicPlayer getInstance(){
        MusicPlayer result = instance;
        if(instance == null){
            synchronized (MusicPlayer.class){
                result= instance;
                if(result == null){
                  instance = result = new MusicPlayer();
                }
            }
        }
        return instance;
    }

    public void setPath(String path){
        this.media = new Media(path);
        this.mediaPlayer = new MediaPlayer(media);
    }

    public void play(){
        this.mediaPlayer.play();
    }
    public void pause(){
        this.mediaPlayer.pause();
    }
}
