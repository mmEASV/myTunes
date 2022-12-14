package mytunessys.dal.repository.interfaces;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import java.util.List;
import java.util.Optional;

public interface IPlaylistDAO {
    /**
     * Method that gets all playlists
     *
     * @return List of playlists from database
     * @throws Exception if unexpected problem occurred
     */
    List<Playlist> getAllPlaylists() throws Exception;

    /**
     * Method that creates new playlist from object model playlist
     *
     * @param playlist that will be created
     * @throws Exception if unexpected problem occurred
     */
    void createPlaylist(Playlist playlist) throws Exception;

    /**
     * Method that will get playlist by its id
     * @param playlist that contains its id that will be retrieved
     * @return playlist object that is found
     * @throws Exception if unexpected problem occurred
     */
    Optional<Playlist> getPlaylistById(Playlist playlist) throws Exception;

    /**
     * Method that add song to its playlist
     * @param song that contains song id that is needed for adding to db
     * @param playlist that contains playlist id that is needed for adding to db
     * @return false not able to add song to playlist
     * @throws Exception when data are not able to be added with message and cause
     */
    boolean addSongToPlaylist(Song song, Playlist playlist) throws Exception;

    /**
     * Updates playlist from object playlist
     *
     * @param playlist object
     * @throws Exception when data are not able to be updated with message and cause
     */
    void updatePlaylist(Playlist playlist) throws Exception;

    /**
     * Method that deleted playlist with given id from the database
     *
     * @param id int of the playlist that will be deleted from database
     * @return return true if playlist was deleted otherwise return false if failed
     * @throws Exception when data are not able to be deleted with message and cause
     */
    boolean deletePlaylist(int id) throws Exception;

    /**
     * Method to remove song from its playlist
     *
     * @param playlist that contains song that will be removed
     * @return false if not able to delete
     * @throws Exception if unexpected problem occurred
     */
    boolean removeSongFromPlaylist(Playlist playlist) throws Exception;
}