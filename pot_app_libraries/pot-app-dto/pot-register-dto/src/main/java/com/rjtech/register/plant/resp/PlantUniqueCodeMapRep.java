package com.rjtech.register.plant.resp;

import java.util.HashMap;
import java.util.Map;

public class PlantUniqueCodeMapRep {

    Map<String, Integer> uniqueCodeMap = new HashMap<String, Integer>();

    public Map<String, Integer> getUniqueCodeMap() {
        return uniqueCodeMap;
    }

    public void setUniqueCodeMap(Map<String, Integer> uniqueCodeMap) {
        this.uniqueCodeMap = uniqueCodeMap;
    }

}
