package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyCostCodeTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyEmpDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyPlantDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyProgressDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyDetailResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyEmpDtlTO> workDairyEmpDtlTOs = null;
    private List<WorkDairyPlantDtlTO> workDairyPlantDtlTOs = null;
    private List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs = null;
    private List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs = null;
    private List<WorkDairyCostCodeTO> workDairyCostCodeTOs = null;
    private WorkDairyTO workDairyTO = null;
    private MaterialProjResp materialProjResp = null;
    private boolean timeFlag = false;

    public WorkDairyDetailResp() {
        workDairyTO = new WorkDairyTO();
        materialProjResp = new MaterialProjResp();
        workDairyEmpDtlTOs = new ArrayList<WorkDairyEmpDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        workDairyPlantDtlTOs = new ArrayList<WorkDairyPlantDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        workDairyMaterialDtlTOs = new ArrayList<WorkDairyMaterialDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        workDairyProgressDtlTOs = new ArrayList<WorkDairyProgressDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        workDairyCostCodeTOs = new ArrayList<WorkDairyCostCodeTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

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

    public List<WorkDairyCostCodeTO> getWorkDairyCostCodeTOs() {
        return workDairyCostCodeTOs;
    }

    public void setWorkDairyCostCodeTOs(List<WorkDairyCostCodeTO> workDairyCostCodeTOs) {
        this.workDairyCostCodeTOs = workDairyCostCodeTOs;
    }

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

    public MaterialProjResp getMaterialProjResp() {
        return materialProjResp;
    }

    public void setMaterialProjResp(MaterialProjResp materialProjResp) {
        this.materialProjResp = materialProjResp;
    }

    public boolean isTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(boolean timeFlag) {
        this.timeFlag = timeFlag;
    }
}
