package com.rjtech.notification.dto;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ProcurementNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String date;
    private String code;
    private String moduleCode;
    private Long notifyRefId;
    private Long fromUserId;
    private Long toUserId;
    private String procureCatg;
    private String procureStage;
    private String notificationStatus;
    private Long preContractId;
    private String fromUserName;
    private String toUserName;
    private Integer apprStatus;
    private String 	reqComments;
    private String notificationMessage;
    private String addlTimeNotificationStatus;
    private Long normalTimeId;
    private Long notificationId;
    private String requisitionDate;
    private String stage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Long getNotifyRefId() {
        return notifyRefId;
    }

    public void setNotifyRefId(Long notifyRefId) {
        this.notifyRefId = notifyRefId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

    public String getProcureStage() {
        return procureStage;
    }

    public void setProcureStage(String procureStage) {
        this.procureStage = procureStage;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }
    
    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
    
    public Integer getApprStatus() {
    	return apprStatus;
    }
    
    public void setApprStatus(Integer apprStatus) {
    	this.apprStatus = apprStatus;
    }
    
    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }
    
    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
    
    public String getAddlTimeNotificationStatus() {
    	return addlTimeNotificationStatus;
    }
    
    public void setAddlTimeNotificationStatus(String addlTimeNotificationStatus) {
    	this.addlTimeNotificationStatus = addlTimeNotificationStatus;
    }
    
    public Long getNormalTimeId() {
    	return normalTimeId;
    }
    
    public void setNormalTimeId(Long normalTimeId) {
        this.normalTimeId = normalTimeId;
    }
    
    public Long getNotificationId() {
    	return notificationId;
    }
    
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    
    public String getRequisitionDate() {
    	return requisitionDate;
    }
    
    public void setRequisitionDate(String requisitionDate) {
    	this.requisitionDate = requisitionDate;
    }
    
    public String getStage() {
    	return stage;
    }
    
    public void setStage(String stage) {
    	this.stage = stage;
    }
}
