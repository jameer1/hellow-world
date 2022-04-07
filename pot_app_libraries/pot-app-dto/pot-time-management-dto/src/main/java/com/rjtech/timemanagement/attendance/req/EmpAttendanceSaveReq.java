package com.rjtech.timemanagement.attendance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceTO;

public class EmpAttendanceSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<EmpAttendanceTO> empAttendenceTOs = new ArrayList<EmpAttendanceTO>();
    private Long attendenceId;
    private Long crewId;
    private String attendenceMonth;

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

    public String getAttendenceMonth() {
        return attendenceMonth;
    }

    public void setAttendenceMonth(String attendenceMonth) {
        this.attendenceMonth = attendenceMonth;
    }

    public List<EmpAttendanceTO> getEmpAttendenceTOs() {
        return empAttendenceTOs;
    }

    public void setEmpAttendenceTOs(List<EmpAttendanceTO> empAttendenceTOs) {
        this.empAttendenceTOs = empAttendenceTOs;
    }

}
