package mytunessys.be;

public class SongOnPlaylist {
    private Song song;
    private Playlist playlist;

    private int order;

    public SongOnPlaylist(Song song, Playlist playlist,int order) {
        this.song = song;
        this.playlist = playlist;
        this.order = order;
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

    public int getOrder(){
        return this.order;
    }
    public void setSong(int order) {
        this.order = order;
    }
}
