package com.rjtech.user.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class UserDeleteReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<Long> userIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

}
