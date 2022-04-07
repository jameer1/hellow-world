package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyEmpSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs = new ArrayList<WorkDairyEmpDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private WorkDairyTO workDairyTO = new WorkDairyTO();

    public List<WorkDairyEmpDtlTO> getWorkDairyEmpDtlTOs() {
        return workDairyEmpDtlTOs;
    }

    public void setWorkDairyEmpDtlTOs(List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs) {
        this.workDairyEmpDtlTOs = workDairyEmpDtlTOs;
    }

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

}
