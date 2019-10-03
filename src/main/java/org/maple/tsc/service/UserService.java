/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.service;

import java.util.List;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.models.UserModel;

public interface UserService {
	
    UserModel selectById(Long id) throws TSCException;
    
    UserModel selectByAccountId(Long accountId) throws TSCException;
    
    /**
     * Delete a user record by id. The way of deleting a record is soft deleting, it means that just set the is_active field to false.
     * 
     * @param id
     * @return
     * @throws TSCException
     */
    int deleteById(Long id) throws TSCException;
    
    /**
     * Delete a user record by accountId. The way of deleting a record is soft deleting, it means that just set the is_active field to false.
     * 
     * @param accountId
     * @return
     * @throws TSCException
     */
    int deleteByAccountId(Long accountId) throws TSCException;
    
    List<UserModel> getAllUsers();
    
    int updateByPrimaryKeySelective(UserModel record) throws TSCException;
    
    UserModel addUserWithDetail(UserModel record) throws TSCException;
    
    UserModel getUserWithDetailById(Long id) throws TSCException;
    
    CodetableModel getUserRoleById(Long id) throws TSCException;
    
    int updateByAccountIdSelective(UserModel record) throws TSCException;
    
    String getUserPasswordByUserId(Long userId) throws TSCException;
    
    int updateUserPasswordByUserId(Long userId, String password) throws TSCException;
}