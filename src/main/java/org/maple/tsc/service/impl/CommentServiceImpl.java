package org.maple.tsc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.CommentModelMapper;
import org.maple.tsc.models.CommentModel;
import org.maple.tsc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class CommentServiceImpl extends BaseServiceImpl implements CommentService {

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(CommentServiceImpl.class);
	
	@Autowired
	CommentModelMapper commentModelMapper;
	
	/**
	 * Validate all NOT NULL fields except id.
	 * 
	 * @param record
	 * @throws TSCException
	 */
	private void validate(CommentModel record) throws TSCException {
		if(null == record){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
		}
		else if(null == record.getContent()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CONTENT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CONTENT);
		}
		else if(null == record.getOwnerId()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
		}
		else if(null == record.getTopicId()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
		}
		else if(null == record.getCreatedDt()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CREATED_DT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CREATED_DT);
		}
		else if(null == record.getLastUpdateDt()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DT);
		}
	}
	
	@Override
	public CommentModel selectById(Long id) throws TSCException {
		if(null == id){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
		}
		return commentModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CommentModel> selectListByTopicId(Long topicId) throws TSCException {
		if(null == topicId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
		}
		return commentModelMapper.selectListByTopicId(topicId);
	}

	@Override
	public List<CommentModel> selectListByOwnerId(Long ownerId) throws TSCException {
		if(null == ownerId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
		}
		return commentModelMapper.selectListByTopicId(ownerId);
	}

	@Override
	public int insert(CommentModel record) throws TSCException {
		record.setCreatedDt(new Date());
		record.setLastUpdateDt(new Date());
		validate(record);
		return commentModelMapper.insertSelective(record);
	}

	@Override
	public int deleteById(Long id) throws TSCException {
		if(null == id){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
		}
		return commentModelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByOwnerIdAndTopicId(Long ownerId, Long topicId) throws TSCException {
		if(null == ownerId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
		}
		else if(null == topicId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
		}
		return commentModelMapper.deleteByOwnerIdAndTopicId(ownerId, topicId);
	}

	@Override
	public List<CommentModel> getListWithOwnerDetailAndSuperCommentByTopicId(Long topicId) throws TSCException {
		if(null == topicId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TOPIC_ID);
		}
		return commentModelMapper.selectListWithOwnerDetailAndSuperCommentByTopicId(topicId);
	}

	@Override
	public Integer update(CommentModel record) throws TSCException {
		record.setLastUpdateDt(new Date());
		validate(record);
		return commentModelMapper.updateByPrimaryKeySelective(record);
	}
}
