package com.rjtech.projectlib.dto;

import java.io.Serializable;

public class CoMaterialTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978075904275574532L;

	private Long id;
	private String materialClassificationId;
	private String materialDescription;
	private Integer approvedMaterialQty;
	private Integer pendingMaterialQty;
	private Integer currentMaterialQty;
	private Integer cumulativeMaterialQty;
	private String notes;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMaterialClassificationId() {
		return materialClassificationId;
	}
	public void setMaterialClassificationId(String materialClassificationId) {
		this.materialClassificationId = materialClassificationId;
	}
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	public Integer getApprovedMaterialQty() {
		return approvedMaterialQty;
	}
	public void setApprovedMaterialQty(Integer approvedMaterialQty) {
		this.approvedMaterialQty = approvedMaterialQty;
	}
	public Integer getPendingMaterialQty() {
		return pendingMaterialQty;
	}
	public void setPendingMaterialQty(Integer pendingMaterialQty) {
		this.pendingMaterialQty = pendingMaterialQty;
	}
	public Integer getCurrentMaterialQty() {
		return currentMaterialQty;
	}
	public void setCurrentMaterialQty(Integer currentMaterialQty) {
		this.currentMaterialQty = currentMaterialQty;
	}
	public Integer getCumulativeMaterialQty() {
		return cumulativeMaterialQty;
	}
	public void setCumulativeMaterialQty(Integer cumulativeMaterialQty) {
		this.cumulativeMaterialQty = cumulativeMaterialQty;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
