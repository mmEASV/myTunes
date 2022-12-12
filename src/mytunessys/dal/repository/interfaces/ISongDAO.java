package mytunessys.dal.repository.interfaces;

import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.exceptions.ConnectionExceptions;

import java.util.List;

public interface ISongDAO {

    /**
     * Gets all the song from database of all songs that
     * are currently stored in the database
     *
     * @return List of object parse as songs later from database
     * @throws Exception when data are not able to be retrieved with message and cause
     */
    List<Song> getAllSongs() throws Exception;

    /**
     * Creates given song into the database
     *
     * @param song that will be created
     * @throws Exception when data are not able to be deleted with message and cause
     */
    void createSong(Song song) throws Exception;

    /**
     * Updates given song in the database
     *
     * @param song that contains its id and will be updated
     * @throws Exception when data are not able to be updated with message and cause
     */
    void updateSong(Song song) throws Exception;

    /**
     * Delete method that deletes song with given id from the database
     *
     * @param id int of the song that will be deleted from database
     * @return return true if song was deleted otherwise return false if failed
     * @throws Exception when data are not able to be deleted with message and cause
     */
    boolean deleteSong(int id) throws Exception;
}