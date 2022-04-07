package com.rjtech.projsettings.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjectCostStmtsDelReq extends ProjectTO {

    private static final long serialVersionUID = -1029509509932664921L;
    private List<Long> projCostStmtsIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjCostStmtsIds() {
        return projCostStmtsIds;
    }

    public void setProjCostStmtsIds(List<Long> projCostStmtsIds) {
        this.projCostStmtsIds = projCostStmtsIds;
    }

}
