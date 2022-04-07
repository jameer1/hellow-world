package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractMaterialDtlTO;

public class PreContractMaterialSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractMaterialDtlTO> preContractMaterialDtlTOs = new ArrayList<PreContractMaterialDtlTO>();
    private Long preContractId;
    private boolean version;
    private boolean external;
    private List<Long> materialDtlIds = new ArrayList<Long>();

    public List<PreContractMaterialDtlTO> getPreContractMaterialDtlTOs() {
        return preContractMaterialDtlTOs;
    }

    public void setPreContractMaterialDtlTOs(List<PreContractMaterialDtlTO> preContractMaterialDtlTOs) {
        this.preContractMaterialDtlTOs = preContractMaterialDtlTOs;
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

    public List<Long> getMaterialDtlIds() {
        return materialDtlIds;
    }

    public void setMaterialDtlIds(List<Long> materialDtlIds) {
        this.materialDtlIds = materialDtlIds;
    }

}
