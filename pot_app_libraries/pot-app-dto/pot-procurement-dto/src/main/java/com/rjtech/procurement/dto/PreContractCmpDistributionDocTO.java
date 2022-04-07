package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class PreContractCmpDistributionDocTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5559342478970854101L;

    private Long id;
    private Long preContractCmpId;
    private Long distributionStatusId;
    private boolean transmit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public Long getDistributionStatusId() {
        return distributionStatusId;
    }

    public void setDistributionStatusId(Long distributionStatusId) {
        this.distributionStatusId = distributionStatusId;
    }

    public boolean isTransmit() {
        return transmit;
    }

    public void setTransmit(boolean transmit) {
        this.transmit = transmit;
    }

}
