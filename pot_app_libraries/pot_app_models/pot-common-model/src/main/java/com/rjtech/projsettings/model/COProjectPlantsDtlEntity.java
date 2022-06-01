package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;

@Entity
@Table(name = "co_project_plants_dtl")
public class COProjectPlantsDtlEntity implements Serializable {

    private static final long serialVersionUID = 24423332913436632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_PJP_ID")
    private Long id;

    @Column(name = "plant_classfctn_id")
    private String plantlClassificationId;
    @Column(name = "plant_desc")
    private String plantDescription;
    @Column(name = "apprvd_plant_hrs")
    private Integer approvedPlantHrs;
    @Column(name = "pend_plant_hrs")
    private Integer pendingPlantHrs;
    @Column(name = "current_plant_hrs")
    private Integer currentPlantHrs;
    @Column(name = "cumulative_plant_hrs")
    private Integer cumulativePlantHrs;
    
    @Column(name = "notes")
	private String notes;
	
	@Column(name = "STATUS")
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
