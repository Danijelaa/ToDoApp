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
import com.toDoApp.model.TaskState;
import com.toDoApp.model.User;
import com.toDoApp.service.DashboardService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.service.UserService;
import com.toDoApp.support.DashboardDTOToDashboard;
import com.toDoApp.support.DashboardToDashboardDTO;
import com.toDoApp.support.TaskStateToTaskStateDTO;
import com.toDoApp.support.Validation;
import com.toDoApp.web.dto.DashboardDTO;
import com.toDoApp.web.dto.TaskStateDTO;

@RestController
@RequestMapping(value="/dashboards")
public class DashboardApiController {

	@Autowired
	DashboardService dashboardService;
	@Autowired
	TaskStateService taskStateService;
	@Autowired
	TaskStateToTaskStateDTO toTaskStateDto;
	@Autowired
	DashboardToDashboardDTO toDashboardDto;
	@Autowired
	Validation validation;
	@Autowired
	DashboardDTOToDashboard toDashboard;
	@Autowired
	UserService userService;

	@RequestMapping(method=RequestMethod.GET, value="/{dashboardId}/task-states")
	ResponseEntity<?> getTaskStates(@PathVariable Integer dashboardId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("Yu are not logged in.", HttpStatus.FORBIDDEN);
		}
		Dashboard dashboard=dashboardService.findByid(dashboardId);
		if(dashboard==null) {
			return new ResponseEntity<>("No dashboard with given id.", HttpStatus.NOT_FOUND);
		}
		if(!dashboard.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		List<TaskState> taskStates=dashboardService.getTaskStates(dashboard);
		return new ResponseEntity<List<TaskStateDTO>>(toTaskStateDto.convert(taskStates), HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{dashboardId}")
	ResponseEntity<?> readDashboard(@PathVariable Integer dashboardId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		Dashboard dashboard=dashboardService.findByid(dashboardId);
		if(dashboard==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(!dashboard.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<DashboardDTO>(toDashboardDto.convert(dashboard), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	ResponseEntity<?> createDashboard(@RequestBody DashboardDTO newDashboardDto, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		boolean validated=validation.validateDashboard(newDashboardDto);
		if(!validated) {
			return new ResponseEntity<>("Title can not be null or empty. Maximum title length must be 25.", HttpStatus.BAD_REQUEST);
		}
		Dashboard dashboard=toDashboard.convert(newDashboardDto);
		dashboard.setUser(user); 
		dashboard=dashboardService.addDashboard(dashboard);
		if(dashboard==null) {
			return new ResponseEntity<>("Error happened. Could not create dashboard.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<DashboardDTO>(toDashboardDto.convert(dashboard), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{dashboardId}")
	ResponseEntity<?> updateDashboard(@RequestBody DashboardDTO updatedDashboardDto, @PathVariable Integer dashboardId, HttpSession session){
		//authentication of user
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		if(updatedDashboardDto.getId()==null || !updatedDashboardDto.getId().equals(dashboardId)) {
			return new ResponseEntity<>("Ids do not match.", HttpStatus.BAD_REQUEST);
		}
		Dashboard dashboard=dashboardService.findByid(updatedDashboardDto.getId());
		if(dashboard==null) {
			return new ResponseEntity<>("Can not update non-existent dashboard.", HttpStatus.NOT_FOUND);
		}
		if(!dashboard.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		boolean validated=validation.validateDashboard(updatedDashboardDto);
		if(!validated) {
			return new ResponseEntity<>("Title can not be null or empty. Maximum title length must be 25.", HttpStatus.BAD_REQUEST);
		}
		dashboard=toDashboard.convert(updatedDashboardDto);
		dashboard=dashboardService.updateDashboard(dashboard);
		if(dashboard==null) {
			return new ResponseEntity<>("Error happened. Could not update dashboard.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<DashboardDTO>(toDashboardDto.convert(dashboard), HttpStatus.OK);
		
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{dashboardId}")
	ResponseEntity<?> deleteDashboard(@PathVariable Integer dashboardId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		Dashboard dashboard=dashboardService.findByid(dashboardId);
		if(dashboard==null) {
			return new ResponseEntity<>("Can not delete non-existent dashboard.", HttpStatus.NOT_FOUND);
		}
		
		if(!dashboard.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		boolean deleted=dashboardService.deleteDashboard(dashboardId);
		if(!deleted) {
			return new ResponseEntity<>("Could not delete dashboard.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
