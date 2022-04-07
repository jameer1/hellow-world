package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjSettingsFilterReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1195837133988848713L;

    private List<Long> projIds = new ArrayList<>();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

}
