package com.rjtech.projschedule.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projschedule.req.ScheduleActivityDataSetReq;
import com.rjtech.projschedule.resp.ScheduleActivityDataSetResp;

public class ScheduleActivityDataSetTO extends ProjectTO implements Serializable {

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
	private Date createdDate;
	private List<ScheduleActivityDataTO> scheduleActivityDataTOs;
	private List<ResourceAssignmentDataTO> resourceAssignmentDataTOs;
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
	public List<ResourceAssignmentDataTO> getResourceAssignmentDataTOs() {
		return resourceAssignmentDataTOs;
	}
	public void setResourceAssignmentDataTOs(List<ResourceAssignmentDataTO> resourceAssignmentDataTOs) {
		this.resourceAssignmentDataTOs = resourceAssignmentDataTOs;
	}

	public void fromScheduleActivityDataSetReq(ScheduleActivityDataSetReq scheduleActivityDataSetReq) {
		this.setProjId(scheduleActivityDataSetReq.getProjId());
	    this.setCode(scheduleActivityDataSetReq.getCode());
	    this.setName(scheduleActivityDataSetReq.getName());
	    this.setParentCode(scheduleActivityDataSetReq.getParentCode());
	    this.setParentName(scheduleActivityDataSetReq.getParentName());
	    this.setParentProjId(scheduleActivityDataSetReq.getParentProjId());
	    this.setClientId(scheduleActivityDataSetReq.getClientId());
	    this.setClientCode(scheduleActivityDataSetReq.getClientCode());
	    this.setEmail(scheduleActivityDataSetReq.getEmail());
	    
		this.id = scheduleActivityDataSetReq.getId();
		this.datasetName = scheduleActivityDataSetReq.getDatasetName();
		this.scheduleDate = scheduleActivityDataSetReq.getScheduleDate();
		this.type = scheduleActivityDataSetReq.getType();
		this.isCurrent = scheduleActivityDataSetReq.isCurrent();
		this.isBaseline = scheduleActivityDataSetReq.isBaseline();
		this.scheduleActivityDataTOs = scheduleActivityDataSetReq.getScheduleActivityDataTOs();
		this.resourceAssignmentDataTOs = scheduleActivityDataSetReq.getResourceAssignmentDataTableTOs();
	}
	
	public ScheduleActivityDataSetResp toScheduleActivityDataSetResp() {
		ScheduleActivityDataSetResp scheduleActivityDataSetResp = new ScheduleActivityDataSetResp();
		scheduleActivityDataSetResp.setProjId(this.getProjId());
		scheduleActivityDataSetResp.setCode(this.getCode());
		scheduleActivityDataSetResp.setName(this.getName());
		scheduleActivityDataSetResp.setParentCode(this.getParentCode());
		scheduleActivityDataSetResp.setParentName(this.getParentName());
		scheduleActivityDataSetResp.setParentProjId(this.getParentProjId());
		scheduleActivityDataSetResp.setClientId(this.getClientId());
		scheduleActivityDataSetResp.setClientCode(this.getClientCode());
		scheduleActivityDataSetResp.setEmail(this.getEmail());
		
		scheduleActivityDataSetResp.setId(this.id);
		scheduleActivityDataSetResp.setDatasetName(this.datasetName);
		scheduleActivityDataSetResp.setScheduleDate(this.scheduleDate);
		scheduleActivityDataSetResp.setType(this.type);
		scheduleActivityDataSetResp.setCurrent(this.isCurrent);
		scheduleActivityDataSetResp.setBaseline(this.isBaseline);
		scheduleActivityDataSetResp.setScheduleActivityDataTOs(this.scheduleActivityDataTOs);
		scheduleActivityDataSetResp.setResourceAssignmentDataTableTOs(this.resourceAssignmentDataTOs);
		
		return scheduleActivityDataSetResp;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
}
