package mytunessys.dal.repository.interfaces;

import mytunessys.be.Playlist;
import mytunessys.be.Song;

import java.util.List;

public interface ISongOnPlaylistDAO {
    /**
     *
     * @return
     */
    List<Object> getAllSongsOnPlaylist();

    /**
     *
     * @param song
     * @param playlist
     */
    void addSongToPlaylist(Song song, Playlist playlist);

    /**
     *
     * @param song
     * @param playlist
     * @return
     */
    boolean removeSongFromPlaylist(Song song,Playlist playlist);
}
