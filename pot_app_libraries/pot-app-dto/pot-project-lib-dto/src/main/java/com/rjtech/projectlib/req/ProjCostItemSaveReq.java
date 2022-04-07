package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjCostItemSaveReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjCostItemTO> projCostItemTOs = new ArrayList<ProjCostItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostItemTO> getProjCostItemTOs() {
        return projCostItemTOs;
    }

    public void setProjCostItemTOs(List<ProjCostItemTO> projCostItemTOs) {
        this.projCostItemTOs = projCostItemTOs;
    }

}
