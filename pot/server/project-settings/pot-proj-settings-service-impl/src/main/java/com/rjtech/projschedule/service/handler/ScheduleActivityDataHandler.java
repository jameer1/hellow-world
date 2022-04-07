package com.rjtech.projschedule.service.handler;

import java.util.Date;

import com.rjtech.common.repository.LoginRepository;
import com.rjtech.projectlib.repository.ProjSOEItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;

import com.rjtech.projschedule.dto.ScheduleActivityDataTO;
import com.rjtech.projschedule.model.ScheduleActivityDataEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ScheduleActivityDataHandler {
	
	public static ScheduleActivityDataTO convertEntityToPOJO(ScheduleActivityDataEntity scheduleActivityDataEntity) {
		ScheduleActivityDataTO scheduleActivityDataTO = new ScheduleActivityDataTO();
		scheduleActivityDataTO.setId(scheduleActivityDataEntity.getId());
		scheduleActivityDataTO.setActivityCode(scheduleActivityDataEntity.getCode());
		scheduleActivityDataTO.setActivityName(scheduleActivityDataEntity.getName());
		scheduleActivityDataTO.setCalendar(scheduleActivityDataEntity.getCalendar());
		scheduleActivityDataTO.setCritical(scheduleActivityDataEntity.getCritical());
		scheduleActivityDataTO.setFinishDate(scheduleActivityDataEntity.getFinishDate());
		scheduleActivityDataTO.setFinishDateFinal(scheduleActivityDataEntity.getFinishDateFlag().equalsIgnoreCase("Y") ? true : false);
		scheduleActivityDataTO.setLeadLag(scheduleActivityDataEntity.getLeadLag());
		scheduleActivityDataTO.setOriginalDuration(scheduleActivityDataEntity.getOriginalDuration());
		scheduleActivityDataTO.setPhysicalComplete(scheduleActivityDataEntity.getPhysicalComplete());
		if (scheduleActivityDataEntity.getSoe() != null) {
			scheduleActivityDataTO.setSoeCode(scheduleActivityDataEntity.getSoe().getCode());
			scheduleActivityDataTO.setSoeId(scheduleActivityDataEntity.getSoe().getId());
		}
		if (scheduleActivityDataEntity.getSow() != null) {
			scheduleActivityDataTO.setSowCode(scheduleActivityDataEntity.getSow().getCode());
			scheduleActivityDataTO.setSowId(scheduleActivityDataEntity.getSow().getId());
		}
		scheduleActivityDataTO.setWbsCode(scheduleActivityDataEntity.getWbsCode());
		scheduleActivityDataTO.setWbsName(scheduleActivityDataEntity.getWbsName());
		scheduleActivityDataTO.setWbsPath(scheduleActivityDataEntity.getWbsPath());
		scheduleActivityDataTO.setStartDate(scheduleActivityDataEntity.getStartDate());
		scheduleActivityDataTO.setStartDateFinal(scheduleActivityDataEntity.getStartDateFlag().equalsIgnoreCase("Y") ? true : false);
		scheduleActivityDataTO.setValid(true);
		
		return scheduleActivityDataTO;
	}
	
	public static ScheduleActivityDataEntity convertPOJOToEntity(ScheduleActivityDataTO scheduleActivityDataTO, Long projectId, ProjSOEItemRepositoryCopy projSOEItemRepositoryCopy, ProjSOWItemRepositoryCopy projSOWItemRepository, LoginRepository loginRepository) {
		ScheduleActivityDataEntity scheduleActivityDataEntity =new ScheduleActivityDataEntity();
		scheduleActivityDataEntity.setId(scheduleActivityDataTO.getId());
		scheduleActivityDataEntity.setCode(scheduleActivityDataTO.getActivityCode());
		scheduleActivityDataEntity.setName(scheduleActivityDataTO.getActivityName());
		scheduleActivityDataEntity.setCalendar(scheduleActivityDataTO.getCalendar());
		scheduleActivityDataEntity.setCritical(scheduleActivityDataTO.getCritical());
		scheduleActivityDataEntity.setFinishDate(scheduleActivityDataTO.getFinishDate());
		scheduleActivityDataEntity.setFinishDateFlag(scheduleActivityDataTO.isFinishDateFinal() ? "Y" : "N");
		scheduleActivityDataEntity.setLeadLag(scheduleActivityDataTO.getLeadLag());
		scheduleActivityDataEntity.setOriginalDuration(scheduleActivityDataTO.getOriginalDuration());
		scheduleActivityDataEntity.setPhysicalComplete(scheduleActivityDataTO.getPhysicalComplete());
		scheduleActivityDataEntity.setSoe(projSOEItemRepositoryCopy.findBy(projectId, scheduleActivityDataTO.getSoeCode()));
		if (scheduleActivityDataEntity.getSoe() != null)
			scheduleActivityDataEntity.setSow(projSOWItemRepository.findBy(scheduleActivityDataEntity.getSoe().getId()));
		scheduleActivityDataEntity.setWbsCode(scheduleActivityDataTO.getWbsCode());
		scheduleActivityDataEntity.setWbsName(scheduleActivityDataTO.getWbsName());
		scheduleActivityDataEntity.setWbsPath(scheduleActivityDataTO.getWbsPath());
		scheduleActivityDataEntity.setStartDate(scheduleActivityDataTO.getStartDate());
		scheduleActivityDataEntity.setStartDateFlag(scheduleActivityDataTO.isFinishDateFinal() ? "Y" : "N");
		scheduleActivityDataEntity.setCreatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
		scheduleActivityDataEntity.setUpdatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
		scheduleActivityDataEntity.setUpdatedOn(new Date());
		
		return scheduleActivityDataEntity;
	}
}
