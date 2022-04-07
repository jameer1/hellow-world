package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjTimeSheetApprTO;

public class ProjTimeSheetApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5765804495809322193L;
    private List<ProjTimeSheetApprTO> projTimeSheetApprTOs = new ArrayList<ProjTimeSheetApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjTimeSheetApprTO> getProjTimeSheetApprTOs() {
        return projTimeSheetApprTOs;
    }

    public void setProjTimeSheetApprTOs(List<ProjTimeSheetApprTO> projTimeSheetApprTOs) {
        this.projTimeSheetApprTOs = projTimeSheetApprTOs;
    }

}
