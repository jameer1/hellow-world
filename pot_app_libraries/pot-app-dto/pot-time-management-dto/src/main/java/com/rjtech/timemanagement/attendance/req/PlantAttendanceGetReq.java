package com.rjtech.timemanagement.attendance.req;

import com.rjtech.common.dto.ProjectTO;

public class PlantAttendanceGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    private String attendenceYear;
    private String attendenceMonth;
    private Long attendenceId;
    private Long crewId;
    private Long fromCrewId;
    private Long fromAttendenceId;

    public String getAttendenceMonth() {
        return attendenceMonth;
    }

    public void setAttendenceMonth(String attendenceMonth) {
        this.attendenceMonth = attendenceMonth;
    }

    public String getAttendenceYear() {
        return attendenceYear;
    }

    public void setAttendenceYear(String attendenceYear) {
        this.attendenceYear = attendenceYear;
    }

    public Long getAttendenceId() {
        return attendenceId;
    }

    public void setAttendenceId(Long attendenceId) {
        this.attendenceId = attendenceId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getFromCrewId() {
        return fromCrewId;
    }

    public void setFromCrewId(Long fromCrewId) {
        this.fromCrewId = fromCrewId;
    }

    public Long getFromAttendenceId() {
        return fromAttendenceId;
    }

    public void setFromAttendenceId(Long fromAttendenceId) {
        this.fromAttendenceId = fromAttendenceId;
    }

}
