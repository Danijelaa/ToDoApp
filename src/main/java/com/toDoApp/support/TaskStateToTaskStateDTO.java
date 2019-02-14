package com.toDoApp.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.TaskState;
import com.toDoApp.web.dto.TaskStateDTO;

@Component
public class TaskStateToTaskStateDTO implements Converter<TaskState, TaskStateDTO>{

	@Override
	public TaskStateDTO convert(TaskState taskState) {
		TaskStateDTO taskStateDto=new TaskStateDTO();
		taskStateDto.setId(taskState.getId());
		taskStateDto.setTitle(taskState.getTitle());
		taskStateDto.setDashboardId(taskState.getDashboard().getId());
		return taskStateDto;
	}

	public List<TaskStateDTO> convert(List<TaskState> taskStates) {
		List<TaskStateDTO> taskStateDtos=new ArrayList<>();
		for(TaskState ts:taskStates) {
			taskStateDtos.add(convert(ts));
		}
		return taskStateDtos;
	}
	
}
