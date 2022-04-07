package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPayRollCycleTO;

public class ProjPayRollCycleResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<ProjPayRollCycleTO> projPayRollCycleTOs = new ArrayList<ProjPayRollCycleTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPayRollCycleTO> getProjPayRollCycleTOs() {
        return projPayRollCycleTOs;
    }

    public void setProjPayRollCycleTOs(List<ProjPayRollCycleTO> projPayRollCycleTOs) {
        this.projPayRollCycleTOs = projPayRollCycleTOs;
    }

}
