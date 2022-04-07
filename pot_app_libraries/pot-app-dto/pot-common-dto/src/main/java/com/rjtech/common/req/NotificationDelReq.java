package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class NotificationDelReq extends ProjectTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<Long> notificationIds = new ArrayList<Long>();

    public List<Long> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }

}