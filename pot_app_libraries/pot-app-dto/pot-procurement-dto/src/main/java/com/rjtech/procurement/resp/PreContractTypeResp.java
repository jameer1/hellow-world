package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.procurement.dto.PreContractTypeTO;

public class PreContractTypeResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 8967527476287892982L;

    private List<PreContractTypeTO> preContractTypeTOs = null;

    public PreContractTypeResp() {
        preContractTypeTOs = new ArrayList<PreContractTypeTO>();
    }

    public List<PreContractTypeTO> getPreContractTypeTOs() {
        return preContractTypeTOs;
    }

    public void setPreContractTypeTOs(List<PreContractTypeTO> preContractTypeTOs) {
        this.preContractTypeTOs = preContractTypeTOs;
    }

}
