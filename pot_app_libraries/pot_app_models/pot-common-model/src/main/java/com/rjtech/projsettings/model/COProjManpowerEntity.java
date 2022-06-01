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
@Table(name = "CO_PROJECT_MANPOWER_DTL")
public class COProjManpowerEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2909091518385740194L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_MPD_ID")
    private Long id;
	
	@Column(name = "mp_classification_Id")
	private String manpowerClassificationId;

	@Column(name = "mp_desc")
	private String manpowerDescription;

	@Column(name = "unit_Of_Measures")
	private String unitOfMeasures;

	@Column(name = "apprvd_mp_hrs")
	private Integer approvedManpowerHrs;

	@Column(name = "pend_mp_hrs")
	private Integer pendingManpowerHrs;

	@Column(name = "current_mp_hrs")
	private Integer currentManpowerHrs;

	@Column(name = "cumulative_mp_hrs")
	private Integer cumulativeManpowerHrs;

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

	public String getUnitOfMeasures() {
		return unitOfMeasures;
	}

	public void setUnitOfMeasures(String unitOfMeasures) {
		this.unitOfMeasures = unitOfMeasures;
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
