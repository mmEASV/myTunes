package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager implements ILogicFacade {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    IPlaylistDAO playlistDAO;

    public PlaylistManager() {
        this.playlistDAO = abstractDAOFactory.playlistDAO();
    }

    @Override
    public List<Object> getAllObject() throws ApplicationException {
        return this.playlistDAO.getAllPlaylists();
    }

    @Override
    public void createObject(Object object)  throws ApplicationException {
        this.playlistDAO.createPlaylist((Playlist) object);
    }

    @Override
    public void updateObject(Object object)  throws ApplicationException {
        this.playlistDAO.updatePlaylist((Playlist) object);
    }

    @Override
    public boolean deleteObject(Object object) throws ApplicationException {
        return this.playlistDAO.deletePlaylist(((Playlist)object).getId());
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return null;
    }
}
