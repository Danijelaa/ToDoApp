package com.toDoApp.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.Dashboard;
import com.toDoApp.service.DashboardService;
import com.toDoApp.web.dto.DashboardDTO;

@Component
public class DashboardDTOToDashboard implements Converter<DashboardDTO, Dashboard>{

	@Autowired
	DashboardService dashboardService;
	@Override
	public Dashboard convert(DashboardDTO dashboardDto) {
		Dashboard dashboard;
		if(dashboardDto.getId()==null) {
			dashboard=new Dashboard(); 
		}
		else {
			dashboard=dashboardService.findByid(dashboardDto.getId());
		}
		dashboard.setTitle(dashboardDto.getTitle().trim());
		return dashboard;
	}

}
