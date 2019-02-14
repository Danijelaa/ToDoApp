package com.toDoApp.model;

public class User {

	private Integer id;
	private String name;
	private String lastName;
	private String username;
	private String password;
	
	public User() {
		super();
	}
	
	public User(Integer id, String name, String lastName, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public User(String name, String lastName, String username, String password) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
}
