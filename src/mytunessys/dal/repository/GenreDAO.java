package mytunessys.dal.repository;


import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements IGenreDAO {

    @Override
    public List<Object> getAllGenre() throws CustomException {
        List<Object> retrievedGenreList = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "SELECT id,genre_name FROM genre";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                retrievedGenreList.add(instantiateGenreObject(rs));
            }
        } catch (SQLException ex) {
            throw new CustomException("Could not retrieve playlists from database",ex.getCause());
        }
        return retrievedGenreList;
    }
    private Genre instantiateGenreObject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String playlistName = rs.getString("genre_name");
        return new Genre(id, playlistName);
    }
}
