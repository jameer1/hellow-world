package com.rjtech.projschedule.service.handler;

import java.util.Date;

import com.rjtech.common.repository.LoginRepository;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projschedule.dto.ScheduleActivityDataSetTO;
import com.rjtech.projschedule.model.ScheduleActivityDataSetEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ScheduleActivityDataSetHandler {

	public static ScheduleActivityDataSetTO convertEntityToPOJO(ScheduleActivityDataSetEntity scheduleActivityDataSetEntity) {
		ScheduleActivityDataSetTO scheduleActivityDataSetTO = new ScheduleActivityDataSetTO();
		scheduleActivityDataSetTO.setId(scheduleActivityDataSetEntity.getId());
		scheduleActivityDataSetTO.setBaseline(scheduleActivityDataSetEntity.getBaseline().equalsIgnoreCase("Y") ? true : false);
		scheduleActivityDataSetTO.setCurrent(scheduleActivityDataSetEntity.getCurrent().equalsIgnoreCase("Y") ? true : false);
		scheduleActivityDataSetTO.setDatasetName(scheduleActivityDataSetEntity.getName());
		scheduleActivityDataSetTO.setType(scheduleActivityDataSetEntity.getType());
		scheduleActivityDataSetTO.setCreatedDate(scheduleActivityDataSetEntity.getCreatedOn());
		
		scheduleActivityDataSetTO.setProjId(scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId());
		scheduleActivityDataSetTO.setCode(scheduleActivityDataSetEntity.getProjMstrEntity().getCode());
		scheduleActivityDataSetTO.setName(scheduleActivityDataSetEntity.getProjMstrEntity().getProjName());
		scheduleActivityDataSetTO.setScheduleDate(scheduleActivityDataSetEntity.getScheduleDate());
		scheduleActivityDataSetTO.setParentCode(scheduleActivityDataSetEntity.getProjMstrEntity().getParentProjectMstrEntity().getCode());
		scheduleActivityDataSetTO.setParentName(scheduleActivityDataSetEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
		scheduleActivityDataSetTO.setParentProjId(scheduleActivityDataSetEntity.getProjMstrEntity().getProjectId());
	    
		return scheduleActivityDataSetTO;
	}
	
	public static ScheduleActivityDataSetEntity convertPOJOToEntity(ScheduleActivityDataSetTO scheduleActivityDataSetTO, EPSProjRepository ePSProjRepository, LoginRepository loginRepository) {
		ScheduleActivityDataSetEntity scheduleActivityDataSetEntity = new ScheduleActivityDataSetEntity();
		scheduleActivityDataSetEntity.setId(scheduleActivityDataSetTO.getId());
		scheduleActivityDataSetEntity.setBaseline(scheduleActivityDataSetTO.isBaseline() ? "Y" : "N");
		scheduleActivityDataSetEntity.setCurrent(scheduleActivityDataSetTO.isCurrent() ? "Y" : "N");
		scheduleActivityDataSetEntity.setName(scheduleActivityDataSetTO.getDatasetName());
		scheduleActivityDataSetEntity.setScheduleDate(scheduleActivityDataSetTO.getScheduleDate());
		scheduleActivityDataSetEntity.setProjMstrEntity(ePSProjRepository.findOne(scheduleActivityDataSetTO.getProjId()));
		scheduleActivityDataSetEntity.setType(scheduleActivityDataSetTO.getType());
		scheduleActivityDataSetEntity.setCreatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
		scheduleActivityDataSetEntity.setUpdatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
		scheduleActivityDataSetEntity.setUpdatedOn(new Date());
		return scheduleActivityDataSetEntity;
	}
}
