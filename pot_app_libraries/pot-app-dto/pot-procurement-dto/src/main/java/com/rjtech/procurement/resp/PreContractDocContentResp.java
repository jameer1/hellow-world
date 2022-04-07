package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractDocContentTO;

public class PreContractDocContentResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3646836867184614278L;
    private List<PreContractDocContentTO> preContractDocContentTOs = null;

    public PreContractDocContentResp() {
        preContractDocContentTOs = new ArrayList<PreContractDocContentTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractDocContentTO> getPreContractDocContentTOs() {
        return preContractDocContentTOs;
    }

    public void setPreContractDocContentTOs(List<PreContractDocContentTO> preContractDocContentTOs) {
        this.preContractDocContentTOs = preContractDocContentTOs;
    }

}
