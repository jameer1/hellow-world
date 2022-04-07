package com.rjtech.notification.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.constants.ApplicationConstants;

public class NotificationStatusOnLoadResp {
    private static final long serialVersionUID = 1L;
    private List<String> statuslist = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();

    public List<String> getStatuslist() {
        return statuslist;
    }

    public void setStatuslist(List<String> statuslist) {
        this.statuslist = statuslist;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

}
