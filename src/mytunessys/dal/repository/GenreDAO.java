package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.connectionFactory.IConnectionFactory;
import mytunessys.dal.connectionFactory.ConnectionFactory;
import mytunessys.dal.mappers.GenreMapper;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomas, Julian
 */

public class GenreDAO implements IGenreDAO {

    IConnectionFactory mssqlFactory;

    public GenreDAO() throws Exception {
        this.mssqlFactory = ConnectionFactory.getFactory(DatabaseType.MSSQL);
    }

    @Override
    public List<Genre> getAllGenre() throws Exception {
        GenreMapper mapper = new GenreMapper();
        List<Genre> retrievedGenreList = new ArrayList<>();
        try (Connection connection = mssqlFactory.createConnection()) {
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
