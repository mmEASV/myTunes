package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.helpers.SearchHelper;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.util.List;

public class LogicManager implements ILogicFacade<Song> {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    ISongDAO songDAO;
    SearchHelper searchHelper;

    public LogicManager() {
        this.songDAO = abstractDAOFactory.getSongDAO();
        this.searchHelper = new SearchHelper();
    }

    @Override
    public List<Song> getAllObject() throws ApplicationException {
        return this.songDAO.getAllSongs();
    }

    @Override
    public Song getObjectById(Song object) throws ApplicationException {
        return null;
    }

    @Override
    public void createObject(Song object) throws ApplicationException {
        this.songDAO.createSong(object);
    }

    @Override
    public void updateObject(Song object) throws ApplicationException {
        this.songDAO.updateSong(object);
    }

    @Override
    public boolean deleteObject(Song object) throws ApplicationException {
        return this.songDAO.deleteSong(object.getId());
    }

    @Override
    public List<Song> searchObjects(List<Song> list, String query) {
        return this.searchHelper.searchSongs(list, query);
    }



}
