package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialProjSchItemTO;

public class MaterialSchItemsResp extends MaterialOnLoadResp {

    /**
     * 
     */
    private static final long serialVersionUID = 610077484457075958L;

    private List<MaterialProjSchItemTO> materialProjSchItemTOs = new ArrayList<MaterialProjSchItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MaterialProjSchItemTO> getMaterialProjSchItemTOs() {
        return materialProjSchItemTOs;
    }

    public void setMaterialProjSchItemTOs(List<MaterialProjSchItemTO> materialProjSchItemTOs) {
        this.materialProjSchItemTOs = materialProjSchItemTOs;
    }

}
