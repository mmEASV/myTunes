package mytunessys.dal.mappers;

import mytunessys.be.Playlist;
import mytunessys.be.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class PlaylistMapper {
    private Playlist playlist;
    public PlaylistMapper(){
        this.playlist = null;
    }

    public Playlist mapPlaylist(ResultSet rs) throws SQLException {
        int amount = rs.getInt("amount");
        int id = rs.getInt("id");
        String playlistName = rs.getString("playlist_name");

        return new Playlist(id, playlistName,amount);
    }

    public Playlist getPlaylist(){
        return playlist;
    }
}
