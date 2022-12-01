package mytunessys.dal.repository.interfaces;

import mytunessys.be.Song;
import mytunessys.bll.exceptions.CustomException;

import java.util.List;

public interface ISongDAO {

    /**
     * Gets all the song from database of all songs that
     * are currently stored in the database
     *
     * @return List of object parse as songs later from database
     * @throws CustomException when data are not able to be retrieved with message and cause
     */
    List<Object> getAllSongs() throws CustomException;

    /**
     * Creates given song into the database
     *
     * @param song object
     * @throws CustomException when data are not able to be deleted with message and cause
     */
    void createSong(Song song) throws CustomException;

    /**
     * Updates given song in the database
     *
     * @param song object
     * @throws CustomException when data are not able to be updated with message and cause
     */
    void updateSong(Song song) throws CustomException;

    /**
     * Delete method that deletes song with given id from the database
     *
     * @param id int of the song that will be deleted from database
     * @return return true if song was deleted otherwise return false if failed
     * @throws CustomException when data are not able to be deleted with message and cause
     */
    boolean deleteSong(int id) throws CustomException;
}
