package com.rjtech.register.emp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.user.dto.UserTO;

public class EmpLeaveReqApprTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 735738042909170237L;
    private Long id;
    private Long parentId;
    private String reqDate;
    private String apprDate;
    private String startDate;
    private String endDate;
    private Integer totalDays;
    private boolean leaveAvailed;
    private Integer leavesApproved;
    private String apprCode;
    private String reqCode;
    private Long empProjId;
    private Long empRegId;
    private String reasonForLeave;
    private String apprComments;
    private String apprStatus;
    private UserTO apprUserTO = new UserTO();
    private UserTO reqUserTO = new UserTO();
    private Integer status;
    private String notifyCode;
    private Long notifyId;
    private String notifyDate;
    private String notifyMsg;
    private Boolean addlTimeFlag;
    private boolean latest;
    private List<EmpLeaveReqApprDetailTO> empLeaveReqApprDetailTOs = new ArrayList<EmpLeaveReqApprDetailTO>();
    private List<EmpPublicHolidayTO> empPublicHolidayTOs = new ArrayList<EmpPublicHolidayTO>();
    private List<EmpPublicHolidayTO> empRosterDays = new ArrayList<EmpPublicHolidayTO>();

    public EmpLeaveReqApprTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getApprCode() {
        return apprCode;
    }

    public void setApprCode(String apprCode) {
        this.apprCode = apprCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
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

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isLeaveAvailed() {
        return leaveAvailed;
    }

    public void setLeaveAvailed(boolean leaveAvailed) {
        this.leaveAvailed = leaveAvailed;
    }

    public Integer getLeavesApproved() {
        return leavesApproved;
    }

    public void setLeavesApproved(Integer leavesApproved) {
        this.leavesApproved = leavesApproved;
    }

    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public String getReasonForLeave() {
        return reasonForLeave;
    }

    public void setReasonForLeave(String reasonForLeave) {
        this.reasonForLeave = reasonForLeave;
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

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserTO getApprUserTO() {
        return apprUserTO;
    }

    public void setApprUserTO(UserTO apprUserTO) {
        this.apprUserTO = apprUserTO;
    }
    
    public UserTO getReqUserTO() {
        return reqUserTO;
    }

    public void setReqUserTO(UserTO reqUserTO) {
        this.reqUserTO = reqUserTO;
    }

    public List<EmpLeaveReqApprDetailTO> getEmpLeaveReqApprDetailTOs() {
        return empLeaveReqApprDetailTOs;
    }

    public void setEmpLeaveReqApprDetailTOs(List<EmpLeaveReqApprDetailTO> empLeaveReqApprDetailTOs) {
        this.empLeaveReqApprDetailTOs = empLeaveReqApprDetailTOs;
    }

    public List<EmpPublicHolidayTO> getEmpPublicHolidayTOs() {
        return empPublicHolidayTOs;
    }

    public void setEmpPublicHolidayTOs(List<EmpPublicHolidayTO> empPublicHolidayTOs) {
        this.empPublicHolidayTOs = empPublicHolidayTOs;
    }

    public List<EmpPublicHolidayTO> getEmpRosterDays() {
        return empRosterDays;
    }

    public void setEmpRosterDays(List<EmpPublicHolidayTO> empRosterDays) {
        this.empRosterDays = empRosterDays;
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
