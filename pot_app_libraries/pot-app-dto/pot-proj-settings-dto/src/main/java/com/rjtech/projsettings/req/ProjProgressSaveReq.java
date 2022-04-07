package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressTO;

public class ProjProgressSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2805175875281733609L;
    private List<ProjProgressTO> projProgressTOs = new ArrayList<ProjProgressTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjProgressTO> getProjProgressTOs() {
        return projProgressTOs;
    }

    public void setProjProgressTOs(List<ProjProgressTO> projProgressTOs) {
        this.projProgressTOs = projProgressTOs;
    }

}
