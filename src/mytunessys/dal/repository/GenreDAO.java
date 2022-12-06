package mytunessys.dal.repository;


import mytunessys.be.Genre;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.mappers.GenreMapper;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements IGenreDAO {

    @Override
    public List<Genre> getAllGenre() throws ApplicationException {
        GenreMapper mapper = new GenreMapper();
        List<Genre> retrievedGenreList = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "SELECT id,genre_name FROM genre";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Genre genre = mapper.mapGenre(rs);
                retrievedGenreList.add(genre);
            }
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(),ex.getCause());
        }
        return retrievedGenreList;
    }

}
