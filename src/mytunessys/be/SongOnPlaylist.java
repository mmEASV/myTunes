package mytunessys.be;

public class SongOnPlaylist {
    private Song song;
    private Playlist playlist;

    public SongOnPlaylist(Song song, Playlist playlist) {
        this.song = song;
        this.playlist = playlist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

}
