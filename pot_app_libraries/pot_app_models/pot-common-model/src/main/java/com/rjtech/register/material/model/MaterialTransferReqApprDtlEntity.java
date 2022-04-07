package com.rjtech.register.material.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "material_trans_req_appr_dtl")
public class MaterialTransferReqApprDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MTRAD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MTRAD_MAP_ID")
    private MaterialProjDtlEntity materialId;

    @Column(name = "MTRAD_REQ_QTY")
    private BigDecimal reqQty;

    @Column(name = "MTRAD_APPR_QTY")
    private BigDecimal apprQty;

    @Column(name = "MTRAD_REQ_COMMENTS")
    private String reqComments;

    @Column(name = "MTRAD_APPR_COMMENTS")
    private String apprComments;

    @Column(name = "MTRAD_APPR_STATUS")
    private String apprStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MTRAD_REQ_DATE")
    private Date reqDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MTRAD_APPR_DATE")
    private Date apprDate;

    @ManyToOne
    @JoinColumn(name = "MTRAD_MTRA_ID")
    private MaterialTransferReqApprEntity materialTransferReqApprEntity;

    @Column(name = "MTRAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "MTRAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Column(name = "MTRAD_CREATED_ON", updatable = false)
    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn(name = "MTRAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MTRAD_UPDATED_ON")
    private Timestamp updatedOn;

    @Column(name = "MTRAD_AVL_QTY")
    private BigDecimal availableQty;

    public MaterialTransferReqApprDtlEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getReqQty() {
        return reqQty;
    }

    public void setReqQty(BigDecimal reqQty) {
        this.reqQty = reqQty;
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

    public BigDecimal getApprQty() {
        return apprQty;
    }

    public void setApprQty(BigDecimal apprQty) {
        this.apprQty = apprQty;
    }

    public MaterialTransferReqApprEntity getMaterialTransferReqApprEntity() {
        return materialTransferReqApprEntity;
    }

    public void setMaterialTransferReqApprEntity(MaterialTransferReqApprEntity materialTransferReqApprEntity) {
        this.materialTransferReqApprEntity = materialTransferReqApprEntity;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public MaterialProjDtlEntity getMaterialId() {
        return materialId;
    }

    public void setMaterialId(MaterialProjDtlEntity materialId) {
        this.materialId = materialId;
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

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }
}
