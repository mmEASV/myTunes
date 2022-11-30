package mytunessys.dal.repository.interfaces;

import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;

import java.util.List;

public interface IPlaylistDAO {
    /**
     *
     * @return List of playlists from database
     */
    List<Playlist> getAllPlaylists();

    /**
     *
     * @param playlistName string given when creating new playlist
     */
    void createPlaylist(String playlistName);

    /**
     *
     * @param id int given id for playlist that will be updated
     * @param playlistName string of new given name for playlist
     */
    void updatePlaylist(int id, String playlistName);

    /**
     * boolean method that deleted playlist with given id from the database
     * @param id int of the playlist that will be deleted from database
     * @return return true if playlist was deleted otherwise return false if failed
     */
    boolean deletePlaylist(int id);
}

