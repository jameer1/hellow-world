package com.rjtech.timemanagement.attendance.req;

import com.rjtech.common.dto.ProjectTO;

public class EmpRegAttendanceSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3754852758992336728L;
    /**
     * 
     */
    private String attendenceMonth;
    private String attendenceYear;
    private Long attendenceId;
    private Long crewId;

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

}
