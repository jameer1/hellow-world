package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.WorkFlowStatusTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class PreContractStatusResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkFlowStatusTO> workFlowStatusTOs = null;

    public PreContractStatusResp() {
        workFlowStatusTOs = new ArrayList<WorkFlowStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<WorkFlowStatusTO> getWorkFlowStatusTOs() {
        return workFlowStatusTOs;
    }

    public void setWorkFlowStatusTOs(List<WorkFlowStatusTO> workFlowStatusTOs) {
        this.workFlowStatusTOs = workFlowStatusTOs;
    }

}
