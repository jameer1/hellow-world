package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.material.dto.MaterialProjDocketTO;

public class MaterialProjDocketResp extends MaterialOnLoadResp {

    private static final long serialVersionUID = -2422341637726407353L;
    private boolean anyIssueExist;

    private List<MaterialProjDocketTO> materialProjDocketTOs = new ArrayList<MaterialProjDocketTO>();

    public boolean isAnyIssueExist() {
        return anyIssueExist;
    }

    public void setAnyIssueExist(boolean anyIssueExist) {
        this.anyIssueExist = anyIssueExist;
    }

    public List<MaterialProjDocketTO> getMaterialProjDocketTOs() {
        return materialProjDocketTOs;
    }

    public void setMaterialProjDocketTOs(List<MaterialProjDocketTO> materialProjDocketTOs) {
        this.materialProjDocketTOs = materialProjDocketTOs;
    }

}
