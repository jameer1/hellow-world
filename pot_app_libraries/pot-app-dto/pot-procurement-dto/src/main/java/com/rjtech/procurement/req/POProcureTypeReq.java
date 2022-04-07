package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class POProcureTypeReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String procureType;
    private Long purId;
    private String apprStatus;
    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();
    private String workDairyDate;

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
    }
}
