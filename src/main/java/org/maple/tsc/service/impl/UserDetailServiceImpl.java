package org.maple.tsc.service.impl;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.UserDetailModelMapper;
import org.maple.tsc.models.UserDetailModel;
import org.maple.tsc.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class UserDetailServiceImpl extends BaseServiceImpl implements UserDetailService{

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(UserDetailServiceImpl.class);
	
	@Autowired
	UserDetailModelMapper userDetailModelMapper;
	
	@Override
	public UserDetailModel selectByUserId(Long userId) throws TSCException {
		if(null == userId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);			
		}
		return userDetailModelMapper.selectByUserId(userId);
	}

	@Override
	public int insert(UserDetailModel record) throws TSCException {
		validate(record);
		return userDetailModelMapper.insert(record);
	}

	@Override
	public UserDetailModel selectByAccountId(Long accountId) throws TSCException {
		if(null == accountId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);			
		}
		return userDetailModelMapper.selectByAccountId(accountId);
	}
	
	@Override
	public int updateById(UserDetailModel record) throws TSCException {
		validate(record);
		if(record.getId() == null) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);			
		}
		return userDetailModelMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * Validate the not null fields of record.
	 * 
	 * @param record
	 * @throws TSCException
	 */
	private void validate(UserDetailModel record) throws TSCException {
		if(null == record) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
		}
		else if(null == record.getUserId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);			
		}
		else if(null == record.getLastUpdateDt()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DATE);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DATE);		
		}

	}



}
