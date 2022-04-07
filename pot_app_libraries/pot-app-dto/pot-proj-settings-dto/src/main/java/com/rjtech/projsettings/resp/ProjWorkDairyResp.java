package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjWorkDairyMstrTO;

public class ProjWorkDairyResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs = null;

    public ProjWorkDairyResp() {
        projWorkDairyMstrTOs = new ArrayList<ProjWorkDairyMstrTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjWorkDairyMstrTO> getProjWorkDairyMstrTOs() {
        return projWorkDairyMstrTOs;
    }

    public void setProjWorkDairyMstrTOs(List<ProjWorkDairyMstrTO> projWorkDairyMstrTOs) {
        this.projWorkDairyMstrTOs = projWorkDairyMstrTOs;
    }

}
