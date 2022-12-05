package mytunessys.bll;

import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.util.List;

public class GenreManager implements ILogicFacade {
    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    IGenreDAO genreDAO;

    public GenreManager(){
        this.genreDAO = abstractDAOFactory.genreDAO();
    }
    @Override
    public List<Object> getAllObject() throws ApplicationException {
        return this.genreDAO.getAllGenre();
    }

    @Override
    public void createObject(Object object) throws ApplicationException {
            // does nothing for now
    }

    @Override
    public void updateObject(Object object) throws ApplicationException {
        // does nothing for now
    }

    @Override
    public boolean deleteObject(Object object) throws ApplicationException {
        // does nothing for now
        return false;
    }

    @Override
    public List<Object> searchObjects(List<Object> list, String query) {
        return null;
    }
}
