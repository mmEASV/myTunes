package mytunessys.bll;

import mytunessys.be.Genre;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.types.DatabaseType;
import mytunessys.dal.AbstractDAOFactory;
import mytunessys.dal.repository.interfaces.IGenreDAO;

import java.util.List;

public class GenreManager implements ILogicFacade<Genre> {
    AbstractDAOFactory abstractDAOFactory = AbstractDAOFactory.getDAO(DatabaseType.MSSQL);
    IGenreDAO genreDAO;

    public GenreManager(){
        this.genreDAO = abstractDAOFactory.genreDAO();
    }
    @Override
    public List<Genre> getAllObject() throws ApplicationException {
        return this.genreDAO.getAllGenre();
    }

    @Override
    public void createObject(Genre object) throws ApplicationException {
            // does nothing for now
    }

    @Override
    public void updateObject(Genre object) throws ApplicationException {
        // does nothing for now
    }

    @Override
    public boolean deleteObject(Genre object) throws ApplicationException {
        // does nothing for now
        return false;
    }

    @Override
    public List<Genre> searchObjects(List<Genre> list, String query) {
        return null;
    }
}
