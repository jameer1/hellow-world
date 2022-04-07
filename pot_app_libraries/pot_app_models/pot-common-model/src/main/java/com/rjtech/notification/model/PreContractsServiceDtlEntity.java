package com.rjtech.notification.model;

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

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.ServiceMstrEntity;
import com.rjtech.common.model.UserMstrEntity;

import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;

/**
 * The persistent class for the pre_contracts_emp_dtl database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_service_dtl")
public class PreContractsServiceDtlEntity implements Serializable {

    private static final long serialVersionUID = -7473201596521478346L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCS_PJCS_ID")
    private ProjCostStmtDtlEntity projcostStatement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCS_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PCS_ITM_DESC", length = 400)
    private String desc;

    @Column(name = "PCS_LATEST")
    private Boolean latest;

    @Column(name = "PCS_ESTIMATE_COST")
    private BigDecimal estimateCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCS_SCM_ID")
    private ServiceMstrEntity serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCS_PCD_ID")
    private ProcureCatgDtlEntity procureSubCatgId;

    @Column(name = "PCS_QTY")
    private Integer quantity;

    @Column(name = "PCS_DELIVERY_PLACE")
    private String deliveryPlace;

    @Temporal(TemporalType.DATE)
    @Column(name = "PCS_START_DATE")
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "PCS_PRC_ID")
    private PreContractEntity preContractEntity;

    @Column(name = "PCS_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCS_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "preContractsServiceDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities = new ArrayList<PreContractsServiceCmpEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(BigDecimal estimateCost) {
        this.estimateCost = estimateCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public List<PreContractsServiceCmpEntity> getPreContractsServiceCmpEntities() {
        return preContractsServiceCmpEntities;
    }

    public void setPreContractsServiceCmpEntities(List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities) {
        this.preContractsServiceCmpEntities = preContractsServiceCmpEntities;
    }

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
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

    public ProjCostStmtDtlEntity getProjcostStatement() {
        return projcostStatement;
    }

    public void setProjcostStatement(ProjCostStmtDtlEntity projcostStatement) {
        this.projcostStatement = projcostStatement;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public ServiceMstrEntity getServiceId() {
        return serviceId;
    }

    public void setServiceId(ServiceMstrEntity serviceId) {
        this.serviceId = serviceId;
    }

    public ProcureCatgDtlEntity getProcureSubCatgId() {
        return procureSubCatgId;
    }

    public void setProcureSubCatgId(ProcureCatgDtlEntity procureSubCatgId) {
        this.procureSubCatgId = procureSubCatgId;
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

}