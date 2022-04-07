package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyMaterialDtlTO;

public class WorkDairyMaterialResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs = null;
    private MaterialProjResp materialProjResp = null;

    public WorkDairyMaterialResp() {
        materialProjResp = new MaterialProjResp();
        workDairyMaterialDtlTOs = new ArrayList<WorkDairyMaterialDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<WorkDairyMaterialDtlTO> getWorkDairyMaterialDtlTOs() {
        return workDairyMaterialDtlTOs;
    }

    public void setWorkDairyMaterialDtlTOs(List<WorkDairyMaterialDtlTO> workDairyMaterialDtlTOs) {
        this.workDairyMaterialDtlTOs = workDairyMaterialDtlTOs;
    }

    public MaterialProjResp getMaterialProjResp() {
        return materialProjResp;
    }

    public void setMaterialProjResp(MaterialProjResp materialProjResp) {
        this.materialProjResp = materialProjResp;
    }

}
