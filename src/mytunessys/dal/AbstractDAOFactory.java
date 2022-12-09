package mytunessys.dal;

import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.repository.interfaces.IGenreDAO;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;

public abstract class AbstractDAOFactory {
    /**
     * abstract instantiation of ISongDAO
     * @return ISongDAO
     * @throws Exception if not able to instantiate new playlist DAO
     */
    public abstract ISongDAO getSongDAO() throws Exception;

    /**
     * abstract instantiation of IPlaylistDAO
     * @return IPlaylistDAO
     * @throws Exception if not able to instantiate new playlist DAO
     */
    public abstract IPlaylistDAO playlistDAO() throws Exception;

    /**
     * abstract instantiation of IGenreDAO
     * @return IGenreDAO
     * @throws Exception if not able to instantiate new playlist DAO
     */

    public abstract IGenreDAO genreDAO() throws Exception;

    /**
     * instantiate new dao factory with predefined chosen db connection
     *
     * @param databaseType takes enum parameter for predefined db type
     *                     Options : MSSQL -> connection currently available for use
     * @return DAO factory for chosen db connection
     */

    public static AbstractDAOFactory getDAOFactory(DatabaseType databaseType) {
        return switch (databaseType) {
            case MSSQL -> new DAOFactory();
            case TEXT_FILE -> throw new UnsupportedOperationException();
        };
    }
}
