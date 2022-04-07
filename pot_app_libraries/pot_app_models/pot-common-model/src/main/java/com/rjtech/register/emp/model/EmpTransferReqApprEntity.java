package com.rjtech.register.emp.model;

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
@Table(name = "emp_trasfer_req_appr")
public class EmpTransferReqApprEntity implements Serializable {

    private static final long serialVersionUID = 7513815761284669545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETRA_ID")
    private Long id;

    @Column(name = "ETRA_REQ_CODE")
    private String reqCode;

    @Column(name = "ETRA_APPR_CODE")
    private String apprCode;

    @ManyToOne
    @JoinColumn(name = "ETRA_TO_EPM_ID")
    private ProjMstrEntity toProjMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ETRA_FROM_EPM_ID")
    private ProjMstrEntity fromProjMstrEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRA_REQ_DATE")
    private Date reqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRA_APPR_DATE")
    private Date apprDate;

    @Column(name = "ETRA_REQ_COMMENTS")
    private String reqComments;

    @Column(name = "ETRA_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "ETRA_APPR_STATUS")
    private String apprStatus;

    @Column(name = "ETRA_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "ETRA_REQ_CUR_PROJECT")
    private String reqCurrentProj;

    @Column(name = "ETRA_APPR_CUR_PROJECT")
    private String apprCurrentProj;

    @Column(name = "ETRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ETRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ETRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETRA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "ETRA_REQ_USR_ID")
    private UserMstrEntity reqUserMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ETRA_APPR_USR_ID")
    private UserMstrEntity apprUserMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ETRA_NOTIFY_ID")
    private EmpNotificationsEntity empNotificationsEntity;

    @OneToMany(mappedBy = "empTransferReqApprEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpTransReqApprDetailEntity> empTransReqApprDetailEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getToProjMstrEntity() {
        return toProjMstrEntity;
    }

    public void setToProjMstrEntity(ProjMstrEntity toProjMstrEntity) {
        this.toProjMstrEntity = toProjMstrEntity;
    }

    public ProjMstrEntity getFromProjMstrEntity() {
        return fromProjMstrEntity;
    }

    public void setFromProjMstrEntity(ProjMstrEntity fromProjMstrEntity) {
        this.fromProjMstrEntity = fromProjMstrEntity;
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

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<EmpTransReqApprDetailEntity> getEmpTransReqApprDetailEntities() {
        return empTransReqApprDetailEntities;
    }

    public void setEmpTransReqApprDetailEntities(List<EmpTransReqApprDetailEntity> empTransReqApprDetailEntities) {
        this.empTransReqApprDetailEntities = empTransReqApprDetailEntities;
    }

    public UserMstrEntity getReqUserMstrEntity() {
        return reqUserMstrEntity;
    }

    public void setReqUserMstrEntity(UserMstrEntity reqUserMstrEntity) {
        this.reqUserMstrEntity = reqUserMstrEntity;
    }

    public UserMstrEntity getApprUserMstrEntity() {
        return apprUserMstrEntity;
    }

    public void setApprUserMstrEntity(UserMstrEntity apprUserMstrEntity) {
        this.apprUserMstrEntity = apprUserMstrEntity;
    }

    public EmpNotificationsEntity getEmpNotificationsEntity() {
        return empNotificationsEntity;
    }

    public void setEmpNotificationsEntity(EmpNotificationsEntity empNotificationsEntity) {
        this.empNotificationsEntity = empNotificationsEntity;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
