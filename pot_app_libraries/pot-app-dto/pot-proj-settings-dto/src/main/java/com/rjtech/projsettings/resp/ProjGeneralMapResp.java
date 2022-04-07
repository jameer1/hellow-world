package com.rjtech.projsettings.resp;

import java.util.HashMap;
import java.util.Map;

public class ProjGeneralMapResp {

    Map<String, Integer> projSettingsUniqueMap = new HashMap<String, Integer>();

    public Map<String, Integer> getProjSettingsUniqueMap() {
        return projSettingsUniqueMap;
    }

    public void setProjSettingsUniqueMap(Map<String, Integer> projSettingsUniqueMap) {
        this.projSettingsUniqueMap = projSettingsUniqueMap;
    }

}
