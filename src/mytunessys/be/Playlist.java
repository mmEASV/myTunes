package mytunessys.be;

import java.util.HashMap;

public class Playlist {

    private int id;

    private String playlistName;

    private int songAmount;

    private HashMap<Integer, Song> songList;

    public Playlist(int id, String playlistName, int amount) {
        this.id = id;
        this.playlistName = playlistName;
        this.songAmount = amount;
    }

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
    }

    public Playlist(int id, String playlistName, HashMap<Integer, Song> songz) {
        this.id = id;
        this.playlistName = playlistName;
        this.songList = songz;
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
        return this.songAmount;
    }

    public void setSongAmount(int songAmount) {
        this.songAmount = songAmount;
    }

    public HashMap<Integer, Song> getSongList() {
        return songList;
    }

    public void setSongList(HashMap<Integer, Song> list) {
        this.songList = list;
    }

    @Override
    public String toString() {
        return this.playlistName;
    }
}
