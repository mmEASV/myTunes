package mytunessys.dal.repository;

import mytunessys.be.Playlist;

import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is very simple jUnitTest for playlistDAO
 * Not included in report helped us find more bugs :)
 * @author Tomas Simko
 */
class PlaylistDAOTest {

    private static final Logger LOGGER = Logger.getLogger(PlaylistDAO.class.getName());

    private IPlaylistDAO playlistDAO;

    @BeforeEach
    void initTest() throws Exception {
        this.playlistDAO = new PlaylistDAO();
    }

    @AfterEach
    void tearDown() {
        playlistDAO = null;
    }

    @Test
    void shouldGetAllPlaylists() throws Exception {
        List<Playlist> expectedPlaylists = List.of(new Playlist(4,"How much is the fish",0),new Playlist(1,"jb rules",0));

        LOGGER.info("Getting all the song on playlist from database");
        List<Playlist> playlistList = (List<Playlist>) (Object) playlistDAO.getAllPlaylists();

        for (int i = 0; i < playlistList.size(); i++) {
            if(playlistList.get(i).getId() != expectedPlaylists.get(i).getId()){
                fail();
            }
            if(!playlistList.get(i).getPlaylistName().equals(expectedPlaylists.get(i).getPlaylistName())){
                fail();
            }
        }
        assertThat(playlistList).isNotNull();
        assertThat(playlistList).isNotEmpty();
    }


    @Test
    void shouldCreateNewPlaylist() throws ApplicationException {
        Playlist expectedPlaylist = new Playlist(0,"Rainy day");
        playlistDAO.createPlaylist(expectedPlaylist);
        List<Playlist> allPlaylists = (List<Playlist>) (Object) playlistDAO.getAllPlaylists();
        Playlist newPlaylist = (allPlaylists.get(allPlaylists.size() - 1));

        int expectedSize = allPlaylists.size() + 1;
        assertEquals(expectedSize, allPlaylists.size());
        assertNotNull(allPlaylists);
        assertNotNull(expectedPlaylist);
        assertEquals(expectedPlaylist.getPlaylistName(),newPlaylist.getPlaylistName());
    }

    @Test
    void shouldDeletePlaylistWithGivenId() throws ApplicationException {
        int playlistId = 6;
        LOGGER.info("Testing deletion of song with id " + playlistId);
        var result = playlistDAO.deletePlaylist(playlistId);
        assertTrue(result);
    }

    @Test
    void shouldUpdatePlaylistNameWithGivenId() throws ApplicationException {
        String newName = "How much is the window?";
        int id = 4;
        Playlist playlistToUpdate = new Playlist(id,newName.trim());
        LOGGER.info("Testing update of song with id " + playlistToUpdate.getId());
        playlistDAO.updatePlaylist(playlistToUpdate);
        List<Playlist> fetchedPlaylists = (List<Playlist>) (Object) playlistDAO.getAllPlaylists(); // I wrote shitty query that gets only playlists that have songs inside otherwise its does not show all playlists
        Playlist updatedPlaylist = null;
        for (Playlist playlist : fetchedPlaylists
        ) {
            if (playlist.getId() == playlistToUpdate.getId()) {
                updatedPlaylist = playlist;
            }
        }
        assert updatedPlaylist != null;
        assertEquals(newName, updatedPlaylist.getPlaylistName());
    }


}