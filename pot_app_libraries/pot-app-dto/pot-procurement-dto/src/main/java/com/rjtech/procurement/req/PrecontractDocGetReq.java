package com.rjtech.procurement.req;

import com.rjtech.common.dto.ProjectTO;

public class PrecontractDocGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3754852758992336728L;
    /**
     * 
     */

    private Long distributionDocId = null;
    private boolean version;
    private Long preContractCmpId;

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public Long getDistributionDocId() {
        return distributionDocId;
    }

    public void setDistributionDocId(Long distributionDocId) {
        this.distributionDocId = distributionDocId;
    }

}
