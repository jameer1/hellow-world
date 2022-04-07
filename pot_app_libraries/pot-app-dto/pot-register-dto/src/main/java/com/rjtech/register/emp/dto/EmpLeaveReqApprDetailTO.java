package com.rjtech.register.emp.dto;

import java.io.Serializable;

public class EmpLeaveReqApprDetailTO implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;
    private Long id;
    private Long empLeaveReqApprId;
    private String leaveCode;
    private String leaveType;
    private String fromDate;
    private String toDate;
    private Integer totalDays;
    private Integer status;
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpLeaveReqApprId() {
        return empLeaveReqApprId;
    }

    public void setEmpLeaveReqApprId(Long empLeaveReqApprId) {
        this.empLeaveReqApprId = empLeaveReqApprId;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
