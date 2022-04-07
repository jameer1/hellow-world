package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjStatusTO extends ProjectTO {

    private static final long serialVersionUID = -8812439850183967154L;
    private List<ProjCostBudgetTO> projCostBudgetTOs = new ArrayList<ProjCostBudgetTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostBudgetTO> getProjCostBudgetTOs() {
        return projCostBudgetTOs;
    }

    public void setProjCostBudgetTOs(List<ProjCostBudgetTO> projCostBudgetTOs) {
        this.projCostBudgetTOs = projCostBudgetTOs;
    }

}
