package mytunessys.dal.repository;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.connectors.MSSQLConnection;
import mytunessys.dal.mappers.SongOnPlaylistMapper;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongOnPlaylistDAO implements ISongOnPlaylistDAO {
    private PreparedStatement preparedStatement;
    @Override
    public List<Object> getAllSongsOnPlaylist() throws CustomException {
        SongOnPlaylistMapper mapper = new SongOnPlaylistMapper();
        List<Object> retrievedSongOnPlaylist = new ArrayList<>();
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = " SELECT s.id,s.title,s.duration,s.artist,s.absolute_path,g.id AS genre_id,g.genre_name,p.id AS playlist_id,p.playlist_name\n" +
                    " FROM song s\n" +
                    " JOIN playlist_song pl ON s.id = pl.song_id\n" +
                    " JOIN playlist p ON pl.playlist_id = p.id\n" +
                    " JOIN genre g ON s.genre_id = g.id";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                SongOnPlaylist sOp = mapper.mapSongOnPlaylist(rs);
                retrievedSongOnPlaylist.add(sOp);
            }
        } catch (SQLException ex) {
            throw new CustomException("Could not retrieve songs on playlist from database",ex.getCause());
        }
        return retrievedSongOnPlaylist;
    }


    @Override
    public void addSongToPlaylist(Song song, Playlist playlist) throws CustomException {
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "INSERT INTO playlist_song(song_id,playlist_id) VALUES(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new CustomException("Could not add song on playlist from database with Id: " + song.getId() + " to playlist with Id: " + playlist.getId() ,ex.getCause());
        }
    }

    @Override
    public boolean removeSongFromPlaylist(Song song, Playlist playlist) throws CustomException{
        try(Connection connection = MSSQLConnection.createConnection()){
            String sql = "DELETE FROM playlist_song WHERE song_id = ? AND playlist_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            int result = preparedStatement.executeUpdate();
            if(result > 0)
                return true;
        } catch (SQLException ex) {
            throw new CustomException("Could not remove song with Id: " + song.getId() + " from playlist with Id " + playlist.getId(),ex.getCause());
        }
        return false;
    }
}
