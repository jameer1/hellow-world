package com.rjtech.user.resp;

import java.util.HashMap;
import java.util.Map;

public class UserMapResp {
    Map<String, Integer> userUniqueMap = new HashMap<String, Integer>();

    public Map<String, Integer> getUserUniqueMap() {
        return userUniqueMap;
    }

    public void setUserUniqueMap(Map<String, Integer> userUniqueMap) {
        this.userUniqueMap = userUniqueMap;
    }

}
