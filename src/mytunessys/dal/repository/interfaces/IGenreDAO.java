package mytunessys.dal.repository.interfaces;

import mytunessys.bll.exceptions.CustomException;

import java.util.List;

public interface IGenreDAO {
    /**
     * Gets all genre from database
     *
     * @return List of object genre from genreDAO
     * @throws CustomException when problem with fetching all genre from db occurs
     */
    List<Object> getAllGenre() throws CustomException;
}

