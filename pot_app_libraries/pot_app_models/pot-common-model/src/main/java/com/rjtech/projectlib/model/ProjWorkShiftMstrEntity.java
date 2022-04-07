package com.rjtech.projectlib.model;

import java.io.Serializable;
import java.sql.Time;
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
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the shifts_mstr database table.
 * 
 * UI Ref: Projects/project library/work shifts
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHF_EPM_ID")
    private ProjMstrEntity projectId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHF_UPDATED_BY")
    private UserMstrEntity updatedBy;

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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ProjMstrEntity getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjMstrEntity projectId) {
        this.projectId = projectId;
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