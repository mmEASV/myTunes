package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.PlaylistDAOException;
import mytunessys.dal.dbConnector.MSSQLConnection;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {

    public List<Object> getAllPlaylists() {
        List<Object> retrievedPlaylists = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "SELECT id, playlist_name FROM playlist";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                retrievedPlaylists.add(instantiatePlaylistObject(rs));
            }
        } catch (SQLException e) {
            throw new PlaylistDAOException("Could not retrieve playlists from database",e.getCause());
        }
        return retrievedPlaylists;
    }

    @Override
    public void createPlaylist(Playlist playlist) {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "INSERT INTO playlist(playlist_name) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "UPDATE playlist SET playlist_name = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePlaylist(int id) {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "DELETE FROM playlist WHERE(id=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private Playlist instantiatePlaylistObject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String playlistName = rs.getString("playlist_name");
        return new Playlist(id, playlistName);
    }


}
