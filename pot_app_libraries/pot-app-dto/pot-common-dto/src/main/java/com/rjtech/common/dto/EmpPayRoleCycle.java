package com.rjtech.common.dto;

import java.io.Serializable;

public class EmpPayRoleCycle implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    
    private Long id;
    
    private String employeeType;

    private String employeeCategory;

    private String payRoleCycle;
    
    private String cyclePeriodStartFrom;
    
    private String cycleDueDate;
    
    private String empPayRoleCycleDisplayId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
