package mytunessys.dal.repository;

import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.mappers.SongMapper;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDAO {
    private PreparedStatement preparedStatement;
    @Override
    public List<Song> getAllSongs() throws ApplicationException {
        SongMapper mapper = new SongMapper();
        List<Song> retrievedSongs = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "SELECT s.id,s.title,s.duration,s.artist,s.absolute_path,s.genre_id,g.genre_name\n" +
                    "FROM song s JOIN genre g ON g.id = s.genre_id";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Song mappedSong = mapper.mapSong(rs);
                retrievedSongs.add(mappedSong);
            }
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
        return retrievedSongs;
    }

    @Override
    public void createSong(Song song) throws ApplicationException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "INSERT INTO song(title, duration, artist, absolute_path, genre_id) VALUES(?,?,?,?,?)";
            prepareData(song, connection, sql);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public void updateSong(Song song) throws ApplicationException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "UPDATE song SET title = ?, duration = ?, artist = ?, absolute_path = ?, genre_id = ? WHERE id = ?";
            prepareData(song, connection, sql);
            preparedStatement.setInt(6, song.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());

        }
    }

    private void prepareData(Song song, Connection connection, String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, song.getTitle());
        preparedStatement.setString(2, song.getDuration());
        preparedStatement.setString(3, song.getArtist());
        preparedStatement.setString(4, song.getAbsolutePath());
        preparedStatement.setInt(5, song.getGenre().getId());
    }

    @Override
    public boolean deleteSong(int id) throws ApplicationException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "DELETE FROM song WHERE(id=?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
        return false;
    }
}
