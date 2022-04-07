package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjScheduledSummaryTO;

public class ProjScheduledSummaryResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -4690010083402728979L;

    private List<ProjScheduledSummaryTO> projScheduledSummaryTOs = null;

    public ProjScheduledSummaryResp() {
        projScheduledSummaryTOs = new ArrayList<ProjScheduledSummaryTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjScheduledSummaryTO> getProjScheduledSummaryTOs() {
        return projScheduledSummaryTOs;
    }

    public void setProjScheduledSummaryTOs(List<ProjScheduledSummaryTO> projScheduledSummaryTOs) {
        this.projScheduledSummaryTOs = projScheduledSummaryTOs;
    }

}
