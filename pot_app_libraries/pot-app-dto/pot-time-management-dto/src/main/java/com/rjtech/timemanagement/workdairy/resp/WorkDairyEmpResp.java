package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpDtlTO;

public class WorkDairyEmpResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs = null;

    public WorkDairyEmpResp() {
        workDairyEmpDtlTOs = new ArrayList<WorkDairyEmpDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<WorkDairyEmpDtlTO> getWorkDairyEmpDtlTOs() {
        return workDairyEmpDtlTOs;
    }

    public void setWorkDairyEmpDtlTOs(List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs) {
        this.workDairyEmpDtlTOs = workDairyEmpDtlTOs;
    }

}
