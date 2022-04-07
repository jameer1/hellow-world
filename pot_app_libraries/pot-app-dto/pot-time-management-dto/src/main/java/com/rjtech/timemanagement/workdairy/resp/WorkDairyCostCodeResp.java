package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyCostCodeTO;

public class WorkDairyCostCodeResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyCostCodeTO> workDairyCostCodeTOs = new ArrayList<WorkDairyCostCodeTO>();

    public WorkDairyCostCodeResp() {
    }

    public List<WorkDairyCostCodeTO> getWorkDairyCostCodeTOs() {
        return workDairyCostCodeTOs;
    }

    public void setWorkDairyCostCodeTOs(List<WorkDairyCostCodeTO> workDairyCostCodeTOs) {
        this.workDairyCostCodeTOs = workDairyCostCodeTOs;
    }

}
