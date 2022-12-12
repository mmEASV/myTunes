/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunessys.bll.interfaces;


import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Tomas Simko, MatejMazur
 */
public interface ILogicFacade<T>
{
    /**
     * Gets list of object that needs to be castes into given object
     * @return object that will be cast in order to get list of Songs
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */
    List<T> getAllObject() throws ApplicationException;

    /**
     * Creates object that will be cast
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */
    T getObjectById(T object) throws ApplicationException;

    /**
     * Creates object that will be cast
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */
    void createObject(T object) throws ApplicationException;

    /**
     * Creates object
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */

    void updateObject(T object) throws ApplicationException;

    /**
     *
     * @param object
     * @return
     */
    boolean deleteObject(T object) throws ApplicationException;

    /**
     *
     * @param list
     * @param query
     * @return
     */
    List<T> searchObjects(List<T> list,String query);

    /**
     *
     * @param object
     * @param secondObject
     * @return
     * @throws ApplicationException
     */
    boolean addToObject(Object object, Object secondObject) throws ApplicationException;

    /**
     *
     * @return
     * @throws ApplicationException
     */
    boolean removeObjectFrom(Object object) throws ApplicationException;


}
