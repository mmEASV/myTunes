package mytunessys.dal.daoFactory;

import mytunessys.dal.repository.GenreDAO;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;
import mytunessys.dal.repository.interfaces.IGenreDAO;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;

public class DAOFactory extends AbstractDAOFactory {

    /**
     * method that instantiate new song dao from mssql
     * @return SongDAO instantiated in factory from its interface
     */
    @Override
    public ISongDAO getSongDAO() throws Exception {
        return new SongDAO();
    }

    /**
     * method that instantiate new playlist dao from mssql
     * @return PlaylistDAO instantiated in factory from its interface
     */
    @Override
    public IPlaylistDAO playlistDAO() throws Exception {
        return new PlaylistDAO();
    }

    /**
     * method that instantiate new genre dao from mssql
     * @return SongOnPlaylistDAO instantiated in the factory from its interface
     */
    @Override
    public IGenreDAO genreDAO() throws Exception {
        return new GenreDAO();
    }

}