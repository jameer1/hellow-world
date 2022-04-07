package com.rjtech.register.material.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.UserTO;

public class MaterialTransferReqApprTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long fromProjId;
    private String fromProjName;
    private Long toProjId;
    private String toProjName;
    private Long fromStoreId;
    private String fromStoreName;
    private Long toStoreId;
    private String toStoreName;
    private UserTO reqUserTO;
    private UserTO apprUserTO;
    private String reqComments;
    private String apprComments;
    private String apprStatus;
    private String reqDate;
    private String reqExpiryDate;
    private String apprDate;
    private Long notifyId;
    private String notifyCode;
    private String reqCode;
    private String apprCode;
    private Long fromStoreProjectId;
    private Long toStoreProjectId;

    private List<MaterialTransferReqApprDtlTO> materialTransferReqApprDtlTOs = new ArrayList<MaterialTransferReqApprDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public MaterialTransferReqApprTO() {

    }

    public String getToStoreName() {
        return toStoreName;
    }

    public void setToStoreName(String toStoreName) {
        this.toStoreName = toStoreName;
    }

    public String getFromStoreName() {
        return fromStoreName;
    }

    public void setFromStoreName(String fromStoreName) {
        this.fromStoreName = fromStoreName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromProjId() {
        return fromProjId;
    }

    public void setFromProjId(Long fromProjId) {
        this.fromProjId = fromProjId;
    }

    public Long getToProjId() {
        return toProjId;
    }

    public void setToProjId(Long toProjId) {
        this.toProjId = toProjId;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
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

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqExpiryDate() {
        return reqExpiryDate;
    }

    public void setReqExpiryDate(String reqExpiryDate) {
        this.reqExpiryDate = reqExpiryDate;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
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

    public List<MaterialTransferReqApprDtlTO> getMaterialTransferReqApprDtlTOs() {
        return materialTransferReqApprDtlTOs;
    }

    public void setMaterialTransferReqApprDtlTOs(List<MaterialTransferReqApprDtlTO> materialTransferReqApprDtlTOs) {
        this.materialTransferReqApprDtlTOs = materialTransferReqApprDtlTOs;
    }

    public String getFromProjName() {
        return fromProjName;
    }

    public void setFromProjName(String fromProjName) {
        this.fromProjName = fromProjName;
    }

    public String getToProjName() {
        return toProjName;
    }

    public void setToProjName(String toProjName) {
        this.toProjName = toProjName;
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
