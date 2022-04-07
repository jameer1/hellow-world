package com.rjtech.timemanagement.attendance.dto;

import java.util.Date;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class EmpAttendanceDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long projLeaveId;
    private LabelKeyTO attendanceTypeTO = new LabelKeyTO();
    private Long empDtlId;
    private String attendenceDay;
    private boolean attendenceFlag;
    private Date attendenceDate;
    private Date checkStartDate;
    private Date checkEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendenceDay() {
        return attendenceDay;
    }

    public void setAttendenceDay(String attendenceDay) {
        this.attendenceDay = attendenceDay;
    }

    public Date getAttendenceDate() {
        return attendenceDate;
    }

    public void setAttendenceDate(Date attendenceDate) {
        this.attendenceDate = attendenceDate;
    }

    public boolean isAttendenceFlag() {
        return attendenceFlag;
    }

    public void setAttendenceFlag(boolean attendenceFlag) {
        this.attendenceFlag = attendenceFlag;
    }

    public Long getProjLeaveId() {
        return projLeaveId;
    }

    public Long getEmpDtlId() {
        return empDtlId;
    }

    public void setEmpDtlId(Long empDtlId) {
        this.empDtlId = empDtlId;
    }

    public void setProjLeaveId(Long projLeaveId) {
        this.projLeaveId = projLeaveId;
    }

    public LabelKeyTO getAttendanceTypeTO() {
        return attendanceTypeTO;
    }

    public void setAttendanceTypeTO(LabelKeyTO attendanceTypeTO) {
        this.attendanceTypeTO = attendanceTypeTO;
    }

    public Date getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(Date checkStartDate) {
        this.checkStartDate = checkStartDate;
    }

    public Date getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(Date checkEndDate) {
        this.checkEndDate = checkEndDate;
    }

}
