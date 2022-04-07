package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractDocsTO;

public class PreContractDocResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3177617404253956817L;
    private List<PreContractDocsTO> preContractDocsTOs = null;

    public PreContractDocResp() {
        preContractDocsTOs = new ArrayList<PreContractDocsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractDocsTO> getPreContractDocsTOs() {
        return preContractDocsTOs;
    }

    public void setPreContractDocsTOs(List<PreContractDocsTO> preContractDocsTOs) {
        this.preContractDocsTOs = preContractDocsTOs;
    }

}
