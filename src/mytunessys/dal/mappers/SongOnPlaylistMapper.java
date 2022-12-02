package mytunessys.dal.mappers;

import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongOnPlaylistMapper {

    private SongOnPlaylist songOnPlaylist;

    public SongOnPlaylistMapper() {
        this.songOnPlaylist = null;
    }

    public SongOnPlaylist mapSongOnPlaylist(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        String artist = rs.getString("artist");
        String absolutePath = rs.getString("absolute_path");

        int genreId = rs.getInt("genre_id");
        int playListId = rs.getInt("playlist_id");
        String playlistName = rs.getString("playlist_name");
        songOnPlaylist = new SongOnPlaylist(new Song(id, title, duration, artist, absolutePath, new Genre(genreId, "Pop")), new Playlist(playListId, playlistName));
        return songOnPlaylist;
    }

    public SongOnPlaylist getSongOnPlaylist(){
        return songOnPlaylist;
    }

}
