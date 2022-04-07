package com.rjtech.projectlib.req;

import com.rjtech.projectlib.dto.ProjWorkShiftTO;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjWorkShiftSaveReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<ProjWorkShiftTO> projWorkShiftTOs = new ArrayList<ProjWorkShiftTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjWorkShiftTO> getProjWorkShiftTOs() {
        return projWorkShiftTOs;
    }

    public void setProjWorkShiftTOs(List<ProjWorkShiftTO> projWorkShiftTOs) {
        this.projWorkShiftTOs = projWorkShiftTOs;
    }

}
