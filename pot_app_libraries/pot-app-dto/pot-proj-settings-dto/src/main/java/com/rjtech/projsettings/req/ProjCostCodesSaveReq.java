package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjCostCodesSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -5713190358442580141L;
    private List<ProjCostItemTO> projCostItemTOs = new ArrayList<ProjCostItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCostItemTO> getProjCostItemTOs() {
        return projCostItemTOs;
    }

    public void setProjCostItemTOs(List<ProjCostItemTO> projCostItemTOs) {
        this.projCostItemTOs = projCostItemTOs;
    }

}
