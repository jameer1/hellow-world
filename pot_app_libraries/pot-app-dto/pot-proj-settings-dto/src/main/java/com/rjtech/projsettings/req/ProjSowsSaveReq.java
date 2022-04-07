package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOWItemTO;

public class ProjSowsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 549086813301912755L;

    private List<ProjSOWItemTO> projSOWItemTOs = new ArrayList<ProjSOWItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjSOWItemTO> getProjSOWItemTOs() {
        return projSOWItemTOs;
    }

    public void setProjSOWItemTOs(List<ProjSOWItemTO> projSOWItemTOs) {
        this.projSOWItemTOs = projSOWItemTOs;
    }
}
