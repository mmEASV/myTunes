package mytunessys.dal.repository;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.repository.interfaces.ISongDAO;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.logging.Logger;

class SongDAOTest {
    private static final Logger LOGGER = Logger.getLogger(SongDAO.class.getName());

    private ISongDAO songDAO;

    @BeforeEach
    void initTest(){
        this.songDAO = new SongDAO();
    }

    @AfterEach
    void tearDown(){
        songDAO = null;
    }

    @Test
    void shouldRetrieveListOfSong() throws CustomException {
        Genre genre = new Genre(1,"Pop");
        Song prepSong = new Song(1,"Baby","3033","Maclemore","User/folder",genre);

        LOGGER.info("Test of getting all songs");
        List<Song> songsList =  (List<Song>) (Object) songDAO.getAllSongs();
        Song song = (songsList.get(songsList.size() - 1));
        assertNotNull(songsList);
        assertNotNull(song);
        assertEquals(prepSong.getId(),song.getId());
    }

    @Test
    void shouldAddSongToTableCheckedByTitle() throws CustomException {
        Song expectedSong = new Song(12,"Get shaky","30032","KXXMA","/user/root/path",new Genre(1,"Pop"));
        songDAO.createSong(expectedSong);
        List<Song> songsList =  (List<Song>) (Object) songDAO.getAllSongs();
        Song song = (songsList.get(songsList.size() - 1));
        assertEquals(expectedSong.getTitle(),song.getTitle());
    }

    @Test
    void shouldCreateSongIntoList() throws CustomException {
        Song expectedSong = new Song(9,"Get shaky","30032","KXXMA","/user/root/path",new Genre(1,"Pop"));
        songDAO.createSong(expectedSong);
        List<Song> fetchedSongs =  (List<Song>) (Object) songDAO.getAllSongs();
        int expectedSize = fetchedSongs.size() + 1;
        assertEquals(expectedSize,fetchedSongs.size());

    }
    @Test
    void shouldIncrementIdOfCreatedSong() throws CustomException {
        List<Song> fetchedSongs =  (List<Song>) (Object) songDAO.getAllSongs();
        Song s = (fetchedSongs.get(fetchedSongs.size() - 1));
        int currentId = s.getId();
        Song newCreatedSong = new Song(0,"Get shaky","30032","KXXMA","/user/root/path",new Genre(1,"Pop"));

        songDAO.createSong(newCreatedSong);

        List<Song> fetchedSongAfter =  (List<Song>) (Object) songDAO.getAllSongs();
        Song lastCreatedSong = (fetchedSongAfter.get(fetchedSongAfter.size() - 1));
        int expectedId = currentId + 1;
        assertEquals(expectedId,lastCreatedSong.getId());
    }

    @Test
    void shouldUpdateSongTitleAndRemainId() throws CustomException{
        Song songToUpdate = new Song(10,"Get shaky","30032","KXXMA","/user/root/path",new Genre(1,"Pop"));
        String changedTitle = "Get shaky 3";
        int expectedId = 10;
        songDAO.updateSong(songToUpdate);

        List<Song> fetchedSongs =  (List<Song>) (Object) songDAO.getAllSongs();
        Song lastSongInList = (fetchedSongs.get(fetchedSongs.size() - 1));
        assertNotNull(fetchedSongs);
        assertEquals(changedTitle,lastSongInList.getTitle());
        assertEquals(expectedId,expectedId);
    }

    @Test
    void shouldDeleteSong() throws CustomException {
        int songId = 10;
        LOGGER.info("Testing deletion of song with id " + songId);
        var result = songDAO.deleteSong(songId);
        assertTrue(result);
    }

}