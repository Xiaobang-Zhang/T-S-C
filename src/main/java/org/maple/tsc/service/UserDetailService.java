package org.maple.tsc.service;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.UserDetailModel;

public interface UserDetailService {
	
	/**
	 * Select a user detail record by userId.
	 * 
	 * @param userId
	 * @return
	 * @throws TSCException
	 */
	UserDetailModel selectByUserId(Long userId) throws TSCException;
	
	/**
	 * Select a user detail record by accountId.
	 * 
	 * @param accountId
	 * @return
	 * @throws TSCException
	 */
	UserDetailModel selectByAccountId(Long accountId) throws TSCException;
	
	/**
	 * Insert a user detail record.
	 * 
	 * @param userDetail
	 * @return
	 * @throws TSCException
	 */
	int insert(UserDetailModel userDetail) throws TSCException;
	
	int updateById(UserDetailModel userDetail) throws TSCException;
}
