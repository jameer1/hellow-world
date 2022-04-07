package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjSummaryTO;

public class ProjSummaryResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -2988713947582304167L;
    private List<ProjSummaryTO> projSummaryTOs = null;

    public ProjSummaryResp() {
        projSummaryTOs = new ArrayList<ProjSummaryTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSummaryTO> getProjSummaryTOs() {
        return projSummaryTOs;
    }

    public void setProjSummaryTOs(List<ProjSummaryTO> projSummaryTOs) {
        this.projSummaryTOs = projSummaryTOs;
    }

}
