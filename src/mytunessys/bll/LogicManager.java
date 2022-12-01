package mytunessys.bll;

import mytunessys.be.Song;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.bll.helpers.SearchHelper;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.util.List;

public class LogicManager implements ILogicFacade {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    ISongDAO songDAO;
    SearchHelper searchHelper;

    public LogicManager() {
        this.songDAO = abstractDAOFactory.getSongDAO();
        this.searchHelper = new SearchHelper();
    }

    @Override
    public List<Object> getAllObject() throws CustomException {
        return this.songDAO.getAllSongs();
    }

    @Override
    public void createObject(Object object) throws CustomException {
        this.songDAO.createSong((Song) object);
    }

    @Override
    public void updateObject(Object object) throws CustomException {
        this.songDAO.updateSong((Song) object);
    }

    @Override
    public boolean deleteObject(Object object) throws CustomException {
        return this.songDAO.deleteSong(((Song) object).getId());
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return List.of();
    }

}
