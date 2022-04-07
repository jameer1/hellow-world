package com.rjtech.projectlib.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostStmtDtlTOCopy;

import java.util.ArrayList;
import java.util.List;

public class ProjPMCPCostStatementsResp extends AppResp {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = null;

    public ProjPMCPCostStatementsResp() {
        projCostStmtDtlTOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCostStmtDtlTOCopy> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

}

