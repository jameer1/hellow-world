package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjWorkDairyApprTO;

public class ProjWorkDairyApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -643526727935618346L;
    private List<ProjWorkDairyApprTO> projWorkDairyApprTOs = new ArrayList<ProjWorkDairyApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjWorkDairyApprTO> getProjWorkDairyApprTOs() {
        return projWorkDairyApprTOs;
    }

    public void setProjWorkDairyApprTOs(List<ProjWorkDairyApprTO> projWorkDairyApprTOs) {
        this.projWorkDairyApprTOs = projWorkDairyApprTOs;
    }

}
