package com.rjtech.projectlib.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjPMCMItemTO;

import java.util.ArrayList;
import java.util.List;

public class ProjPMCMItemSaveReq extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private List<ProjPMCMItemTO> projSORItemTOs = new ArrayList<ProjPMCMItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPMCMItemTO> getProjSORItemTOs() {
        return projSORItemTOs;
    }

    public void setProjSORItemTOs(List<ProjPMCMItemTO> projSORItemTOs) {
        this.projSORItemTOs = projSORItemTOs;
    }
}
