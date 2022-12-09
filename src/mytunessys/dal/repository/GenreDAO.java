package mytunessys.dal.repository;


import mytunessys.be.Genre;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.mappers.GenreMapper;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas,Julian
 */

public class GenreDAO implements IGenreDAO {

    private final MSSQLConnection mssqlConnection;

    public GenreDAO() throws Exception{
        this.mssqlConnection = new MSSQLConnection();
    }

    @Override
    public List<Genre> getAllGenre() throws Exception {
        GenreMapper mapper = new GenreMapper();
        List<Genre> retrievedGenreList = new ArrayList<>();
        try (Connection connection = mssqlConnection.createConnection()) {
            String sql = "SELECT id,genre_name FROM genre";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                mapper.mapGenre(rs);
                retrievedGenreList.add(mapper.getGenre());
            }
        }
        return retrievedGenreList;
    }

}
