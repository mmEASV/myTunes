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
import java.util.*;

/**
 * @author Tomas,Julian
 */


public class PlaylistDAO implements IPlaylistDAO {

    private PreparedStatement preparedStatement;
    private final MSSQLConnection mssqlConnection;

    public PlaylistDAO() throws Exception {
        this.mssqlConnection = new MSSQLConnection();
    }

    public List<Playlist> getAllPlaylists() throws Exception {
        PlaylistMapper playlistMapper = new PlaylistMapper();
        List<Playlist> retrievedPlaylists = new ArrayList<>();
        try (Connection connection = mssqlConnection.createConnection()) {
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
                playlistMapper.mapPlaylist(rs);
                retrievedPlaylists.add(playlistMapper.getPlaylist());
            }
        }
        return retrievedPlaylists;
    }

    public Optional<Playlist> getPlaylistById(Playlist playlist) throws Exception{
        HashMap<Integer, Song> fetchedSongs = new HashMap<>();
        SongMapper songMapper = new SongMapper();
        Playlist playlist1 = null;
        String name = "";
        try (Connection connection = mssqlConnection.createConnection()) {
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
                songMapper.mapSong(rs);
                fetchedSongs.put(rs.getInt("song_order"),songMapper.getSong());
                name = rs.getString("playlist_name");
            }
        }
        playlist1 = new Playlist(playlist.getId(), name,fetchedSongs);
        return Optional.of(playlist1);
    }
    @Override
    public void createPlaylist(Playlist playlist) throws Exception {
        try (Connection connection = mssqlConnection.createConnection()) {
            String sql = "INSERT INTO playlist(playlist_name) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws Exception {
        try (Connection connection = mssqlConnection.createConnection()) {
            String sql = "UPDATE playlist SET playlist_name = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean addSongToPlaylist(Song song,Playlist playlist) throws Exception {
        try(Connection connection = mssqlConnection.createConnection()){
            String sql = "INSERT INTO playlist_song(song_id,playlist_id,song_order) VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());

            String latestNumber = "SELECT Max(ps.song_order) as latest\n" +
                    "FROM song s\n" +
                    "         INNER JOIN playlist_song ps on s.id = ps.song_id\n" +
                    "         RIGHT JOIN playlist p on p.id = ps.playlist_id\n" +
                    "WHERE playlist_id = ?";

            PreparedStatement secondPreparedStatement = connection.prepareStatement(latestNumber);
            secondPreparedStatement.setInt(1,playlist.getId());
            ResultSet resultSet = secondPreparedStatement.executeQuery();
            int latestOrder = 0;
            while (resultSet.next()){
                latestOrder = resultSet.getInt("latest") + 1;
            }

            preparedStatement.setInt(3, latestOrder);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletePlaylist(int id) throws Exception {
        try (Connection connection = mssqlConnection.createConnection()) {
            String sql = "DELETE FROM playlist WHERE(id=?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean removeSongFromPlaylist(Playlist playlist) throws Exception{
        try (Connection connection = mssqlConnection.createConnection()) {
            String sql = "DELETE FROM playlist_song WHERE song_id = ? AND playlist_id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            Song flagSong = null;
            for (Map.Entry<Integer, Song> entry : playlist.getSongList().entrySet()) {
                flagSong = entry.getValue();
                // Do things with the list
            }
            preparedStatement.setInt(1,flagSong.getId());
            preparedStatement.setInt(2, playlist.getId());
            // TODO: updates all other song_order;
            int result = preparedStatement.executeUpdate();
            if(result > 0)
                return true;
        }
        return false;
    }


}