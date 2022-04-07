package com.rjtech.projectlib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.TotalActualTO;

public class SOWTotalActualQuantitiesResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 4284281857854236926L;
    Map<Long, TotalActualTO> actualRevisedMap = new HashMap<Long, TotalActualTO>();

    public Map<Long, TotalActualTO> getActualRevisedMap() {
        return actualRevisedMap;
    }

    public void setActualRevisedMap(Map<Long, TotalActualTO> actualRevisedMap) {
        this.actualRevisedMap = actualRevisedMap;
    }

}
