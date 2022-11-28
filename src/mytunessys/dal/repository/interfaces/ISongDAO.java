package mytunessys.dal.repository.interfaces;

import mytunessys.be.Genre;
import mytunessys.be.Song;

import java.util.List;

public interface ISongDAO {
    List<Song> getAllSongs();
    void createSong(String title, Genre genre, String duration, String artist, String absolutePath);
    void updateSong(int id, String title,Genre genre,String duration,String artist,String absolutePath);
    boolean deleteSong(int id);
}
