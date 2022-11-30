/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunessys.bll.interfaces;

import java.util.List;

/**
 *
 * @author
 */
public interface ILogicFacade
{
    /**
     *
     * @return
     */
    List<Object> getAllObject();

    /**
     *
     * @param object
     */
    void createObject(Object object);

    /**
     *
     * @param object
     */

    void updateObject(Object object);

    /**
     *
     * @param id
     * @return
     */
    boolean deleteObject(int id);

    /**
     *
     * @param list
     * @param query
     * @return
     */
    List<Object> searchObjects(List<Object> list,String query);
}