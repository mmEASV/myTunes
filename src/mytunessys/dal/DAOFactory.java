package mytunessys.dal;

import mytunessys.dal.repository.GenreDAO;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;
import mytunessys.dal.repository.SongOnPlaylistDAO;
import mytunessys.dal.repository.interfaces.IGenreDAO;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;

public class DAOFactory extends AbstractDAOFactory{

    /**
     *
     * @return SongDAO instantiated in factory from its interface
     */
    @Override
    public ISongDAO getSongDAO() {
         return new SongDAO();
    }

    /**
     *
     * @return PlaylistDAO instantiated in factory from its interface
     */
    @Override
    public IPlaylistDAO playlistDAO() {
        return new PlaylistDAO();
    }

    /**
     *
     * @return SongOnPlaylistDAO instantiated in the factory from its interface
     */
    @Override
    public ISongOnPlaylistDAO songOnPlaylistDAO() {
        return new SongOnPlaylistDAO();
    }

    /**
     *
     * @return SongOnPlaylistDAO instantiated in the factory from its interface
     */
    @Override
    public IGenreDAO genreDAO() {
        return new GenreDAO();
    }

}
