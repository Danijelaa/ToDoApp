package com.toDoApp.web.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskStateDTOAlldata {

	private Integer id;
	private String title;
	private List<TaskDTOAllData> tasks=new ArrayList<TaskDTOAllData>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<TaskDTOAllData> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDTOAllData> tasks) {
		this.tasks = tasks;
	}
	
	
}
