package mytunessys.dal.mappers;

import mytunessys.be.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistMapper {
    private Playlist playlist;
    public PlaylistMapper(){
        this.playlist = null;
    }

    public Playlist mapPlaylist(ResultSet rs) throws SQLException {
        int amount = rs.getInt("amount");
        int id = rs.getInt("id");
        String playlistName = rs.getString("playlist_name");
        return new Playlist(id, playlistName, amount);
    }

    public Playlist getPlaylist(){
        return playlist;
    }
}
