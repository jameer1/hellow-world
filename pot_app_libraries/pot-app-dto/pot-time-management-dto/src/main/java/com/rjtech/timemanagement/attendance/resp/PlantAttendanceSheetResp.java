package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceMstrTO;

public class PlantAttendanceSheetResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PlantAttendanceMstrTO> plantAttendanceMstrTOs = null;

    public PlantAttendanceSheetResp() {
        plantAttendanceMstrTOs = new ArrayList<PlantAttendanceMstrTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PlantAttendanceMstrTO> getPlantAttendanceMstrTOs() {
        return plantAttendanceMstrTOs;
    }

    public void setPlantAttendanceMstrTOs(List<PlantAttendanceMstrTO> plantAttendanceMstrTOs) {
        this.plantAttendanceMstrTOs = plantAttendanceMstrTOs;
    }

}
