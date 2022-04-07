package com.rjtech.projsettings.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class ProjCostActualQtyResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    Map<Long, CostActualHoursTO> projectBudgetMap = new HashMap<Long, CostActualHoursTO>();

    public Map<Long, CostActualHoursTO> getProjectBudgetMap() {
        return projectBudgetMap;
    }

    public void setProjectBudgetMap(Map<Long, CostActualHoursTO> projectBudgetMap) {
        this.projectBudgetMap = projectBudgetMap;
    }

}
