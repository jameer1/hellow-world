package com.rjtech.register.material.dto;

import com.rjtech.common.dto.ProjectTO;

public class MaterialNotificationsTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String date;
    private String code;
    private Long notifyRefId;
    private Long apprUserId;
    private Long reqUserId;
    private String notifyStatus;
    private String type;
    private Long parentId;
    private Integer reqId;
    private String comments;
    private String requistionCode;
    private Long toProjId;

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

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRequistionCode() {
        return requistionCode;
    }

    public void setRequistionCode(String requistionCode) {
        this.requistionCode = requistionCode;
    }

    public Long getToProjId() {
        return toProjId;
    }

    public void setToProjId(Long toProjId) {
        this.toProjId = toProjId;
    }

}
