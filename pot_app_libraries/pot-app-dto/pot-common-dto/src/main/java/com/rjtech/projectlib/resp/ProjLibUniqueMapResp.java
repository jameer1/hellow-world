package com.rjtech.projectlib.resp;

import java.util.HashMap;
import java.util.Map;

public class ProjLibUniqueMapResp {

    Map<Long, Integer> projUniqueCodeMap = new HashMap<Long, Integer>();

    public Map<Long, Integer> getProjUniqueCodeMap() {
        return projUniqueCodeMap;
    }

    public void setProjUniqueCodeMap(Map<Long, Integer> projUniqueCodeMap) {
        this.projUniqueCodeMap = projUniqueCodeMap;
    }

}
