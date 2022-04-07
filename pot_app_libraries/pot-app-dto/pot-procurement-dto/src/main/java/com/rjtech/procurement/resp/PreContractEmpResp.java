package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PreContractCmpTO;
import com.rjtech.procurement.dto.PreContractEmpDtlTO;

public class PreContractEmpResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractEmpDtlTO> preContractEmpDtlTOs = null;
    private List<PreContractCmpTO> preContractCmpTOs = null;

    public PreContractEmpResp() {
        preContractEmpDtlTOs = new ArrayList<PreContractEmpDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PreContractEmpDtlTO> getPreContractEmpDtlTOs() {
        return preContractEmpDtlTOs;
    }

    public void setPreContractEmpDtlTOs(List<PreContractEmpDtlTO> preContractEmpDtlTOs) {
        this.preContractEmpDtlTOs = preContractEmpDtlTOs;
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

    private Long preContractId;

}
