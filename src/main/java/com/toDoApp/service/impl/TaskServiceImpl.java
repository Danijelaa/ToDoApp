package com.toDoApp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.model.Task;
import com.toDoApp.model.TaskState;
import com.toDoApp.service.TaskService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.support.CreateConnection;
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskStateService taskStateService;
	private Logger logger=Logger.getLogger(TaskServiceImpl.class);
	
	@Override
	public Task findById(Integer id) {
		Task task=null;
		String query="SELECT t.title, t.description, t.taskStateId FROM task t WHERE id="+id;
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				String title=rs.getString(1);
				String description=rs.getString(2);
				int taskStateId=rs.getInt(3);
				TaskState taskState=taskStateService.getById(taskStateId);
				task=new Task(id, title, description, taskState);
			}
			
		} catch (SQLException e) {
			logger.error("Could not find task. Message: "+e.getMessage());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Could not close ResultSet instance. Message: "+e.getMessage());
				}
			}
			if(st!=null) {
				try {
					st.close();
				} catch (SQLException e) {
					logger.error("Could not close Statement instance. Message: "+e.getMessage());
				}
			}
		}
		
		return task;
	}

	@Override
	public Task addTask(Task task) {
		Integer id=null;
		String query="INSERT INTO `task`(`title`, `description`, `taskStateId`) VALUES(?, ?, ?)";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, task.getTitle());
			pstmt.setString(2, task.getDescription());
			pstmt.setInt(3, task.getTaskState().getId());
			pstmt.executeUpdate();
			rs=pstmt.getGeneratedKeys();
			if(rs.next()) {
				id=rs.getInt(1);
				return findById(id);
			}
		} catch (SQLException e) {
			logger.error("Could not save task. Message: "+e.getMessage());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Could not close ResultSet instance. Message: "+e.getMessage());
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error("Could not close PreparedStatement instance. Message: "+e.getMessage());
				}
			}
		}
		return null;
}

	@Override
	public boolean deleteTask(Integer taskId) {
		String query="DELETE FROM task WHERE id="+taskId;
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
		try {
			st=conn.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			logger.error("Could not delete task. Message: "+e.getMessage());
			return false;
		}finally {
			if(st!=null) {
				try {
					st.close();
				} catch (SQLException e) {
					logger.error("Could not close Statement instance. Message: "+e.getMessage());
				}
			}
		}
		
		return true;	
	}

	@Override
	public Task updateTask(Task task) {
		String query="UPDATE task SET title=?, description=?, taskStateId=? WHERE id=?";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, task.getTitle());
			pstmt.setString(2, task.getDescription());
			pstmt.setInt(3, task.getTaskState().getId());
			pstmt.setInt(4, task.getId());
			pstmt.executeUpdate();
			return findById(task.getId());
		} catch (SQLException e) {
			logger.error("Could not update task. Message: "+e.getMessage());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error("Could not close PreparedStatement instance. Message: "+e.getMessage());
				}
			}
		}
		return null;
		
	}
	
	

}
