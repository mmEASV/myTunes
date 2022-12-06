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

public class LogicManager implements ILogicFacade {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    ISongDAO songDAO;
    SearchHelper searchHelper;

    public LogicManager() {
        this.songDAO = abstractDAOFactory.getSongDAO();
        this.searchHelper = new SearchHelper();
    }

    @Override
    public List<Object> getAllObject() throws ApplicationException {
        return this.songDAO.getAllSongs();
    }

    @Override
    public void createObject(Object object) throws ApplicationException {
        this.songDAO.createSong((Song) object);
    }

    @Override
    public void updateObject(Object object) throws ApplicationException {
        this.songDAO.updateSong((Song) object);
    }

    @Override
    public boolean deleteObject(Object object) throws ApplicationException {
        return this.songDAO.deleteSong(((Song) object).getId());
    }



    //need to check out
    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return  searchHelper.searchSongs(list, query);
    }

    public static void main(String[] args) throws ApplicationException {
        LogicManager lm = new LogicManager();
        List<Object> songs = lm.getAllObject();
        List<Song> searched = (List<Song>) (Object) lm.searchObjects(songs, "ba");
        for(Song s : searched){
            System.out.println(s.getTitle());
        }
    }


}
