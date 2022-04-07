package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpDistributionDocTO;

public class PreContractCmpDistributionDocResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3177617404253956817L;
    private List<PreContractCmpDistributionDocTO> preContractCmpDistributionDocTOs = null;

    public PreContractCmpDistributionDocResp() {
        preContractCmpDistributionDocTOs = new ArrayList<PreContractCmpDistributionDocTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractCmpDistributionDocTO> getPreContractCmpDistributionDocTOs() {
        return preContractCmpDistributionDocTOs;
    }

    public void setPreContractCmpDistributionDocTOs(
            List<PreContractCmpDistributionDocTO> preContractCmpDistributionDocTOs) {
        this.preContractCmpDistributionDocTOs = preContractCmpDistributionDocTOs;
    }

}
