package mytunessys.bll.interfaces;

import mytunessys.bll.exceptions.ApplicationException;

import java.util.List;

/**
 * @author Tomas Simko
 */
public interface ILogicFacade<T> {
    /**
     * Method to be called in order to retrieve List<T> of specified objects
     *
     * @return List<T> of specified objects
     * @throws ApplicationException if it is not possible to retrieve needed objects from defined manager and its DAO
     */
    List<T> getAllObject() throws ApplicationException;

    /**
     * Method that creates <T> object
     *
     * @param object that is needed to be created inside the database of specified object
     * @throws ApplicationException if it is not possible to create needed objects from defined manager and its DAO
     */
    void createObject(T object) throws ApplicationException;

    /**
     * Method that updates <T> object
     *
     * @param object that is needed to be updated inside the database of specified object
     * @throws ApplicationException if it is not possible to update needed objects from defined manager and its DAO
     */
    void updateObject(T object) throws ApplicationException;

    /**
     * @param object that will be deleted if provided correct id
     * @return true if successfully deleted from the database by its id else return false
     * @throws ApplicationException it is not possible to delete needed objects with its id
     */
    boolean deleteObject(T object) throws ApplicationException;

    /**
     * @param list  of object that search method will iterate through
     * @param query that needs to be find inside the list
     * @return all the result if they are find defined in query
     */
    List<T> searchObjects(List<T> list, String query);

}
