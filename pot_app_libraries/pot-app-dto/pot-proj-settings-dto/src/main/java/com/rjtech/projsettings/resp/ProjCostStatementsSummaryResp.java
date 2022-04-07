package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostStatementsSummaryTO;

public class ProjCostStatementsSummaryResp extends AppResp {

    private static final long serialVersionUID = 4094306947904872095L;
    List<ProjCostStatementsSummaryTO> projCostStatementsSummaryTOs = null;

    public ProjCostStatementsSummaryResp() {
        projCostStatementsSummaryTOs = new ArrayList<ProjCostStatementsSummaryTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCostStatementsSummaryTO> getProjCostStatementsSummaryTOs() {
        return projCostStatementsSummaryTOs;
    }

    public void setProjCostStatementsSummaryTOs(List<ProjCostStatementsSummaryTO> projCostStatementsSummaryTOs) {
        this.projCostStatementsSummaryTOs = projCostStatementsSummaryTOs;
    }

}
