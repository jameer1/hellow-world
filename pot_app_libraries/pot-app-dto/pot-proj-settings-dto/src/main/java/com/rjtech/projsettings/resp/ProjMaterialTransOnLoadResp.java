package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPrecontractTO;
import com.rjtech.projsettings.dto.ProjMaterialTransTO;

public class ProjMaterialTransOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3148703566541257045L;
    private List<ProjPrecontractTO> precontractTOs = null;
    private List<ProjMaterialTransTO> projMaterialTransTOs = null;

    public ProjMaterialTransOnLoadResp() {
        projMaterialTransTOs = new ArrayList<ProjMaterialTransTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPrecontractTO> getPrecontractTOs() {
        return precontractTOs;
    }

    public void setPrecontractTOs(List<ProjPrecontractTO> precontractTOs) {
        this.precontractTOs = precontractTOs;
    }

    public List<ProjMaterialTransTO> getProjMaterialTransTOs() {
        return projMaterialTransTOs;
    }

    public void setProjMaterialTransTOs(List<ProjMaterialTransTO> projMaterialTransTOs) {
        this.projMaterialTransTOs = projMaterialTransTOs;
    }

}
