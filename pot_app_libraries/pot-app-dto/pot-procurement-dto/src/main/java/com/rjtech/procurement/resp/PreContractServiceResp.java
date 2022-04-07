package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractServiceDtlTO;

public class PreContractServiceResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -9159056375797097278L;
    private List<PreContractServiceDtlTO> preContractServiceDtlTOs = null;
    private Long preContractId;
    private List<PreContractCmpTO> preContractCmpTOs = null;

    public PreContractServiceResp() {
        preContractServiceDtlTOs = new ArrayList<PreContractServiceDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<PreContractServiceDtlTO> getPreContractServiceDtlTOs() {
        return preContractServiceDtlTOs;
    }

    public void setPreContractServiceDtlTOs(List<PreContractServiceDtlTO> preContractServiceDtlTOs) {
        this.preContractServiceDtlTOs = preContractServiceDtlTOs;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public List<PreContractCmpTO> getPreContractCmpTOs() {
        return preContractCmpTOs;
    }

    public void setPreContractCmpTOs(List<PreContractCmpTO> preContractCmpTOs) {
        this.preContractCmpTOs = preContractCmpTOs;
    }

}
