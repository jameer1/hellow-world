package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "proj_time_sheet")
public class TimesheetNormalTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PTS_ID")
    private Long id;

    @Column(name = "PTS_TYPE")
    private String type;

    @Column(name = "PTS_TYPE_ID")
    private Long typeId;

    @Column(name = "PTS_CUT_OFF_DAYS")
    private Integer cutOffDays;

    @Column(name = "PTS_CUT_OFF_TIME")
    private Time cutOffTime;

    @ManyToOne
    @JoinColumn(name = "PTS_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "PTS_WEEK_START_DAY")
    private String weeakStartDay;

    @Column(name = "PTS_WEEK_END_DAY")
    private String weeakEndDay;

    @Column(name = "PTS_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PTS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PTS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PTS_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PTS_DEF_STATUS")
    private String defaultStatus;

    @Column(name = "PTS_CUT_OFF_HOURS")
    private Integer cutOffHours;

    @Column(name = "PTS_CUT_OFF_MINUTES")
    private Integer cutOffMinutes;

    @Column(name = "PTS_IS_DEFAULT")
    private String isDefault;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getWeeakStartDay() {
        return weeakStartDay;
    }

    public void setWeeakStartDay(String weeakStartDay) {
        this.weeakStartDay = weeakStartDay;
    }

    public String getWeeakEndDay() {
        return weeakEndDay;
    }

    public void setWeeakEndDay(String weeakEndDay) {
        this.weeakEndDay = weeakEndDay;
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

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Integer getCutOffHours() {
        return cutOffHours;
    }

    public void setCutOffHours(Integer cutOffHours) {
        this.cutOffHours = cutOffHours;
    }

    public Integer getCutOffMinutes() {
        return cutOffMinutes;
    }

    public void setCutOffMinutes(Integer cutOffMinutes) {
        this.cutOffMinutes = cutOffMinutes;
    }

    public TimesheetNormalTimeEntity() {
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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