package com.toDoApp.web.dto;

public class TaskStateDTO {

	private Integer id;
	private String title;
	private Integer dashboardId;
	
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
	public Integer getDashboardId() {
		return dashboardId;
	}
	public void setDashboardId(Integer dashboardId) {
		this.dashboardId = dashboardId;
	}
	
	
}
