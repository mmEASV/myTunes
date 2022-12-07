package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This is very simple jUnitTest for songOnPlaylist
 * Not included in report helped us find more bugs :)
 * @author Tomas Simko
 */
class SongOnPlaylistDAOTest {


    private static final Logger LOGGER = Logger.getLogger(SongDAO.class.getName());
    private ISongOnPlaylistDAO songOnPlaylistDAO;
    private ISongDAO songDAO;
    private IPlaylistDAO playlistDAO;

    @BeforeEach
    void initTest() {
        this.songOnPlaylistDAO = new SongOnPlaylistDAO();
        this.songDAO = new SongDAO();
        this.playlistDAO = new PlaylistDAO();
    }

    @AfterEach
    void tearDown() {
        songOnPlaylistDAO = null;
        songDAO = null;
        playlistDAO = null;
    }

    @Test
    void getListOfSongsOnPlaylist() throws ApplicationException {
        LOGGER.info("Getting all the song on playlist from database");
        List<SongOnPlaylist> songsList = (List<SongOnPlaylist>) (Object) songOnPlaylistDAO.getAllSongsOnPlaylist();
        assertThat(songsList).isNotNull();
        assertThat(songsList).isNotEmpty();
    }

    @Test
    void removeSongFromGivenPlaylist() throws ApplicationException {
        int songId = 10;
        int playlistId = 10;
        LOGGER.info("Removing song with Id: " + songId + " " + " from playlist with id: " + playlistId );
        Song songToRemove = new Song(songId, null, null, null, null, null);
        Playlist toBeRemovedFrom = new Playlist(playlistId, null);
        var result = songOnPlaylistDAO.removeSongFromPlaylist(songToRemove, toBeRemovedFrom);
        assertTrue(result);
    }

    @Test
    void addedSongToPlaylistIfDoesNotExist() throws ApplicationException {
        // randomly choose any of the available song and playlist and add it to that if not already exists inside
        // could have been done better tho but will do for now
        Random randomIndex = new Random();
        Random randomIndex2 = new Random();

        List<Song> songToChooseFrom = (List<Song>) (Object)songDAO.getAllSongs();
        List<Playlist> playlistToChooseFrom = (List<Playlist>) (Object)playlistDAO.getAllPlaylists();

        Song randomSong = songToChooseFrom.get(randomIndex.nextInt(songToChooseFrom.size()));
        Playlist randomPlaylist =  playlistToChooseFrom.get(randomIndex2.nextInt(playlistToChooseFrom.size()));

        songOnPlaylistDAO.addSongToPlaylist(randomSong,randomPlaylist);

        List<SongOnPlaylist> songsList = (List<SongOnPlaylist>) (Object) songOnPlaylistDAO.getAllSongsOnPlaylist();

        SongOnPlaylist foundOne = null;
        for (SongOnPlaylist s: songsList
             ) {
            if(s.getSong().getId() == randomSong.getId()){
                foundOne = s;
            }
        }
        assertThat(foundOne).isNotNull();
        assertThat(randomSong).isIn(songsList);

    }
    @Test
    void chooseFromSongInPlaylistAndTryRemoveOne() throws ApplicationException {
        int songId = 10;
        int playlistId = 4;
        Song songToUpdate = new Song(songId, "does not matter", "30032", "KXXMA", "/user/root/path", new Genre(1, "Pop"));
        Playlist toBeDeletedFrom = new Playlist(playlistId,"null");

        LOGGER.info("Testing deletion of song with id " + songId + " from playlist with id : " + playlistId);
        var result = songOnPlaylistDAO.removeSongFromPlaylist(songToUpdate,toBeDeletedFrom);
        assertTrue(result);
    }

}
