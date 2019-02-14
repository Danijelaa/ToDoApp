package com.toDoApp.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.Task;
import com.toDoApp.web.dto.TaskDTO;

@Component
public class TaskToTaskDTO implements Converter<Task, TaskDTO> {

	@Override
	public TaskDTO convert(Task task) {
		TaskDTO taskDto=new TaskDTO();
		taskDto.setDescription(task.getDescription());
		taskDto.setId(task.getId());
		taskDto.setTitle(task.getTitle());
		taskDto.setTaskStateId(task.getTaskState().getId());
		return taskDto;
	}

	public List<TaskDTO> convert(List<Task> tasks){
		List<TaskDTO> taskDtos=new ArrayList<>();
		for(Task t:tasks) {
			taskDtos.add(convert(t));
		}
		return taskDtos;
	}
	
}
