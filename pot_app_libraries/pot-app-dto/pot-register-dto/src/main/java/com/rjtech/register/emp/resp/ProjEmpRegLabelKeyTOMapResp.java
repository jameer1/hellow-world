package com.rjtech.register.emp.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class ProjEmpRegLabelKeyTOMapResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -9222635953451720346L;

    private Map<Long, LabelKeyTO> projEmpRegMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getProjEmpRegMap() {
        return projEmpRegMap;
    }

    public void setProjEmpRegMap(Map<Long, LabelKeyTO> projEmpRegMap) {
        this.projEmpRegMap = projEmpRegMap;
    }

}
