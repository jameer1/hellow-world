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
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.notification.model.TimeSheetNotificationsEntityCopy;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;

@Entity
@Table(name = "proj_time_sheet_approval")
public class TimeSheetAddtionalTimeApprEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TSA_ID")
    private Long id;

    @Column(name = "TSA_TNTF")
    private String notification;

    @ManyToOne
    @JoinColumn(name = "TSA_TNTF_ID")
    private TimeSheetNotificationsEntity timeSheetNotificationsEntity;

    @Column(name = "TSA_CRW_TYPE")
    private String crewType;

    @Temporal(TemporalType.DATE)
    @Column(name = "TSA_DATE")
    private Date timeSheetDate;

    @Column(name = "TSA_IS_LATEST")
    private boolean latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSA_END_DATE")
    private Date timeSheetEndDate;

    @Column(name = "TSA_GRANT_HRS")
    private Integer grantHrs;

    // TODO remove this, if not required
    @Column(name = "TSA_TSM_ID")
    private Long timeSheetNo;

    @Column(name = "TSA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TSA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TSA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "TSA_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @ManyToOne
    @JoinColumn(name = "TSA_PTS_ID")
    private TimesheetNormalTimeEntity projTimeSheetEntity;

    @ManyToOne
    @JoinColumn(name = "TSA_CRW_ID")
    private ProjCrewMstrEntity projCrewMasterEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCrewType() {
        return crewType;
    }

    public void setCrewType(String crewType) {
        this.crewType = crewType;
    }

    public TimeSheetNotificationsEntity getTimeSheetNotificationsEntity() {
        return timeSheetNotificationsEntity;
    }

    public void setTimeSheetNotificationsEntity(TimeSheetNotificationsEntity timeSheetNotificationsEntity) {
        this.timeSheetNotificationsEntity = timeSheetNotificationsEntity;
    }

    public Date getTimeSheetDate() {
        return timeSheetDate;
    }

    public void setTimeSheetDate(Date timeSheetDate) {
        this.timeSheetDate = timeSheetDate;
    }

    public Date getTimeSheetEndDate() {
        return timeSheetEndDate;
    }

    public void setTimeSheetEndDate(Date timeSheetEndDate) {
        this.timeSheetEndDate = timeSheetEndDate;
    }

    public Integer getGrantHrs() {
        return grantHrs;
    }

    public void setGrantHrs(Integer grantHrs) {
        this.grantHrs = grantHrs;
    }

    public Long getTimeSheetNo() {
        return timeSheetNo;
    }

    public void setTimeSheetNo(Long timeSheetNo) {
        this.timeSheetNo = timeSheetNo;
    }

    public TimesheetNormalTimeEntity getProjTimeSheetEntity() {
        return projTimeSheetEntity;
    }

    public void setProjTimeSheetEntity(TimesheetNormalTimeEntity projTimeSheetEntity) {
        this.projTimeSheetEntity = projTimeSheetEntity;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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

    public ProjCrewMstrEntity getProjCrewMasterEntity() {
        return projCrewMasterEntity;
    }

    public void setProjCrewMasterEntity(ProjCrewMstrEntity projCrewMasterEntity) {
        this.projCrewMasterEntity = projCrewMasterEntity;
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