package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PrecontractSowDtlTO;

public class PreContractSowResp extends AppResp {

    private static final long serialVersionUID = 1L;

    List<PrecontractSowDtlTO> precontractSowDtlTOs = new ArrayList<PrecontractSowDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PrecontractSowDtlTO> getPrecontractSowDtlTOs() {
        return precontractSowDtlTOs;
    }

    public void setPrecontractSowDtlTOs(List<PrecontractSowDtlTO> precontractSowDtlTOs) {
        this.precontractSowDtlTOs = precontractSowDtlTOs;
    }

}
