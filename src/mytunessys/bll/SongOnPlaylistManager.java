package mytunessys.bll;

import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.exceptions.CustomException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;

import java.util.List;

public class SongOnPlaylistManager implements ILogicFacade {

    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    ISongOnPlaylistDAO songOnPlaylistDAO;

    public SongOnPlaylistManager() {
        this.songOnPlaylistDAO = abstractDAOFactory.songOnPlaylistDAO();
    }

    @Override
    public List<Object> getAllObject() throws CustomException {
        return this.songOnPlaylistDAO.getAllSongsOnPlaylist();
    }

    @Override
    public void createObject(Object object) throws CustomException{
        this.songOnPlaylistDAO.addSongToPlaylist((((SongOnPlaylist) object).getSong()),((SongOnPlaylist) object).getPlaylist());
    }

    @Override
    public void updateObject(Object object) throws CustomException{
        // does nothing now
    }

    @Override
    public boolean deleteObject(Object object) throws CustomException{
        return this.songOnPlaylistDAO.removeSongFromPlaylist(((Song) object),((Playlist) object));
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return null;
    }
}
