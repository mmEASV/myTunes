package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDAO {

    public static void main(String[] args) throws CustomException {
        ISongDAO song = new SongDAO();
        song.getAllSongs();
    }

    @Override
    public List<Object> getAllSongs() throws CustomException {
        List<Object> retrievedSongs = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "SELECT s.id,s.title,s.duration,s.artist,s.absolute_path,s.genre_id,g.genre_name\n" +
                    "FROM song s JOIN genre g ON g.id = s.genre_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                retrievedSongs.add(instantiateSongObject(rs));
            }
        } catch (SQLException ex) {
            throw new CustomException("Could not retrieve song from database", ex.getCause());
        }
        return retrievedSongs;
    }

    private Song instantiateSongObject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        String artist = rs.getString("artist");
        String absolutePath = rs.getString("absolute_path");

        int genreId = rs.getInt("genre_id");
        String genreName = rs.getString("genre_name");
        Genre fetchedGenre = new Genre(genreId, genreName);
        return new Song(id, title, duration, artist, absolutePath, fetchedGenre);
    }

    @Override
    public void createSong(Song song) throws CustomException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "INSERT INTO song(title, duration, artist, absolute_path, genre_id) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getDuration());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getAbsolutePath());
            preparedStatement.setInt(5, song.getGenre().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new CustomException("Could not create song from database" + song.getId(), ex.getCause());
        }
    }

    @Override
    public void updateSong(Song song) throws CustomException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "UPDATE song SET title = ?, duration = ?, artist = ?, absolute_path = ?, genre_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getDuration());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getAbsolutePath());
            preparedStatement.setInt(5, song.getGenre().getId());
            preparedStatement.setInt(6, song.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new CustomException("Could not update song from database with Id: " + song.getId(), ex.getCause());

        }
    }

    @Override
    public boolean deleteSong(int id) throws CustomException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "DELETE FROM song WHERE(id=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new CustomException("Could not delete song from database with Id: " + id, ex.getCause());
        }
        return false;
    }
}
