package com.rjtech.register.emp.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.centrallib.dto.LeaveTypeTO;
import com.rjtech.common.dto.ProjectTO;

public class EmpLeaveAttendanceTO extends ProjectTO {

    private static final long serialVersionUID = 5062424744624375145L;
    private Long id;
    private Long empRegId;
    private String leaveType;
    private BigDecimal totalLeaves;
    private BigDecimal usedLeaves;
    private Date startDate;
    private Date endDate;

    public EmpLeaveAttendanceTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
