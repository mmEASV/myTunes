package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.helpers.SearchHelper;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.util.List;

public class PlaylistManager implements ILogicFacade<Playlist> {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    IPlaylistDAO playlistDAO;

    SearchHelper searchHelper;

    public PlaylistManager() {
        this.playlistDAO = abstractDAOFactory.playlistDAO();
        this.searchHelper = new SearchHelper();
    }

    @Override
    public List<Playlist> getAllObject() throws ApplicationException {
        return this.playlistDAO.getAllPlaylists();
    }

    @Override
    public Playlist getObjectById(Playlist object) throws ApplicationException {
        return this.playlistDAO.getPlaylistById(object);
    }

    @Override
    public void createObject(Playlist object)  throws ApplicationException {
        this.playlistDAO.createPlaylist(object);
    }

    @Override
    public void updateObject(Playlist object)  throws ApplicationException {
        this.playlistDAO.updatePlaylist(object);
    }

    @Override
    public boolean deleteObject(Playlist object) throws ApplicationException {
        return this.playlistDAO.deletePlaylist((object).getId());
    }

    @Override
    public List<Playlist> searchObjects(List<Playlist> list, String query) {
        return this.searchHelper.searchPlaylists(list, query);
    }

    @Override
    public boolean addToObject(Object object, Object secondObject) throws ApplicationException {
        return this.playlistDAO.addSongToPlaylist((Song)object,(Playlist)secondObject);
    }

    @Override
    public boolean removeObjectFrom(Object firstObject) throws ApplicationException {
        return this.playlistDAO.removeSongFromPlaylist((Playlist) firstObject);
    }
}
