package com.rjtech.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "emp_leave_req_approval")
public class EmpLeaveReqApprEntity implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELRA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ELRA_PARENT_ID")
    private EmpLeaveReqApprEntity parentEmpLeaveReqApprEntity;

    @Column(name = "ELRA_REQ_CODE")
    private String reqCode;

    @Column(name = "ELRA_APPR_CODE")
    private String apprCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRA_REQ_DATE")
    private Date reqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRA_APPR_DATE")
    private Date apprDate;

    @ManyToOne
    @JoinColumn(name = "ELRA_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ELRA_ERD_ID")
    private EmpRegisterDtlEntityCopy empRegisterDtlEntity;

    @Column(name = "ELRA_REASON_FOR_LEAVE")
    private String reasonForLeave;

    @Column(name = "ELRA_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "ELRA_APPR_STATUS")
    private String apprStatus;

    @ManyToOne
    @JoinColumn(name = "ELRA_APPR_USR_ID")
    private UserMstrEntity apprUserEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRA_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRA_END_DATE")
    private Date endDate;

    @Column(name = "ELRA_TOTAL_DAYS")
    private Integer totalDays;

    @Column(name = "ELRA_IS_LATEST")
    private boolean latest;

    @Column(name = "ELRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ELRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ELRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRA_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "ELRA_ENTF_CODE")
    private String notifyCode;

    @ManyToOne
    @JoinColumn(name = "ELRA_REQ_USR_ID")
    private UserMstrEntity reqUserEntity;

    @ManyToOne
    @JoinColumn(name = "ELRA_NOTIFY_ID")
    private EmployeeNotificationsEntity empNotificationsEntity;

    @OneToMany(mappedBy = "empLeaveReqApprEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpLeaveReqApprDetailEntity> empLeaveReqApprDetailEntities = new ArrayList<>();

    @OneToMany(mappedBy = "empLeaveReqApprEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpPublicHolidayEntity> empPublicHolidayEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
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

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
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

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public UserMstrEntity getApprUserEntity() {
        return apprUserEntity;
    }

    public void setApprUserEntity(UserMstrEntity apprUserEntity) {
        this.apprUserEntity = apprUserEntity;
    }

    public List<EmpLeaveReqApprDetailEntity> getEmpLeaveReqApprDetailEntities() {
        return empLeaveReqApprDetailEntities;
    }

    public void setEmpLeaveReqApprDetailEntities(List<EmpLeaveReqApprDetailEntity> empLeaveReqApprDetailEntities) {
        this.empLeaveReqApprDetailEntities = empLeaveReqApprDetailEntities;
    }

    public List<EmpPublicHolidayEntity> getEmpPublicHolidayEntities() {
        return empPublicHolidayEntities;
    }

    public void setEmpPublicHolidayEntities(List<EmpPublicHolidayEntity> empPublicHolidayEntities) {
        this.empPublicHolidayEntities = empPublicHolidayEntities;
    }

    public EmployeeNotificationsEntity getEmpNotificationsEntity() {
        return empNotificationsEntity;
    }

    public void setEmpNotificationsEntity(EmployeeNotificationsEntity empNotificationsEntity) {
        this.empNotificationsEntity = empNotificationsEntity;
    }

    public EmpLeaveReqApprEntity getParentEmpLeaveReqApprEntity() {
        return parentEmpLeaveReqApprEntity;
    }

    public void setParentEmpLeaveReqApprEntity(EmpLeaveReqApprEntity parentEmpLeaveReqApprEntity) {
        this.parentEmpLeaveReqApprEntity = parentEmpLeaveReqApprEntity;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
    }

    public UserMstrEntity getReqUserEntity() {
        return reqUserEntity;
    }

    public void setReqUserEntity(UserMstrEntity reqUserEntity) {
        this.reqUserEntity = reqUserEntity;
    }

    public EmpRegisterDtlEntityCopy getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntityCopy empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

}
