package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDiaryResp extends AppResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<WorkDairyTO> workDairyTOs = null;

    public WorkDiaryResp() {
    	workDairyTOs = new ArrayList<WorkDairyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<WorkDairyTO> getWorkDairyTOs() {
        return workDairyTOs;
    }

    public void setWorkDairyTOs(List<WorkDairyTO> workDairyTOs) {
        this.workDairyTOs = workDairyTOs;
    }

}
