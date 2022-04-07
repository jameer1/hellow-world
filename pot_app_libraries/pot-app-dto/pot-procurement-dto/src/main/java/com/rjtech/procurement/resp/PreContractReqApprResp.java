package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractReqApprTO;

public class PreContractReqApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3719597858807086400L;
    private List<PreContractReqApprTO> preContractReqApprTOs = new ArrayList<PreContractReqApprTO>();
    private Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();

    public PreContractReqApprResp() {
        preContractReqApprTOs = new ArrayList<PreContractReqApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractReqApprTO> getPreContractReqApprTOs() {
        return preContractReqApprTOs;
    }

    public void setPreContractReqApprTOs(List<PreContractReqApprTO> preContractReqApprTOs) {
        this.preContractReqApprTOs = preContractReqApprTOs;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

}
