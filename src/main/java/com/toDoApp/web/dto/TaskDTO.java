package com.toDoApp.web.dto;

public class TaskDTO {

	private Integer id;
	private String title;
	private String description;
	private Integer taskStateId;
	
	public Integer getTaskStateId() {
		return taskStateId;
	}
	public void setTaskStateId(Integer taskStateId) {
		this.taskStateId = taskStateId;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
