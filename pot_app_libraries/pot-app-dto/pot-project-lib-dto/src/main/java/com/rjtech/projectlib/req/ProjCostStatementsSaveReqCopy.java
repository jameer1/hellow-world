package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostStmtDtlTOCopy;

import java.util.ArrayList;
import java.util.List;

public class ProjCostStatementsSaveReqCopy extends ProjectTO{

    private static final long serialVersionUID = 2L;
    private List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTOCopy>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostStmtDtlTOCopy> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }
}
