package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPlantTransTO;

public class ProjPlantTransSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5630221042373139023L;
    private List<ProjPlantTransTO> projPlantTransTOs = new ArrayList<ProjPlantTransTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPlantTransTO> getProjPlantTransTOs() {
        return projPlantTransTOs;
    }

    public void setProjPlantTransTOs(List<ProjPlantTransTO> projPlantTransTOs) {
        this.projPlantTransTOs = projPlantTransTOs;
    }

}
