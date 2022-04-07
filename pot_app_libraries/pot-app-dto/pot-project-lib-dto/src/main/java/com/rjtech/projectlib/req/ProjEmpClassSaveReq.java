package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjEmpClassTO;

public class ProjEmpClassSaveReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;

    private List<ProjEmpClassTO> projEmpClassTOs = new ArrayList<ProjEmpClassTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjEmpClassTO> getProjEmpClassTOs() {
        return projEmpClassTOs;
    }

    public void setProjEmpClassTOs(List<ProjEmpClassTO> projEmpClassTOs) {
        this.projEmpClassTOs = projEmpClassTOs;
    }

}
