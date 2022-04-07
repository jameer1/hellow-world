package com.rjtech.timemanagement.workdairy.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyEmpDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long workDairyId;
    private Long empRegId;
    private String apprStatus;
    private String apprComments;
    private String classType;
    private String cmpCatgName;
    private String code;
    private String firstName;
    private String gender;
    private String lastName;
    private String empCategory;
    private String procureCatg;
    private String unitOfMeasure;
    private Integer status;
    private Date mobilizationDate;
    private Long empClassId;

    private List<WorkDairyEmpWageTO> workDairyEmpWageTOs = new ArrayList<WorkDairyEmpWageTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public List<WorkDairyEmpWageTO> getWorkDairyEmpWageTOs() {
        return workDairyEmpWageTOs;
    }

    public void setWorkDairyEmpWageTOs(List<WorkDairyEmpWageTO> workDairyEmpWageTOs) {
        this.workDairyEmpWageTOs = workDairyEmpWageTOs;
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

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCmpCatgName() {
        return cmpCatgName;
    }

    public void setCmpCatgName(String cmpCatgName) {
        this.cmpCatgName = cmpCatgName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmpCategory() {
        return empCategory;
    }

    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    public Date getMobilizationDate() {
        return mobilizationDate;
    }

    public void setMobilizationDate(Date mobilizationDate) {
        this.mobilizationDate = mobilizationDate;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

}