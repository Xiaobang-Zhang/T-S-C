package org.maple.tsc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.TeacherCourseXrefModelMapper;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.models.TeacherCourseXrefModel;
import org.maple.tsc.models.UserModel;
import org.maple.tsc.service.TeacherCourseXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = true, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class TeacherCourseXrefServiceImpl extends BaseServiceImpl implements TeacherCourseXrefService {

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(TeacherCourseXrefServiceImpl.class);
	
	@Autowired
	TeacherCourseXrefModelMapper teacherCourseXrefModelMapper;
	
	private void validate(TeacherCourseXrefModel record) throws TSCException {
		if(null == record){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
		}
		else if(null == record.getTeacherId()){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_ID);
		}
		else if(null == record.getCourseId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
	}
	
	@Override
	public int insert(TeacherCourseXrefModel record) throws TSCException {
		validate(record);
		return teacherCourseXrefModelMapper.insertSelective(record);
	}

	@Override
	public List<CourseModel> selectCourseLisByTeacherId(Long teacherId) throws TSCException {
		if(null == teacherId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_ID);
		}
		return teacherCourseXrefModelMapper.selectCourseLisByTeacherId(teacherId);
	}

	@Override
	public List<UserModel> selectTeacherListByCourseId(Long courseId) throws TSCException {
		if(null == courseId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
		return teacherCourseXrefModelMapper.selectTeacherListByCourseId(courseId);
	}
	

	@Override
	public int deleteByTeacherIdAndCourseId(Long teacherId, Long courseId) throws TSCException {
		if(null == teacherId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_ID);
		}
		else if(null == courseId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
		return teacherCourseXrefModelMapper.deleteByTeacherIdAndCourseId(teacherId, courseId);
	}

	@Override
	public List<TeacherCourseXrefModel> getAllWithCourseTeacherInfo() {
		return teacherCourseXrefModelMapper.selectAllWithCourseTeacherInfo();
	}

	@Override
	public int removeById(Long id) throws TSCException {
		if(null == id){
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);		
		}
		return teacherCourseXrefModelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateSelective(TeacherCourseXrefModel record) throws TSCException {
		validate(record);
		if(null == record.getId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);			
		}
		return teacherCourseXrefModelMapper.updateByPrimaryKeySelective(record);
	}
	
	
}
