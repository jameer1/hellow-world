package com.rjtech.register.emp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmpLeaveAttendanceDtlTO implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;
    private Long id;
    private Long empLeaveYearId;
    private String leaveCode;
    private String leaveType;
    private BigDecimal totalLeaves;
    private BigDecimal usedLeaves;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpLeaveYearId() {
        return empLeaveYearId;
    }

    public void setEmpLeaveYearId(Long empLeaveYearId) {
        this.empLeaveYearId = empLeaveYearId;
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

    public BigDecimal getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(BigDecimal totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public BigDecimal getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(BigDecimal usedLeaves) {
        this.usedLeaves = usedLeaves;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
