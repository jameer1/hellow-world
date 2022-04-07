package com.rjtech.projsettings.dto;

import java.io.Serializable;

import com.rjtech.common.dto.ProjectTO;

public class ProjPlantTransApprTO extends ProjectTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long plantTransId;
    private String requisitionDate;
    private Long notificationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantTransId() {
        return plantTransId;
    }

    public void setPlantTransId(Long plantTransId) {
        this.plantTransId = plantTransId;
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

}
