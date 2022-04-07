package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;

public class ProjCostStaementsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 5070291468280350417L;
    private List<ProjCostStmtDtlTO> projCostStmtDtlTOs = null;

    public ProjCostStaementsResp() {
        projCostStmtDtlTOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCostStmtDtlTO> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTO> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

}
