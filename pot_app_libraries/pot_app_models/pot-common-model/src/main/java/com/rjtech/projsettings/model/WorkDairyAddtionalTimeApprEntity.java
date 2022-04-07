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
import com.rjtech.notification.model.WorkDairyNotificationsEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.notification.model.WorkDairyNotificationsEntityCopy;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;

@Entity
@Table(name = "proj_work_dairy_approval")
public class WorkDairyAddtionalTimeApprEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PWA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PWA_WNTF_ID")
    private WorkDairyNotificationsEntity workDairyNotificationsEntity;

    @Column(name = "PWA_IS_LATEST")
    private boolean latest;

    @ManyToOne
    @JoinColumn(name = "PWA_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PWA_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PWA_TO_DATE")
    private Date toDate;

    @Column(name = "PWA_NTF", updatable = false)
    private String notification;

    @Column(name = "PWA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PWA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PWA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PWA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PWA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PWA_PJW_ID")
    private WorkDairyNormalTimeEntity projWorkDairyMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PWA_CRW_ID")
    private ProjCrewMstrEntity projCrewMasterEntity;

    public ProjCrewMstrEntity getProjCrewMasterEntity() {
        return projCrewMasterEntity;
    }

    public void setProjCrewMasterEntity(ProjCrewMstrEntity projCrewMasterEntity) {
        this.projCrewMasterEntity = projCrewMasterEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkDairyNormalTimeEntity getProjWorkDairyMstrEntity() {
        return projWorkDairyMstrEntity;
    }

    public void setProjWorkDairyMstrEntity(WorkDairyNormalTimeEntity projWorkDairyMstrEntity) {
        this.projWorkDairyMstrEntity = projWorkDairyMstrEntity;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
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

    public WorkDairyNotificationsEntity getWorkDairyNotificationsEntity() {
        return workDairyNotificationsEntity;
    }

    public void setWorkDairyNotificationsEntity(WorkDairyNotificationsEntity workDairyNotificationsEntity) {
        this.workDairyNotificationsEntity = workDairyNotificationsEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

}