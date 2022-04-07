package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.ProcurementCompanyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractDistributionDocTO;

public class PreContractDistributionDocResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3177617404253956817L;
    private List<PreContractDistributionDocTO> preContractDistributionDocTOs = null;
    private Map<Long, ProcurementCompanyTO> preContractCompanyMap = null;
    private List<PreContractCmpTO> preContractCmpTOs = null;

    public PreContractDistributionDocResp() {
        preContractDistributionDocTOs = new ArrayList<PreContractDistributionDocTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        preContractCmpTOs = new ArrayList<PreContractCmpTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        preContractCompanyMap = new HashMap<Long, ProcurementCompanyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractDistributionDocTO> getPreContractDistributionDocTOs() {
        return preContractDistributionDocTOs;
    }

    public void setPreContractDistributionDocTOs(List<PreContractDistributionDocTO> preContractDistributionDocTOs) {
        this.preContractDistributionDocTOs = preContractDistributionDocTOs;
    }

    public Map<Long, ProcurementCompanyTO> getPreContractCompanyMap() {
        return preContractCompanyMap;
    }

    public void setPreContractCompanyMap(Map<Long, ProcurementCompanyTO> preContractCompanyMap) {
        this.preContractCompanyMap = preContractCompanyMap;
    }

    public List<PreContractCmpTO> getPreContractCmpTOs() {
        return preContractCmpTOs;
    }

    public void setPreContractCmpTOs(List<PreContractCmpTO> preContractCmpTOs) {
        this.preContractCmpTOs = preContractCmpTOs;
    }

}
