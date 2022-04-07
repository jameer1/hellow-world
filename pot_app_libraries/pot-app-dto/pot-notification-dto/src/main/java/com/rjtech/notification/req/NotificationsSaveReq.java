package com.rjtech.notification.req;

import com.rjtech.common.dto.ProjectTO;

public class NotificationsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String notifyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

}
