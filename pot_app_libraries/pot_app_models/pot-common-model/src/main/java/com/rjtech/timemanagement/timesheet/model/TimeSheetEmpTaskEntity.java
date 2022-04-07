package com.rjtech.timemanagement.timesheet.model;

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

/**
 * The persistent class for the time_sheet_task_dtl database table.
 * 
 */
@Entity
@Table(name = "time_sheet_task_dtl")
public class TimeSheetEmpTaskEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TSTD_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSTD_DATE")
    private Date date;

    @Column(name = "TSTD_TASK_NAME")
    private String taskName;

    @ManyToOne
    @JoinColumn(name = "TSTD_APPR_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @Column(name = "TSTD_APPR_STATUS")
    private String apprStatus;

    @ManyToOne
    @JoinColumn(name = "TSTD_PARENT_ID")
    private TimeSheetEmpTaskEntity parentTimeSheetEmpTaskEntity;

    @ManyToOne
    @JoinColumn(name = "TSTD_TSED_ID")
    private TimeSheetEmpDtlEntity timeSheetEmpDtlEntity;

    @Column(name = "TSTD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TSTD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSTD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TSTD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSTD_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public TimeSheetEmpTaskEntity getParentTimeSheetEmpTaskEntity() {
        return parentTimeSheetEmpTaskEntity;
    }

    public void setParentTimeSheetEmpTaskEntity(TimeSheetEmpTaskEntity parentTimeSheetEmpTaskEntity) {
        this.parentTimeSheetEmpTaskEntity = parentTimeSheetEmpTaskEntity;
    }

    public TimeSheetEmpDtlEntity getTimeSheetEmpDtlEntity() {
        return timeSheetEmpDtlEntity;
    }

    public void setTimeSheetEmpDtlEntity(TimeSheetEmpDtlEntity timeSheetEmpDtlEntity) {
        this.timeSheetEmpDtlEntity = timeSheetEmpDtlEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

}
