package mytunessys.dal;

import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;

public class DAOFactory extends AbstractDAOFactory{

    /**
     *
     * @return SongDAO instantiated in factory
     */
    @Override
    public SongDAO getSongDAO() {
         return new SongDAO();
    }

    /**
     *
     * @return PlaylistDAO instantiated in factory
     */
    @Override
    public PlaylistDAO playlistDAO() {
        return new PlaylistDAO();
    }

}
