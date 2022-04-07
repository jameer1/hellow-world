package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjProgressDelReq extends ProjectTO {

    private static final long serialVersionUID = 3303396761789001068L;

    private List<Long> projProgressIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjProgressIds() {
        return projProgressIds;
    }

    public void setProjProgressIds(List<Long> projProgressIds) {
        this.projProgressIds = projProgressIds;
    }

}
