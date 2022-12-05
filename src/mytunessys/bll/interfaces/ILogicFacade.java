/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunessys.bll.interfaces;

import mytunessys.bll.exceptions.ApplicationException;

import java.util.List;

/**
 *
 * @author Tomas Simko, MatejMazur
 */
public interface ILogicFacade
{
    /**
     * Gets list of object that needs to be castes into given object
     * @return object that will be cast in order to get list of Songs
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */
    List<Object> getAllObject() throws ApplicationException;

    /**
     * Creates object that will be cast
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */
    void createObject(Object object) throws ApplicationException;

    /**
     * Creates object
     * @throws ApplicationException if fails to establish connection due to some unexpected reasons
     */

    void updateObject(Object object) throws ApplicationException;

    /**
     *
     * @param object
     * @return
     */
    boolean deleteObject(Object object) throws ApplicationException;

    /**
     *
     * @param list
     * @param query
     * @return
     */
    List<Object> searchObjects(List<Object> list,String query);

}
