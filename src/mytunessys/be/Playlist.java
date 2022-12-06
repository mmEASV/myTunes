package mytunessys.be;

import java.util.HashMap;
import java.util.List;

public class Playlist {

    private int id;

    private String playlistName;

    private int songAmount;

    private HashMap<Integer,Song> songList;

    public Playlist(int id, String playlistName, int songAmount,HashMap<Integer,Song> songz) {
        this.id = id;
        this.playlistName = playlistName;
        this.songAmount = songAmount;
        this.songList = songz;
    }
    public Playlist(int id,String playlistName){
        this.id = id;
        this.playlistName = playlistName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getSongAmount() {
        return songAmount;
    }

    public void setSongAmount(int songAmount) {
        this.songAmount = songAmount;
    }

    public HashMap<Integer,Song> getSongList() {
        return songList;
    }
}
