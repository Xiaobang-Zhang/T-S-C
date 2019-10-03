package org.maple.tsc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.CourseModelMapper;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(CourseServiceImpl.class);
	
	@Autowired
	CourseModelMapper courseModelMapper;
	
	private void validate(CourseModel record) throws TSCException{
		if(null == record){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
		}
		else if(null == record.getCourseNumber()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_NUMBER);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_NUMBER);		
		}
		else if(null == record.getCreatedDt()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_CREATED_DT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_CREATED_DT);		
		}
		else if(null == record.getLastUpdateDt()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DT);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_LAST_UPDATE_DT);				
		}
	}
	
	@Override
	public List<CourseModel> getAllCourses() {
		return courseModelMapper.selectAll();
	}

	@Override
	public Integer addCourse(CourseModel course) throws TSCException {
		validate(course);
		return courseModelMapper.insertSelective(course);

	}

	@Override
	public Integer removeCourseById(Long courseId) throws TSCException {
		if(null == courseId){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
		return courseModelMapper.deleteByPrimaryKey(courseId);
	}

	@Override
	public Integer removeCourseByCourseNumber(Long courseNumber) throws TSCException {
		if(null == courseNumber) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_NUMBER);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_NUMBER);			
		}
		return courseModelMapper.deleteByCourseNumber(courseNumber);
	}

	@Override
	public CourseModel getCourseByCourseNumber(Long courseNumber) throws TSCException {
		if(null == courseNumber){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_NUMBER);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_NUMBER);		
		}
		return courseModelMapper.selectByCourseNumber(courseNumber);
	}

	@Override
	public int updateCourseSelective(CourseModel course) throws TSCException {
		validate(course);
		if(null == course.getId()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);			
		}
		return courseModelMapper.updateByPrimaryKeySelective(course);
	}

	@Override
	public CourseModel getCourseById(Long id) throws TSCException {
		if(null == id){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);		
		}
		return courseModelMapper.selectByPrimaryKey(id);
	}

}
