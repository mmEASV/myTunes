package mytunessys.dal.repository.interfaces;

import mytunessys.be.Genre;
import mytunessys.be.Song;

import java.util.List;

public interface ISongDAO {

    /**
     *
     * @return List of object parse as songs later from database
     */
    List<Object> getAllSongs();

    /**
     * creates new song in the database with given params
     * @param song object
     */
    void createSong(Song song);

    /**
     * updates song in the database from param object
     * @param song object
     */
    void updateSong(Song song);

    /**
     * boolean method that deleted song with given id from the database
     * @param id int of the song that will be deleted from database
     * @return return true if song was deleted otherwise return false if failed
     */
    boolean deleteSong(int id);
}
