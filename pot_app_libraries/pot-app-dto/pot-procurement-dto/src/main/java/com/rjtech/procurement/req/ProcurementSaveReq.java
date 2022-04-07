package com.rjtech.procurement.req;

import java.util.Map;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractTO;

public class ProcurementSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private PreContractTO preContractTO = new PreContractTO();
    private Integer apprvStatus;
    private boolean version;
    private Map<Long, Long> approvedCompanyMap;

    public PreContractTO getPreContractTO() {
        return preContractTO;
    }

    public void setPreContractTO(PreContractTO preContractTO) {
        this.preContractTO = preContractTO;
    }

    public Integer getApprvStatus() {
        return apprvStatus;
    }

    public void setApprvStatus(Integer apprvStatus) {
        this.apprvStatus = apprvStatus;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public Map<Long, Long> getApprovedCompanyMap() {
        return approvedCompanyMap;
    }

    public void setApprovedCompanyMap(Map<Long, Long> approvedCompanyMap) {
        this.approvedCompanyMap = approvedCompanyMap;
    }

}
