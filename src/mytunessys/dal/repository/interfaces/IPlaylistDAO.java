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
    List<Object> getAllPlaylists();

    /**
     * creates new playlist from object model playlist
     * @param playlist object
     */
    void createPlaylist(Playlist playlist);

    /**
     * updates playlist from object playlist
     * @param playlist object
     */
    void updatePlaylist(Playlist playlist);

    /**
     * boolean method that deleted playlist with given id from the database
     * @param id int of the playlist that will be deleted from database
     * @return return true if playlist was deleted otherwise return false if failed
     */
    boolean deletePlaylist(int id);
}

