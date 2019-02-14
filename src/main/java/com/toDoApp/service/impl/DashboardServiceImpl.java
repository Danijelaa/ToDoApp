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
import com.toDoApp.model.User;
import com.toDoApp.service.DashboardService;
import com.toDoApp.service.TaskStateService;
import com.toDoApp.service.UserService;
import com.toDoApp.support.CreateConnection;
import com.toDoApp.web.dto.DashboardDTOAllData;
import com.toDoApp.web.dto.TaskDTOAllData;
import com.toDoApp.web.dto.TaskStateDTOAlldata;

@Service
public class DashboardServiceImpl implements DashboardService{

	private static Logger logger=Logger.getLogger(DashboardServiceImpl.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private TaskStateService taskStateSErvice;

	@Override
	public List<TaskState> getTaskStates(Dashboard dashboard) {
		List<TaskState> taskStates=new ArrayList<>();
		String query="SELECT ts.id, ts.title FROM taskstate ts WHERE ts.dashboardId="+dashboard.getId();
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			
			rs=st.executeQuery(query);
			while(rs.next()) {
				Integer id=rs.getInt(1);
				String title=rs.getString(2);
				TaskState taskState=new TaskState(id, title, dashboard);
				taskStates.add(taskState);
			}
		} catch (SQLException e) {
			logger.error("Could not get taskstates. Message: "+e.getMessage());
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
		return taskStates;
	}


	@Override
	public Dashboard findByid(Integer id) {
		Dashboard dashboard=null;
		String query="SELECT  d.title, d.userId FROM dashboard d where d.id="+id;
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				String title=rs.getString(1);
				Integer userId=rs.getInt(2);
				User user=userService.findById(userId);
				dashboard=new Dashboard(id, title, user);
			}
		} catch (SQLException e) {
			logger.error("Could not find dashboard. Message: "+e.getMessage());
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
		
		return dashboard;
	}


	@Override
	public Dashboard addDashboard(Dashboard dashboard) {
		String query="INSERT INTO `dashboard`(`title`, `userId`) VALUES(?, ?)";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		Integer id=null;
		ResultSet rs=null;
			try {
				pstmt=conn.prepareStatement(query);
				int index=1;
				pstmt.setString(index++, dashboard.getTitle());
				pstmt.setInt(index++, dashboard.getUser().getId());
				pstmt.executeUpdate();
				rs=pstmt.getGeneratedKeys();
				if(rs.next()) {
					id=rs.getInt(1);
					return findByid(id);
				}
			} catch (SQLException e) {
				logger.error("Could not save dashboard. Message: "+e.getMessage());
			}finally {
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
	public Dashboard updateDashboard(Dashboard dashboard) {
		String query="UPDATE dashboard SET title=? WHERE id=?";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
			try {
				pstmt=conn.prepareStatement(query);
				int index=1;
				pstmt.setString(index++, dashboard.getTitle());
				pstmt.setInt(index++, dashboard.getId());
				pstmt.executeUpdate();
				return findByid(dashboard.getId());
			} catch (SQLException e) {
				logger.error("Could not update dashboard. Message: "+e.getMessage());
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


	@Override
	public boolean deleteDashboard(Integer id) {
		String foreignKeysOn="PRAGMA foreign_keys=ON";
		String query="DELETE FROM  dashboard WHERE id="+id;
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
			try {
				st=conn.createStatement();
				st.execute(foreignKeysOn);
				st.executeUpdate(query);
			} catch (SQLException e) {
				logger.error("Could not delete dashboard. Message: "+e.getMessage());
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
	public DashboardDTOAllData allData(Integer id) {
	
		Dashboard dashboard=findByid(id);
		if(dashboard==null) {
			return null;
		}
		DashboardDTOAllData dashboardDto=new DashboardDTOAllData();
		dashboardDto.setId(dashboard.getId());
		dashboardDto.setTitle(dashboard.getTitle());
		
		List<TaskStateDTOAlldata> taskStatesDto=new ArrayList<>();
		List<TaskState> taskStates=getTaskStates(dashboard);
		
		for(TaskState ts:taskStates) {
			TaskStateDTOAlldata taskStateDto=new TaskStateDTOAlldata();
			taskStateDto.setId(ts.getId());
			taskStateDto.setTitle(ts.getTitle());
			taskStatesDto.add(taskStateDto);
			
			List<TaskDTOAllData> tasksDto=new ArrayList<>();
			List<Task> tasks=taskStateSErvice.getTasks(ts);
			for(Task t:tasks) {
				TaskDTOAllData taskDto=new TaskDTOAllData();
				taskDto.setDescription(t.getDescription());
				taskDto.setId(t.getId());
				taskDto.setTitle(t.getTitle());
				tasksDto.add(taskDto);
			}
			taskStateDto.setTasks(tasksDto);
		}
		dashboardDto.setTaskstates(taskStatesDto);
		return dashboardDto;
	}


	

}
