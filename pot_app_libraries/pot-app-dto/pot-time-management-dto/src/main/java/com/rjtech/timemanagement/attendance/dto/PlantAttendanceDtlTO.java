package com.rjtech.timemanagement.attendance.dto;

import java.util.Date;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class PlantAttendanceDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long plantDtlId;
    private Long crewId;
    private Long plantId;
    private LabelKeyTO attendanceTypeTO = new LabelKeyTO();
    private Date attendanceDate;
    private String attendenceDay;
    private boolean attendenceFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getPlantDtlId() {
        return plantDtlId;
    }

    public void setPlantDtlId(Long plantDtlId) {
        this.plantDtlId = plantDtlId;
    }

    public LabelKeyTO getAttendanceTypeTO() {
        return attendanceTypeTO;
    }

    public void setAttendanceTypeTO(LabelKeyTO attendanceTypeTO) {
        this.attendanceTypeTO = attendanceTypeTO;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getAttendenceDay() {
        return attendenceDay;
    }

    public void setAttendenceDay(String attendenceDay) {
        this.attendenceDay = attendenceDay;
    }

    public boolean isAttendenceFlag() {
        return attendenceFlag;
    }

    public void setAttendenceFlag(boolean attendenceFlag) {
        this.attendenceFlag = attendenceFlag;
    }

}