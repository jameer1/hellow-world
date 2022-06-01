package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;

@Entity
@Table(name = "CO_PROJ_MATERIAL_BUDGET")
public class COProjectMaterialBudgetEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6819653859847496710L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_pmb_id")
    private Long id;
	
	@Column(name = "material_clsfcn_id")
	private String materialClassificationId;
	@Column(name = "material_desc")
	private String materialDescription;
	@Column(name = "approved_mat_qty")
	private Integer approvedMaterialQty;
	@Column(name = "pending_mat_qty")
	private Integer pendingMaterialQty;
	@Column(name = "current_mat_qty")
	private Integer currentMaterialQty;
	@Column(name = "cumulative_mat_qty")
	private Integer cumulativeMaterialQty;
	@Column(name = "notes")
	private String notes;

	@Column(name = "status")
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON", updatable = false)
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	@ManyToOne
	@JoinColumn(name = "CO_ID", updatable = false)
	private ChangeOrderMstrEntity changeOrderMstr;
	
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public UserMstrEntity getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserMstrEntity createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public UserMstrEntity getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(UserMstrEntity updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public ChangeOrderMstrEntity getChangeOrderMstr() {
		return changeOrderMstr;
	}
	public void setChangeOrderMstr(ChangeOrderMstrEntity changeOrderMstr) {
		this.changeOrderMstr = changeOrderMstr;
	}
	
}
