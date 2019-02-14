package com.toDoApp.model;

public class TaskState {

	private Integer id;
	private String title;
	private Dashboard dashboard;

	public TaskState(Integer id, String title, Dashboard dashboard) {
		super();
		this.id = id;
		this.title = title;
		this.dashboard = dashboard;
	}
	
	public TaskState(String title, Dashboard dashboard) {
		super();
		this.title = title;
		this.dashboard = dashboard;
	}

	public TaskState() {
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
	public Dashboard getDashboard() {
		return dashboard;
	}
	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	
	
}
