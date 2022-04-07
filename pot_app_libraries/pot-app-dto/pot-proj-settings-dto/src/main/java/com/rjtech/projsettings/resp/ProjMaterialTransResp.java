package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjMaterialTransTO;

public class ProjMaterialTransResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5058364869327054588L;
    private List<ProjMaterialTransTO> projMaterialTransTOs = null;

    public ProjMaterialTransResp() {
        projMaterialTransTOs = new ArrayList<ProjMaterialTransTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjMaterialTransTO> getProjMaterialTransTOs() {
        return projMaterialTransTOs;
    }

    public void setProjMaterialTransTOs(List<ProjMaterialTransTO> projMaterialTransTOs) {
        this.projMaterialTransTOs = projMaterialTransTOs;
    }

}
