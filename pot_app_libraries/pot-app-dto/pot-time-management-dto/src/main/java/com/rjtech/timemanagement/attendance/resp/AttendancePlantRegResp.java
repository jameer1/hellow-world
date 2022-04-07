package com.rjtech.timemanagement.attendance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class AttendancePlantRegResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<LabelKeyTO> plantRegLabelKeyTOs = null;

    public AttendancePlantRegResp() {
        plantRegLabelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<LabelKeyTO> getPlantRegLabelKeyTOs() {
        return plantRegLabelKeyTOs;
    }

    public void setPlantRegLabelKeyTOs(List<LabelKeyTO> plantRegLabelKeyTOs) {
        this.plantRegLabelKeyTOs = plantRegLabelKeyTOs;
    }

}
