package com.toDoApp.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.Dashboard;
import com.toDoApp.web.dto.DashboardDTO;

@Component
public class DashboardToDashboardDTO implements Converter<Dashboard, DashboardDTO> {

	
	@Override
	public DashboardDTO convert(Dashboard dashboard) {
		DashboardDTO dashboardDto=new DashboardDTO();
		dashboardDto.setId(dashboard.getId());
		dashboardDto.setTitle(dashboard.getTitle());
		dashboardDto.setUserId(dashboard.getUser().getId());
		return dashboardDto;
	}

	public List<DashboardDTO> convert(List<Dashboard> dashboards){
		List<DashboardDTO> dashboardDtos=new ArrayList<>();
		for(Dashboard d:dashboards) {
			dashboardDtos.add(convert(d));
		}
		return dashboardDtos;
	}
	
}
