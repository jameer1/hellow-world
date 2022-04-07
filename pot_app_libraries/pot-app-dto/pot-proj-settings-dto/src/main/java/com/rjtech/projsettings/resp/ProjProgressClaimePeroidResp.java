package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimePeriodTO;

public class ProjProgressClaimePeroidResp extends AppResp {

    private static final long serialVersionUID = 2883203683069717924L;
    private List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs = null;

    public ProjProgressClaimePeroidResp() {
        projProgressClaimePeriodTOs = new ArrayList<ProjProgressClaimePeriodTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjProgressClaimePeriodTO> getProjProgressClaimePeriodTOs() {
        return projProgressClaimePeriodTOs;
    }

    public void setProjProgressClaimePeriodTOs(List<ProjProgressClaimePeriodTO> projProgressClaimePeriodTOs) {
        this.projProgressClaimePeriodTOs = projProgressClaimePeriodTOs;
    }

}
