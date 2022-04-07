package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialProjDtlTO;

public class MaterialProjResp extends MaterialOnLoadResp {

    private static final long serialVersionUID = -6820238095347272817L;

    private List<MaterialProjDtlTO> materialProjDtlTOs = new ArrayList<MaterialProjDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MaterialProjDtlTO> getMaterialProjDtlTOs() {
        return materialProjDtlTOs;
    }

    public void setMaterialProjDtlTOs(List<MaterialProjDtlTO> materialProjDtlTOs) {
        this.materialProjDtlTOs = materialProjDtlTOs;
    }

}
