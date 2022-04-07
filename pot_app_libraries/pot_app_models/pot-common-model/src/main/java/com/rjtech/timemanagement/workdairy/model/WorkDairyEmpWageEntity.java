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

import com.rjtech.centrallib.model.EmpWageMstrEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the work_dairy_manpower_wage database table.
 * 
 */
@Entity
@Table(name = "work_dairy_manpower_wage")
public class WorkDairyEmpWageEntity implements Serializable {

    private static final long serialVersionUID = 3900870884862635073L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDMW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMW_EWM_ID")
    private EmpWageMstrEntity wageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMW_WDMP_ID")
    private WorkDairyEmpDtlEntity workDairyEmpDtlEntity;

    @Column(name = "WDMW_APPR_STATUS")
    private String apprStatus;

    @Column(name = "WDMW_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "WDMW_USED_TOTAL")
    private double usedTotal;

    @Column(name = "WDMW_IDLE_TOTAL")
    private double idleTotal;

    @Column(name = "WDMW_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMW_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMW_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMW_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMW_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "workDairyEmpWageEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkDairyEmpCostEntity> workDairyEmpCostEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkDairyEmpDtlEntity getWorkDairyEmpDtlEntity() {
        return workDairyEmpDtlEntity;
    }

    public void setWorkDairyEmpDtlEntity(WorkDairyEmpDtlEntity workDairyEmpDtlEntity) {
        this.workDairyEmpDtlEntity = workDairyEmpDtlEntity;
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

    public List<WorkDairyEmpCostEntity> getWorkDairyEmpCostEntities() {
        return workDairyEmpCostEntities;
    }

    public void setWorkDairyEmpCostEntities(List<WorkDairyEmpCostEntity> workDairyEmpCostEntities) {
        this.workDairyEmpCostEntities = workDairyEmpCostEntities;
    }

    public EmpWageMstrEntity getWageId() {
        return wageId;
    }

    public void setWageId(EmpWageMstrEntity wageId) {
        this.wageId = wageId;
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