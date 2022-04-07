package com.rjtech.procurement.req;

import java.io.Serializable;

public class GetTermsAndConditionsReq implements Serializable {

    private static final long serialVersionUID = 5042157593798746152L;

    private Long poId;

    private Long preContractId;

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

}
