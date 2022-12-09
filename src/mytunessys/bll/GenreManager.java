package mytunessys.bll;

import mytunessys.be.Genre;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.util.List;

/**
 * @author Bálint, Matej & Tomas,Julian
 */

public class GenreManager implements ILogicFacade<Genre> {
    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAOFactory(DatabaseType.MSSQL);
    IGenreDAO genreDAO;

    public GenreManager() throws ApplicationException {
        try {
            this.genreDAO = abstractDAOFactory.genreDAO();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(),e.getCause());
        }
    }
    @Override
    public List<Genre> getAllObject() throws Exception {
        return this.genreDAO.getAllGenre();
    }

    /** NOT IMPLEMENTED **/
    @Override
    public Genre getObjectById(Genre object) throws Exception {
        return null;
    }

    @Override
    public void createObject(Genre object) throws Exception {
    }

    @Override
    public void updateObject(Genre object) throws Exception {
    }

    @Override
    public boolean deleteObject(Genre object) throws Exception {
        return false;
    }

    @Override
    public List<Genre> searchObjects(List<Genre> list, String query) {
        return null;
    }

    @Override
    public boolean addToObject(Object object, Object secondObject) throws Exception {
        return false;
    }

    @Override
    public boolean removeObjectFrom(Object firstObject) throws Exception {
        return false;
    }
}
