package mytunessys.dal.mappers;

import mytunessys.be.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper {
    private Genre genre;
    public GenreMapper(){
        this.genre = null;
    }

    public Genre mapGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String genreName = rs.getString("genre_name");
        return new Genre(id, genreName);
    }
    public Genre getGenre(){
        return genre;
    }
}
