package mytunessys.bll;

import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.bll.types.ModelDAOType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.DAOFactory;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;

import java.util.List;

public class LogicManager implements ILogicFacade {

    // This here is just for testing for now go try the abstract factory
    public static void main(String[] args) {
        AbstractDAOFactory songFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL); // fixed with extracted enums
        SongDAO songDAO = songFactory.getSongDAO();
        PlaylistDAO playlistDAO = songFactory.playlistDAO();

    }





}
