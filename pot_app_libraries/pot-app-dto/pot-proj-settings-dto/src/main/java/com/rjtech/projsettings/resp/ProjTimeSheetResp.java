package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjTimeSheetTO;
import com.rjtech.projsettings.dto.ProjTimeSheetWeekDtlTO;

public class ProjTimeSheetResp extends AppResp {

    private static final long serialVersionUID = 6061372650460533516L;

    private List<ProjTimeSheetTO> projTimeSheetTOs = null;

    private ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO = null;

    public ProjTimeSheetResp() {
        projTimeSheetTOs = new ArrayList<ProjTimeSheetTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projTimeSheetWeekDtlTO = new ProjTimeSheetWeekDtlTO();
    }

    public List<ProjTimeSheetTO> getProjTimeSheetTOs() {
        return projTimeSheetTOs;
    }

    public void setProjTimeSheetTOs(List<ProjTimeSheetTO> projTimeSheetTOs) {
        this.projTimeSheetTOs = projTimeSheetTOs;
    }

    public ProjTimeSheetWeekDtlTO getProjTimeSheetWeekDtlTO() {
        return projTimeSheetWeekDtlTO;
    }

    public void setProjTimeSheetWeekDtlTO(ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO) {
        this.projTimeSheetWeekDtlTO = projTimeSheetWeekDtlTO;
    }

}
