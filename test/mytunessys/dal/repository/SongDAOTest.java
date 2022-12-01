package mytunessys.dal.repository;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.dal.repository.interfaces.ISongDAO;
import org.junit.jupiter.api.Test;
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
    void shouldBeListOfSongS() throws CustomException {
        Genre genre = new Genre(1,"Pop");
        Song prepSong = new Song(1,"Baby","3033","Maclemore","User/folder",genre);

        LOGGER.info("Test of getting all songs");
        List<Song> songsList =  (List<Song>) (Object) songDAO.getAllSongs();

        for (Song s: songsList
             ) {
            LOGGER.info(s.getTitle());
        }

    }

    @Test
    void deletionOfSong() throws CustomException {
        int songId = 10;
        LOGGER.info("Testing deletion of song with id " + songId);
        var result = songDAO.deleteSong(songId);
        assertTrue(result);
    }

}