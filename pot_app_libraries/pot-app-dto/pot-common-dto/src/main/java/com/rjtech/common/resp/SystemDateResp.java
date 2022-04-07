package com.rjtech.common.resp;

import java.util.Date;

import com.rjtech.common.dto.LabelKeyTO;

public class SystemDateResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private LabelKeyTO labelKeyTO = new LabelKeyTO();
    private Date currentDate = null;

    public LabelKeyTO getLabelKeyTO() {
        return labelKeyTO;
    }

    public void setLabelKeyTO(LabelKeyTO labelKeyTO) {
        this.labelKeyTO = labelKeyTO;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

}