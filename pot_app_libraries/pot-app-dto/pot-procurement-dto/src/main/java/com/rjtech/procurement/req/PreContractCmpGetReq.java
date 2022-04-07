package com.rjtech.procurement.req;

import com.rjtech.common.dto.ProjectTO;

public class PreContractCmpGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private Long preContractId;

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

}
