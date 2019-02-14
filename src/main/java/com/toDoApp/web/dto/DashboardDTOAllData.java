package com.toDoApp.web.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardDTOAllData {

	private Integer id;
	private String title;
	private List<TaskStateDTOAlldata> taskstates=new ArrayList<TaskStateDTOAlldata>();
	
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
	public List<TaskStateDTOAlldata> getTaskstates() {
		return taskstates;
	}
	public void setTaskstates(List<TaskStateDTOAlldata> taskstates) {
		this.taskstates = taskstates;
	}
	
	
}
