package org.maple.tsc.service;

import java.util.List;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.models.StudentCourseXrefModel;
import org.maple.tsc.models.UserModel;

public interface StudentCourseXrefService {

	List<CourseModel> selectCourseListByStudentId(Long studentId) throws TSCException;
	
	List<UserModel> selectStudentListByCourseId(Long courseId) throws TSCException;
	
	int insert(StudentCourseXrefModel record) throws TSCException;
	
	int updateByPrimaryKey(StudentCourseXrefModel record) throws TSCException;
	
	int deleteByCourseIdAndStudentId(Long courseId, Long studentId) throws TSCException;
	
	List<UserModel> getStudentListByTCXRefId(Long tcxrefId) throws TSCException;
}
