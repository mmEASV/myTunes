package mytunessys.dal;

import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;

public class DAOFactory extends AbstractDAOFactory{

    /**
     *
     * @return SongDAO instantiated in factory
     */
    @Override
    public ISongDAO getSongDAO() {
         return new SongDAO();
    }

    /**
     *
     * @return PlaylistDAO instantiated in factory
     */
    @Override
    public IPlaylistDAO playlistDAO() {
        return new PlaylistDAO();
    }

}
