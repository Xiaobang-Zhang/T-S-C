package org.maple.tsc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.models.TeacherCourseXrefModel;
import org.maple.tsc.models.UserModel;

public interface TeacherCourseXrefModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course_xref
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course_xref
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int insert(TeacherCourseXrefModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course_xref
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int insertSelective(TeacherCourseXrefModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course_xref
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    TeacherCourseXrefModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course_xref
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int updateByPrimaryKeySelective(TeacherCourseXrefModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course_xref
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int updateByPrimaryKey(TeacherCourseXrefModel record);
    
    // --------- Below is customized methods ----------------
    
    List<CourseModel> selectCourseLisByTeacherId(Long teacherId);
    
    List<UserModel> selectTeacherListByCourseId(Long courseId);
    
    int deleteByTeacherIdAndCourseId(@Param("teacherId")Long teacherId, @Param("courseId")Long courseId);
    
    List<TeacherCourseXrefModel> selectAllWithCourseTeacherInfo();
}