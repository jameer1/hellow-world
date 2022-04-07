package com.rjtech.notification.req;

import com.rjtech.common.dto.ProjectTO;

public class NotificationCountGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fromDate;
    private String toDate;
    private String date;
    private String notifyStatus;
    private boolean loginUser;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public boolean isLoginUser() {
        return loginUser;
    }

    public void setLoginUser(boolean loginUser) {
        this.loginUser = loginUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
