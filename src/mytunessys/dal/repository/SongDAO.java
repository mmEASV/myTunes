package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Song;
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
    public List<Song> getAllSongs() {
        List<Song> retrievedSongs = new ArrayList<>();
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "SELECT id,title,duration,artist,absolute_path,genre_id FROM song";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                retrievedSongs.add(instantiateSongObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public void createSong(String title, Genre genre, String duration, String artist, String absolutePath) {
        // Do jdbc implementation
    }

    @Override
    public void updateSong(int id, String title, Genre genre, String duration, String artist, String absolutePath) {
        // Do jdbc implementation
    }

    @Override
    public boolean deleteSong(int id) {
        // Do jdbc implementation
        return false;
    }
}
