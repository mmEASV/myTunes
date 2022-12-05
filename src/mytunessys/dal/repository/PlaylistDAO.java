package mytunessys.dal.repository;

import mytunessys.be.Playlist;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.mappers.PlaylistMapper;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {
    private PreparedStatement preparedStatement;

    public List<Object> getAllPlaylists() throws ApplicationException {
        PlaylistMapper playlistMapper = new PlaylistMapper();
        List<Object> retrievedPlaylists = new ArrayList<>();
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = """
                    SELECT COALESCE(count(ps.song_id), 0) as amount,
                     p.id,
                     p.playlist_name
                    FROM playlist p
                         LEFT JOIN playlist_song ps
                              ON p.id = ps.playlist_id
                    GROUP BY p.playlist_name, p.id""";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Playlist playlistMapped = playlistMapper.mapPlaylist(rs);
                retrievedPlaylists.add(playlistMapped);
            }
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
        return retrievedPlaylists;
    }

    @Override
    public void createPlaylist(Playlist playlist) throws ApplicationException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "INSERT INTO playlist(playlist_name) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws ApplicationException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "UPDATE playlist SET playlist_name = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean deletePlaylist(int id) throws ApplicationException {
        try (Connection connection = MSSQLConnection.createConnection()) {
            String sql = "DELETE FROM playlist WHERE(id=?)";
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
