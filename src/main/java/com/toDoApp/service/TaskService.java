package com.toDoApp.service;

import com.toDoApp.model.Task;

public interface TaskService {
	Task findById(Integer id);
	Task addTask(Task task);
	Task updateTask(Task task);
	boolean deleteTask(Integer taskId);
}
