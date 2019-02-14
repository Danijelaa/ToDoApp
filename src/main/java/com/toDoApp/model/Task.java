package com.toDoApp.model;

public class Task {

	private Integer id;
	private String title;
	private String description;
	private TaskState taskState;
	
	public Task(Integer id, String title, String description, TaskState taskState) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.taskState = taskState;
	}
	
	public Task(String title, String description, TaskState taskState) {
		super();
		this.title = title;
		this.description = description;
		this.taskState = taskState;
	}
	public Task() {
		super();
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
	public TaskState getTaskState() {
		return taskState;
	}
	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}
	
	
	
	
}
