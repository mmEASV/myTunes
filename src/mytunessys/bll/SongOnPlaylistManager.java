package mytunessys.bll;

import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;

import java.util.List;

public class SongOnPlaylistManager implements ILogicFacade<SongOnPlaylist> {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    ISongOnPlaylistDAO songOnPlaylistDAO;

    public SongOnPlaylistManager() {
        this.songOnPlaylistDAO = abstractDAOFactory.songOnPlaylistDAO();
    }

    @Override
    public List<SongOnPlaylist> getAllObject() throws ApplicationException {
        return this.songOnPlaylistDAO.getAllSongsOnPlaylist();
    }

    @Override
    public SongOnPlaylist getObjectById(SongOnPlaylist object) throws ApplicationException {
        return null;
    }

    @Override
    public void createObject(SongOnPlaylist object) throws ApplicationException {
        this.songOnPlaylistDAO.addSongToPlaylist((((SongOnPlaylist) object).getSong()),((SongOnPlaylist) object).getPlaylist());
    }

    @Override
    public void updateObject(SongOnPlaylist object) throws ApplicationException {
        // does nothing now
    }

    @Override
    public boolean deleteObject(SongOnPlaylist object) throws ApplicationException {
        return this.songOnPlaylistDAO.removeSongFromPlaylist(object.getSong(),object.getPlaylist());
    }

    @Override
    public List<SongOnPlaylist> searchObjects(List<SongOnPlaylist> list, String query) {
        return null;
    }
}
