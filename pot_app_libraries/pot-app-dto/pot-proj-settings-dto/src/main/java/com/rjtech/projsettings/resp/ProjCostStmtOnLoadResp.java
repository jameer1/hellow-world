package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;

public class ProjCostStmtOnLoadResp extends AppResp {

    private static final long serialVersionUID = 6428930979858735658L;
    private ProjCostStmtDtlTO projCostStmtDtlTO = null;
    private List<ProjCostStmtDtlTO> projCostStmtDtlTOs = null;
    private List<LabelKeyTO> budgets = null;

    public ProjCostStmtOnLoadResp() {
        projCostStmtDtlTO = new ProjCostStmtDtlTO();
        projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        budgets = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public ProjCostStmtDtlTO getProjCostStmtDtlTO() {
        return projCostStmtDtlTO;
    }

    public void setProjCostStmtDtlTO(ProjCostStmtDtlTO projCostStmtDtlTO) {
        this.projCostStmtDtlTO = projCostStmtDtlTO;
    }

    public List<ProjCostStmtDtlTO> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTO> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

    public List<LabelKeyTO> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<LabelKeyTO> budgets) {
        this.budgets = budgets;
    }

}
