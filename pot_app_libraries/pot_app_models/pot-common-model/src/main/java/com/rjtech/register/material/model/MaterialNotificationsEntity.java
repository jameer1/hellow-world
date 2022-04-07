package com.rjtech.register.material.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.StockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;

@Entity
@Table(name = "material_notifications")
public class MaterialNotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MNTF_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MNTF_DATE")
    private Date date;

    @Column(name = "MNTF_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "MNTF_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "MNTF_FROM_PROJ_ID")
    private ProjMstrEntity fromProjId;

    @ManyToOne
    @JoinColumn(name = "MNTF_TO_EPM_ID")
    private ProjMstrEntity toProjId;

    @ManyToOne
    @JoinColumn(name = "MNTF_APPR_USR_ID")
    private UserMstrEntity apprUserId;

    @ManyToOne
    @JoinColumn(name = "MNTF_REQ_USR_ID")
    private UserMstrEntity reqUserId;

    @ManyToOne
    @JoinColumn(name = "MNTF_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "MNTF_NOTIFICATION_STATUS")
    private String notificationStatus;

    @Column(name = "MNTF_TYPE")
    private String type;

    @Column(name = "MNTF_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MNTF_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MNTF_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MNTF_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MNTF_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "MNTF_IS_LATEST")
    private String isLatest;

    @Column(name = "MNTF_MTRA_REQ_CODE")
    private String reqCode;

    @Column(name = "MNTF_MTRA_REQ_COMMENTS")
    private String reqComments;

    @Column(name = "MNTF_MTRA_APPR_COMMENTS")
    private String apprComments;

    @ManyToOne
    @JoinColumn(name = "MNTF_FROM_STORE")
    private StockMstrEntity fromStore;

    @ManyToOne
    @JoinColumn(name = "MNTF_TO_STORE")
    private StockMstrEntity toStore;

    @ManyToOne
    @JoinColumn(name = "MNTF_FROM_STORE_PROJECT")
    private ProjStoreStockMstrEntity fromStoreProject;

    @ManyToOne
    @JoinColumn(name = "MNTF_TO_STORE_PROJECT")
    private ProjStoreStockMstrEntity toStoreProject;
    
    @Column(name = "MNTF_NOTIFICATION_MSG")
    private String notificationMsg;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
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

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public ProjMstrEntity getFromProjId() {
        return fromProjId;
    }

    public void setFromProjId(ProjMstrEntity fromProjId) {
        this.fromProjId = fromProjId;
    }

    public ProjMstrEntity getToProjId() {
        return toProjId;
    }

    public void setToProjId(ProjMstrEntity toProjId) {
        this.toProjId = toProjId;
    }

    public UserMstrEntity getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(UserMstrEntity apprUserId) {
        this.apprUserId = apprUserId;
    }

    public UserMstrEntity getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(UserMstrEntity reqUserId) {
        this.reqUserId = reqUserId;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public StockMstrEntity getFromStore() {
        return fromStore;
    }

    public void setFromStore(StockMstrEntity fromStore) {
        this.fromStore = fromStore;
    }

    public StockMstrEntity getToStore() {
        return toStore;
    }

    public void setToStore(StockMstrEntity toStore) {
        this.toStore = toStore;
    }

    public ProjStoreStockMstrEntity getFromStoreProject() {
        return fromStoreProject;
    }

    public void setFromStoreProject(ProjStoreStockMstrEntity fromStoreProject) {
        this.fromStoreProject = fromStoreProject;
    }

    public ProjStoreStockMstrEntity getToStoreProject() {
        return toStoreProject;
    }

    public void setToStoreProject(ProjStoreStockMstrEntity toStoreProject) {
        this.toStoreProject = toStoreProject;
    }
    
    public String getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(String notificationMsg) {
        this.notificationMsg = notificationMsg;
    }
}
