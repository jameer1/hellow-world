package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjPlantClassTO;

public class ProjPlantClassSaveReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<ProjPlantClassTO> projPlantClassTOs = new ArrayList<ProjPlantClassTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPlantClassTO> getProjPlantClassTOs() {
        return projPlantClassTOs;
    }

    public void setProjPlantClassTOs(List<ProjPlantClassTO> projPlantClassTOs) {
        this.projPlantClassTOs = projPlantClassTOs;
    }

}
