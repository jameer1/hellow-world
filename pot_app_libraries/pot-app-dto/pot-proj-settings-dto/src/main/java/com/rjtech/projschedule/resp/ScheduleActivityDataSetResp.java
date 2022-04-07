package com.rjtech.projschedule.resp;

import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projschedule.dto.ResourceAssignmentDataTO;
import com.rjtech.projschedule.dto.ScheduleActivityDataSetTO;
import com.rjtech.projschedule.dto.ScheduleActivityDataTO;

public class ScheduleActivityDataSetResp extends ProjectTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String datasetName;
	private Date scheduleDate;
	private String type;
	private boolean isCurrent;
	private boolean isBaseline;
	private List<ScheduleActivityDataTO> scheduleActivityDataTOs = null;
	private List<ResourceAssignmentDataTO> resourceAssignmentDataTableTOs = null;
	private boolean hasInvalidData;
	private List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOs = null;
	private boolean isPrimaveraIntegrated = true;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDatasetName() {
		return datasetName;
	}
	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isCurrent() {
		return isCurrent;
	}
	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public boolean isBaseline() {
		return isBaseline;
	}
	public void setBaseline(boolean isBaseline) {
		this.isBaseline = isBaseline;
	}
	public List<ScheduleActivityDataTO> getScheduleActivityDataTOs() {
		return scheduleActivityDataTOs;
	}
	public void setScheduleActivityDataTOs(List<ScheduleActivityDataTO> scheduleActivityDataTOs) {
		this.scheduleActivityDataTOs = scheduleActivityDataTOs;
	}
	public boolean isHasInvalidData() {
		return hasInvalidData;
	}
	public void setHasInvalidData(boolean hasInvalidData) {
		this.hasInvalidData = hasInvalidData;
	}
	public List<ScheduleActivityDataSetTO> getScheduleActivityDataSetTOs() {
		return scheduleActivityDataSetTOs;
	}
	public void setScheduleActivityDataSetTOs(List<ScheduleActivityDataSetTO> scheduleActivityDataSetTOs) {
		this.scheduleActivityDataSetTOs = scheduleActivityDataSetTOs;
	}
	public List<ResourceAssignmentDataTO> getResourceAssignmentDataTableTOs() {
		return resourceAssignmentDataTableTOs;
	}
	public void setResourceAssignmentDataTableTOs(List<ResourceAssignmentDataTO> resourceAssignmentDataTOs) {
		this.resourceAssignmentDataTableTOs = resourceAssignmentDataTOs;
	}
	public boolean isPrimaveraIntegrated() {
		return isPrimaveraIntegrated;
	}
	public void setPrimaveraIntegrated(boolean isPrimaveraIntegrated) {
		this.isPrimaveraIntegrated = isPrimaveraIntegrated;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
}
