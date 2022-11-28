package mytunessys.dal;

import mytunessys.be.Genre;
import mytunessys.bll.types.DatabaseType;
import mytunessys.bll.types.ModelDAOType;
import mytunessys.dal.repository.PlaylistDAO;
import mytunessys.dal.repository.SongDAO;

public abstract class AbstractDAOFactory {
    public abstract SongDAO getSongDAO();
    public abstract PlaylistDAO playlistDAO();

    public static DAOFactory getDAO(DatabaseType databaseType) {
        return switch (databaseType) {
            case MSSQL -> new DAOFactory(); // Returns new Factory
            // code block
            case ANOTHER_DB -> null;
        };
    }
}
