package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class ProjGenCurrencyResp extends AppResp {

    private static final long serialVersionUID = 3564887175730406177L;

    LabelKeyTO labelKeyTO = new LabelKeyTO();

    public LabelKeyTO getLabelKeyTO() {
        return labelKeyTO;
    }

    public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
        this.labelKeyTO = labelKeyTO;
    }
}
