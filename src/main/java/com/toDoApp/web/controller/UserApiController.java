package com.toDoApp.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.toDoApp.model.Dashboard;
import com.toDoApp.model.User;
import com.toDoApp.service.DashboardService;
import com.toDoApp.service.UserService;
import com.toDoApp.support.DashboardToDashboardDTO;
import com.toDoApp.support.UserDTORegisterToUser;
import com.toDoApp.support.UserToUserDTO;
import com.toDoApp.support.Validation;
import com.toDoApp.web.dto.DashboardDTOAllData;
import com.toDoApp.web.dto.UserDTO;
import com.toDoApp.web.dto.UserDTOLogin;
import com.toDoApp.web.dto.UserDTORegister;

@RestController
@RequestMapping(value="/users")
public class UserApiController {
	@Autowired
	UserService userService;
	@Autowired
	UserToUserDTO toUserDto;
	@Autowired
	DashboardToDashboardDTO toDashboardDto;
	@Autowired
	DashboardService dashboardService;
	@Autowired
	Validation validation;
	@Autowired
	UserDTORegisterToUser userRegisterToUser;
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	ResponseEntity<?> login(@RequestBody UserDTOLogin userDtoLogin, HttpSession session){
		if(userDtoLogin.getUsername()==null || userDtoLogin.getPassword()==null || userDtoLogin.getUsername().trim().equals("") || userDtoLogin.getPassword().trim().equals("")) {
			return new ResponseEntity<String>("Neither username or password can be empty or NULL.", HttpStatus.BAD_REQUEST);
		}
		User user=userService.login(userDtoLogin.getUsername(), userDtoLogin.getPassword());
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		session.setAttribute("loggedUser", user);
		UserDTO userDto=toUserDto.convert(user);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/dashboards")
	ResponseEntity<?> getTasks(HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<String>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		List<Dashboard> dashboards=userService.getUsersDashboards(user);
		return new ResponseEntity<>(toDashboardDto.convert(dashboards), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/logout")
	ResponseEntity<?> logout(HttpSession session){
		session.invalidate();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/register")
	ResponseEntity<?> register(HttpSession session, @RequestBody UserDTORegister userDtoRegister) {
		User user=(User) session.getAttribute("loggedUser");
		if(user!=null) {
			return new ResponseEntity<>("Log out before trying to create new account.",HttpStatus.BAD_REQUEST);
		}
		
		boolean validated=validation.validateNewUser(userDtoRegister);
		if(!validated) {
			return new ResponseEntity<>("Neither parameter (name, lastName, username, password, repeatedPassword) can  be null or empty.", HttpStatus.BAD_REQUEST);
		}
		validated=validation.validateNewUserFieldsLength(userDtoRegister);
		if(!validated) {
			return new ResponseEntity<>("Maximum fields length is 25.", HttpStatus.BAD_REQUEST);
		}
		if(!userDtoRegister.getPassword().equals(userDtoRegister.getRepeatedPassword())) {
			return new ResponseEntity<>("Passwords do not match.", HttpStatus.BAD_REQUEST);
		}
		user=userRegisterToUser.convert(userDtoRegister);
		try {
			user=userService.addUser(user);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		if(user==null) {
			return new ResponseEntity<>("Error happened. Could not add user.", HttpStatus.BAD_REQUEST);
		}
		session.setAttribute("loggedUser", user);
		return new ResponseEntity<UserDTO>(toUserDto.convert(user), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/dashboards/{dashboardId}")
	ResponseEntity<?> getAlldataOfDashboard(@PathVariable Integer dashboardId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<String>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		Dashboard dashboard=dashboardService.findByid(dashboardId);
		if(dashboard==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(!dashboard.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		DashboardDTOAllData dashboardAllData=dashboardService.allData(dashboardId);
		return new ResponseEntity<>(dashboardAllData, HttpStatus.OK);
	}
	
	
	
}
