package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjStatusDatesTO;

public class ProjStatusDatesSaveReq extends ProjectTO {

    private static final long serialVersionUID = 6849884593061737887L;

    List<ProjStatusDatesTO> projStatusDatesTOs = new ArrayList<ProjStatusDatesTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjStatusDatesTO> getProjStatusDatesTOs() {
        return projStatusDatesTOs;
    }

    public void setProjStatusDatesTOs(List<ProjStatusDatesTO> projStatusDatesTOs) {
        this.projStatusDatesTOs = projStatusDatesTOs;
    }

}
