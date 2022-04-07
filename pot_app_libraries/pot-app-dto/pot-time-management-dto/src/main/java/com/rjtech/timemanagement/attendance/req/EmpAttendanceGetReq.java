package com.rjtech.timemanagement.attendance.req;

import com.rjtech.common.dto.ProjectTO;

public class EmpAttendanceGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3754852758992336728L;
    /**
     * 
     */
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

    public Long getAttendenceId() {
        return attendenceId;
    }

    public void setAttendenceId(Long attendenceId) {
        this.attendenceId = attendenceId;
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

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

}
