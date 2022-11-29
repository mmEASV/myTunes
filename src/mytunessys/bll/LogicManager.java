package mytunessys.bll;

import mytunessys.be.Song;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.SongDAO;

import java.util.List;

public class LogicManager implements ILogicFacade {

    AbstractDAOFactory songFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL); // fixed with extracted enums
    SongDAO songDAO = songFactory.getSongDAO();

    public List<Song> getAllSongs() {
        return List.of();
       // return songDAO.getAllSongs();
    }
}
