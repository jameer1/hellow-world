package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CostCodeTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjCostItemOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ProjCostItemTO projCostItemTO = null;
    private List<CostCodeTO> costCodeTOs = null;
    private List<ProjCostItemTO> projCostItemTOs = null;

    public ProjCostItemOnLoadResp() {
        projCostItemTO = new ProjCostItemTO();
        costCodeTOs = new ArrayList<CostCodeTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projCostItemTOs = new ArrayList<ProjCostItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public ProjCostItemTO getProjCostItemTO() {
        return projCostItemTO;
    }

    public List<CostCodeTO> getCostCodeTOs() {
        return costCodeTOs;
    }

    public List<ProjCostItemTO> getProjCostItemTOs() {
        return projCostItemTOs;
    }

}
