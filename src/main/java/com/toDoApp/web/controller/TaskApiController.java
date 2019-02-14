package com.toDoApp.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.toDoApp.model.Task;
import com.toDoApp.model.TaskState;
import com.toDoApp.model.User;
import com.toDoApp.service.TaskService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.support.TaskDTOToTask;
import com.toDoApp.support.TaskToTaskDTO;
import com.toDoApp.support.Validation;
import com.toDoApp.web.dto.TaskDTO;

@RestController
@RequestMapping(value="/tasks")
public class TaskApiController {
	@Autowired
	TaskService taskService;
	@Autowired
	TaskToTaskDTO toTaskDto;
	@Autowired
	Validation validation;
	@Autowired
	TaskDTOToTask toTask;
	@Autowired
	TaskStateService taskStateService;

	@RequestMapping(method=RequestMethod.GET, value="/{taskId}")
	ResponseEntity<?> readTask(@PathVariable Integer taskId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		Task task=taskService.findById(taskId);
		if(task==null) {
			return new ResponseEntity<>("No task with given id.", HttpStatus.NOT_FOUND);
		}
		if(!task.getTaskState().getDashboard().getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<TaskDTO>(toTaskDto.convert(task), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST)
	ResponseEntity<?> createTask(@RequestBody TaskDTO newTaskDto, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>("You are not logged in.", HttpStatus.FORBIDDEN);
		}
		boolean validated=validation.validateTask(newTaskDto);
		if(!validated) {
			return new ResponseEntity<>("Parameters: title, taskStateId must not be null or empty. Maximum title length is 25. Maximum descripion length is 200.", HttpStatus.BAD_REQUEST);
		}
		TaskState taskState=taskStateService.getById(newTaskDto.getTaskStateId());
		if(taskState==null){ 
			return new ResponseEntity<>("No taskstate with given id.", HttpStatus.NOT_FOUND);
		}
		if(!taskState.getDashboard().getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		Task task=toTask.convert(newTaskDto);
		task.setTaskState(taskState);
		task=taskService.addTask(task);
		if(task==null) {
			return new ResponseEntity<>("Could not add new task.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(toTaskDto.convert(task), HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{taskId}")
	ResponseEntity<?> updateTask(@RequestBody TaskDTO updatedTaskDto, @PathVariable Integer taskId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(updatedTaskDto.getId()==null || !updatedTaskDto.getId().equals(taskId)){
			return new ResponseEntity<String>("Ids do not match.", HttpStatus.BAD_REQUEST);
		}
		boolean validated=validation.validateTask(updatedTaskDto);
		if(!validated){
			return new ResponseEntity<>("Parameters: title, taskStateId must not be null or empty. Maximum title length is 25. Maximum descripion length is 200.", HttpStatus.BAD_REQUEST);
		} 
		Task task=taskService.findById(taskId);
		if(task==null){
			return new ResponseEntity<String>("Can not update non-existent task.", HttpStatus.BAD_REQUEST);
		}
		if(!task.getTaskState().getDashboard().getUser().getId().equals(user.getId())){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		TaskState taskState=taskStateService.getById(updatedTaskDto.getTaskStateId());
		if(taskState==null){ 
			return new ResponseEntity<>("No taskstate with given id.", HttpStatus.NOT_FOUND);
		}
		if(!taskState.getDashboard().getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		task=toTask.convert(updatedTaskDto);
		task.setTaskState(taskState);
		
		task=taskService.updateTask(task);
		if(task==null) {
			return new ResponseEntity<String>("Error happened. Could not update task.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TaskDTO>(toTaskDto.convert(task), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/{taskId}")
	ResponseEntity<?> deleteTask(@PathVariable Integer taskId, HttpSession session){
		User user=(User) session.getAttribute("loggedUser");
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Task task=taskService.findById(taskId);
		if(task==null){
			return new ResponseEntity<String>("Can not delete non-existent task.", HttpStatus.BAD_REQUEST);
		}
		if(!task.getTaskState().getDashboard().getUser().getId().equals(user.getId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		boolean deleted=taskService.deleteTask(taskId);
		if(!deleted) {
			return new ResponseEntity<String>("Error happened. Could not delete task.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	
	
	
	
}
