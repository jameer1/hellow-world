package com.rjtech.projectlib.resp;

import java.util.HashMap;
import java.util.Map;

public class ProjLibMapResp {

    Map<String, Integer> projUniqueCodeMap = new HashMap<String, Integer>();

    public Map<String, Integer> getProjUniqueCodeMap() {
        return projUniqueCodeMap;
    }

    public void setProjUniqueCodeMap(Map<String, Integer> projUniqueCodeMap) {
        this.projUniqueCodeMap = projUniqueCodeMap;
    }

}
