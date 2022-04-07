package com.rjtech.procurement.model;

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

/**
 * The persistent class for the pre_contracts database table.
 * 
 */
@Entity
@Table(name = "pre_contracts")
public class PreContractEntity implements Serializable {

    private static final long serialVersionUID = 2724330218209302939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRC_ID")
    private Long id;

    @Column(name = "PRC_DESC", length = 400)
    private String desc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRC_EPM_ID")
    private ProjMstrEntity projId;

    @Column(name = "PRC_APPR_STATUS")
    private Integer preContarctStatus;

    @Column(name = "PRC_PRE_CONTRACT_STATUS")
    private String contarctStageStatus;

    @Column(name = "PRC_PO_STATUS")
    private String purchaseOrderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRC_REQ_USER_ID")
    private UserMstrEntity reqUserId;

    @Column(name = "PRC_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRC_UPDATED_ON")
    private Date updatedOn;

    // TODO: Remove this column, this is no more useful
    @Column(name = "PRC_IS_LATEST")
    private Boolean isLatest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRC_CLOSE_DATE")
    private Date closeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRC_REVISED_CLOSE_DATE")
    private Date revisedCloseDate;

    @Column(name = "ALLOW_MULTIPLE_POS", columnDefinition = "int default 0")
    private boolean allowMultiplePurchaseOrders;

    @Column(name = "PRC_CUR_ID")
    private String currencyCode;

    @Column(name = "PRC_TYPE")
    private String preContractType;

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsCmpEntity> preContractsCmpEntities = new ArrayList<>();

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsPlantDtlEntity> preContractsPlantDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrecontractSowDtlEntity> precontractSowDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "preContractEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractReqApprEntity> preContractReqApprEntities = new ArrayList<>();

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

    public Integer getPreContarctStatus() {
        return preContarctStatus;
    }

    public void setPreContarctStatus(Integer preContarctStatus) {
        this.preContarctStatus = preContarctStatus;
    }

    public String getContarctStageStatus() {
        return contarctStageStatus;
    }

    public void setContarctStageStatus(String contarctStageStatus) {
        this.contarctStageStatus = contarctStageStatus;
    }

    public String getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(String purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
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

    public List<PreContractsCmpEntity> getPreContractsCmpEntities() {
        return preContractsCmpEntities;
    }

    public void setPreContractsCmpEntities(List<PreContractsCmpEntity> preContractsCmpEntities) {
        this.preContractsCmpEntities = preContractsCmpEntities;
    }

    public List<PreContractsEmpDtlEntity> getPreContractsEmpDtlEntities() {
        return preContractsEmpDtlEntities;
    }

    public void setPreContractsEmpDtlEntities(List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities) {
        this.preContractsEmpDtlEntities = preContractsEmpDtlEntities;
    }

    public List<PreContractsPlantDtlEntity> getPreContractsPlantDtlEntities() {
        return preContractsPlantDtlEntities;
    }

    public void setPreContractsPlantDtlEntities(List<PreContractsPlantDtlEntity> preContractsPlantDtlEntities) {
        this.preContractsPlantDtlEntities = preContractsPlantDtlEntities;
    }

    public List<PreContractsMaterialDtlEntity> getPreContractsMaterialDtlEntities() {
        return preContractsMaterialDtlEntities;
    }

    public void setPreContractsMaterialDtlEntities(
            List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities) {
        this.preContractsMaterialDtlEntities = preContractsMaterialDtlEntities;
    }

    public List<PreContractsServiceDtlEntity> getPreContractsServiceDtlEntities() {
        return preContractsServiceDtlEntities;
    }

    public void setPreContractsServiceDtlEntities(List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities) {
        this.preContractsServiceDtlEntities = preContractsServiceDtlEntities;
    }

    public List<PrecontractSowDtlEntity> getPrecontractSowDtlEntities() {
        return precontractSowDtlEntities;
    }

    public void setPrecontractSowDtlEntities(List<PrecontractSowDtlEntity> precontractSowDtlEntities) {
        this.precontractSowDtlEntities = precontractSowDtlEntities;
    }

    public List<PreContractReqApprEntity> getPreContractReqApprEntities() {
        return preContractReqApprEntities;
    }

    public void setPreContractReqApprEntities(List<PreContractReqApprEntity> preContractReqApprEntities) {
        this.preContractReqApprEntities = preContractReqApprEntities;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getRevisedCloseDate() {
        return revisedCloseDate;
    }

    public void setRevisedCloseDate(Date revisedCloseDate) {
        this.revisedCloseDate = revisedCloseDate;
    }

    public boolean isAllowMultiplePurchaseOrders() {
        return allowMultiplePurchaseOrders;
    }

    public void setAllowMultiplePurchaseOrders(boolean allowMultiplePurchaseOrders) {
        this.allowMultiplePurchaseOrders = allowMultiplePurchaseOrders;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public UserMstrEntity getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(UserMstrEntity reqUserId) {
        this.reqUserId = reqUserId;
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

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(Boolean isLatest) {
        this.isLatest = isLatest;
    }

    public String getPreContractType() {
        return preContractType;
    }

    public void setPreContractType(String precontractType) {
        this.preContractType = precontractType;
    }

}
