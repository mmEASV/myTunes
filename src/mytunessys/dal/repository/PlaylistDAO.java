package mytunessys.dal.repository;

import mytunessys.be.Playlist;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {
    private PreparedStatement preparedStatement;

    public List<Object> getAllPlaylists() throws CustomException {
        List<Object> retrievedPlaylists = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "SELECT Count(ps.song_id) as amount,p.id,p.playlist_name FROM playlist p JOIN playlist_song ps on p.id = ps.playlist_id\n" +
                    "GROUP BY p.playlist_name, p.id";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                retrievedPlaylists.add(instantiatePlaylistObject(rs));
            }
        } catch (SQLException ex) {
            throw new CustomException("Could not retrieve playlists from database", ex.getCause());
        }
        return retrievedPlaylists;
    }

    @Override
    public void createPlaylist(Playlist playlist) throws CustomException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "INSERT INTO playlist(playlist_name) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new CustomException("Could not create playlists to database with Id: " + playlist.getId(), ex.getCause());
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws CustomException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "UPDATE playlist SET playlist_name = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new CustomException("Could not update playlists from database with Id: " + playlist.getId(), ex.getCause());
        }
    }

    @Override
    public boolean deletePlaylist(int id) throws CustomException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "DELETE FROM playlist WHERE(id=?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new CustomException("Could not delete playlists from database with Id: " + id , ex.getCause());
        }
        return false;
    }

    private Playlist instantiatePlaylistObject(ResultSet rs) throws SQLException {
        int amount = rs.getInt("amount");
        int id = rs.getInt("id");
        String playlistName = rs.getString("playlist_name");
        return new Playlist(id, playlistName, amount);
    }


}
