package com.rjtech.register.emp.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.ProjectTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjEmpRegisterTO extends ProjectTO {

    private static final long serialVersionUID = -5886004618076289192L;

    private Long id;
    private Long empId;
    private String enrollmentDate;
    private String enrollmentLoc;
    private String contractDate;
    private String contractNumber;
    private String mobilaizationDate;
    private String deMobilaizationDate;
    private String expecteddeMobilaizationDate;
    private String latest;
    private String empStatus;
    private Long deploymentId;
    private String notes;
    private String workType;
    private Long empClassifyId;
    private String empClassifyName;
    private String taxFileNum;
    private String empContractName;
    private String empWorkUnionName;
    private String empCatgName;
    private String profitCentreId;
    private String financeCentreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEnrollmentLoc() {
        return enrollmentLoc;
    }

    public void setEnrollmentLoc(String enrollmentLoc) {
        this.enrollmentLoc = enrollmentLoc;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getMobilaizationDate() {
        return mobilaizationDate;
    }

    public void setMobilaizationDate(String mobilaizationDate) {
        this.mobilaizationDate = mobilaizationDate;
    }

    public String getDeMobilaizationDate() {
        return deMobilaizationDate;
    }

    public void setDeMobilaizationDate(String deMobilaizationDate) {
        this.deMobilaizationDate = deMobilaizationDate;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getExpecteddeMobilaizationDate() {
        return expecteddeMobilaizationDate;
    }

    public String getLatest() {
        return latest;
    }

    public void setExpecteddeMobilaizationDate(String expecteddeMobilaizationDate) {
        this.expecteddeMobilaizationDate = expecteddeMobilaizationDate;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getNotes() {
        return notes;
    }

    public String getWorkType() {
        return workType;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Long getEmpClassifyId() {
        return empClassifyId;
    }

    public void setEmpClassifyId(Long empClassifyId) {
        this.empClassifyId = empClassifyId;
    }

    public String getEmpClassifyName() {
        return empClassifyName;
    }

    public void setEmpClassifyName(String empClassifyName) {
        this.empClassifyName = empClassifyName;
    }

    public String getTaxFileNum() {
        return taxFileNum;
    }

    public void setTaxFileNum(String taxFileNum) {
        this.taxFileNum = taxFileNum;
    }

    public String getEmpContractName() {
        return empContractName;
    }

    public void setEmpContractName(String empContractName) {
        this.empContractName = empContractName;
    }

    public String getEmpWorkUnionName() {
        return empWorkUnionName;
    }

    public void setEmpWorkUnionName(String empWorkUnionName) {
        this.empWorkUnionName = empWorkUnionName;
    }

    public String getEmpCatgName() {
        return empCatgName;
    }

    public void setEmpCatgName(String empCatgName) {
        this.empCatgName = empCatgName;
    }
    
    public String getProfitCentreId() {
        return profitCentreId;
    }

    public void setProfitCentreId(String profitCentreId) {
        this.profitCentreId = profitCentreId;
    }

    public String getFinanceCentreId() {
        return financeCentreId;
    }

    public void setFinanceCentreId(String financeCentreId) {
        this.financeCentreId = financeCentreId;
    }
    

}
