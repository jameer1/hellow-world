package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PrecontractSowDtlTO;

public class PreContractSowDtlsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<PrecontractSowDtlTO> precontractSowDtlTOs = new ArrayList<PrecontractSowDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private Long preContractId;
    private boolean version;
    private boolean external;
    private List<Long> sowDtlIds = new ArrayList<Long>();

    public List<PrecontractSowDtlTO> getPrecontractSowDtlTOs() {
        return precontractSowDtlTOs;
    }

    public void setPrecontractSowDtlTOs(List<PrecontractSowDtlTO> precontractSowDtlTOs) {
        this.precontractSowDtlTOs = precontractSowDtlTOs;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public List<Long> getSowDtlIds() {
        return sowDtlIds;
    }

    public void setSowDtlIds(List<Long> sowDtlIds) {
        this.sowDtlIds = sowDtlIds;
    }

}
