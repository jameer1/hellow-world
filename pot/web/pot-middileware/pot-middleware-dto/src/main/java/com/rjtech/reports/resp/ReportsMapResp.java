package com.rjtech.reports.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ReportsMapResp extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -5362402028612363675L;

    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, ProjCostItemTO> costCodeMap = null;

    public ReportsMapResp() {
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        costCodeMap = new HashMap<Long, ProjCostItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, ProjCostItemTO> getCostCodeMap() {
        return costCodeMap;
    }

    public void setCostCodeMap(Map<Long, ProjCostItemTO> costCodeMap) {
        this.costCodeMap = costCodeMap;
    }

}
