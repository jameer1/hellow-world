package com.rjtech.register.material.model;

import java.io.Serializable;
import java.sql.Timestamp;
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

import com.rjtech.centrallib.model.StockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;

@Entity
@Table(name = "material_trans_req_approval")
public class MaterialTransferReqApprEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MTRA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MTRA_FROM_EPM_ID")
    private ProjMstrEntity fromProjId;

    @ManyToOne
    @JoinColumn(name = "MTRA_TO_EPM_ID")
    private ProjMstrEntity toProjId;

    @ManyToOne
    @JoinColumn(name = "MTRA_FROM_SM_ID")
    private StockMstrEntity fromStoreId;

    @ManyToOne
    @JoinColumn(name = "MTRA_TO_SM_ID")
    private StockMstrEntity toStoreId;

    @Column(name = "MTRA_REQ_CODE")
    private String reqCode;

    @Column(name = "MTRA_APPR_CODE")
    private String apprCode;

    @Column(name = "MTRA_REQ_COMMENTS")
    private String reqComments;

    @Column(name = "MTRA_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "MTRA_APPR_STATUS")
    private String apprStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MTRA_REQ_DATE")
    private Date reqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MTRA_REQ_EXPIRY_DATE")
    private Date reqExpiryDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MTRA_APPR_DATE")
    private Date apprDate;

    @Column(name = "MTRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MTRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MTRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MTRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MTRA_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "MTRA_MNTF_ID")
    private MaterialNotificationsEntity materialNotificationsEntity;

    @OneToMany(mappedBy = "materialTransferReqApprEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MaterialTransferReqApprDtlEntity> materialTransferReqApprDtlEntities = new ArrayList<MaterialTransferReqApprDtlEntity>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    @ManyToOne
    @JoinColumn(name = "MTRA_REQ_USR_ID")
    private UserMstrEntity reqUserEntity;

    @ManyToOne
    @JoinColumn(name = "MTRA_APPR_USR_ID")
    private UserMstrEntity apprUserEntity;

    @ManyToOne
    @JoinColumn(name = "MTRA_FROM_SPM_ID")
    private ProjStoreStockMstrEntity fromStoreProjectId;

    @ManyToOne
    @JoinColumn(name = "MTRA_TO_SPM_ID")
    private ProjStoreStockMstrEntity toStoreProjectId;

    public MaterialTransferReqApprEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public Date getReqExpiryDate() {
        return reqExpiryDate;
    }

    public void setReqExpiryDate(Date reqExpiryDate) {
        this.reqExpiryDate = reqExpiryDate;
    }

    public MaterialNotificationsEntity getMaterialNotificationsEntity() {
        return materialNotificationsEntity;
    }

    public void setMaterialNotificationsEntity(MaterialNotificationsEntity materialNotificationsEntity) {
        this.materialNotificationsEntity = materialNotificationsEntity;
    }

    public List<MaterialTransferReqApprDtlEntity> getMaterialTransferReqApprDtlEntities() {
        return materialTransferReqApprDtlEntities;
    }

    public void setMaterialTransferReqApprDtlEntities(
            List<MaterialTransferReqApprDtlEntity> materialTransferReqApprDtlEntities) {
        this.materialTransferReqApprDtlEntities = materialTransferReqApprDtlEntities;
    }

    public UserMstrEntity getReqUserEntity() {
        return reqUserEntity;
    }

    public void setReqUserEntity(UserMstrEntity reqUserEntity) {
        this.reqUserEntity = reqUserEntity;
    }

    public UserMstrEntity getApprUserEntity() {
        return apprUserEntity;
    }

    public void setApprUserEntity(UserMstrEntity apprUserEntity) {
        this.apprUserEntity = apprUserEntity;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
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

    public StockMstrEntity getFromStoreId() {
        return fromStoreId;
    }

    public void setFromStoreId(StockMstrEntity fromStoreId) {
        this.fromStoreId = fromStoreId;
    }

    public StockMstrEntity getToStoreId() {
        return toStoreId;
    }

    public void setToStoreId(StockMstrEntity toStoreId) {
        this.toStoreId = toStoreId;
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

    public ProjStoreStockMstrEntity getFromStoreProjectId() {
        return fromStoreProjectId;
    }

    public void setFromStoreProjectId(ProjStoreStockMstrEntity fromStoreProjectId) {
        this.fromStoreProjectId = fromStoreProjectId;
    }

    public ProjStoreStockMstrEntity getToStoreProjectId() {
        return toStoreProjectId;
    }

    public void setToStoreProjectId(ProjStoreStockMstrEntity toStoreProjectId) {
        this.toStoreProjectId = toStoreProjectId;
    }

}
