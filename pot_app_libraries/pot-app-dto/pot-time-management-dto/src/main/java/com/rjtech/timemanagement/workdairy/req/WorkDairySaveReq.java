package com.rjtech.timemanagement.workdairy.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private WorkDairyTO workDairyTO = new WorkDairyTO();

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

}
