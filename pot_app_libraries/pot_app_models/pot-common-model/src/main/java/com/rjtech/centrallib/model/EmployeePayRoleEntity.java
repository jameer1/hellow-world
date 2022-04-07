package com.rjtech.centrallib.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the emp_pay_role_cycle database table.
 * 
 */
@Entity
@Table(name = "emp_pay_role_cycle")
public class EmployeePayRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPRC_ID")
    private Long id;

    @Column(name = "EPRC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPRC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPRC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPRC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "EPRC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPRC_UPDATED_ON")
    private Date updatedOn;
    
    @Column(name = "EPRC_EMPLOYEETYPE")
    private String employeeType;

    @Column(name = "EPRC_EMPLOYEECATEGORY")
    private String employeeCategory;

    @Column(name = "EPRC_PAYROLLCYCLE")
    private String payRoleCycle;
    
    @Column(name = "EPRC_CYCLEPERIODSTARTFROM")
    private String cyclePeriodStartFrom;
    
    @Column(name = "EPRC_CYCLEDUEDATE")
    private String cycleDueDate;
    
    @Column(name = "EPRC_EMPPAYROLECYCLEDISPLAYID")
    private String empPayRoleCycleDisplayId;
    
    @ManyToOne
    @JoinColumn(name = "EPRC_FC_ID")
    private FinanceCenterEntity financeCenterEntity;

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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

    public FinanceCenterEntity getFinanceCenterEntity() {
        return financeCenterEntity;
    }

    public void setFinanceCenterEntity(FinanceCenterEntity financeCenterEntity) {
        this.financeCenterEntity = financeCenterEntity;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(String employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public String getPayRoleCycle() {
        return payRoleCycle;
    }

    public void setPayRoleCycle(String payRoleCycle) {
        this.payRoleCycle = payRoleCycle;
    }

    public String getCyclePeriodStartFrom() {
        return cyclePeriodStartFrom;
    }

    public void setCyclePeriodStartFrom(String cyclePeriodStartFrom) {
        this.cyclePeriodStartFrom = cyclePeriodStartFrom;
    }

    public String getCycleDueDate() {
        return cycleDueDate;
    }

    public void setCycleDueDate(String cycleDueDate) {
        this.cycleDueDate = cycleDueDate;
    }

    public String getEmpPayRoleCycleDisplayId() {
        return empPayRoleCycleDisplayId;
    }

    public void setEmpPayRoleCycleDisplayId(String empPayRoleCycleDisplayId) {
        this.empPayRoleCycleDisplayId = empPayRoleCycleDisplayId;
    }
    
 
}