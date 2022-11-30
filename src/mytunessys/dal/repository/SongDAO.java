package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.SongDAOException;
import mytunessys.dal.dbConnector.MSSQLConnection;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDAO {

    @Override
    public List<Object> getAllSongs() {
        List<Object> retrievedSongs = new ArrayList<>();
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "SELECT id,title,duration,artist,absolute_path,genre_id FROM song";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                retrievedSongs.add(instantiateSongObject(rs));
            }
        } catch (SQLException e) {
            throw new SongDAOException("Could not retrieve songs from database",e.getCause());
        }
        return retrievedSongs;
    }

    private Song instantiateSongObject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        String artist = rs.getString("artist");
        String absolutePath = rs.getString("absolute_path");
        Genre g = new Genre(300,"Pop"); // testing not real db
        return new Song(id,title,duration,artist,absolutePath,g);
    }

    @Override
    public void createSong(Song song) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "INSERT INTO song(title, duration, artist, absolute_path, genre_id) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getDuration());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getAbsolutePath());
            preparedStatement.setInt(5,song.getGenre().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSong(Song song) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "UPDATE song SET title = ?, duration = ?, artist = ?, absolute_path = ?, genre_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getDuration());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getAbsolutePath());
            preparedStatement.setInt(5,song.getGenre().getId());
            preparedStatement.setInt(6, song.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteSong(int id) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "DELETE FROM song WHERE(id=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
        }
}
