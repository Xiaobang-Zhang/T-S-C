package org.maple.tsc.service;

import java.util.List;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CourseModel;

public interface CourseService {
	
	List<CourseModel> getAllCourses();
	
	Integer addCourse(CourseModel course) throws TSCException;
	
	Integer removeCourseById(Long courseId) throws TSCException;
	
	Integer removeCourseByCourseNumber(Long courseNumber) throws TSCException;
	
	CourseModel getCourseByCourseNumber(Long courseNumber) throws TSCException;
	
    int updateCourseSelective(CourseModel course) throws TSCException;
    
    CourseModel getCourseById(Long id) throws TSCException;
}
