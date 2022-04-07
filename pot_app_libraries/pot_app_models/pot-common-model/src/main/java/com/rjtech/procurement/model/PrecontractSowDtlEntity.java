package com.rjtech.procurement.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.copy.ProjSOWItemEntityCopy;

@Entity
@Table(name = "precontract_sow_dtl")
public class PrecontractSowDtlEntity implements Serializable {

    private static final long serialVersionUID = 121989906325451899L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCSW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCSW_SOW_ID")
    private ProjSOWItemEntity sowId;

    @Column(name = "PCSW_ITM_DESC", length = 400)
    private String itemDesc;

    @Column(name = "PCSW_DELIVERY_PLACE")
    private String deliveryPlace;

    @Temporal(TemporalType.DATE)
    @Column(name = "PCSW_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCSW_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PCSW_QTY")
    private Integer quantity;

    @Column(name = "PCSW_LATEST")
    private Boolean latest;

    @Column(name = "PCSW_ESTIMATE_COST")
    private BigDecimal estimateCost;

    @Column(name = "PCSW_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCSW_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCSW_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCSW_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCSW_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "precontractSowDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrecontractSowCmpEntity> precontractSowCmpEntites = new ArrayList<>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    @ManyToOne
    @JoinColumn(name = "PCSW_PRC_ID")
    private PreContractEntity preContractEntity;

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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
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

    public List<PrecontractSowCmpEntity> getPrecontractSowCmpEntites() {
        return precontractSowCmpEntites;
    }

    public void setPrecontractSowCmpEntites(List<PrecontractSowCmpEntity> precontractSowCmpEntites) {
        this.precontractSowCmpEntites = precontractSowCmpEntites;
    }

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
    }

    public ProjSOWItemEntity getSowId() {
        return sowId;
    }

    public void setSowId(ProjSOWItemEntity sowId) {
        this.sowId = sowId;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
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

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

}
