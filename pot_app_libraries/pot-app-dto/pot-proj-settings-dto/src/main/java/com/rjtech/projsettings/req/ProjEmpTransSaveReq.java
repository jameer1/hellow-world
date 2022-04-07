package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEmpTransTO;

public class ProjEmpTransSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4954409332602501468L;

    private List<ProjEmpTransTO> projEmpTransTOs = new ArrayList<ProjEmpTransTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjEmpTransTO> getProjEmpTransTOs() {
        return projEmpTransTOs;
    }

    public void setProjEmpTransTOs(List<ProjEmpTransTO> projEmpTransTOs) {
        this.projEmpTransTOs = projEmpTransTOs;
    }

}
