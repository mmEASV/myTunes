package mytunessys.dal.repository.interfaces;

import mytunessys.be.Playlist;
import mytunessys.bll.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistDAO {
    /**
     * Gets all playlists from database
     *
     * @return List of playlists from database
     * @throws ApplicationException when data are not able to be updated with message and cause
     */
    List<Object> getAllPlaylists() throws ApplicationException;

    /**
     * Creates new playlist from object model playlist
     *
     * @param playlist object
     * @throws ApplicationException when data are not able to be updated with message and cause
     */
    void createPlaylist(Playlist playlist) throws ApplicationException;

    /**
     * Updates playlist from object playlist
     *
     * @param playlist object
     * @throws ApplicationException when data are not able to be updated with message and cause
     */
    void updatePlaylist(Playlist playlist) throws ApplicationException;

    /**
     * Boolean method that deleted playlist with given id from the database
     *
     * @param id int of the playlist that will be deleted from database
     * @return return true if playlist was deleted otherwise return false if failed
     * @throws ApplicationException when data are not able to be updated with message and cause
     */
    boolean deletePlaylist(int id) throws ApplicationException;
}

