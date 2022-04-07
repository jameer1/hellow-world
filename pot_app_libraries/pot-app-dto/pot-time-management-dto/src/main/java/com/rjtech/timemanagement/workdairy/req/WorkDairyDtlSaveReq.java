package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyProgressDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyDtlSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs = new ArrayList<WorkDairyEmpDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs = new ArrayList<WorkDairyPlantDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs = new ArrayList<WorkDairyMaterialDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs = new ArrayList<WorkDairyProgressDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private WorkDairyTO workDairyTO = new WorkDairyTO();

    public List<WorkDairyEmpDtlTO> getWorkDairyEmpDtlTOs() {
        return workDairyEmpDtlTOs;
    }

    public void setWorkDairyEmpDtlTOs(List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs) {
        this.workDairyEmpDtlTOs = workDairyEmpDtlTOs;
    }

    public List<WorkDairyPlantDtlTO> getWorkDairyPlantDtlTOs() {
        return workDairyPlantDtlTOs;
    }

    public void setWorkDairyPlantDtlTOs(List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs) {
        this.workDairyPlantDtlTOs = workDairyPlantDtlTOs;
    }

    public List<WorkDairyMaterialDtlTO> getWorkDairyMaterialDtlTOs() {
        return workDairyMaterialDtlTOs;
    }

    public void setWorkDairyMaterialDtlTOs(List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs) {
        this.workDairyMaterialDtlTOs = workDairyMaterialDtlTOs;
    }

    public List<WorkDairyProgressDtlTO> getWorkDairyProgressDtlTOs() {
        return workDairyProgressDtlTOs;
    }

    public void setWorkDairyProgressDtlTOs(List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs) {
        this.workDairyProgressDtlTOs = workDairyProgressDtlTOs;
    }

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

}
