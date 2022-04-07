package com.rjtech.projectlib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjMultiCostItemMapResp {

    Map<Long, ProjCostItemTO> multiProjCostItemMap = new HashMap<Long, ProjCostItemTO>();

    public Map<Long, ProjCostItemTO> getMultiProjCostItemMap() {
        return multiProjCostItemMap;
    }

    public void setMultiProjCostItemMap(Map<Long, ProjCostItemTO> multiProjCostItemMap) {
        this.multiProjCostItemMap = multiProjCostItemMap;
    }

}