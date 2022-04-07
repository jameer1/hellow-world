package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimePeriodTO;

public class ProjProgressClaimePeroidSaveReq extends ProjectTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs = new ArrayList<ProjProgressClaimePeriodTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjProgressClaimePeriodTO> getProjProgressClaimePeriodTOs() {
        return projProgressClaimePeriodTOs;
    }

    public void setProjProgressClaimePeriodTOs(List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs) {
        this.projProgressClaimePeriodTOs = projProgressClaimePeriodTOs;
    }

}
