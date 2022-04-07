package com.rjtech.projsettings.model;

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

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.common.model.UserMstrEntity;
//import com.rjtech.projsettings.model.ProjCostStmtDtlEntityCopy;

/**
 * The persistent class for the pre_contracts_emp_dtl database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_emp_dtl")
public class PreContractsEmpDtlEntity implements Serializable {

    private static final long serialVersionUID = -7052848185534548863L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCE_ID")
    private Long id;

    @Column(name = "PCE_ITM_DESC", length = 400)
    private String desc;

    @Column(name = "PCE_LATEST")
    private Boolean latest;

    @Column(name = "PCE_ESTIMATE_COST")
    private BigDecimal estimateCost;

    @Column(name = "PCE_QTY")
    private Integer quantity;

    @Temporal(TemporalType.DATE)
    @Column(name = "PCE_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCE_FINISH_DATE")
    private Date finishDate;

    @Column(name = "PCE_DELIVERY_PLACE")
    private String deliveryPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCE_PJCS_ID")
    private ProjCostStmtDtlEntity costStatement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCE_PCD_ID")
    private ProcureCatgDtlEntity procureSubCatgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCE_ECM_ID")
    private EmpClassMstrEntity projEmpClassId;

    @Column(name = "PCE_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCE_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCE_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCE_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCE_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "contractsEmpDtlEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities = new ArrayList<PreContractsEmpCmpEntity>();

    @ManyToOne
    @JoinColumn(name = "PCE_PRC_ID")
    private PreContractEntity preContractEntity;

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

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
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

    public List<PreContractsEmpCmpEntity> getPreContractsEmpCmpEntities() {
        return preContractsEmpCmpEntities;
    }

    public void setPreContractsEmpCmpEntities(List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities) {
        this.preContractsEmpCmpEntities = preContractsEmpCmpEntities;
    }

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public ProcureCatgDtlEntity getProcureSubCatgId() {
        return procureSubCatgId;
    }

    public void setProcureSubCatgId(ProcureCatgDtlEntity procureSubCatgId) {
        this.procureSubCatgId = procureSubCatgId;
    }

    public EmpClassMstrEntity getProjEmpClassId() {
        return projEmpClassId;
    }

    public void setProjEmpClassId(EmpClassMstrEntity projEmpClassId) {
        this.projEmpClassId = projEmpClassId;
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

    public ProjCostStmtDtlEntity getCostStatement() {
        return costStatement;
    }

    public void setCostStatement(ProjCostStmtDtlEntity costStatement) {
        this.costStatement = costStatement;
    }

}