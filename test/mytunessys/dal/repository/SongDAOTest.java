package mytunessys.dal.repository;

import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.dal.repository.interfaces.ISongDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.logging.Logger;

/**
 * This is very simple jUnitTest for songDAO
 * Not included in report helped us find more bugs :)
 * @author Tomas Simko
 */

class SongDAOTest {
    private static final Logger LOGGER = Logger.getLogger(SongDAO.class.getName());

    private ISongDAO songDAO;

    @BeforeEach
    void initTest() throws Exception {
        this.songDAO = new SongDAO();
    }

    @AfterEach
    void tearDown() {
        songDAO = null;
    }

    @Test
    void shouldBeListOfSongS() throws Exception {
        Genre genre = new Genre(1, "Pop");
        Song prepSong = new Song(1, "Baby", "3033", "Maclemore", "User/folder", genre);

        LOGGER.info("Test of getting all songs");
        List<Song> songsList = (List<Song>) (Object) songDAO.getAllSongs();
        Song song = (songsList.get(songsList.size() - 1));
        assertNotNull(songsList);
        assertNotNull(song);
        assertEquals(prepSong.getId(), song.getId());
    }

    @Test
    void shouldAddSongToTableCheckedByTitle() throws Exception {
        Song expectedSong = new Song(12, "Get shaky", "30032", "KXXMA", "/user/root/path", new Genre(1, "Pop"));
        songDAO.createSong(expectedSong);
        List<Song> songsList = (List<Song>) (Object) songDAO.getAllSongs();
        Song song = (songsList.get(songsList.size() - 1));
        assertEquals(expectedSong.getTitle(), song.getTitle());
    }

    @Test
    void shouldCreateSongIntoList() throws Exception {
        Song expectedSong = new Song(9, "Get shaky", "30032", "KXXMA", "/user/root/path", new Genre(1, "Pop"));
        songDAO.createSong(expectedSong);
        List<Song> fetchedSongs = (List<Song>) (Object) songDAO.getAllSongs();
        int expectedSize = fetchedSongs.size() + 1;
        assertEquals(expectedSize, fetchedSongs.size());
    }

    @Test
    void deletionOfSong() throws Exception {
        int songId = 10;
        LOGGER.info("Testing deletion of song with id " + songId);
        var result = songDAO.deleteSong(songId);
        assertTrue(result);
    }

    @Test
    void shouldUpdateSongWithNewTitle() throws Exception {
        String newTitle = "She can't love you that much neh ?";
        Song songToUpdate = new Song(10, newTitle, "30032", "KXXMA", "/user/root/path", new Genre(1, "Pop"));
        LOGGER.info("Testing update of song with title " + songToUpdate.getTitle());
        songDAO.updateSong(songToUpdate);
        List<Song> fetchedSongs = (List<Song>) (Object) songDAO.getAllSongs();
        Song updatedSong = null;
        for (Song s : fetchedSongs
        ) {
            if (s.getTitle().equals(songToUpdate.getTitle())) {
                updatedSong = s;
            }
        }
        assert updatedSong != null;
        assertEquals(newTitle, updatedSong.getTitle());
    }

}