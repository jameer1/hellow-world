package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjSettingsTO;

public class ProjSettingsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<ProjSettingsTO> projSettingsTOs = null;

    public ProjSettingsResp() {
        projSettingsTOs = new ArrayList<ProjSettingsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSettingsTO> getProjSettingsTOs() {
        return projSettingsTOs;
    }

}
