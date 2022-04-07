package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;

public class ProjCostStatementsSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -5713190358442580141L;
    private List<ProjCostStmtDtlTO> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostStmtDtlTO> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTO> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

}
