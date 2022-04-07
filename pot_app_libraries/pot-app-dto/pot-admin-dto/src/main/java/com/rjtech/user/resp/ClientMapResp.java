package com.rjtech.user.resp;

import java.util.HashMap;
import java.util.Map;

public class ClientMapResp {

    Map<String, Integer> clientUniqueMap = new HashMap<String, Integer>();

    Map<String, Integer> emailUniqueMap = new HashMap<String, Integer>();

    public Map<String, Integer> getClientUniqueMap() {
        return clientUniqueMap;
    }

    public void setClientUniqueMap(Map<String, Integer> clientUniqueMap) {
        this.clientUniqueMap = clientUniqueMap;
    }

    public Map<String, Integer> getEmailUniqueMap() {
        return emailUniqueMap;
    }

    public void setEmailUniqueMap(Map<String, Integer> emailUniqueMap) {
        this.emailUniqueMap = emailUniqueMap;
    }

}
