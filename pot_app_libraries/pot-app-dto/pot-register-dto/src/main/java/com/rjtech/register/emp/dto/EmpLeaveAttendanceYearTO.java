package com.rjtech.register.emp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class EmpLeaveAttendanceYearTO extends ProjectTO {

    private static final long serialVersionUID = 5062424744624375145L;
    private Long id;
    private Long empRegId;
    private List<EmpLeaveAttendanceDtlTO> empLeaveAccuredTOs = new ArrayList<EmpLeaveAttendanceDtlTO>();
    private Map<String, LabelKeyTO> empLeaveAttendanceMap = new HashMap<>();
    private BigDecimal totalLeaves;
    private BigDecimal usedLeaves;
    private String year;
    private String endDate;

    public EmpLeaveAttendanceYearTO() {

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

    public List<EmpLeaveAttendanceDtlTO> getEmpLeaveAccuredTOs() {
        return empLeaveAccuredTOs;
    }

    public void setEmpLeaveAccuredTOs(List<EmpLeaveAttendanceDtlTO> empLeaveAccuredTOs) {
        this.empLeaveAccuredTOs = empLeaveAccuredTOs;
    }

    public Map<String, LabelKeyTO> getEmpLeaveAttendanceMap() {
        return empLeaveAttendanceMap;
    }

    public void setEmpLeaveAttendanceMap(Map<String, LabelKeyTO> empLeaveAttendanceMap) {
        this.empLeaveAttendanceMap = empLeaveAttendanceMap;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
