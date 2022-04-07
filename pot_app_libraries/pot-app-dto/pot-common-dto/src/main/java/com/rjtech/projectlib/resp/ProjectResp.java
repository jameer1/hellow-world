package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.resp.AppResp;

public class ProjectResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<EPSProjectTO> epsProjs = null;
    private List<Long> newProjIds = null;

    public ProjectResp() {
        epsProjs = new ArrayList<EPSProjectTO>();
    }

    public List<EPSProjectTO> getEpsProjs() {
        return this.epsProjs;
    }

    public List<Long> getNewProjIds() {
        return newProjIds;
    }

    public void setNewProjIds(List<Long> newProjIds) {
        this.newProjIds = newProjIds;
    }

}
