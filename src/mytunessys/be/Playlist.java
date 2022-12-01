package mytunessys.be;

public class Playlist {

    private int id;

    private String playlistName;

    private int songAmount;

    public Playlist(int id, String playlistName, int songAmount) {
        this.id = id;
        this.playlistName = playlistName;
        this.songAmount = songAmount;
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
}
