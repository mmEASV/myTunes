package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.helpers.SearchHelper;
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

    public PlaylistManager(){
        this.playlistDAO = new PlaylistDAO();
    }
    @Override
    public List<Object> getAllObject() {
        return playlistDAO.getAllPlaylists();
    }

    @Override
    public void createObject(Object object) {
        playlistDAO.createPlaylist((Playlist) object);
    }

    @Override
    public void updateObject(Object object) {
        playlistDAO.updatePlaylist((Playlist) object);
    }

    @Override
    public boolean deleteObject(int id) {
        return playlistDAO.deletePlaylist(id);
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return null;
    }

}
