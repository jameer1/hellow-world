package com.rjtech.centrallib.resp;

import java.util.HashMap;
import java.util.Map;

public class CentLibMapResp {

    Map<String, Integer> uniqueCodeMap = new HashMap<String, Integer>();

    public Map<String, Integer> getUniqueCodeMap() {
        return uniqueCodeMap;
    }

    public void setUniqueCodeMap(Map<String, Integer> uniqueCodeMap) {
        this.uniqueCodeMap = uniqueCodeMap;
    }

}
