package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractServiceDtlTO;

public class PreContractServiceSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractServiceDtlTO> preContractServiceDtlTOs = new ArrayList<PreContractServiceDtlTO>();
    private Long preContractId;
    private boolean version;
    private boolean external;
    private List<Long> serviceDtlIds = new ArrayList<Long>();

    public List<Long> getServiceDtlIds() {
        return serviceDtlIds;
    }

    public void setServiceDtlIds(List<Long> serviceDtlIds) {
        this.serviceDtlIds = serviceDtlIds;
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

}
