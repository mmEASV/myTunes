package mytunessys.dal.repository;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.mappers.PlaylistMapper;
import mytunessys.dal.mappers.SongMapper;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {

    private PreparedStatement preparedStatement;

    public List<Playlist> getAllPlaylists() throws ApplicationException {
        PlaylistMapper playlistMapper = new PlaylistMapper();
        List<Playlist> retrievedPlaylists = new ArrayList<>();
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

    public Playlist getPlaylistById(Playlist playlist) throws ApplicationException{
        HashMap<Integer, Song> fetchedSongs = new HashMap<>();
        SongMapper songMapper = new SongMapper();
        String name = "";
        try (Connection connection = MSSQLConnection.createConnection()) {

            String sql = """
                    SELECT p.id as playlist_id,s.id,s.title,s.duration,s.artist,s.absolute_path,g.id as genre_id,g.genre_name as genre_name,ps.song_order as song_order,p.playlist_name
                    FROM song s
                    INNER JOIN playlist_song ps on s.id = ps.song_id
                    RIGHT JOIN genre g on g.id = s.genre_id
                    RIGHT JOIN playlist p on p.id = ps.playlist_id
                    WHERE playlist_id = ?
                    ORDER BY p.id,song_order;
                    """;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,playlist.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                fetchedSongs.put(rs.getInt("song_order"),songMapper.mapSong(rs));
                name = rs.getString("playlist_name");
            }
        } catch (SQLException ex) {
            throw new ApplicationException(ex.getMessage(), ex.getCause());
        }
        return new Playlist(playlist.getId(), name,fetchedSongs);
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
