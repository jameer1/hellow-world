package com.rjtech.timemanagement.workdairy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the work_dairy_manpower_wage database table.
 * 
 */
@Entity
@Table(name = "work_dairy_plant_status")
public class WorkDairyPlantStatusEntity implements Serializable {

    private static final long serialVersionUID = 54731890118455876L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDPS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPS_WDPD_ID")
    private WorkDairyPlantDtlEntity workDairyPlantDtlEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPS_USR_ID")
    private UserMstrEntity userId;

    @Column(name = "WDPS_COMMENTS")
    private String comments;

    @Column(name = "WDPS_APPR_STATUS")
    private String apprStatus;

    @Column(name = "WDPS_USED_TOTAL")
    private double usedTotal;

    @Column(name = "WDPS_IDLE_TOTAL")
    private double idleTotal;

    @Column(name = "WDPS_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPS_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "workDairyPlantStatusEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkDairyPlantCostEntity> workDairyPlantCostEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public double getUsedTotal() {
        return usedTotal;
    }

    public void setUsedTotal(double usedTotal) {
        this.usedTotal = usedTotal;
    }

    public double getIdleTotal() {
        return idleTotal;
    }

    public void setIdleTotal(double idleTotal) {
        this.idleTotal = idleTotal;
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

    public WorkDairyPlantDtlEntity getWorkDairyPlantDtlEntity() {
        return workDairyPlantDtlEntity;
    }

    public void setWorkDairyPlantDtlEntity(WorkDairyPlantDtlEntity workDairyPlantDtlEntity) {
        this.workDairyPlantDtlEntity = workDairyPlantDtlEntity;
    }

    public List<WorkDairyPlantCostEntity> getWorkDairyPlantCostEntities() {
        return workDairyPlantCostEntities;
    }

    public void setWorkDairyPlantCostEntities(List<WorkDairyPlantCostEntity> workDairyPlantCostEntities) {
        this.workDairyPlantCostEntities = workDairyPlantCostEntities;
    }

    public UserMstrEntity getUserId() {
        return userId;
    }

    public void setUserId(UserMstrEntity userId) {
        this.userId = userId;
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