package com.rjtech.timemanagement.attendance.dto;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;

public class EmpAttendanceTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long attandanceId;
    private Long empId;
    private Long crewId;
    private String reason;
    private String empCode;
    private String empFirstName;
    private String empLastName;
    private String empClassType;
    private String empCmpCatgName;

    private Map<String, EmpAttendanceDtlTO> empAttendenceDtlMap = new HashMap<String, EmpAttendanceDtlTO>();

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttandanceId() {
        return attandanceId;
    }

    public void setAttandanceId(Long attandanceId) {
        this.attandanceId = attandanceId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Map<String, EmpAttendanceDtlTO> getEmpAttendenceDtlMap() {
        return empAttendenceDtlMap;
    }

    public void setEmpAttendenceDtlMap(Map<String, EmpAttendanceDtlTO> empAttendenceDtlMap) {
        this.empAttendenceDtlMap = empAttendenceDtlMap;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
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

    public String getEmpClassType() {
        return empClassType;
    }

    public void setEmpClassType(String empClassType) {
        this.empClassType = empClassType;
    }

    public String getEmpCmpCatgName() {
        return empCmpCatgName;
    }

    public void setEmpCmpCatgName(String empCmpCatgName) {
        this.empCmpCatgName = empCmpCatgName;
    }

}
