package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjWorkDairyApprTO;

public class ProjWorkDairyApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -7517570618864506808L;
    private List<ProjWorkDairyApprTO> projWorkDairyApprTOs = null;

    public ProjWorkDairyApprResp() {
        projWorkDairyApprTOs = new ArrayList<ProjWorkDairyApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjWorkDairyApprTO> getProjWorkDairyApprTOs() {
        return projWorkDairyApprTOs;
    }

    public void setProjWorkDairyApprTOs(List<ProjWorkDairyApprTO> projWorkDairyApprTOs) {
        this.projWorkDairyApprTOs = projWorkDairyApprTOs;
    }

}
