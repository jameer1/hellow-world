package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractMaterialDtlTO;

public class PreContractMaterialResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6615129750182669967L;
    private List<PreContractMaterialDtlTO> preContractMaterialDtlTOs = null;
    private List<PreContractCmpTO> preContractCmpTOs = null;
    private Long preContractId;

    public PreContractMaterialResp() {
        preContractMaterialDtlTOs = new ArrayList<PreContractMaterialDtlTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<PreContractMaterialDtlTO> getPreContractMaterialDtlTOs() {
        return preContractMaterialDtlTOs;
    }

    public void setPreContractMaterialDtlTOs(List<PreContractMaterialDtlTO> preContractMaterialDtlTOs) {
        this.preContractMaterialDtlTOs = preContractMaterialDtlTOs;
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
