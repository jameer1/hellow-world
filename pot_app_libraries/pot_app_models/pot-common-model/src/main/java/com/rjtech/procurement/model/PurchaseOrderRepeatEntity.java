package com.rjtech.procurement.model;

import com.rjtech.common.model.UserMstrEntity;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "procurement_po_repeatpo")
public class PurchaseOrderRepeatEntity implements Serializable {

    private static final long serialVersionUID = 22L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPR_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ppr_parent_po_id")
    private Long parentPOId;

    @Column(name = "PPR_REPEAT_PO_ID")
    private Long repeatPOId;

    @Column(name = "PPR_REPEAT_PO_STATUS")
    private String repeatPOStatus;

    @Column(name = "PPR_EPM_ID")
    private Long projId;

    @Column(name = "ppr_delivery_place")
    private String deliveryPlace;

    @Column(name = "ppr_qty")
    private Long quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ppr_start_date", updatable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ppr_finish_date", updatable = false)
    private Date finishDate;

    @Column(name = "ppr_bid")
    private Integer isBid;

    @Column(name = "PPR_APPROVED_BY")
    private Long approvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_created_by", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ppr_created_on", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ppr_updated_by")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ppr_updated_on")
    private Date updatedOn;

    /*alter table procurement_po_repeatpo add (ppr_PRC_ID number(19));  -- PreContractEntity --> pre_contracts --> PRC_ID
    alter table procurement_po_repeatpo add (PPR_PCC_ID number(19)); -- PreContractsCmpEntity --> pre_contracts_cmp --> PCC_ID
    alter table procurement_po_repeatpo add (PPR_ITEM_ID number(19)); -- PreContractsMaterialCmpEntity --> pre_contracts_mat_cmp --> CMC_ID*/

    @Column(name = "PPR_PRC_ID")
    private Long contractId;

    @Column(name = "PPR_PCC_ID")
    private Long contractCmpId;

    @Column(name = "PPR_ITEM_ID")
    private Long contractItemId;

    @Column(name = "PPR_ITEM_DETAIL_ID")
    private Long contractItemDetailId; // fro Material,Service Etc..

    @Column(name = "PPR_ITEM_TYPE")
    private String contractItemType;

    @Column(name = "PPR_ITEM_RATE")
    private BigDecimal contractItemRate;

    public Long getContractItemDetailId() {
        return contractItemDetailId;
    }

    public void setContractItemDetailId(Long contractItemDetailId) {
        this.contractItemDetailId = contractItemDetailId;
    }

    public String getContractItemType() {
        return contractItemType;
    }

    public void setContractItemType(String contractItemType) {
        this.contractItemType = contractItemType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getIsBid() {
        return isBid;
    }

    public void setIsBid(Integer isBid) {
        this.isBid = isBid;
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

    public Long getParentPOId() {
        return parentPOId;
    }

    public void setParentPOId(Long parentPOId) {
        this.parentPOId = parentPOId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getContractCmpId() {
        return contractCmpId;
    }

    public void setContractCmpId(Long contractCmpId) {
        this.contractCmpId = contractCmpId;
    }

    public Long getContractItemId() {
        return contractItemId;
    }

    public void setContractItemId(Long contractItemId) {
        this.contractItemId = contractItemId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public BigDecimal getContractItemRate() {
        return contractItemRate;
    }

    public void setContractItemRate(BigDecimal contractItemRate) {
        this.contractItemRate = contractItemRate;
    }

    public Long getRepeatPOId() {
        return repeatPOId;
    }

    public void setRepeatPOId(Long repeatPOId) {
        this.repeatPOId = repeatPOId;
    }

    public String getRepeatPOStatus() {
        return repeatPOStatus;
    }

    public void setRepeatPOStatus(String repeatPOStatus) {
        this.repeatPOStatus = repeatPOStatus;
    }
}