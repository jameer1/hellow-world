package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractReqApprTO;

public class PreContractReqResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3719597858807086400L;
    private List<PreContractReqApprTO> preContractReqApprTOs = new ArrayList<PreContractReqApprTO>();

    public PreContractReqResp() {
        preContractReqApprTOs = new ArrayList<PreContractReqApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractReqApprTO> getPreContractReqApprTOs() {
        return preContractReqApprTOs;
    }

    public void setPreContractReqApprTOs(List<PreContractReqApprTO> preContractReqApprTOs) {
        this.preContractReqApprTOs = preContractReqApprTOs;
    }

}
