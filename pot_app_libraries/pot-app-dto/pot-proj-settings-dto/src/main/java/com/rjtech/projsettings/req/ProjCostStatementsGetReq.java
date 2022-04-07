package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjCostStatementsGetReq extends ProjectTO {

    private static final long serialVersionUID = -4777171542605247720L;

    private List<Long> projIds = new ArrayList<>();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
