package mytunessys.dal.mappers;

import mytunessys.be.Genre;
import mytunessys.be.Song;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SongMapper {

    private Song song;
    public SongMapper(){
        this.song = null;
    }

    public Song mapSong(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        String artist = rs.getString("artist");
        String absolutePath = rs.getString("absolute_path");

        int genreId = rs.getInt("genre_id");
        String genreName = rs.getString("genre_name");
        Genre fetchedGenre = new Genre(genreId, genreName);
        song = new Song(id, title, duration, artist, absolutePath, fetchedGenre);
        return song;
    }
    public Song getSong(){
        return song;
    }
}
