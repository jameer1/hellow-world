package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ProjectTO;

public class CoProjManpowerTO extends ProjectTO {

    private static final long serialVersionUID = 725069871190599664L;
    private Long id;
    
    private String manpowerClassificationId;
    private String manpowerDescription;
    private String unitOfMeasures;
    private Integer approvedManpowerHrs;
    private Integer pendingManpowerHrs;
    private Integer currentManpowerHrs;
    private Integer cumulativeManpowerHrs;
    private String notes;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getManpowerClassificationId() {
		return manpowerClassificationId;
	}
	public void setManpowerClassificationId(String manpowerClassificationId) {
		this.manpowerClassificationId = manpowerClassificationId;
	}
	public String getManpowerDescription() {
		return manpowerDescription;
	}
	public void setManpowerDescription(String manpowerDescription) {
		this.manpowerDescription = manpowerDescription;
	}
	public Integer getApprovedManpowerHrs() {
		return approvedManpowerHrs;
	}
	public void setApprovedManpowerHrs(Integer approvedManpowerHrs) {
		this.approvedManpowerHrs = approvedManpowerHrs;
	}
	public Integer getPendingManpowerHrs() {
		return pendingManpowerHrs;
	}
	public void setPendingManpowerHrs(Integer pendingManpowerHrs) {
		this.pendingManpowerHrs = pendingManpowerHrs;
	}
	public Integer getCurrentManpowerHrs() {
		return currentManpowerHrs;
	}
	public void setCurrentManpowerHrs(Integer currentManpowerHrs) {
		this.currentManpowerHrs = currentManpowerHrs;
	}
	public Integer getCumulativeManpowerHrs() {
		return cumulativeManpowerHrs;
	}
	public void setCumulativeManpowerHrs(Integer cumulativeManpowerHrs) {
		this.cumulativeManpowerHrs = cumulativeManpowerHrs;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getUnitOfMeasures() {
		return unitOfMeasures;
	}
	public void setUnitOfMeasures(String unitOfMeasures) {
		this.unitOfMeasures = unitOfMeasures;
	}
    
}
