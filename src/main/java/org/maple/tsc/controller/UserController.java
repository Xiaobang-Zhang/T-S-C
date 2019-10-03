/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.maple.tsc.constants.CommonConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.models.UserDetailModel;
import org.maple.tsc.models.UserModel;
import org.maple.tsc.service.CodeTableService;
import org.maple.tsc.service.UserDetailService;
import org.maple.tsc.service.UserService;
import org.maple.tsc.utils.TSCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	CodeTableService CodeTableService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	TSCUtils tscUtils;
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<UserModel> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/getAllUserRoleCategory", method = RequestMethod.GET)
	public List<CodetableModel> getAllUserRoleCategoryCodetable() throws TSCException {
		return CodeTableService.selectListByCategory(CommonConstants.CODETABLE_CATEGORY_USER_ROLE);
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public Integer updateUser(@RequestBody UserModel user) throws TSCException {
		return userService.updateByPrimaryKeySelective(user);
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public UserModel addUser(@RequestBody UserModel user) throws TSCException {
		user.setCreatedDt(new Date());
		return userService.addUserWithDetail(user);
	}
	
	
	@RequestMapping(value = "/getUserDetailById", method = RequestMethod.GET)
	public UserDetailModel getUserDetailById(Long userId) throws TSCException {
		UserDetailModel result = userDetailService.selectByUserId(userId);
		// insert a user detail record if this user hasn't a record
		if(null == result) {
			result = new UserDetailModel();
			result.setUserId(userId);
			result.setLastUpdateDt(new Date());
			userDetailService.insert(result);
		}
		return result;
	}
	
	@RequestMapping(value = "/updateUserWithDetail", method = RequestMethod.POST)
	public Integer updateUserWithDetail(@RequestBody UserModel user) throws TSCException {
		userService.updateByPrimaryKeySelective(user);
		// update date
		user.getUserDetail().setLastUpdateDt(new Date());
		return userDetailService.updateById(user.getUserDetail());
	}
	
	@RequestMapping(value = "/checkAccountId", method = RequestMethod.GET)
	public Boolean checkAccountId(Long accountId) throws TSCException {
		if(null == userService.selectByAccountId(accountId))
			return true;
		else 
			return false;
	}
	
	@RequestMapping(value = "/getTeacherByAccountId", method = RequestMethod.GET)
	public UserModel getTeacherByAccountId(Long accountId) throws TSCException {
		UserModel result = userService.selectByAccountId(accountId);
		CodetableModel userRoleCode = CodeTableService.selectById(result.getUserRole());
		if(CommonConstants.CODETABLE_NAME_TEACHER.equals(userRoleCode.getName())){
			return result;
		}
		else {
			return null;
		}
	}
	
	@RequestMapping(value = "/getStudentByAccountId", method = RequestMethod.GET)
	public UserModel getStudentByAccountId(Long accountId) throws TSCException {
		UserModel result = userService.selectByAccountId(accountId);
		CodetableModel userRoleCode = CodeTableService.selectById(result.getUserRole());
		if(CommonConstants.CODETABLE_NAME_STUDENT.equals(userRoleCode.getName())){
			return result;
		}
		else {
			return null;
		}
	}
	
	@RequestMapping(value = "/deleteUsersByIdList", method = RequestMethod.POST)
	public Boolean deleteUsersByIdList(@RequestBody List<Long> userIdList) throws TSCException{
		for(Long userId : userIdList){
			userService.deleteById(userId);
		}
		return true;
	}
	
	@RequestMapping(value = "/getUserWithDetailById", method = RequestMethod.GET)
	public UserModel getUserWithDetailById(@RequestParam("id") Long id) throws TSCException {
		return userService.getUserWithDetailById(id);
	}
	
	@RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
	public String uploadPicture(@RequestParam MultipartFile file) throws TSCException{
		String picName = tscUtils.storeUserPicture(file);
		String getPictureUri = "/user/getUserPicture?picName=";
		return "{\"fileName\" :\"" + getPictureUri + picName + "\"}";
	}
	
	@GetMapping(value = "/getUserPicture")
	public void getUserPicture(@RequestParam("picName") String picName) throws TSCException{
		final int bufferSize = 1024; 
		InputStream picInputStream = tscUtils.getUserPicture(picName);
		
		response.setHeader("Content-Type", "image/*");
		OutputStream responseOut = null;
		try {
			// Write image to response
			responseOut = response.getOutputStream();
			byte[] buffer = new byte[bufferSize];
			int count = 0;
			while((count = picInputStream.read(buffer)) > 0){
				responseOut.write(buffer, 0, count);
			}
				
		} catch (IOException e) {
			throw new TSCException("Get OutputStream of the response fail!");
		}
		finally{
			IOUtils.closeQuietly(picInputStream);
			IOUtils.closeQuietly(responseOut);
		}
	}
	
	@GetMapping(value = "/getUserPasswordByUserId")
	public String getUserPassword(@RequestParam("userId") Long userId) throws TSCException{
		return userService.getUserPasswordByUserId(userId);
	}
	
	@GetMapping(value = "/updateUserPasswordByUserId")
	public int updateUserPasswordByUserId(@RequestParam("userId") Long userId, @RequestParam("password") String password) throws TSCException{
		return userService.updateUserPasswordByUserId(userId, password);
	}
}