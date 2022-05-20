package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ProjectTO;

public class CoProjPlantTO extends ProjectTO {

    private static final long serialVersionUID = 725069871190599664L;
    private Long id;
    private String plantlClassificationId;
    private String plantDescription;
    private Integer approvedPlantHrs;
    private Integer pendingPlantHrs;
    private Integer currentPlantHrs;
    private Integer cumulativePlantHrs;
	private String notes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlantlClassificationId() {
		return plantlClassificationId;
	}
	public void setPlantlClassificationId(String plantlClassificationId) {
		this.plantlClassificationId = plantlClassificationId;
	}
	public String getPlantDescription() {
		return plantDescription;
	}
	public void setPlantDescription(String plantDescription) {
		this.plantDescription = plantDescription;
	}
	public Integer getApprovedPlantHrs() {
		return approvedPlantHrs;
	}
	public void setApprovedPlantHrs(Integer approvedPlantHrs) {
		this.approvedPlantHrs = approvedPlantHrs;
	}
	public Integer getPendingPlantHrs() {
		return pendingPlantHrs;
	}
	public void setPendingPlantHrs(Integer pendingPlantHrs) {
		this.pendingPlantHrs = pendingPlantHrs;
	}
	public Integer getCurrentPlantHrs() {
		return currentPlantHrs;
	}
	public void setCurrentPlantHrs(Integer currentPlantHrs) {
		this.currentPlantHrs = currentPlantHrs;
	}
	public Integer getCumulativePlantHrs() {
		return cumulativePlantHrs;
	}
	public void setCumulativePlantHrs(Integer cumulativePlantHrs) {
		this.cumulativePlantHrs = cumulativePlantHrs;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

    
}
