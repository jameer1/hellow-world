package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostBudgetTO;

public class ProjCostBudgetResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1985821550155203905L;
    private List<ProjCostBudgetTO> projCostBudgetTOs = null;

    public ProjCostBudgetResp() {
        projCostBudgetTOs = new ArrayList<ProjCostBudgetTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCostBudgetTO> getProjCostBudgetTOs() {
        return projCostBudgetTOs;
    }

    public void setProjCostBudgetTOs(List<ProjCostBudgetTO> projCostBudgetTOs) {
        this.projCostBudgetTOs = projCostBudgetTOs;
    }

}
