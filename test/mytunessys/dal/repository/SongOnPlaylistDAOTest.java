package mytunessys.dal.repository;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class SongOnPlaylistDAOTest {

    private static final Logger LOGGER = Logger.getLogger(SongDAO.class.getName());

    private ISongOnPlaylistDAO songOnPlaylistDAO;

    @BeforeEach
    void initTest(){
        this.songOnPlaylistDAO = new SongOnPlaylistDAO();
    }

    @AfterEach
    void tearDown(){
        songOnPlaylistDAO = null;
    }

    @Test
    void getListOfSongsOnPlaylist() throws CustomException {
        List<SongOnPlaylist> songsList =  (List<SongOnPlaylist>) (Object) songOnPlaylistDAO.getAllSongsOnPlaylist();
        assertThat(songsList).isNotNull();
        assertThat(songsList).isNotEmpty();
    }

    @Test
    void removeSongFromGivenPlaylist() throws CustomException {
        int songId = 10;
        int playlistId = 10;
        Song songToRemove = new Song(songId,null,null,null,null,null);
        Playlist toBeRemovedFrom = new Playlist(playlistId,null);
        var result = songOnPlaylistDAO.removeSongFromPlaylist(songToRemove,toBeRemovedFrom);
        assertTrue(result);
    }

    @Test
    void addedSongToPlaylist(){
        // get song that already exist and inject it into the playlist then check if it was added with right ids and if its located inside of the list
    }
}