package mytunessys.dal.mappers;

import mytunessys.be.Genre;
import mytunessys.be.Song;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Tomas Simko
 */
public class SongMapper {

    private Song song;

    public SongMapper() {
        this.song = null;
    }

    /**
     * maps result set into object for song
     *
     * @param rs that will be mapped to object
     * @throws SQLException happens cannot get right values from table
     **/
    public void mapSong(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        String artist = rs.getString("artist");
        String absolutePath = rs.getString("absolute_path");

        int genreId = rs.getInt("genre_id");
        String genreName = rs.getString("genre_name");
        Genre fetchedGenre = new Genre(genreId, genreName);
        this.song = new Song(id, title, duration, artist, absolutePath, fetchedGenre);
    }

    public Song getSong() {
        return this.song;
    }
}