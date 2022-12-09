package mytunessys.dal.mappers;

import mytunessys.be.Playlist;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tomas Simko
 */
public class PlaylistMapper {
    private Playlist playlist;
    public PlaylistMapper(){
        this.playlist = null;
    }

     /**
     * maps result set into object for playlist
     * @param rs that will be mapped to object
     * @throws SQLException happens cannot get right values from table
     */
    public void mapPlaylist(ResultSet rs) throws SQLException {
        int amount = rs.getInt("amount");
        int id = rs.getInt("id");
        String playlistName = rs.getString("playlist_name");

        this.playlist = new Playlist(id, playlistName,amount);
    }

    public Playlist getPlaylist(){
        return this.playlist;
    }
}
