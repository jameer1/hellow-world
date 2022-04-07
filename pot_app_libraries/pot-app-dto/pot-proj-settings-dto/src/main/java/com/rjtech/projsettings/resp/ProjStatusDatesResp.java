package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjStatusDatesTO;

public class ProjStatusDatesResp extends AppResp {

    private static final long serialVersionUID = 884177998682385630L;

    List<ProjStatusDatesTO> projStatusDatesTOs = new ArrayList<ProjStatusDatesTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjStatusDatesTO> getProjStatusDatesTOs() {
        return projStatusDatesTOs;
    }

    public void setProjStatusDatesTOs(List<ProjStatusDatesTO> projStatusDatesTOs) {
        this.projStatusDatesTOs = projStatusDatesTOs;
    }

}
