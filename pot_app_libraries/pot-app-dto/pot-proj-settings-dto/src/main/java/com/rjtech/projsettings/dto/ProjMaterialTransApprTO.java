package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjMaterialTransApprTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long materialTransId;
    private String requisitionDate;
    private Long notificationId;
    private String notificationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialTransId() {
        return materialTransId;
    }

    public void setMaterialTransId(Long materialTransId) {
        this.materialTransId = materialTransId;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    
    public String getNotificationStatus() {
    	return notificationStatus;
    }
    
    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
