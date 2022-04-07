package com.rjtech.timemanagement.attendance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceTO;

public class PlantAttendanceSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PlantAttendanceTO> plantAttendenceTOs = new ArrayList<PlantAttendanceTO>();
    private Long attendenceId;
    private Long crewId;
    private String attendenceMonth;

    public List<PlantAttendanceTO> getPlantAttendenceTOs() {
        return plantAttendenceTOs;
    }

    public void setPlantAttendenceTOs(List<PlantAttendanceTO> plantAttendenceTOs) {
        this.plantAttendenceTOs = plantAttendenceTOs;
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

    public String getAttendenceMonth() {
        return attendenceMonth;
    }

    public void setAttendenceMonth(String attendenceMonth) {
        this.attendenceMonth = attendenceMonth;
    }

}
