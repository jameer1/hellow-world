package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.procurement.dto.PreContractCmpDistributionDocTO;
import com.rjtech.procurement.dto.PreContractDistributionDocTO;

public class PrecontractDistributionDocSaveReq extends ProcurementGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractDistributionDocTO> preContractDistributionDocTOs = new ArrayList<PreContractDistributionDocTO>();
    private List<PreContractCmpDistributionDocTO> preContractCmpDistributionDocTOs = new ArrayList<PreContractCmpDistributionDocTO>();

    public List<PreContractDistributionDocTO> getPreContractDistributionDocTOs() {
        return preContractDistributionDocTOs;
    }

    public void setPreContractDistributionDocTOs(List<PreContractDistributionDocTO> preContractDistributionDocTOs) {
        this.preContractDistributionDocTOs = preContractDistributionDocTOs;
    }

    public List<PreContractCmpDistributionDocTO> getPreContractCmpDistributionDocTOs() {
        return preContractCmpDistributionDocTOs;
    }

    public void setPreContractCmpDistributionDocTOs(
            List<PreContractCmpDistributionDocTO> preContractCmpDistributionDocTOs) {
        this.preContractCmpDistributionDocTOs = preContractCmpDistributionDocTOs;
    }

}
