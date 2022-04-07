package com.rjtech.projectlib.resp;

import java.io.Serializable;
import java.util.Map;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjCostItemMapResp extends AppResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4627247625754427309L;

    private Map<Long, ProjCostItemTO> projCostItemMap = null;

    public Map<Long, ProjCostItemTO> getProjCostItemMap() {
        return projCostItemMap;
    }

    public void setProjCostItemMap(Map<Long, ProjCostItemTO> projCostItemMap) {
        this.projCostItemMap = projCostItemMap;
    }

}
