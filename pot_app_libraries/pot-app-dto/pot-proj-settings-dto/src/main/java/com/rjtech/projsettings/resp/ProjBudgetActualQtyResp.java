package com.rjtech.projsettings.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class ProjBudgetActualQtyResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    Map<Long, LabelKeyTO> projectBudgetMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getProjectBudgetMap() {
        return projectBudgetMap;
    }

    public void setProjectBudgetMap(Map<Long, LabelKeyTO> projectBudgetMap) {
        this.projectBudgetMap = projectBudgetMap;
    }

}
