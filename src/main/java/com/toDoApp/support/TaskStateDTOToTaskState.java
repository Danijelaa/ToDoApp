package com.toDoApp.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.TaskState;
import com.toDoApp.service.DashboardService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.web.dto.TaskStateDTO;

@Component
public class TaskStateDTOToTaskState  implements Converter<TaskStateDTO, TaskState>{


	@Autowired
	TaskStateService taskStateService;
	@Autowired
	DashboardService dashboardService;
	
	@Override
	public TaskState convert(TaskStateDTO taskStateDto) {
		TaskState taskState;
		if(taskStateDto.getId()==null){ 
			taskState=new TaskState();
			//taskState.setDashboard(dashboardService.findByid(taskStateDto.getDashboardId()));
			
		}
		else{
			taskState=taskStateService.getById(taskStateDto.getId());
		}
		taskState.setTitle(taskStateDto.getTitle().trim());
		return taskState;
	}


	
}
