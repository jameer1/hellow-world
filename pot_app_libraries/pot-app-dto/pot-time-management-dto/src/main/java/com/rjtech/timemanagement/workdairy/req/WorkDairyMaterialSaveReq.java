package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyMaterialSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs = new ArrayList<WorkDairyMaterialDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private WorkDairyTO workDairyTO = new WorkDairyTO();

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

    public List<WorkDairyMaterialDtlTO> getWorkDairyMaterialDtlTOs() {
        return workDairyMaterialDtlTOs;
    }

    public void setWorkDairyMaterialDtlTOs(List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs) {
        this.workDairyMaterialDtlTOs = workDairyMaterialDtlTOs;
    }

}
