package com.toDoApp.service;

import java.util.List;

import com.toDoApp.model.Task;
import com.toDoApp.model.TaskState;

public interface TaskStateService {
	List<Task> getTasks(TaskState taskState);
	TaskState getById(Integer id);
	TaskState addTaskState(TaskState taskState);
	TaskState updateTaskState(TaskState taskState);
	boolean deleteTaskState(Integer taskStateId);
}
