package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjProcurementApprTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long procurementId;
    private String requisitionDate;
    private Long notificationId;
    private Long notifyRefId;
    private Long apprUserId;
    private String stage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcurementId() {
        return procurementId;
    }

    public void setProcurementId(Long procurementId) {
        this.procurementId = procurementId;
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
    
    public Long getNotifyRefId() {
        return notifyRefId;
    }

    public void setNotifyRefId(Long notifyRefId) {
        this.notifyRefId = notifyRefId;
    }
    
    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }
    
    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

}
