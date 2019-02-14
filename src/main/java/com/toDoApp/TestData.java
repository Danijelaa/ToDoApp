package com.toDoApp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toDoApp.support.CreateConnection;


@Component
public class TestData {
	
	@Autowired
	private static Connection conn=CreateConnection.getConnection();
	@PostConstruct
	public void init() {
		
		String dropUsers="DROP TABLE  IF EXISTS `user`";
		String dropDashs="DROP TABLE  IF EXISTS `dashboard`";
		String dropTaskStates="DROP TABLE  IF EXISTS `taskstate`";
		String dropTasks="DROP TABLE  IF EXISTS `task`";
		
		//String foreignKeysOn="PRAGMA foreign_keys = ON";

		String createUsers="CREATE TABLE `user` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` VARCHAR(25) NOT NULL,`lastName` VARCHAR(25) NOT NULL,`username` VARCHAR(25) UNIQUE,`password` TEXT(25))";			
		String createDashs="CREATE TABLE `dashboard` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`title` VARCHAR(25),`userId` INT NOT NULL, FOREIGN KEY (`userId`) REFERENCES `user`(`id`) ON DELETE CASCADE)";			
		String createTaskStates="CREATE TABLE `taskstate` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`title` VARCHAR(25),`dashboardId` INT NOT NULL, FOREIGN KEY (`dashboardId`) REFERENCES `dashboard`(`id`)  ON DELETE CASCADE)";			
		String createTasks="CREATE TABLE `task` (`id` INTEGER PRIMARY KEY AUTOINCREMENT,`title` VARCHAR(25), `description` VARCHAR(200),`taskStateId` INT NOT NULL, FOREIGN KEY (`taskStateId`) REFERENCES `taskstate`(`id`)  ON DELETE CASCADE)";			

		
		String user1="INSERT INTO `user`(`id`,`name`, `lastName`, `username`, `password`) VALUES(1,'name1', 'lastName1',  'username1', 'password1')";
		String user2="INSERT INTO `user`(`name`, `lastName`,`username`, `password`) VALUES('name2', 'lastName2','username2', 'password2')";
		String user3="INSERT INTO `user`(`name`, `lastName`, `username`, `password`) VALUES('name3', 'lastName3','username3', 'password3')";

		String dash1="INSERT INTO `dashboard`(`id`, `title`, `userId`) VALUES(1, 'dashboard1OfUser1', 1)";
		String dash2="INSERT INTO `dashboard`(`title`, `userId`) VALUES('dashboard1OfUser2', 2)";
		
		String taskState1="INSERT INTO `taskstate`(`id`, `title`, `dashboardId`) VALUES(1, 'taskState1OfDashboardOfUser1', 1)";
		String taskState2="INSERT INTO `taskstate`(`title`, `dashboardId`) VALUES('taskState2OfDashboardOfUser1', 1)";
		String taskState3="INSERT INTO `taskstate`(`title`, `dashboardId`) VALUES('taskState1OfDashboardOfUser2', 2)";
		String taskState4="INSERT INTO `taskstate`(`title`, `dashboardId`) VALUES('taskState2OfDashboardOfUser2', 2)";

		String task1="INSERT INTO `task`(`id`, `title`, `description`, `taskStateId`) VALUES(1, 'task1OfTaskState1OfDashboardOfUser1', 'description1', 1)";
		String task2="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task2OfTaskState1OfDashboardOfUser1', 'description2', 1)";
		String task3="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task3OfTaskState2OfDashboardOfUser1', 'description3',2)";
		String task4="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task4OfTaskState2OfDashboardOfUser1', 'description4', 2)";
		String task5="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task1OfTaskState1OfDashboardOfUser2', 'description5',3)";
		String task6="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task2OfTaskState1OfDashboardOfUser2', 'description6', 3)";
		String task7="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task3OfTaskState2OfDashboardOfUser2', 'description7', 4)";
		String task8="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES('task4OfTaskState2OfDashboardOfUser2', 'description8', 4)";

		Statement st=null;
		try {
			st=conn.createStatement();
			st.execute(dropUsers);
			st.execute(dropDashs);
			st.execute(dropTaskStates);
			st.execute(dropTasks);
			//st.execute(foreignKeysOn);
			st.execute(createUsers);
			st.execute(createDashs);
			st.execute(createTaskStates);
			st.execute(createTasks);
			
			st.execute(user1);
			st.execute(user2);
			st.execute(user3);
			st.execute(dash1);
			st.execute(dash2);
			st.execute(taskState1);
			st.execute(taskState2);
			st.execute(taskState3);
			st.execute(taskState4);
			st.execute(task1);
			st.execute(task2);
			st.execute(task3);
			st.execute(task4);
			st.execute(task5);
			st.execute(task6);
			st.execute(task7);
			st.execute(task8);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st!=null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
}
