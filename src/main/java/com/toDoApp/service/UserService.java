package com.toDoApp.service;

import java.util.List;

import com.toDoApp.model.Dashboard;
import com.toDoApp.model.User;

public interface UserService {
	User login(String username, String password);
	List<Dashboard> getUsersDashboards(User user);
	User findById(Integer userId);
	User findByUsername(String username);
	User addUser(User user);
}
