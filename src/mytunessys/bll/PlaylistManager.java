package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.FactoryException;
import mytunessys.bll.helpers.ISearchHelper;
import mytunessys.bll.helpers.SearchHelper;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;

import java.util.List;
import java.util.Optional;


/**
 * @author Bálint, Matej & Tomas,Julian
 */

public class PlaylistManager implements ILogicFacade<Playlist> {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAOFactory(DatabaseType.MSSQL);
    IPlaylistDAO playlistDAO;

    ISearchHelper searchHelper;

    public PlaylistManager() throws FactoryException {
        try {
            this.playlistDAO = abstractDAOFactory.playlistDAO();
        } catch (Exception e) {
            throw new FactoryException(e.getMessage(), e.getCause());
        }
        this.searchHelper = new SearchHelper();
    }

    @Override
    public List<Playlist> getAllObject() throws Exception {
        return this.playlistDAO.getAllPlaylists();
    }

    @Override
    public Optional<Playlist> getObjectById(Playlist object) throws Exception {
        return this.playlistDAO.getPlaylistById(object);
    }

    @Override
    public void createObject(Playlist object) throws Exception {
        this.playlistDAO.createPlaylist(object);
    }

    @Override
    public void updateObject(Playlist object) throws Exception {
        this.playlistDAO.updatePlaylist(object);
    }

    @Override
    public boolean deleteObject(Playlist object) throws Exception {
        return this.playlistDAO.deletePlaylist((object).getId());
    }

    @Override
    public List<Playlist> searchObjects(List<Playlist> list, String query) {
        return this.searchHelper.searchPlaylists(list, query);
    }

    @Override
    public boolean addToObject(Object object, Object secondObject) throws Exception {
        return this.playlistDAO.addSongToPlaylist((Song) object, (Playlist) secondObject);
    }

    @Override
    public boolean removeObjectFrom(Object firstObject) throws Exception {
        return this.playlistDAO.removeSongFromPlaylist((Playlist) firstObject);
    }
}