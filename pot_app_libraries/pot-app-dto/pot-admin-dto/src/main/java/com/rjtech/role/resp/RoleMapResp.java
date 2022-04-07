package com.rjtech.role.resp;

import java.util.HashMap;
import java.util.Map;

public class RoleMapResp {

    Map<String, Integer> uniqueRoleMap = new HashMap<String, Integer>();

    public Map<String, Integer> getUniqueRoleMap() {
        return uniqueRoleMap;
    }

    public void setUniqueRoleMap(Map<String, Integer> uniqueRoleMap) {
        this.uniqueRoleMap = uniqueRoleMap;
    }

}
