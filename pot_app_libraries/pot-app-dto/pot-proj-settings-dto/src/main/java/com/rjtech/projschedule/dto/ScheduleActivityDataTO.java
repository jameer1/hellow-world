package com.rjtech.projschedule.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleActivityDataTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer critical;
	private String wbsCode;
	private String wbsName;
	private String wbsPath;
	private String activityCode;
	private String activityName;
	private Long soeId;
	private String soeCode;
	private Long originalDuration;
	private Date startDate;
	private boolean isStartDateFinal;
    private Date finishDate;
    private boolean isFinishDateFinal;
    private String predecessors;
    private String successors;
    private Long physicalComplete;
    private String calendar;
    private int leadLag;
    private boolean isValid;
    private List<ScheduleActivityDataTO> predecessorTOs = new ArrayList<ScheduleActivityDataTO>();
    private List<ScheduleActivityDataTO> successorTOs = new ArrayList<ScheduleActivityDataTO>();
    private List<String> validationMessages = new ArrayList<String>();
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;
    private Long sowId;
	private String sowCode;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCritical() {
		return critical;
	}
	public void setCritical(Integer critical) {
		this.critical = critical;
	}
	public String getWbsCode() {
		return wbsCode;
	}
	public void setWbsCode(String wbsCode) {
		this.wbsCode = wbsCode;
	}
	public String getWbsName() {
		return wbsName;
	}
	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Long getSoeId() {
		return soeId;
	}
	public void setSoeId(Long soeId) {
		this.soeId = soeId;
	}
	public String getSoeCode() {
		return soeCode;
	}
	public void setSoeCode(String soeCode) {
		this.soeCode = soeCode;
	}
	public Long getOriginalDuration() {
		return originalDuration;
	}
	public void setOriginalDuration(Long originalDuration) {
		this.originalDuration = originalDuration;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public boolean isStartDateFinal() {
		return isStartDateFinal;
	}
	public void setStartDateFinal(boolean isStartDateFinal) {
		this.isStartDateFinal = isStartDateFinal;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public boolean isFinishDateFinal() {
		return isFinishDateFinal;
	}
	public void setFinishDateFinal(boolean isFinishDateFinal) {
		this.isFinishDateFinal = isFinishDateFinal;
	}
	public String getPredecessors() {
		return predecessors;
	}
	public void setPredecessors(String predecessors) {
		this.predecessors = predecessors;
	}
	public String getSuccessors() {
		return successors;
	}
	public void setSuccessors(String successors) {
		this.successors = successors;
	}
	public Long getPhysicalComplete() {
		return physicalComplete;
	}
	public void setPhysicalComplete(Long physicalComplete) {
		this.physicalComplete = physicalComplete;
	}
	public String getCalendar() {
		return calendar;
	}
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public List<ScheduleActivityDataTO> getPredecessorTOs() {
		return predecessorTOs;
	}
	public void setPredecessorTOs(List<ScheduleActivityDataTO> predecessorTOs) {
		this.predecessorTOs = predecessorTOs;
	}
	public List<ScheduleActivityDataTO> getSuccessorTOs() {
		return successorTOs;
	}
	public void setSuccessorTOs(List<ScheduleActivityDataTO> successorTOs) {
		this.successorTOs = successorTOs;
	}
    public void addPredecessor(ScheduleActivityDataTO scheduleActivityDataTO) {
    	this.predecessorTOs.add(scheduleActivityDataTO);
    	if (this.predecessors == null || this.predecessors.length() == 0)
    		this.predecessors = scheduleActivityDataTO.activityCode;
    	else
    		this.predecessors = this.predecessors + ", " + scheduleActivityDataTO.activityCode;
    	
    }
    public void addSuccessor(ScheduleActivityDataTO scheduleActivityDataTO) {
    	this.successorTOs.add(scheduleActivityDataTO);
    	if (this.successors == null || this.successors.length() == 0)
    		this.successors = scheduleActivityDataTO.activityCode;
    	else
    		this.successors = this.successors + ", " + scheduleActivityDataTO.activityCode;
    }
	public List<String> getValidationMessages() {
		return validationMessages;
	}
	public void setValidationMessages(List<String> validationMessages) {
		this.validationMessages = validationMessages;
	}
	public void addValidationMessage(String validationMessage) {
		this.validationMessages.add(validationMessage);
	}
	public int getLeadLag() {
		return leadLag;
	}
	public void setLeadLag(int leadLag) {
		this.leadLag = leadLag;
	}
	public Date getMinStartDateOfBaseline() {
		return minStartDateOfBaseline;
	}
	public void setMinStartDateOfBaseline(Date minStartDateOfBaseline) {
		this.minStartDateOfBaseline = minStartDateOfBaseline;
	}
	public Date getMaxFinishDateOfBaseline() {
		return maxFinishDateOfBaseline;
	}
	public void setMaxFinishDateOfBaseline(Date maxFinishDateOfBaseline) {
		this.maxFinishDateOfBaseline = maxFinishDateOfBaseline;
	}
	public String getWbsPath() {
		return wbsPath;
	}
	public void setWbsPath(String wbsPath) {
		this.wbsPath = wbsPath;
	}
	public Long getSowId() {
		return sowId;
	}
	public void setSowId(Long sowId) {
		this.sowId = sowId;
	}
	public String getSowCode() {
		return sowCode;
	}
	public void setSowCode(String sowCode) {
		this.sowCode = sowCode;
	}
}
