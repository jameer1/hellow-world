package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjCostItemResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjCostItemTO> projCostItemTOs = null;

    public ProjCostItemResp() {
        projCostItemTOs = new ArrayList<ProjCostItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCostItemTO> getProjCostItemTOs() {
        return projCostItemTOs;
    }

    public void setProjCostItemTOs(List<ProjCostItemTO> projCostItemTOs) {
        this.projCostItemTOs = projCostItemTOs;
    }

}
