package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjCostCodeStatusTO extends ProjectTO {

    private static final long serialVersionUID = -2992761129808703931L;
    private List<ProjCostStmtDtlTO> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostStmtDtlTO> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTO> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

}
