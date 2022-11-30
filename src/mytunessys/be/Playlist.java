package mytunessys.be;

public class Playlist {

    private int id;

    private String playlistName;

    public Playlist(int id, String playlistName) {
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
}
