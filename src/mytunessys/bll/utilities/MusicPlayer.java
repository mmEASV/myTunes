package mytunessys.bll.utilities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
    private static MusicPlayer _singleInstance = null;
    private MediaPlayer _mediaPlayer;
    private Media _media;

    private MusicPlayer(){
        this._mediaPlayer = null;
        this._media = null;
    }

    public static MusicPlayer getInstance(){
        if(_singleInstance == null){
            _singleInstance = new MusicPlayer();
        }
        return _singleInstance;
    }

    public void setPath(String path){
        this._media = new Media(path);
        this._mediaPlayer = new MediaPlayer(_media);
    }

    public void play(){
        this._mediaPlayer.play();
    }
    public void pause(){
        this._mediaPlayer.pause();
    }
}
