package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpDocsTO;

public class PreContractCmpDocResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 8089004729276806187L;
    private List<PreContractCmpDocsTO> preContractCmpDocsTOs = null;
    private Map<Long, LabelKeyTO> companyMap = null;

    public PreContractCmpDocResp() {
        preContractCmpDocsTOs = new ArrayList<PreContractCmpDocsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        companyMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractCmpDocsTO> getPreContractCmpDocsTOs() {
        return preContractCmpDocsTOs;
    }

    public void setPreContractCmpDocsTOs(List<PreContractCmpDocsTO> preContractCmpDocsTOs) {
        this.preContractCmpDocsTOs = preContractCmpDocsTOs;
    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

}
