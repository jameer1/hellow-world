package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractEmpDtlTO;

public class PreContractEmpSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractEmpDtlTO> preContractEmpDtlTOs = new ArrayList<PreContractEmpDtlTO>();
    private Long preContractId;
    private boolean version;
    private boolean external;
    private List<Long> empDtlIds = new ArrayList<Long>();

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

    public List<Long> getEmpDtlIds() {
        return empDtlIds;
    }

    public void setEmpDtlIds(List<Long> empDtlIds) {
        this.empDtlIds = empDtlIds;
    }

}
