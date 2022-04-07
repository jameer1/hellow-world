package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjLeaveApprTO;

public class ProjLeaveApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3119883154685839546L;
    private List<ProjLeaveApprTO> projLeaveApprTOs = null;

    public ProjLeaveApprResp() {
        projLeaveApprTOs = new ArrayList<ProjLeaveApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjLeaveApprTO> getProjLeaveApprTOs() {
        return projLeaveApprTOs;
    }

    public void setProjLeaveApprTOs(List<ProjLeaveApprTO> projLeaveApprTOs) {
        this.projLeaveApprTOs = projLeaveApprTOs;
    }

}
