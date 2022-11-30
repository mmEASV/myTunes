package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.exceptions.SongDAOException;
import mytunessys.dal.dbConnector.MSSQLConnection;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongOnPlaylistDAO implements ISongOnPlaylistDAO {

    @Override
    public List<Object> getAllSongsOnPlaylist() {
        List<Object> retrievedSongOnPlaylist = new ArrayList<>();
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "SELECT s.id,s.title,s.duration,s.artist,s.absolute_path,s.genre_id,p.id AS playlist_id,p.playlist_name\n" +
                    "FROM song s\n" +
                    "JOIN playlist_song pl ON s.id = pl.song_id\n" +
                    "JOIN playlist p ON pl.playlist_id = p.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                retrievedSongOnPlaylist.add(instantiateSongOnPlaylist(rs));
            }
        } catch (SQLException e) {
            throw new SongDAOException("Could not retrieve songs on playlist from database",e.getCause());
        }
        return retrievedSongOnPlaylist;
    }

    private SongOnPlaylist instantiateSongOnPlaylist(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        String artist = rs.getString("artist");
        String absolutePath = rs.getString("absolute_path");

        // Playlist
        int genreId = rs.getInt("genre_id");
        int playListId = rs.getInt("playlist_id");
        String playlistName = rs.getString("playlist_name");

        return new SongOnPlaylist(new Song(id,title,duration,artist,absolutePath,new Genre(genreId,"Pop")),new Playlist(playListId,playlistName));
    }

    @Override
    public void addSongToPlaylist(Song song, Playlist playlist) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "INSERT INTO playlist_song(song_id,playlist_id) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeSongFromPlaylist(Song song, Playlist playlist) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "DELETE FROM playlist_song WHERE song_id = ? AND playlist_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            int result = preparedStatement.executeUpdate();
            if(result > 0)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
