package mytunessys.dal.mappers;

import mytunessys.be.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Simko
 */

public class GenreMapper {
    private Genre genre;

    public GenreMapper() {
        this.genre = null;
    }

    /**
     * maps result set into object for genre
     *
     * @param rs that will be mapped to object
     * @throws SQLException happens cannot get right values from table
     **/
    public void mapGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String genreName = rs.getString("genre_name");
        this.genre = new Genre(id, genreName);
    }

    public Genre getGenre() {
        return this.genre;
    }
}
