package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPrecontractTO;
import com.rjtech.projsettings.dto.ProjPlantTransTO;

public class ProjPlantTransOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -4040563035614515309L;
    private List<ProjPrecontractTO> precontractTOs = null;
    private List<ProjPlantTransTO> projPlantTransTOs = null;

    public List<ProjPlantTransTO> getProjPlantTransTOs() {
        return projPlantTransTOs;
    }

    public void setProjPlantTransTOs(List<ProjPlantTransTO> projPlantTransTOs) {
        this.projPlantTransTOs = projPlantTransTOs;
    }

    public ProjPlantTransOnLoadResp() {
        precontractTOs = new ArrayList<ProjPrecontractTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPrecontractTO> getPrecontractTOs() {
        return precontractTOs;
    }

    public void setPrecontractTOs(List<ProjPrecontractTO> precontractTOs) {
        this.precontractTOs = precontractTOs;
    }

}
