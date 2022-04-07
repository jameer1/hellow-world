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
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;

/**
 * The persistent class for the work_dairy_manpower database table.
 * 
 */
@Entity
@Table(name = "work_dairy_manpower")
public class WorkDairyEmpDtlEntity implements Serializable {

    private static final long serialVersionUID = 5222830923929197708L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDMP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMP_ERD_ID")
    private EmpRegisterDtlEntity empRegId;

    @Column(name = "WDMP_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMP_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMP_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMP_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMP_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "WDMP_WDM_ID")
    private WorkDairyEntity workDairyEntity;

    @OneToMany(mappedBy = "workDairyEmpDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkDairyEmpWageEntity> workDairyEmpWageEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkDairyEntity getWorkDairyEntity() {
        return workDairyEntity;
    }

    public void setWorkDairyEntity(WorkDairyEntity workDairyEntity) {
        this.workDairyEntity = workDairyEntity;
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

    public List<WorkDairyEmpWageEntity> getWorkDairyEmpWageEntities() {
        return workDairyEmpWageEntities;
    }

    public void setWorkDairyEmpWageEntities(List<WorkDairyEmpWageEntity> workDairyEmpWageEntities) {
        this.workDairyEmpWageEntities = workDairyEmpWageEntities;
    }

    public EmpRegisterDtlEntity getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(EmpRegisterDtlEntity empRegId) {
        this.empRegId = empRegId;
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