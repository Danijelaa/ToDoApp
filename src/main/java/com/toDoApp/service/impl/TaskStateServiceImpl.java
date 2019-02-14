package com.toDoApp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toDoApp.model.Dashboard;
import com.toDoApp.model.Task;
import com.toDoApp.model.TaskState;
import com.toDoApp.service.DashboardService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.support.CreateConnection;
@Service
public class TaskStateServiceImpl implements TaskStateService {


	@Autowired
	private DashboardService dashboardService;
	private Logger logger=Logger.getLogger(TaskStateServiceImpl.class);
	
	@Override
	public List<Task> getTasks(TaskState taskState) {
		List<Task> tasks=new ArrayList<>();
		Connection conn=null;
		conn=CreateConnection.getConnection();
		String query="SELECT * FROM task WHERE task.taskStateId="+taskState.getId();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery(query);
			
			while(rs.next()) {
				int id=rs.getInt(1);
				String title=rs.getString(2);
				String description=rs.getString(3);
				Task task=new Task(id, title, description, taskState);
				tasks.add(task);
			}
		} catch (SQLException e) {
			logger.error("Could not get tasks. Message: "+e.getMessage());
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
		
		return tasks;
	}

	@Override
	public TaskState getById(Integer id) {
		TaskState taskState=null;
		String query="SELECT ts.title, ts.dashboardId FROM taskstate ts WHERE ts.id="+id;
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				String title=rs.getString(1);
				Integer dashboardId=rs.getInt(2);
				Dashboard dashboard=dashboardService.findByid(dashboardId);
				taskState=new TaskState(id, title, dashboard);
			}
		} catch (SQLException e) {
			logger.error("Could not find taskstate. Message: "+e.getMessage());
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
		return taskState;
	}

	@Override
	public TaskState addTaskState(TaskState taskState) {
		Integer id=null;
		String query="INSERT INTO `taskstate`(`title`, `dashboardId`) VALUES(?, ?)";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, taskState.getTitle());
			pstmt.setInt(2, taskState.getDashboard().getId());
			pstmt.executeUpdate();
			rs=pstmt.getGeneratedKeys();
			if(rs.next()) {
				id=rs.getInt(1);
				return getById(id);
			}
		} catch (SQLException e) {
			logger.error("Could not save taskstate. Message: "+e.getMessage());
		}finally{
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Could not close ResultSet instance. Message: "+e.getMessage());
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
	public TaskState updateTaskState(TaskState taskState) {
		String query="UPDATE taskstate SET title=? WHERE id=?";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, taskState.getTitle());
			pstmt.setInt(2, taskState.getId());
			pstmt.executeUpdate();
			return getById(taskState.getId());
		} catch (SQLException e) {
			logger.error("Could not update taskstate. Message: "+e.getMessage());
		}finally{
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
	public boolean deleteTaskState(Integer taskStateId) {
		String foreignKeysOn="PRAGMA foreign_keys = ON";
		String query="DELETE FROM taskstate WHERE id="+taskStateId;
		Connection conn=CreateConnection.getConnection();
		Statement st=null;
		try {
			st=conn.createStatement();
			st.execute(foreignKeysOn);
			st.executeUpdate(query);
		} catch (SQLException e) {
			logger.error("Could not delete taskstate. Message: "+e.getMessage());
			return false;
		}
		return true;		
	}

}
