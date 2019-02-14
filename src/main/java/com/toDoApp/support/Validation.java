package com.toDoApp.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toDoApp.service.DashboardService;
import com.toDoApp.service.TaskService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.web.dto.DashboardDTO;
import com.toDoApp.web.dto.TaskDTO;
import com.toDoApp.web.dto.TaskStateDTO;
import com.toDoApp.web.dto.UserDTORegister;

@Component
public class Validation {
	@Autowired
	TaskStateService taskStateService;
	@Autowired
	TaskService taskService;
	@Autowired
	DashboardService dashboardService;

	public boolean validateTask(TaskDTO newTaskDto){
		if(newTaskDto.getTitle()==null || newTaskDto.getTaskStateId()==null || newTaskDto.getTitle().trim().equals("") || newTaskDto.getTaskStateId().equals("") || newTaskDto.getTitle().trim().length()>25){
			return false;
		}
		if(newTaskDto.getDescription()!=null && newTaskDto.getDescription().trim().length()>200) {
				return false;
		}
		return true;
	}

	public boolean validateNewTaskState(TaskStateDTO newTaskStateDto){
		if(newTaskStateDto.getDashboardId()==null || newTaskStateDto.getTitle()==null
				|| newTaskStateDto.getDashboardId().equals("") || newTaskStateDto.getTitle().trim().equals("") || newTaskStateDto.getTitle().trim().length()>25){
			return false;
		}
		return true;
	}
	
	public boolean validateUpdatedTaskState(TaskStateDTO updatedTaskStateDto){
		if(updatedTaskStateDto.getTitle()==null || updatedTaskStateDto.getTitle().trim().equals("") || updatedTaskStateDto.getTitle().trim().length()>25){
			return false;
		}
		return true;
	}
	
	public boolean validateDashboard(DashboardDTO newDashboardDto) {
		if(newDashboardDto.getTitle()==null || newDashboardDto.getTitle().trim().equals("") || newDashboardDto.getTitle().trim().length()>25) {
			return false;
		}
		return true;
	}
	
	
	public boolean validateNewUser(UserDTORegister userDtoRegister) {
		if(userDtoRegister.getLastName()==null || userDtoRegister.getName()==null || userDtoRegister.getPassword()==null || userDtoRegister.getRepeatedPassword()==null || userDtoRegister.getUsername()==null
				|| userDtoRegister.getLastName().trim().equals("") || userDtoRegister.getName().trim().equals("") || userDtoRegister.getPassword().trim().equals("") || userDtoRegister.getRepeatedPassword().trim().equals("") || userDtoRegister.getUsername().trim().equals("")) {
			return false;
		}
		return true;
	}
	
	public boolean validateNewUserFieldsLength(UserDTORegister userDtoRegister) {
		if(userDtoRegister.getLastName().trim().length()>25 || userDtoRegister.getName().trim().length()>25 || userDtoRegister.getPassword().length()>25 || userDtoRegister.getUsername().length()>25) {
			return false;
		}
		return true;
	}
}
