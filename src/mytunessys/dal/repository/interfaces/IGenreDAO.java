package mytunessys.dal.repository.interfaces;

import mytunessys.be.Genre;
import mytunessys.bll.exceptions.ApplicationException;

import java.util.List;

public interface IGenreDAO {
    /**
     * Gets all genre from database
     *
     * @return List of genres from database
     * @throws Exception when problem with fetching all genre from db occurs
     */
    List<Genre> getAllGenre() throws Exception;
}

