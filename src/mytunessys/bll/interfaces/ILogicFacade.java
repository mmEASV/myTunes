/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunessys.bll.interfaces;


import java.util.List;

/**
 *
 * @author Tomas Simko, Matej Mazur
 */
public interface ILogicFacade<T>
{
    /**
     * Gets list of object that from the manager
     * @return object that will be cast in order to get list of Songs
     * @throws Exception if fails to establish connection due to some unexpected reasons
     */
    List<T> getAllObject() throws Exception;

    /**
     * Creates object that will be fetched by its id
     * @param object that will be fetched by its id
     * @throws Exception if fails to establish connection due to some unexpected reasons
     */
    T getObjectById(T object) throws Exception;

    /**
     * Creates object that will be created
     * @param object that will be created
     * @throws Exception if fails to establish connection due to some unexpected reasons
     */
    void createObject(T object) throws Exception;

    /**
     * creates object
     * @param object that will be updated by its id
     * @throws Exception if fails to establish connection due to some unexpected reasons
     */

    void updateObject(T object) throws Exception;

    /**
     * deletes object by its id
     * @param object that will be removed
     * @return false if not able to delete
     */
    boolean deleteObject(T object) throws Exception;

    /**
     * executes simple iteration (Linear search) by the query that is provided
     * @param list to be iterated
     * @param query to be found inside the list
     * @return list that contains query
     */
    List<T> searchObjects(List<T> list,String query);

    /**
     * adds song to playlist
     * @param object first object that will be added
     * @param secondObject object that will add first object
     * @return false if not added
     * @throws Exception if not able to remove it from its playlist
     */
    boolean addToObject(Object object, Object secondObject) throws Exception;

    /**
     * removes songs from playlist
     * @return false if object could not have been removed
     * @throws Exception if not able to remove it from its playlist
     */
    boolean removeObjectFrom(Object object) throws Exception;


}
