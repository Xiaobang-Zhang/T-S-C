package org.maple.tsc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.mappers.StudentCourseXrefModelMapper;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.models.StudentCourseXrefModel;
import org.maple.tsc.models.UserModel;
import org.maple.tsc.service.StudentCourseXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service
public class StudentCourseXrefServiceImpl extends BaseServiceImpl implements StudentCourseXrefService {

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(StudentCourseXrefServiceImpl.class);
	
	@Autowired
	StudentCourseXrefModelMapper studentCourseXrefModelMapper;
	
	/**
	 * Validate all fields NOT NULL except id.
	 * 
	 * @param record
	 * @throws TSCException
	 */
	public void validate(StudentCourseXrefModel record) throws TSCException {
		if(null == record) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);		
		}
		else if(null == record.getStudentId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);		
		}
		else if(null == record.getTeacherCourseXrefId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_COURSE_XREF_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_COURSE_XREF_ID);		
		}
	}
	
	@Override
	public List<CourseModel> selectCourseListByStudentId(Long studentId) throws TSCException {
		if(null == studentId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
		}
		return studentCourseXrefModelMapper.selectCourseLisByStudentId(studentId);
	}

	@Override
	public List<UserModel> selectStudentListByCourseId(Long courseId) throws TSCException {
		if(null == courseId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
		}
		return studentCourseXrefModelMapper.selectStudentListByCourseId(courseId);
	}

	@Override
	public int insert(StudentCourseXrefModel record) throws TSCException {
		validate(record);
		return studentCourseXrefModelMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(StudentCourseXrefModel record) throws TSCException {
		validate(record);
		if(null == record.getId()) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_ID);	
		}
		return studentCourseXrefModelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByCourseIdAndStudentId(Long courseId, Long studentId) throws TSCException {
		if(null == courseId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_ID);			
		}
		else if(null == studentId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_USER_ID);			
		}
		return studentCourseXrefModelMapper.deleteByStudentIdAndCourseId(studentId, courseId);
	}

	@Override
	public List<UserModel> getStudentListByTCXRefId(Long tcxrefId) throws TSCException {
		if(null == tcxrefId) {
			logger.error(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_COURSE_XREF_ID);
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_COURSE_XREF_ID);			
		}
		return studentCourseXrefModelMapper.selectStudentListByTCXRefId(tcxrefId);
	}
}
