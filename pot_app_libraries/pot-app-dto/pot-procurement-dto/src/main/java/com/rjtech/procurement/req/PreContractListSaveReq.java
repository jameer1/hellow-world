package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractTO;

public class PreContractListSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractTO> preContractTOs = new ArrayList<PreContractTO>();
    private Integer apprvStatus;
    private boolean version;
    private Map<Long, Long> approvedCompanyMap;
    private ProcurementFilterReq procurementFilterReq = new ProcurementFilterReq();

    public List<PreContractTO> getPreContractTOs() {
        return preContractTOs;
    }

    public void setPreContractTOs(List<PreContractTO> preContractTOs) {
        this.preContractTOs = preContractTOs;
    }

    public ProcurementFilterReq getProcurementFilterReq() {
        return procurementFilterReq;
    }

    public void setProcurementFilterReq(ProcurementFilterReq procurementFilterReq) {
        this.procurementFilterReq = procurementFilterReq;
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
