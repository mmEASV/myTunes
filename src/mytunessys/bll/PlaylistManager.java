package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;

import java.util.List;

public class PlaylistManager implements ILogicFacade {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    IPlaylistDAO playlistDAO;

    public PlaylistManager() {
        this.playlistDAO = abstractDAOFactory.playlistDAO();
    }

    @Override
    public List<Object> getAllObject() throws CustomException {
        return this.playlistDAO.getAllPlaylists();
    }

    @Override
    public void createObject(Object object)  throws CustomException  {
        this.playlistDAO.createPlaylist((Playlist) object);
    }

    @Override
    public void updateObject(Object object)  throws CustomException  {
        this.playlistDAO.updatePlaylist((Playlist) object);
    }

    @Override
    public boolean deleteObject(Object object) throws CustomException  {
        return this.playlistDAO.deletePlaylist(((Playlist)object).getId());
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return null;
    }
}
