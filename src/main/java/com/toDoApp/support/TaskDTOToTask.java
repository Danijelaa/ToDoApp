package com.toDoApp.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.Task;
import com.toDoApp.service.TaskService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.web.dto.TaskDTO;

@Component
public class TaskDTOToTask implements Converter<TaskDTO, Task> {

	@Autowired
	TaskStateService taskStateService;
	@Autowired
	TaskService taskService;

	@Override
	public Task convert(TaskDTO taskDto) {
		Task task;
		if(taskDto.getId()==null){ 
			task=new Task();
		}
		else{
			task=taskService.findById(taskDto.getId());
		}
		//task.setTaskState(taskStateService.getById(taskDto.getTaskStateId()));
		task.setTitle(taskDto.getTitle().trim());
		if(taskDto.getDescription()!=null) {
			task.setDescription(taskDto.getDescription().trim());
		}
		
		return task;

	}

}
