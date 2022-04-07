package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjMaterialTransTO;

public class ProjMaterialTransSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3938957813936037693L;
    private List<ProjMaterialTransTO> projMaterialTransTOs = new ArrayList<ProjMaterialTransTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjMaterialTransTO> getProjMaterialTransTOs() {
        return projMaterialTransTOs;
    }

    public void setProjMaterialTransTOs(List<ProjMaterialTransTO> projMaterialTransTOs) {
        this.projMaterialTransTOs = projMaterialTransTOs;
    }

}
