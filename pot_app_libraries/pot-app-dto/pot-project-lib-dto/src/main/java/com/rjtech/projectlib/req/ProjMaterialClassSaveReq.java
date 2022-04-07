package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjMaterialClassTO;

public class ProjMaterialClassSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> ids = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private List<ProjMaterialClassTO> projMaterialClassTOs = new ArrayList<ProjMaterialClassTO>();

    public ProjMaterialClassSaveReq() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<ProjMaterialClassTO> getProjMaterialClassTOs() {
        return projMaterialClassTOs;
    }

    public void setProjMaterialClassTOs(List<ProjMaterialClassTO> projMaterialClassTOs) {
        this.projMaterialClassTOs = projMaterialClassTOs;
    }

}
