package com.rjtech.register.plant.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.user.dto.UserTO;

public class PlantTransferReqApprTO extends ProjectTO {

    private static final long serialVersionUID = 7685912383026052204L;

    private Long id;
    private Long plantId;
    private String reqCode;
    private String apprCode;
    private String reqDate;
    private String apprDate;
    private String apprComments;
    private String reqComments;
    private String apprStatus;
    private Long toProjId;
    private String toProjName;
    private Long fromProjId;
    private String fromProjName;
    private UserTO reqUserTO = new UserTO();
    private UserTO apprUserTO = new UserTO();
    private String notifyCode;
    private Long notifyId;
    private String apprCurrentProj;
    private String reqCurrentProj;
    private String notificationStatus;
    private String notifyDate;
    private String notifyMsg;
    private Boolean addlTimeFlag;

    private List<PlantTransReqApprDtlTO> plantTransReqApprDtlTOs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getApprCode() {
        return apprCode;
    }

    public void setApprCode(String apprCode) {
        this.apprCode = apprCode;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Long getToProjId() {
        return toProjId;
    }

    public void setToProjId(Long toProjId) {
        this.toProjId = toProjId;
    }

    public String getToProjName() {
        return toProjName;
    }

    public void setToProjName(String toProjName) {
        this.toProjName = toProjName;
    }

    public Long getFromProjId() {
        return fromProjId;
    }

    public void setFromProjId(Long fromProjId) {
        this.fromProjId = fromProjId;
    }

    public String getFromProjName() {
        return fromProjName;
    }

    public void setFromProjName(String fromProjName) {
        this.fromProjName = fromProjName;
    }

    public UserTO getReqUserTO() {
        return reqUserTO;
    }

    public void setReqUserTO(UserTO reqUserTO) {
        this.reqUserTO = reqUserTO;
    }

    public UserTO getApprUserTO() {
        return apprUserTO;
    }

    public void setApprUserTO(UserTO apprUserTO) {
        this.apprUserTO = apprUserTO;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getApprCurrentProj() {
        return apprCurrentProj;
    }

    public void setApprCurrentProj(String apprCurrentProj) {
        this.apprCurrentProj = apprCurrentProj;
    }

    public String getReqCurrentProj() {
        return reqCurrentProj;
    }

    public void setReqCurrentProj(String reqCurrentProj) {
        this.reqCurrentProj = reqCurrentProj;
    }

    public List<PlantTransReqApprDtlTO> getPlantTransReqApprDtlTOs() {
        return plantTransReqApprDtlTOs;
    }

    public void setPlantTransReqApprDtlTOs(List<PlantTransReqApprDtlTO> plantTransReqApprDtlTOs) {
        this.plantTransReqApprDtlTOs = plantTransReqApprDtlTOs;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
    
    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }
    
    public String getNotifyMsg() {
        return notifyMsg;
    }

    public void setNotifyMsg(String notifyMsg) {
        this.notifyMsg = notifyMsg;
    }
    
    public Boolean getAddlTimeFlag() {
        return addlTimeFlag;
    }

    public void setAddlTimeFlag(Boolean addlTimeFlag) {
        this.addlTimeFlag = addlTimeFlag;
    }

}
