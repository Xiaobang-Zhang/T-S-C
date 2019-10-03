package org.maple.tsc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.CodetableModelMapper;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.service.CodeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = true, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class CodeTableServiceImpl extends BaseServiceImpl implements CodeTableService{

	Logger logger = Logger.getLogger(CodeTableServiceImpl.class);
	
	@Autowired
	CodetableModelMapper codetableModelMapper;
	
	@Override
	public CodetableModel selectById(Long id) throws TSCException {
		if(null == id) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
		}
		return codetableModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public CodetableModel selectByName(String name) throws TSCException {
		if(null == name){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_NAME);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_NAME);
		}
		return codetableModelMapper.selectByName(name);
	}

	@Override
	public List<CodetableModel> selectListByCategory(String category) throws TSCException {
		if(null == category){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CATEGORY);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CATEGORY);
		}
		return codetableModelMapper.selectListByCategory(category);
	}

}
