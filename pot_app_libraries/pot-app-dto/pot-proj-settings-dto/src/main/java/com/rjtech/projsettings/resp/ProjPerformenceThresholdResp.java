package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPerformenceThresholdTO;

public class ProjPerformenceThresholdResp extends AppResp {

    private static final long serialVersionUID = 1L;
    private List<ProjPerformenceThresholdTO> projPerformenceThresholdTOs = new ArrayList<ProjPerformenceThresholdTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPerformenceThresholdTO> getProjPerformenceThresholdTOs() {
        return projPerformenceThresholdTOs;
    }

    public void setProjPerformenceThresholdTOs(List<ProjPerformenceThresholdTO> projPerformenceThresholdTOs) {
        this.projPerformenceThresholdTOs = projPerformenceThresholdTOs;
    }
}
