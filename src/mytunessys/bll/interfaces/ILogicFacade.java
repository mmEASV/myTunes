/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunessys.bll.interfaces;

import mytunessys.bll.exceptions.CustomException;

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
     * @throws CustomException if fails to establish connection due to some unexpected reasons
     */
    List<Object> getAllObject() throws CustomException;

    /**
     * Creates object that will be cast
     * @throws CustomException if fails to establish connection due to some unexpected reasons
     */
    void createObject(Object object) throws CustomException;

    /**
     * Creates object
     * @throws CustomException if fails to establish connection due to some unexpected reasons
     */

    void updateObject(Object object) throws CustomException;

    /**
     *
     * @param object
     * @return
     */
    boolean deleteObject(Object object) throws CustomException;

    /**
     *
     * @param list
     * @param query
     * @return
     */
    List<Object> searchObjects(List<Object> list,String query);

}
