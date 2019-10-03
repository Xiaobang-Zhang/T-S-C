package org.maple.tsc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.CommonConstants;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.TopicModelMapper;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.models.TopicModel;
import org.maple.tsc.service.CommentService;
import org.maple.tsc.service.TopicService;
import org.maple.tsc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class TopicServiceImpl extends BaseServiceImpl implements TopicService{

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(TopicServiceImpl.class);
	
	@Autowired
	TopicModelMapper topicModelMapper;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	
	/**
	 * Validate all NOT NULL fields except id.
	 * 
	 * @param record
	 * @throws TSCException
	 */
	private void validate(TopicModel record) throws TSCException {
		if(record == null) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
		}
		else if(null == record.getOwnerId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
		}
		else if(null == record.getCategoryId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CATEGORY);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CATEGORY);
		}
		else if(null == record.getCourseId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
		
		else if(null == record.getTitle()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TITLE);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TITLE);
		}
		else if(null == record.getContent()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CONTENT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CONTENT);
		}
		else if(null == record.getCreatedDt()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CREATED_DT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CREATED_DT);
		}
		else if(null == record.getLastUpdateDt()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DT);
		}
	}
	
	@Override
	public List<TopicModel> selectListByUserId(Long userId) throws TSCException {
		if(null == userId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
		}
		return topicModelMapper.selectListByUserId(userId);
	}

	@Override
	public List<TopicModel> selectListByCourseId(Long courseId) throws TSCException {
		if(null == courseId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
		return topicModelMapper.selectListByCourseId(courseId);
	}

	@Override
	public int insert(TopicModel record) throws TSCException {
		validate(record);
		return topicModelMapper.insertSelective(record);
	}

	@Override
	public int deleteById(Long id) throws TSCException {
		if(null == id) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);		
		}
		return topicModelMapper.deleteByPrimaryKey(id);
	}
	

	@Override
	public List<TopicModel> getAll() {
		return topicModelMapper.selectAll();
	}

	@Override
	public TopicModel getTopicById(Long topicId) throws TSCException {
		if(null == topicId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);		
		}
		TopicModel result = topicModelMapper.selectByPrimaryKey(topicId);
		result.setCommentModelList(commentService.getListWithOwnerDetailAndSuperCommentByTopicId(topicId));
		return result;
	}

	@Override
	public Integer updateTopic(TopicModel topic) throws TSCException {
		validate(topic);
		if(null == topic.getId()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);			
		}
		return topicModelMapper.updateByPrimaryKeySelective(topic);
	}

	@Override
	public Integer addTopic(TopicModel topic) throws TSCException {
		topic.setCreatedDt(new Date());
		topic.setLastUpdateDt(new Date());
		validate(topic);
		return topicModelMapper.insertSelective(topic);
	}

	@Override
	public List<TopicModel> getTopicListByUserIdAndCount(Long userId, Integer count) throws TSCException {
		CodetableModel userRoleCode = userService.getUserRoleById(userId);
		if(CommonConstants.CODETABLE_NAME_TEACHER.equals(userRoleCode.getName())){
			return topicModelMapper.selectListByTeacherIdAndCount(userId, count);
		}
		else if(CommonConstants.CODETABLE_NAME_STUDENT.equals(userRoleCode.getName())){
			return topicModelMapper.selectListByStudentIdAndCount(userId, count);
		}
		else {
			return null;
		}
	}

	@Override
	public List<TopicModel> getTopicListTopByCount(Integer count) {
		if(count == null || count <= 0)
			count = 3;
		
		return topicModelMapper.selectListTopByCount(count);
	}
}
