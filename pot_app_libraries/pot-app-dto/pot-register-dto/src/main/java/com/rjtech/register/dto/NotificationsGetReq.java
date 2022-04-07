package com.rjtech.register.dto;

import com.rjtech.common.dto.ProjectTO;

public class NotificationsGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long toProjId;
    private String notificationStatus;
    private Long fromStoreId;
    private Long toStoreId;
    private Long fromStoreProjectId;
    private Long toStoreProjectId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToProjId() {
        return toProjId;
    }

    public void setToProjId(Long toProjId) {
        this.toProjId = toProjId;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Long getFromStoreId() {
        return fromStoreId;
    }

    public void setFromStoreId(Long fromStoreId) {
        this.fromStoreId = fromStoreId;
    }

    public Long getToStoreId() {
        return toStoreId;
    }

    public void setToStoreId(Long toStoreId) {
        this.toStoreId = toStoreId;
    }

    public Long getFromStoreProjectId() {
        return fromStoreProjectId;
    }

    public void setFromStoreProjectId(Long fromStoreProjectId) {
        this.fromStoreProjectId = fromStoreProjectId;
    }

    public Long getToStoreProjectId() {
        return toStoreProjectId;
    }

    public void setToStoreProjectId(Long toStoreProjectId) {
        this.toStoreProjectId = toStoreProjectId;
    }
}
