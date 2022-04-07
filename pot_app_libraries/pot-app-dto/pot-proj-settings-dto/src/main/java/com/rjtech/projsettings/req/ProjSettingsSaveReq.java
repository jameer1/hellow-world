package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projsettings.dto.ProjSettings;

public class ProjSettingsSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<ProjSettings> projSettings = new ArrayList<ProjSettings>();

    public List<ProjSettings> getProjSettings() {
        return projSettings;
    }

    public void setProjSettings(List<ProjSettings> projSettings) {
        this.projSettings = projSettings;
    }

}
