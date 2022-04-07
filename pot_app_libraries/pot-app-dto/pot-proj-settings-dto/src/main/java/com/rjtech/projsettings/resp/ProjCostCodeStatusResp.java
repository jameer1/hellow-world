package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjCostCodeStatusTO;

public class ProjCostCodeStatusResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 770819735739926146L;
    private List<ProjCostCodeStatusTO> projCostCodeStatusTOs = null;

    ProjCostCodeStatusResp() {
        projCostCodeStatusTOs = new ArrayList<ProjCostCodeStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCostCodeStatusTO> getProjCostCodeStatusTOs() {
        return projCostCodeStatusTOs;
    }

    public void setProjCostCodeStatusTOs(List<ProjCostCodeStatusTO> projCostCodeStatusTOs) {
        this.projCostCodeStatusTOs = projCostCodeStatusTOs;
    }

}
