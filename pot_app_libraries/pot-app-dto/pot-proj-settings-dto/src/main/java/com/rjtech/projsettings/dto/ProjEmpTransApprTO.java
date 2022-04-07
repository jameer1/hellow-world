package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjEmpTransApprTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long empTransId;
    private String requisitionDate;
    private Long notificationId;
    private String notificationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public Long getEmpTransId() {
        return empTransId;
    }

    public void setEmpTransId(Long empTransId) {
        this.empTransId = empTransId;
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