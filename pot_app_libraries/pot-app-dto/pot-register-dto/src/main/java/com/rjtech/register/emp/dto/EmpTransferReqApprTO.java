package com.rjtech.register.emp.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.UserTO;

public class EmpTransferReqApprTO extends ProjectTO {

    private static final long serialVersionUID = 6492104776365173920L;
    private Long id;
    private Long toProjId;
    private String toProjName;
    private Long fromProjId;
    private String fromProjName;
    private String reqCode;
    private String apprCode;
    private String apprDate;
    private String reqDate;
    private String reqComments;
    private String apprComments;
    private String apprStatus;
    private Long notifyId;
    private String reqNotifyCode;
    private String reqCurrentProj;
    private String apprCurrentProj;
    private String notifyDate;
    private String notifyMsg;
    private Boolean addlTimeFlag;

    private UserTO reqUserTO = new UserTO();
    private UserTO apprUserTO = new UserTO();
    private List<EmpTransReqApprDetailTO> empTransReqApprDetailTOs = new ArrayList<EmpTransReqApprDetailTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReqNotifyCode() {
        return reqNotifyCode;
    }

    public void setReqNotifyCode(String reqNotifyCode) {
        this.reqNotifyCode = reqNotifyCode;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public Long getToProjId() {
        return toProjId;
    }

    public void setToProjId(Long toProjId) {
        this.toProjId = toProjId;
    }

    public Long getFromProjId() {
        return fromProjId;
    }

    public void setFromProjId(Long fromProjId) {
        this.fromProjId = fromProjId;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
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

    public String getReqCurrentProj() {
        return reqCurrentProj;
    }

    public void setReqCurrentProj(String reqCurrentProj) {
        this.reqCurrentProj = reqCurrentProj;
    }

    public String getApprCurrentProj() {
        return apprCurrentProj;
    }

    public void setApprCurrentProj(String apprCurrentProj) {
        this.apprCurrentProj = apprCurrentProj;
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

    public List<EmpTransReqApprDetailTO> getEmpTransReqApprDetailTOs() {
        return empTransReqApprDetailTOs;
    }

    public void setEmpTransReqApprDetailTOs(List<EmpTransReqApprDetailTO> empTransReqApprDetailTOs) {
        this.empTransReqApprDetailTOs = empTransReqApprDetailTOs;
    }

    public String getToProjName() {
        return toProjName;
    }

    public void setToProjName(String toProjName) {
        this.toProjName = toProjName;
    }

    public String getFromProjName() {
        return fromProjName;
    }

    public void setFromProjName(String fromProjName) {
        this.fromProjName = fromProjName;
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
