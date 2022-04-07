package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceTO;

public class PlantAttendanceResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -9159056375797097278L;
    private List<PlantAttendanceTO> plantAttendenceTOs = null;
    private LabelKeyTO labelKeyTO = new LabelKeyTO();
    private Map<String, Boolean> plantDemobilizationDateMap = new HashMap<String, Boolean>();

    public PlantAttendanceResp() {
        plantAttendenceTOs = new ArrayList<PlantAttendanceTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<PlantAttendanceTO> getPlantAttendenceTOs() {
        return plantAttendenceTOs;
    }

    public void setPlantAttendenceTOs(List<PlantAttendanceTO> plantAttendenceTOs) {
        this.plantAttendenceTOs = plantAttendenceTOs;
    }

    public LabelKeyTO getLabelKeyTO() {
        return labelKeyTO;
    }

    public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
        this.labelKeyTO = labelKeyTO;
    }

    public Map<String, Boolean> getPlantDemobilizationDateMap() {
        return plantDemobilizationDateMap;
    }

    public void setPlantDemobilizationDateMap(Map<String, Boolean> plantDemobilizationDateMap) {
        this.plantDemobilizationDateMap = plantDemobilizationDateMap;
    }

}
