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
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;

/**
 * The persistent class for the work_dairy_plant_dtl database table.
 * 
 */
@Entity
@Table(name = "work_dairy_plant_dtl")
public class WorkDairyPlantDtlEntity implements Serializable {

    private static final long serialVersionUID = 6584721211942237213L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDPD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPD_PLRD_ID")
    private PlantRegisterDtlEntity plantRegId;

    @Column(name = "WDPD_SHIFT_NAME")
    private String shiftName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPD_USR_ID")
    private UserMstrEntity apprUserId;

    @Column(name = "WDPD_APPRV_STATUS")
    private String apprStatus;

    @Column(name = "WDPD_APPRV_COMMENTS")
    private String apprComments;

    @Column(name = "WDPD_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDPD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDPD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "WDPD_WDM_ID")
    private WorkDairyEntity workDairyEntity;

    @OneToMany(mappedBy = "workDairyPlantDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkDairyPlantStatusEntity> workDairyPlantStatusEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
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

    public WorkDairyEntity getWorkDairyEntity() {
        return workDairyEntity;
    }

    public void setWorkDairyEntity(WorkDairyEntity workDairyEntity) {
        this.workDairyEntity = workDairyEntity;
    }

    public List<WorkDairyPlantStatusEntity> getWorkDairyPlantStatusEntities() {
        return workDairyPlantStatusEntities;
    }

    public void setWorkDairyPlantStatusEntities(List<WorkDairyPlantStatusEntity> workDairyPlantStatusEntities) {
        this.workDairyPlantStatusEntities = workDairyPlantStatusEntities;
    }

    public PlantRegisterDtlEntity getPlantRegId() {
        return plantRegId;
    }

    public void setPlantRegId(PlantRegisterDtlEntity plantRegId) {
        this.plantRegId = plantRegId;
    }

    public UserMstrEntity getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(UserMstrEntity apprUserId) {
        this.apprUserId = apprUserId;
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