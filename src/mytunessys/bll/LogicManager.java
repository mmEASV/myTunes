package mytunessys.bll;

import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.SongDAO;

import java.util.List;

public class LogicManager implements ILogicFacade {




    public List<Song> getAllSongs() {
        return List.of();
       // return songDAO.getAllSongs();
    }

    public void createSong(String title, String duration, String artist, String absolutePath, Genre genre){
        //songDAO.createSong(title, duration, artist, absolutePath, genre);
    }
}
