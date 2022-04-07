package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceMstrTO;

public class PlantAttendanceOnloadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -9159056375797097278L;
    private PlantAttendanceMstrTO plantAttendanceMstrTO = new PlantAttendanceMstrTO();
    private List<String> attendenceDays = new ArrayList<String>();
    private Map<String, Boolean> attendenceDayMap = new HashMap<String, Boolean>();
    private Map<Long, LabelKeyTO> plantAttendanceMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> plantDetailsMap = new HashMap<Long, LabelKeyTO>();

    public PlantAttendanceMstrTO getPlantAttendanceMstrTO() {
        return plantAttendanceMstrTO;
    }

    public void setPlantAttendanceMstrTO(PlantAttendanceMstrTO plantAttendanceMstrTO) {
        this.plantAttendanceMstrTO = plantAttendanceMstrTO;
    }

    public List<String> getAttendenceDays() {
        return attendenceDays;
    }

    public void setAttendenceDays(List<String> attendenceDays) {
        this.attendenceDays = attendenceDays;
    }

    public Map<String, Boolean> getAttendenceDayMap() {
        return attendenceDayMap;
    }

    public void setAttendenceDayMap(Map<String, Boolean> attendenceDayMap) {
        this.attendenceDayMap = attendenceDayMap;
    }

    public Map<Long, LabelKeyTO> getPlantAttendanceMap() {
        return plantAttendanceMap;
    }

    public void setPlantAttendanceMap(Map<Long, LabelKeyTO> plantAttendanceMap) {
        this.plantAttendanceMap = plantAttendanceMap;
    }

    public Map<Long, LabelKeyTO> getPlantDetailsMap() {
        return plantDetailsMap;
    }

    public void setPlantDetailsMap(Map<Long, LabelKeyTO> plantDetailsMap) {
        this.plantDetailsMap = plantDetailsMap;
    }

}
