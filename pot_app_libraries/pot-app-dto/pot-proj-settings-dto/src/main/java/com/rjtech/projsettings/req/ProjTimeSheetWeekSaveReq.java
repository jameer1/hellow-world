package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projsettings.dto.ProjTimeSheetWeekDtlTO;

public class ProjTimeSheetWeekSaveReq extends ProjectTO {

    private static final long serialVersionUID = -6253530593234730256L;

    private ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO = new ProjTimeSheetWeekDtlTO();

    public ProjTimeSheetWeekDtlTO getProjTimeSheetWeekDtlTO() {
        return projTimeSheetWeekDtlTO;
    }

    public void setProjTimeSheetWeekDtlTO(ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO) {
        this.projTimeSheetWeekDtlTO = projTimeSheetWeekDtlTO;
    }

}
