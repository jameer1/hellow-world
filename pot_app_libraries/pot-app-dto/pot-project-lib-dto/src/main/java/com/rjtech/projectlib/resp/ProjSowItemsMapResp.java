package com.rjtech.projectlib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class ProjSowItemsMapResp {

    private Map<Long, LabelKeyTO> sowItemMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getSowItemMap() {
        return sowItemMap;
    }

    public void setSowItemMap(Map<Long, LabelKeyTO> sowItemMap) {
        this.sowItemMap = sowItemMap;
    }
}
