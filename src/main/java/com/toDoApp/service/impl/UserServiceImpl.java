package com.toDoApp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.toDoApp.model.Dashboard;
import com.toDoApp.model.User;
import com.toDoApp.service.UserService;
import com.toDoApp.support.CreateConnection;
@Service
public class UserServiceImpl implements UserService {

	private static Logger logger=Logger.getLogger(UserServiceImpl.class);
	
	@Override
	public User login(String username, String password) {
		User user=null;
		
		String query="SELECT * FROM user WHERE user.username LIKE ? AND user.password LIKE ?";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				String lastName=rs.getString(3);
				user=new User(id, name, lastName, username, password);
			}
		} catch (SQLException e) {
			logger.error("Could not execute query. Message: "+e.getMessage());
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
					logger.error("Could not close PreperedStatement instance. Message: "+e.getMessage());
				}
			}
		}
		return user;
	}

	@Override
	public List<Dashboard> getUsersDashboards(User user) {
		List<Dashboard> dashboards=new ArrayList<>();
		String query="SELECT d.id, d.title FROM dashboard d WHERE d.userId="+user.getId();
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
				Dashboard dashboard=new Dashboard(id, title, user);
				dashboards.add(dashboard);
			}
		} catch (SQLException e) {
			logger.error("Could not execute query. Message: "+e.getMessage());
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
		return dashboards;
	}

	@Override
	public User findById(Integer userId) {
		User user=null;
		String query="SELECT * FROM user u WHERE u.id="+userId;
		Connection conn=null;
		conn=CreateConnection.getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery(query);
			if(rs.next()) {
				String name=rs.getString(2);
				String lastName=rs.getString(3);
				String username=rs.getString(4);
				String password=rs.getString(5);
				user=new User(userId, name, lastName, username, password);
				
			}
		} catch (SQLException e) {
			logger.error("Could not find user. Message: "+e.getMessage());
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
		return user;
	}

	@Override
	public User findByUsername(String username) {
		User user=null;
		String query="SELECT * FROM user WHERE user.username LIKE ?";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, username);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				String lastName=rs.getString(3);
				String password=rs.getString(5);
				user=new User(id, name, lastName, username, password);
			}
		} catch (SQLException e) {
			logger.error("Could not find user. Message: "+e.getMessage());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Could not close ResulSet instance. Message: "+e.getMessage());
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
		return user;
	}

	@Override
	public User addUser(User user) {
		User userAleadyExistent=findByUsername(user.getUsername());
		if(userAleadyExistent!=null) {
			throw new IllegalArgumentException("Username already exists.");
		}
		String query="INSERT INTO `user`(`name`, `lastName`, `username`, `password`) VALUES(?, ?, ?, ?)";
		Connection conn=null;
		conn=CreateConnection.getConnection();
		PreparedStatement pstmt=null;
			try {
				pstmt=conn.prepareStatement(query);
				int index=1;
				pstmt.setString(index++, user.getName());
				pstmt.setString(index++, user.getLastName());
				pstmt.setString(index++, user.getUsername());
				pstmt.setString(index++, user.getPassword());
				pstmt.executeUpdate();
				return findByUsername(user.getUsername());
			} catch (SQLException e) {
				logger.error("Could not save new User instance. Message: "+e.getMessage());
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
