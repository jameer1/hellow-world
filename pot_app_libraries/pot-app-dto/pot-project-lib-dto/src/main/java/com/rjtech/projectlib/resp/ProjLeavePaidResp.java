package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjLeavePaidCatgTO;

public class ProjLeavePaidResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 3779954683101858740L;
    private List<ProjLeavePaidCatgTO> projLeavePaidCatgTOs = null;

    public ProjLeavePaidResp() {
        projLeavePaidCatgTOs = new ArrayList<ProjLeavePaidCatgTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjLeavePaidCatgTO> getProjLeavePaidCatgTOs() {
        return projLeavePaidCatgTOs;
    }

}
