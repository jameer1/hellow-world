package com.rjtech.timemanagement.workdairy.resp;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class WorkDairyProjSettingResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private LabelKeyTO labelKeyTO = new LabelKeyTO();

    public WorkDairyProjSettingResp() {

    }

    public LabelKeyTO getLabelKeyTO() {
        return labelKeyTO;
    }

    public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
        this.labelKeyTO = labelKeyTO;
    }

}
