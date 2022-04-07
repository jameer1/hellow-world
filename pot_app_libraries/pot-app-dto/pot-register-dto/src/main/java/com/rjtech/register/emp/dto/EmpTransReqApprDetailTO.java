package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpTransReqApprDetailTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empTransId;
    private Long empRegId;
    private String expectedTransDate;
    private String transDate;
    private String comments;
    private String apprStatus;
    private String empFirstName;
    private String empLastName;
    private String empCode;
    private String empClassType;
    private String empCmpName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpTransId() {
        return empTransId;
    }

    public void setEmpTransId(Long empTransId) {
        this.empTransId = empTransId;
    }

    public String getExpectedTransDate() {
        return expectedTransDate;
    }

    public void setExpectedTransDate(String expectedTransDate) {
        this.expectedTransDate = expectedTransDate;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
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

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpClassType() {
        return empClassType;
    }

    public void setEmpClassType(String empClassType) {
        this.empClassType = empClassType;
    }

    public String getEmpCmpName() {
        return empCmpName;
    }

    public void setEmpCmpName(String empCmpName) {
        this.empCmpName = empCmpName;
    }

}
