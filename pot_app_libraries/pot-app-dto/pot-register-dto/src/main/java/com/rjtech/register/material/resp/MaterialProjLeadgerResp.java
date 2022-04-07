package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialProjLedgerTO;

public class MaterialProjLeadgerResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<MaterialProjLedgerTO> materialProjLedgerTOs = new ArrayList<MaterialProjLedgerTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MaterialProjLedgerTO> getMaterialProjLedgerTOs() {
        return materialProjLedgerTOs;
    }

    public void setMaterialProjLedgerTOs(List<MaterialProjLedgerTO> materialProjLedgerTOs) {
        this.materialProjLedgerTOs = materialProjLedgerTOs;
    }

}
