package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractPlantDtlTO;

public class PreContractPlantResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 377025936954273856L;

    private List<PreContractPlantDtlTO> preContractPlantDtlTOs = null;
    private List<PreContractCmpTO> preContractCmpTOs = null;
    private Long preContractId;

    public PreContractPlantResp() {
        preContractPlantDtlTOs = new ArrayList<PreContractPlantDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractPlantDtlTO> getPreContractPlantDtlTOs() {
        return preContractPlantDtlTOs;
    }

    public void setPreContractPlantDtlTOs(List<PreContractPlantDtlTO> preContractPlantDtlTOs) {
        this.preContractPlantDtlTOs = preContractPlantDtlTOs;
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
