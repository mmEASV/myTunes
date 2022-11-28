package mytunessys.dal;

import mytunessys.bll.types.ModelDAOType;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;
public class DAOFactory extends AbstractDAOFactory{

    /**
     *
     * @return
     */
    @Override
    public SongDAO getSongDAO() {
         return new SongDAO();
    }

    /**
     *
     * @return
     */
    @Override
    public PlaylistDAO playlistDAO() {
        return new PlaylistDAO();
    }

}
