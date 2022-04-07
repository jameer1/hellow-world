package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPlantTransTO;

public class ProjPlantTransResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 9017431618205674049L;
    private List<ProjPlantTransTO> projPlantTransTOs = null;

    public ProjPlantTransResp() {
        projPlantTransTOs = new ArrayList<ProjPlantTransTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPlantTransTO> getProjPlantTransTOs() {
        return projPlantTransTOs;
    }

    public void setProjPlantTransTOs(List<ProjPlantTransTO> projPlantTransTOs) {
        this.projPlantTransTOs = projPlantTransTOs;
    }

}
