package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPerformenceThresholdTO;

public class ProjPerformenceThresholdSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    List<ProjPerformenceThresholdTO> projPerformenceThresholdTOs = new ArrayList<ProjPerformenceThresholdTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPerformenceThresholdTO> getProjPerformenceThresholdTOs() {
        return projPerformenceThresholdTOs;
    }

    public void setProjPerformenceThresholdTOs(List<ProjPerformenceThresholdTO> projPerformenceThresholdTOs) {
        this.projPerformenceThresholdTOs = projPerformenceThresholdTOs;
    }
}
