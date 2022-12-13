package mytunessys.bll;

import mytunessys.be.Song;
import mytunessys.bll.exceptions.*;
import mytunessys.bll.helpers.ISearchHelper;
import mytunessys.bll.helpers.SearchHelper;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.daoFactory.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.util.List;
import java.util.Optional;


/**
 * @author Bálint, Matej & Tomas,Julian
 */

public class SongManager implements ILogicFacade<Song> {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAOFactory(DatabaseType.MSSQL);
    ISongDAO songDAO;
    ISearchHelper searchHelper;

    public SongManager() throws FactoryException{
        try {
            this.songDAO = abstractDAOFactory.getSongDAO();
        } catch (Exception e) {
            throw new FactoryException(e.getMessage(),e.getCause());
        }
        this.searchHelper = new SearchHelper();
    }

    @Override
    public List<Song> getAllObject() throws Exception {
        return this.songDAO.getAllSongs();
    }

    @Override
    public Optional<Song> getObjectById(Song object) throws Exception {
        return null;
    }

    @Override
    public void createObject(Song object) throws Exception {
        this.songDAO.createSong(object);
    }

    @Override
    public void updateObject(Song object) throws Exception {
        this.songDAO.updateSong(object);
    }

    @Override
    public boolean deleteObject(Song object) throws Exception {
        return this.songDAO.deleteSong(object.getId());
    }

    @Override
    public List<Song> searchObjects(List<Song> list, String query) {
        return this.searchHelper.searchSongs(list, query);
    }

    @Override
    public boolean addToObject(Object object, Object secondObject) throws ApplicationException {
        return false;
    }

    @Override
    public boolean removeObjectFrom(Object firstObject) throws ApplicationException {
        return false;
    }
}