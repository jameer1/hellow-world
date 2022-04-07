package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOWItemTO;

public class ProjSOWItemSaveReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjSOWItemTO> projSOWItemTOs = new ArrayList<ProjSOWItemTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjSOWItemTO> getProjSOWItemTOs() {
        return projSOWItemTOs;
    }

    public void setProjSOWItemTOs(List<ProjSOWItemTO> projSOWItemTOs) {
        this.projSOWItemTOs = projSOWItemTOs;
    }

}
