package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjDeleteReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<Long> projectIds = new ArrayList<Long>();

    public List<Long> getProjectIds() {
        return this.projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }
}
