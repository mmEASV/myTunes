package mytunessys.dal.repository.interfaces;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.CustomException;

import java.util.List;

public interface ISongOnPlaylistDAO {
    /**
     * Gets all the song on playlist from database
     *
     * @return list of objects song that are on playlist from SongOnPlaylistDAO
     * @throws CustomException when data are not able to be retrieved with message and cause
     */
    List<Object> getAllSongsOnPlaylist() throws CustomException;

    /**
     * Adds given song to be added into the database table
     *
     * @param song     object that needs to be added to the given playlist
     * @param playlist object that given song will be added onto
     * @throws CustomException when data are not able to be added with message and cause
     */
    void addSongToPlaylist(Song song, Playlist playlist) throws CustomException;

    /**
     * Removed given song from specific playlist
     *
     * @param song     that will be removed from database
     * @param playlist that given song will be removed from
     * @return true if successfully deleted from database otherwise return false
     * @throws CustomException when data are not able to be deleted with message and cause
     */
    boolean removeSongFromPlaylist(Song song, Playlist playlist) throws CustomException;
}
