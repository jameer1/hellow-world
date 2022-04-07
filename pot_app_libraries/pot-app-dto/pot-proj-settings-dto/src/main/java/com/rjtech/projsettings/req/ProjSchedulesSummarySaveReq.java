package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjScheduledSummaryTO;

public class ProjSchedulesSummarySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3261395393642462894L;

    private List<ProjScheduledSummaryTO> projScheduledSummaryTOs = new ArrayList<ProjScheduledSummaryTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjScheduledSummaryTO> getProjScheduledSummaryTOs() {
        return projScheduledSummaryTOs;
    }

    public void setProjScheduledSummaryTOs(List<ProjScheduledSummaryTO> projScheduledSummaryTOs) {
        this.projScheduledSummaryTOs = projScheduledSummaryTOs;
    }

}
