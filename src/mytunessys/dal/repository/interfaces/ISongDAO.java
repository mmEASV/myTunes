package mytunessys.dal.repository.interfaces;

import mytunessys.be.Genre;
import mytunessys.be.Song;

import java.util.List;

public interface ISongDAO {

    /**
     *
     * @return List of song from database
     */
    List<Song> getAllSongs();

    /**
     *
     * @param title string given when creating new song
     * @param genre genre object with given id,name when creating new song
     * @param duration string given for song playtime
     * @param artist string artist name of the artist for new song
     * @param absolutePath string path where the song is stored locally
     */
    void createSong(String title, Genre genre, String duration, String artist, String absolutePath);

    /**
     *
     * @param id int given id for song that will be updated
     * @param title string of new given title for song
     * @param genre object with id,name for new creation of the song
     * @param duration new string given for song playtime
     * @param artist  string artist name  for song playtime
     * @param absolutePath string path where the song is stored locally
     */
    void updateSong(int id, String title,Genre genre,String duration,String artist,String absolutePath);

    /**
     * boolean method that deleted song with given id from the database
     * @param id int of the song that will be deleted from database
     * @return return true if song was deleted otherwise return false if failed
     */
    boolean deleteSong(int id);
}
