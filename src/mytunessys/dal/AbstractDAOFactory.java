package mytunessys.dal;

import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;

public abstract class AbstractDAOFactory {
    public abstract SongDAO getSongDAO();
    public abstract PlaylistDAO playlistDAO();

    /**
     * getDao method that generates DAO factory with predefined db connection
     * @param databaseType takes enum parameter for predefined db type
     *                     Options : MSSQL db connection
     * @return DAO factory for chosen db connection
     */

    public static DAOFactory getDAO(DatabaseType databaseType) {
        return switch (databaseType) {
            case MSSQL -> new DAOFactory();
            case ANOTHER_DB -> null;
        };
    }
}
