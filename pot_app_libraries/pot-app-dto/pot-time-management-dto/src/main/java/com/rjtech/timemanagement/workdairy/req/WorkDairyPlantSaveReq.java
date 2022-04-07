package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyPlantSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs = new ArrayList<WorkDairyPlantDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private WorkDairyTO workDairyTO = new WorkDairyTO();

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

    public List<WorkDairyPlantDtlTO> getWorkDairyPlantDtlTOs() {
        return workDairyPlantDtlTOs;
    }

    public void setWorkDairyPlantDtlTOs(List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs) {
        this.workDairyPlantDtlTOs = workDairyPlantDtlTOs;
    }

}
