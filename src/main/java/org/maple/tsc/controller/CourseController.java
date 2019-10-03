package org.maple.tsc.controller;

import java.util.Date;
import java.util.List;

import org.maple.tsc.constants.CommonConstants;
import org.maple.tsc.constants.ErrorConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.models.CourseModel;
import org.maple.tsc.models.StudentCourseXrefModel;
import org.maple.tsc.models.TeacherCourseXrefModel;
import org.maple.tsc.models.UserModel;
import org.maple.tsc.service.CodeTableService;
import org.maple.tsc.service.CourseService;
import org.maple.tsc.service.StudentCourseXrefService;
import org.maple.tsc.service.TeacherCourseXrefService;
import org.maple.tsc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@RestController
@RequestMapping(value = "/course")
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	TeacherCourseXrefService teacherCourseXrefService;
	
	@Autowired
	StudentCourseXrefService studentCourseXrefService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CodeTableService codetableService;
	

	@RequestMapping(value = "/checkCourseNumber", method = RequestMethod.GET)
	public Boolean checkCourseNumber(Long courseNumber) throws TSCException{
		return courseService.getCourseByCourseNumber(courseNumber) == null;
	}
	
	@RequestMapping(value = "/getCourseByCourseNumber", method = RequestMethod.GET)
	public CourseModel getCourseByCourseNumber(Long courseNumber) throws TSCException{
		return courseService.getCourseByCourseNumber(courseNumber);
	}
	
	@RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
	public List<CourseModel> getAllcourses(){
		return courseService.getAllCourses();
	}
	
	@RequestMapping(value = "/deleteCoursesByIdList", method = RequestMethod.POST)
	public Boolean deleteCoursesByIdList(@RequestBody List<Long> courseIdList) throws TSCException{
		int count = 0;
		for(Long courseId : courseIdList){
			count += courseService.removeCourseById(courseId);
		}
		if(count < courseIdList.size()){
			return false;
		}
		else {
			return true;
		}
	}
	
	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	public CourseModel addCourse(@RequestBody CourseModel course) throws TSCException{
		course.setCreatedDt(new Date());
		course.setLastUpdateDt(new Date());
		courseService.addCourse(course);
		
		return course;	// This course has id.

	}

	@RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
	public Integer updateCourse(@RequestBody CourseModel course) throws TSCException{
		course.setLastUpdateDt(new Date());
		return courseService.updateCourseSelective(course);
	}
	
	@RequestMapping(value = "/getAllTCXrefs", method = RequestMethod.GET)
	public List<TeacherCourseXrefModel> getAllTCXRefWithCourseTeacherInfo() {
		return teacherCourseXrefService.getAllWithCourseTeacherInfo();
	}

	@RequestMapping(value = "/addTCXRef", method = RequestMethod.POST)
	public TeacherCourseXrefModel addTCXRef(@RequestBody TeacherCourseXrefModel tcxref) throws TSCException{
		// set some necessary value from model
		setTCXRefValues(tcxref);
		teacherCourseXrefService.insert(tcxref);

		if(tcxref.getStudentModelList() != null) {	// Insert student course records
			for(UserModel stu : tcxref.getStudentModelList()){
				StudentCourseXrefModel record = new StudentCourseXrefModel();
				record.setStudentId(stu.getId());
				record.setTeacherCourseXrefId(tcxref.getId());
				studentCourseXrefService.insert(record);
			}
		}
		return tcxref;	// with id
	}
	
	@RequestMapping(value = "/deleteTCXRefsByIdList", method = RequestMethod.POST)
	public Boolean deleteTCXRefsByIdList(@RequestBody List<Long> tcrefIdList) throws TSCException{
		int count = 0;
		for(Long tcrefId : tcrefIdList){
			count += teacherCourseXrefService.removeById(tcrefId);
		}
		if(count < tcrefIdList.size()){
			return false;
		}
		else {
			return true;
		}
	}
	
	@RequestMapping(value = "/updateTCXRef", method = RequestMethod.POST)
	public Integer updateCourse(@RequestBody TeacherCourseXrefModel tcxref) throws TSCException{
		setTCXRefValues(tcxref);
		int count = teacherCourseXrefService.updateSelective(tcxref);
		updateTCXRefStudentRecords(tcxref);
		return count;
	}
	
	@RequestMapping(value = "/getAllStudentsByTCXRefId", method = RequestMethod.GET)
	public List<UserModel> getAllStudentsByTCXRefId(Long tcxrefId) throws TSCException {
		return studentCourseXrefService.getStudentListByTCXRefId(tcxrefId);
	}
	
	@RequestMapping(value = "/getAllCoursesByUserId", method = RequestMethod.GET)
	public List<CourseModel> getAllCoursesByStudentId(@RequestParam("userId") Long userId) throws TSCException {
		
		UserModel user = userService.selectById(userId);
		CodetableModel userRoleCode = codetableService.selectById(user.getUserRole());
		if(CommonConstants.CODETABLE_NAME_TEACHER.equals(userRoleCode.getName())){
			return teacherCourseXrefService.selectCourseLisByTeacherId(userId);
		}
		else if(CommonConstants.CODETABLE_NAME_STUDENT.equals(userRoleCode.getName())){
			return studentCourseXrefService.selectCourseListByStudentId(userId);
		}
		else {
			throw new TSCException("Unknown user role");
		}
		
	}
	
	@RequestMapping(value = "/getCourseById", method = RequestMethod.GET)
	public CourseModel getCourseById(@RequestParam("id") Long id) throws TSCException{
		return courseService.getCourseById(id);
	}
	
	private void updateTCXRefStudentRecords(TeacherCourseXrefModel tcxref) throws TSCException {
		if(tcxref.getStudentModelList() != null){
			List<UserModel> originStuList = studentCourseXrefService.getStudentListByTCXRefId(tcxref.getId());
			
			// Resolve the repeat element in two collection
			for(int i = 0; i < originStuList.size(); i++){
				
				if(tcxref.getStudentModelList().size() == 0)
					break;
				for(int j = 0; j < tcxref.getStudentModelList().size(); j++){
					if(originStuList.get(i).getId() == tcxref.getStudentModelList().get(j).getId()) {
						originStuList.remove(i);
						tcxref.getStudentModelList().remove(j);
						i--;
						j--;
						break;
					}
				}
			}
			
			// delete all elements in originStuList
			for(UserModel stu : originStuList) {
				studentCourseXrefService.deleteByCourseIdAndStudentId(tcxref.getCourseId(), stu.getId());
			}
			// insert all elements in tcxref's stuList
			for(UserModel stu : tcxref.getStudentModelList()){
				StudentCourseXrefModel record = new StudentCourseXrefModel();
				record.setStudentId(stu.getId());
				record.setTeacherCourseXrefId(tcxref.getId());
				
				studentCourseXrefService.insert(record);
			}
		}
	}
	
	private void setTCXRefValues(TeacherCourseXrefModel tcxref) throws TSCException {
		if(tcxref == null){
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_RECORD);
		}
		else if(tcxref.getTeacherModel() == null){
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_TEACHER_MODEL);
		}
		else if(tcxref.getCourseModel() == null){
			throw new TSCException(ErrorConstants.TSC_INPUT_PARAM_NO_COURSE_MODEL);
		}
		
		tcxref.setTeacherId(tcxref.getTeacherModel().getId());
		tcxref.setCourseId(tcxref.getCourseModel().getId());
	}
	
}
