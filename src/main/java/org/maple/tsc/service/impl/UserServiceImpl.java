/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.UserModelMapper;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.models.UserModel;
import org.maple.tsc.service.CodeTableService;
import org.maple.tsc.service.UserDetailService;
import org.maple.tsc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	
	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserModelMapper userModelMapper;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	CodeTableService codetableService;
	
	private void validate(UserModel record) throws TSCException {
		if(null == record) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);			
		}
		else if(null == record.getAccountId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);			
		}
		else if(null == record.getName()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_NAME);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_NAME);			
		}
	}
	
	@Override
	public UserModel selectById(Long id) throws TSCException {
		if(null == id) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);			
		}
		return userModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserModel selectByAccountId(Long accountId) throws TSCException {
		if(null == accountId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}
		return userModelMapper.selectByAccountId(accountId);
	}

	@Override
	public int deleteById(Long id) throws TSCException {
		if(null == id) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}
		
		return userModelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByAccountId(Long accountId) throws TSCException {
		if(null == accountId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}
		return userModelMapper.delectByAccountId(accountId);
	}

	@Override
	public List<UserModel> getAllUsers() {
		return userModelMapper.selectAllUsers();
	}

	@Override
	public int updateByPrimaryKeySelective(UserModel record) throws TSCException {
		validate(record);
		if(null == record.getId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);			
		}
		return userModelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public UserModel addUserWithDetail(UserModel record) throws TSCException {
		validate(record);
		userModelMapper.insertSelective(record);
		if(null != record.getUserDetail()) {
			record.getUserDetail().setLastUpdateDt(new Date());
			record.getUserDetail().setUserId(record.getId());
			userDetailService.insert(record.getUserDetail());
		}
		return record;
	}

	@Override
	public UserModel getUserWithDetailById(Long id) throws TSCException {
		if(null == id) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}
		return userModelMapper.selectWithDetailById(id);
	}

	@Override
	public CodetableModel getUserRoleById(Long id) throws TSCException {
		if(null == id) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}		
		UserModel result = userModelMapper.selectByPrimaryKey(id);
		return codetableService.selectById(result.getUserRole());

	}

	@Override
	public int updateByAccountIdSelective(UserModel record) throws TSCException {
		if(null == record.getAccountId()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}
		return userModelMapper.updateByAccountIdSelective(record);
	}

	@Override
	public String getUserPasswordByUserId(Long userId) throws TSCException {
		if(null == userId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}	
		return userModelMapper.selectPasswordByUserId(userId);
	}

	@Override
	public int updateUserPasswordByUserId(Long userId, String password) throws TSCException {
		if(null == userId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ACCOUNT_ID);
		}
		if(null == password) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_PASSWORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_PASSWORD);
		}
		return userModelMapper.updateUserPasswordByUserId(userId, password);
	}
}