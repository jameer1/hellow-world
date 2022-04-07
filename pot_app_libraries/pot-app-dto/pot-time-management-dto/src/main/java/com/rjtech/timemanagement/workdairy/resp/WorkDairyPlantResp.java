package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantDtlTO;

public class WorkDairyPlantResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs = null;

    public WorkDairyPlantResp() {
        workDairyPlantDtlTOs = new ArrayList<WorkDairyPlantDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<WorkDairyPlantDtlTO> getWorkDairyPlantDtlTOs() {
        return workDairyPlantDtlTOs;
    }

    public void setWorkDairyPlantDtlTOs(List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs) {
        this.workDairyPlantDtlTOs = workDairyPlantDtlTOs;
    }

}
