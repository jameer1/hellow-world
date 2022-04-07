package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractPlantDtlTO;

public class PreContractPlantSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractPlantDtlTO> preContractPlantDtlTOs = new ArrayList<PreContractPlantDtlTO>();
    private Long preContractId;
    private boolean version;
    private boolean external;
    private List<Long> plantDtlIds = new ArrayList<Long>();

    public List<PreContractPlantDtlTO> getPreContractPlantDtlTOs() {
        return preContractPlantDtlTOs;
    }

    public void setPreContractPlantDtlTOs(List<PreContractPlantDtlTO> preContractPlantDtlTOs) {
        this.preContractPlantDtlTOs = preContractPlantDtlTOs;
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

    public List<Long> getPlantDtlIds() {
        return plantDtlIds;
    }

    public void setPlantDtlIds(List<Long> plantDtlIds) {
        this.plantDtlIds = plantDtlIds;
    }

}
