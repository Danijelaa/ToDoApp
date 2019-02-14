package com.toDoApp.service;

import java.util.List;

import com.toDoApp.model.Dashboard;
import com.toDoApp.model.TaskState;
import com.toDoApp.web.dto.DashboardDTOAllData;

public interface DashboardService {
	List<TaskState> getTaskStates(Dashboard dashboard);
	Dashboard findByid(Integer id);
	Dashboard addDashboard(Dashboard dashboard);
	Dashboard updateDashboard(Dashboard dashboard);
	boolean deleteDashboard(Integer id);
	DashboardDTOAllData allData(Integer id);
}
