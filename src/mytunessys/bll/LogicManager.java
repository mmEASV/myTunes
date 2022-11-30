package mytunessys.bll;

import mytunessys.be.Song;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.util.List;

public class LogicManager implements ILogicFacade {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    ISongDAO songDAO;

    public LogicManager() {
        this.songDAO = abstractDAOFactory.getSongDAO();
    }

    @Override
    public List<Object> getAllObject() {
        return songDAO.getAllSongs();
    }

    @Override
    public void createObject(Object object) {
        songDAO.createSong((Song) object);
    }

    @Override
    public void updateObject(Object object) {
        songDAO.updateSong((Song) object);
    }

    @Override
    public boolean deleteObject(int id) {
        return songDAO.deleteSong(id);
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return null;
    }

}
