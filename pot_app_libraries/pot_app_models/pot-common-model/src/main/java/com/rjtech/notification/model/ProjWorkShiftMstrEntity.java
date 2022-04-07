package com.rjtech.notification.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

/**
 * The persistent class for the shifts_mstr database table.
 * 
 */
@Entity
@Table(name = "proj_shifts_mstr")
public class ProjWorkShiftMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHF_ID")
    private Long id;

    @Column(name = "SHF_CODE")
    private String code;

    @Column(name = "SHF_EPM_ID")
    private Long projectId;

    @Column(name = "SHF_FINISH_TIME")
    private Time finishTime;

    @Column(name = "SHF_START_TIME")
    private Time startTime;

    @Column(name = "SHF_START_HOURS")
    private Integer startHours;

    @Column(name = "SHF_START_MINTUES")
    private Integer startMinutes;

    @Column(name = "SHF_FINISH_HOURS")
    private Integer finishHours;

    @Column(name = "SHF_FINISH_MINTUES")
    private Integer finishMinutes;

    @Column(name = "SHF_STATUS")
    private Integer status;

    @Column(name = "SHF_CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHF_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "SHF_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHF_UPDATED_ON")
    private Date updatedOn;

    public ProjWorkShiftMstrEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Integer getStartHours() {
        return startHours;
    }

    public void setStartHours(Integer startHours) {
        this.startHours = startHours;
    }

    public Integer getFinishHours() {
        return finishHours;
    }

    public void setFinishHours(Integer finishHours) {
        this.finishHours = finishHours;
    }

    public Integer getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(Integer startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Integer getFinishMinutes() {
        return finishMinutes;
    }

    public void setFinishMinutes(Integer finishMinutes) {
        this.finishMinutes = finishMinutes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @PrePersist
    public void onPrePersist() {
        this.setCreatedOn(CommonUtil.getNow());
        this.setCreatedBy(AppUserUtils.getUserName());
        this.setUpdatedOn(CommonUtil.getNow());
        this.setUpdatedBy(AppUserUtils.getUserName());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedOn(CommonUtil.getNow());
        this.setUpdatedBy(AppUserUtils.getUserName());
    }
}