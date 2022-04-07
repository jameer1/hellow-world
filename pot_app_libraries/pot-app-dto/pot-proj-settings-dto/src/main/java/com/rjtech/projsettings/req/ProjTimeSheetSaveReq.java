package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjTimeSheetTO;
import com.rjtech.projsettings.dto.ProjTimeSheetWeekDtlTO;

public class ProjTimeSheetSaveReq extends ProjectTO {

    private static final long serialVersionUID = 3262183530021437722L;

    private List<ProjTimeSheetTO> projTimeSheetTOs = new ArrayList<ProjTimeSheetTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private ProjTimeSheetWeekDtlTO projTimeSheetWeekDtlTO = new ProjTimeSheetWeekDtlTO();

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
