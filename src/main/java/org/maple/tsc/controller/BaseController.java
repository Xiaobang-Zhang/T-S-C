package org.maple.tsc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maple.tsc.constants.CommonConstants;
import org.maple.tsc.exception.TSCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BaseController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String, Object> handleExceptions(Exception exc){
		Map<String, Object> map = new HashMap<>();
		String errorMsg = null;
		if(exc instanceof TSCException){
			errorMsg = ((TSCException) exc).getErrorMessage();
		}
		else {
			errorMsg = exc.getMessage();
		}
		
		map.put("status", request.getAttribute("javax.servlet.error.status_code"));
		map.put("reason", errorMsg);
		
		return map;
	}
	
	@RequestMapping(value = "/login")
	public String login(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@AuthenticationPrincipal User user) throws IOException{

		if(null != user){
			String role = ((GrantedAuthority)(user.getAuthorities().toArray()[0])).getAuthority();
			if(CommonConstants.CODETABLE_NAME_ADMIN.equals(role)){
				response.sendRedirect("/admin");
			}
			else if(CommonConstants.CODETABLE_NAME_TEACHER.equals(role)){
				response.sendRedirect("/user");
			}
			else if(CommonConstants.CODETABLE_NAME_STUDENT.equals(role)){
				response.sendRedirect("/user");
			}
		}
		return "\\TSCexternal\\login\\login";
	}
	
	@RequestMapping(value = "/")
	public String root() throws IOException{
		response.sendRedirect("/login");
		return "\\TSCexternal\\login\\login";
	}
	
	@RequestMapping(value = "/admin")
	public String toAdminPage(){
		return "\\TSCinternal\\AdminContents\\dashboard";
	}
	
	@RequestMapping(value = "/user")
	public String toUserPage(){
		return "\\TSCinternal\\UserContents\\dashboard";
	}
	
}
