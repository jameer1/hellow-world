package com.rjtech.projsettings.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projsettings.dto.ProjTimeSheetWeekDtlTO;

public class ProjTimeSheetWeekResp extends AppResp {

    private static final long serialVersionUID = 6734601521856185372L;
    private ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO = null;

    public ProjTimeSheetWeekResp() {
        projTimeSheetWeekDtlTO = new ProjTimeSheetWeekDtlTO();
    }

    public ProjTimeSheetWeekDtlTO getProjTimeSheetWeekDtlTO() {
        return projTimeSheetWeekDtlTO;
    }

    public void setProjTimeSheetWeekDtlTO(ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO) {
        this.projTimeSheetWeekDtlTO = projTimeSheetWeekDtlTO;
    }

}
