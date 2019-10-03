package org.maple.tsc.service;

import java.util.List;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.models.TeacherCourseXrefModel;
import org.maple.tsc.models.UserModel;

public interface TeacherCourseXrefService {
	
	/**
	 * Insert a relationship between teacher and course record. 
	 * 
	 * @param record
	 * @return
	 * @throws TSCException
	 */
    int insert(TeacherCourseXrefModel record) throws TSCException;
    
    /**
     * Delete a record by teacher id and course id.
     * 
     * @param teacherId
     * @param courseId
     * @return
     * @throws TSCException
     */
    int deleteByTeacherIdAndCourseId(Long teacherId, Long courseId) throws TSCException;
    
    /**
     * Get a list of course by teacher id.
     * 
     * @param teacherId
     * @return
     * @throws TSCException
     */
    List<CourseModel> selectCourseLisByTeacherId(Long teacherId) throws TSCException;
    
    /**
     * Get a list of teacher by course id.
     * 
     * @param courseId
     * @return
     * @throws TSCException
     */
    List<UserModel> selectTeacherListByCourseId(Long courseId) throws TSCException;
    
    /**
     * Get a list of TeacherCourseXrefModel contains courseModel and teacherModel without studentList.
     * 
     * @return
     */
    List<TeacherCourseXrefModel> getAllWithCourseTeacherInfo();
    
    /**
     * Remove softly(set is_active to false) a record by id.
     * 
     * @param id
     * @return
     * @throws TSCException
     */
    int removeById(Long id) throws TSCException;
    
    int updateSelective(TeacherCourseXrefModel record) throws TSCException;
}
