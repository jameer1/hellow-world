package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostStatementsSummaryTO;
import com.rjtech.projsettings.dto.ProjManPowerStatusTO;

public class ProjectStatusResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3490921151549466218L;

    private List<ProjManPowerStatusTO> projManPowerStatusTOs = null;
    private List<ProjCostStatementsSummaryTO> projCostStatementsSummaryTOs = null;

    public ProjectStatusResp() {
        projManPowerStatusTOs = new ArrayList<ProjManPowerStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projCostStatementsSummaryTOs = new ArrayList<ProjCostStatementsSummaryTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjManPowerStatusTO> getProjManPowerStatusTOs() {
        return projManPowerStatusTOs;
    }

    public void setProjManPowerStatusTOs(List<ProjManPowerStatusTO> projManPowerStatusTOs) {
        this.projManPowerStatusTOs = projManPowerStatusTOs;
    }

    public List<ProjCostStatementsSummaryTO> getProjCostStatementsSummaryTOs() {
        return projCostStatementsSummaryTOs;
    }

    public void setProjCostStatementsSummaryTOs(List<ProjCostStatementsSummaryTO> projCostStatementsSummaryTOs) {
        this.projCostStatementsSummaryTOs = projCostStatementsSummaryTOs;
    }

}
