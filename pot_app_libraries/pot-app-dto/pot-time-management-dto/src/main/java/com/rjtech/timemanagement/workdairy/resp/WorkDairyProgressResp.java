package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyProgressDtlTO;

public class WorkDairyProgressResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs = null;

    public WorkDairyProgressResp() {
        workDairyProgressDtlTOs = new ArrayList<WorkDairyProgressDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<WorkDairyProgressDtlTO> getWorkDairyProgressDtlTOs() {
        return workDairyProgressDtlTOs;
    }

    public void setWorkDairyProgressDtlTOs(List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs) {
        this.workDairyProgressDtlTOs = workDairyProgressDtlTOs;
    }

}
