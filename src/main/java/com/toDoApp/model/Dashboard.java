package com.toDoApp.model;

public class Dashboard {
	private Integer id;
	private String title;
	private User user;
	
	
	public Dashboard() {
		super();
	}

	public Dashboard(Integer id, String title, User user) {
		super();
		this.id = id;
		this.title = title;
		this.user = user;
	}

	public Dashboard(String title, User user) {
		super();
		this.title = title;
		this.user = user;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
