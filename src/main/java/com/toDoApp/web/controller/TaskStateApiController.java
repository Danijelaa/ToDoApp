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
import com.toDoApp.model.Task;
import com.toDoApp.model.TaskState;
import com.toDoApp.model.User;
import com.toDoApp.service.DashboardService;
import com.toDoApp.service.TaskService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.support.TaskStateDTOToTaskState;
import com.toDoApp.support.TaskStateToTaskStateDTO;
import com.toDoApp.support.TaskToTaskDTO;
import com.toDoApp.support.Validation;
import com.toDoApp.web.dto.TaskStateDTO;

@RestController
@RequestMapping(value="/task-states")
public class TaskStateApiController {

	@Autowired
	TaskService taskService;
	@Autowired
	TaskToTaskDTO toTaskDto;
	@Autowired
	TaskStateService taskStateService;
	@Autowired
	TaskStateToTaskStateDTO toTaskStateDto;
	@Autowired
	Validation validation;
	@Autowired
	TaskStateDTOToTaskState toTaskState;
	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value="/{taskStateId}/tasks", method=RequestMethod.GET)
	ResponseEntity<?> readTasksOfTaskStates(@PathVariable Integer taskStateId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		TaskState taskState=taskStateService.getById(taskStateId);
		if(taskState==null){
			return new ResponseEntity<>("No taskstate with given id.", HttpStatus.NOT_FOUND);
		}
		if(!taskState.getDashboard().getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		List<Task> tasks=taskStateService.getTasks(taskState);
		return new ResponseEntity<>(toTaskDto.convert(tasks), HttpStatus.OK);
	}

	@RequestMapping(value="/{taskStateId}", method=RequestMethod.GET)
	ResponseEntity<?> readTaskState(@PathVariable Integer taskStateId,  HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		TaskState taskState=taskStateService.getById(taskStateId);
		if(taskState==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(!taskState.getDashboard().getUser().getId().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<TaskStateDTO>(toTaskStateDto.convert(taskState), HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	ResponseEntity<?> createTaskState(@RequestBody TaskStateDTO newTaskStateDto,  HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		
		boolean validated=validation.validateNewTaskState(newTaskStateDto);
		if(!validated){
			return new ResponseEntity<>("Neither parameter (title, dashboardId) can be missing or empty. Maximum title length is 25.", HttpStatus.BAD_REQUEST);
		}
		Dashboard dashboard=dashboardService.findByid(newTaskStateDto.getDashboardId());
		if(dashboard==null){ 
			return new ResponseEntity<>("Can not add taskstate to non-existent dashboard.", HttpStatus.NOT_FOUND);
		}
		if(!dashboard.getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		TaskState taskState=toTaskState.convert(newTaskStateDto);
		taskState.setDashboard(dashboard);
		taskState=taskStateService.addTaskState(taskState);
		if(taskState==null) {
			return new ResponseEntity<>("Error happened. Could not add task state.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TaskStateDTO>(toTaskStateDto.convert(taskState), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{taskStateId}")
	ResponseEntity<?> updateTaskState(@RequestBody TaskStateDTO updatedTaskStateDto, @PathVariable Integer taskStateId,HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not looged in.", HttpStatus.FORBIDDEN);
		}
		if(updatedTaskStateDto.getId()==null || !updatedTaskStateDto.getId().equals(taskStateId)){
			return new ResponseEntity<>("Ids do not match.", HttpStatus.BAD_REQUEST);
		}
		boolean validated=validation.validateUpdatedTaskState(updatedTaskStateDto);
		if(!validated){
			return new ResponseEntity<>("Title can not be null or empty. Maximum title length is 25.", HttpStatus.BAD_REQUEST);
		}
		TaskState taskState=taskStateService.getById(updatedTaskStateDto.getId());
		if(taskState==null) {
			return new ResponseEntity<>("Can not update non-existent taskstate.", HttpStatus.NOT_FOUND);
		}
		if(!taskState.getDashboard().getUser().getId().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		taskState=toTaskState.convert(updatedTaskStateDto);
		taskState=taskStateService.updateTaskState(taskState);
		if(taskState==null) {
			return new ResponseEntity<>("Could not update taskstate.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TaskStateDTO>(toTaskStateDto.convert(taskState), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{taskStateId}")
	ResponseEntity<?> deleteTaskState(@PathVariable Integer taskStateId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		TaskState taskState=taskStateService.getById(taskStateId);
		if(taskState==null){
			return new ResponseEntity<>("Can not delete non-existent taskstate.", HttpStatus.NOT_FOUND);
		}
		if(!taskState.getDashboard().getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		boolean deleted=taskStateService.deleteTaskState(taskStateId);
		if(!deleted) {
			return new ResponseEntity<>("Error happened. Could not delete task.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
