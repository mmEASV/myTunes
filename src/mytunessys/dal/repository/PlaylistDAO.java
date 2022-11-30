package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.dal.dbConnector.MSSQLConnection;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {

    public List<Playlist> getAllPlaylists() {
        List<Playlist> retrievedPlaylists = new ArrayList<>();
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "SELECT id, playlist_name FROM playlist";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                retrievedPlaylists.add(instantiatePlaylistObject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retrievedPlaylists;
    }

    @Override
    public void createPlaylist(String playlistName) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "INSERT INTO playlist(playlist_name) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlistName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlaylist(int id, String playlistName) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "UPDATE playlist SET playlist_name = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePlaylist(int id) {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "DELETE FROM playlist WHERE(id=?)";
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


    private Playlist instantiatePlaylistObject(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String playlistName = rs.getString("playlist_name");
        return new Playlist(id, playlistName);
    }


}
