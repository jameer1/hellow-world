package com.rjtech.register.plant.dto;

import com.rjtech.common.dto.ProjectTO;

public class PlantNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String date;
    private String code;
    private Long plantTransId;
    private Long notifyRefId;
    private Long projId;
    private Long apprUserId;
    private Long reqUserId;
    private Long parentId;
    private String notificationStatus;
    private String type;
    private String comments;
    private String requistionCode;
    private Long forProject;

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

    public Long getPlantTransId() {
        return plantTransId;
    }

    public void setPlantTransId(Long plantTransId) {
        this.plantTransId = plantTransId;
    }

    public Long getNotifyRefId() {
        return notifyRefId;
    }

    public void setNotifyRefId(Long notifyRefId) {
        this.notifyRefId = notifyRefId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRequistionCode() {
        return requistionCode;
    }

    public void setRequistionCode(String requistionCode) {
        this.requistionCode = requistionCode;
    }

    public Long getForProject() {
        return forProject;
    }

    public void setForProject(Long forProject) {
        this.forProject = forProject;
    }

}
