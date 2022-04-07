package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjPlantClassTO;

public class ProjPlantClassResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjPlantClassTO> projPlantClassTOs = null;

    public ProjPlantClassResp() {
        projPlantClassTOs = new ArrayList<ProjPlantClassTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPlantClassTO> getProjPlantClassTOs() {
        return projPlantClassTOs;
    }

    public void setProjPlantClassTOs(List<ProjPlantClassTO> projPlantClassTOs) {
        this.projPlantClassTOs = projPlantClassTOs;
    }

}
