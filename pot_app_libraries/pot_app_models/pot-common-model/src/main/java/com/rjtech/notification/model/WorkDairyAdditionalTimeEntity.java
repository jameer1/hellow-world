package com.rjtech.notification.model;

import java.io.Serializable;
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

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

@Entity
@Table(name = "WORK_DAIRY_ADDT_TIME")
public class WorkDairyAdditionalTimeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDAT_ID")
    private Long id;

    @Column(name = "WDAT_FROM_DATE")
    private Date fromDate;

    @Column(name = "WDAT_TO_DATE")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "WDAT_APPR_USER")
    private UserMstrEntity apprUser;

    @Column(name = "WDAT_STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDAT_CREW_ID")
    private ProjCrewMstrEntity crewId;

    @Column(name = "WDAT_TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "WDAT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDAT_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "WDAT_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDAT_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDAT_WDM_ID")
    private WorkDairyEntity workDairyEntity;
    
    @Column(name = "WDAT_APPROVED_DATE")
    private Date approvedDate;

    public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public UserMstrEntity getApprUser() {
        return apprUser;
    }

    public void setApprUser(UserMstrEntity apprUser) {
        this.apprUser = apprUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjCrewMstrEntity getCrewId() {
        return crewId;
    }

    public void setCrewId(ProjCrewMstrEntity crewId) {
        this.crewId = crewId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public WorkDairyEntity getWorkDairyEntity() {
        return workDairyEntity;
    }

    public void setWorkDairyEntity(WorkDairyEntity workDairyEntity) {
        this.workDairyEntity = workDairyEntity;
    }
}
