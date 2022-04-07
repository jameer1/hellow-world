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
import com.rjtech.eps.model.ProjMstrEntity;

// TODO remove this table, if not required

@Entity
@Table(name = "proj_timesheet_week_dtl")
public class ProjTimeSheetWeekDtlEntity implements Serializable {

    private static final long serialVersionUID = -7351387309848564187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TWD_ID")
    private Long id;

    @Column(name = "TWD_WEEK_END_DAYS")
    private String weekEndDay;

    @Column(name = "TWD_COMMENCING_DAYS")
    private String weekStartDay;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TWD_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TWD_EFFECTIVE_TO")
    private Date effectiveTo;

    @Column(name = "TWD_IS_LATEST")
    private String isLatest;

    @ManyToOne
    @JoinColumn(name = "TWD_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "TWD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TWD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TWD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TWD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TWD_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "TWD_IS_DEFAULT")
    private String isDefault;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeekEndDay() {
        return weekEndDay;
    }

    public void setWeekEndDay(String weekEndDay) {
        this.weekEndDay = weekEndDay;
    }

    public String getWeekStartDay() {
        return weekStartDay;
    }

    public void setWeekStartDay(String weekStartDay) {
        this.weekStartDay = weekStartDay;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
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

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}
