package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostBudgetTO;

public class ProjCostBudgetSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4596527514526141740L;
    private List<ProjCostBudgetTO> projCostBudgetTOs = new ArrayList<ProjCostBudgetTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostBudgetTO> getProjCostBudgetTOs() {
        return projCostBudgetTOs;
    }

    public void setProjCostBudgetTOs(List<ProjCostBudgetTO> projCostBudgetTOs) {
        this.projCostBudgetTOs = projCostBudgetTOs;
    }

}
