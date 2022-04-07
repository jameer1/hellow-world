package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCrewTO;

public class ProjCrewSaveReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<ProjCrewTO> projCrewTOs = new ArrayList<ProjCrewTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjCrewTO> getProjCrewTOs() {
        return projCrewTOs;
    }

    public void setProjCrewTOs(List<ProjCrewTO> projCrewTOs) {
        this.projCrewTOs = projCrewTOs;
    }

}
