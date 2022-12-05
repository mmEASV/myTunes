package mytunessys.dal;

import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.repository.interfaces.IGenreDAO;
import mytunessys.dal.repository.interfaces.IPlaylistDAO;
import mytunessys.dal.repository.interfaces.ISongDAO;
import mytunessys.dal.repository.interfaces.ISongOnPlaylistDAO;

public abstract class AbstractDAOFactory {
    public abstract ISongDAO getSongDAO();

    public abstract IPlaylistDAO playlistDAO();

    public abstract ISongOnPlaylistDAO songOnPlaylistDAO();

    public abstract IGenreDAO genreDAO();

    /**
     * getDao method that generates DAO factory with predefined db connection
     *
     * @param databaseType takes enum parameter for predefined db type
     *                     Options : MSSQL -> connection currently available for use
     *                     (future) : AZURE -> connection to different database
     * @return DAO factory for chosen db connection
     */

    public static AbstractDAOFactory getDAO(DatabaseType databaseType) {
        return switch (databaseType) {
            case MSSQL -> new DAOFactory();
            case TEXT_FILE -> throw new RuntimeException("Could not retrieve factory to this type of database ");
        };
    }
}
