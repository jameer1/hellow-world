package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class UserDelReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<Long> userIds = new ArrayList<Long>();

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

}