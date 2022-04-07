package com.rjtech.timemanagement.timesheet.model;

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
 * The persistent class for the time_sheet_emp_dtl database table.
 * 
 */
@Entity
@Table(name = "time_sheet_emp_dtl")
public class TimeSheetEmpDtlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TSED_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TSED_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "TSED_TSM_ID")
    private TimeSheetEntity timeSheetEntity;

    @OneToMany(mappedBy = "timeSheetEmpDtlEntity", fetch = FetchType.LAZY)
    private List<TimeSheetEmpExpenseEntity> timeSheetEmpExpenseEntities = new ArrayList<>();

    @OneToMany(mappedBy = "timeSheetEmpDtlEntity", fetch = FetchType.LAZY)
    private List<TimeSheetEmpTaskEntity> timeSheetEmpTaskEntities = new ArrayList<>();

    @OneToMany(mappedBy = "timeSheetEmpDtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TimeSheetEmpWorkDtlEntity> timeSheetEmpWorkDtlEntities = new ArrayList<>();

    @Column(name = "TSED_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "TSED_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSED_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "TSED_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TSED_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public TimeSheetEntity getTimeSheetEntity() {
        return timeSheetEntity;
    }

    public void setTimeSheetEntity(TimeSheetEntity timeSheetEntity) {
        this.timeSheetEntity = timeSheetEntity;
    }

    public List<TimeSheetEmpExpenseEntity> getTimeSheetEmpExpenseEntities() {
        return timeSheetEmpExpenseEntities;
    }

    public void setTimeSheetEmpExpenseEntities(List<TimeSheetEmpExpenseEntity> timeSheetEmpExpenseEntities) {
        this.timeSheetEmpExpenseEntities = timeSheetEmpExpenseEntities;
    }

    public List<TimeSheetEmpWorkDtlEntity> getTimeSheetEmpWorkDtlEntities() {
        return timeSheetEmpWorkDtlEntities;
    }

    public void setTimeSheetEmpWorkDtlEntities(List<TimeSheetEmpWorkDtlEntity> timeSheetEmpWorkDtlEntities) {
        this.timeSheetEmpWorkDtlEntities = timeSheetEmpWorkDtlEntities;
    }

    public List<TimeSheetEmpTaskEntity> getTimeSheetEmpTaskEntities() {
        return timeSheetEmpTaskEntities;
    }

    public void setTimeSheetEmpTaskEntities(List<TimeSheetEmpTaskEntity> timeSheetEmpTaskEntities) {
        this.timeSheetEmpTaskEntities = timeSheetEmpTaskEntities;
    }

}
