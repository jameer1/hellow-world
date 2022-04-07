package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPayRollCycleTO;

public class ProjPayRollCycleSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -9100933701444932349L;
    private List<ProjPayRollCycleTO> projPayRollCycleTOs = new ArrayList<ProjPayRollCycleTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPayRollCycleTO> getProjPayRollCycleTOs() {
        return projPayRollCycleTOs;
    }

    public void setProjPayRollCycleTOs(List<ProjPayRollCycleTO> projPayRollCycleTOs) {
        this.projPayRollCycleTOs = projPayRollCycleTOs;
    }

}
