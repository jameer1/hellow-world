package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the pre_contracts_emp_cmp_dtl database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_plant_cmp")
public class PreContractsPlantCmpEntity implements Serializable {

    private static final long serialVersionUID = 2458898847561713052L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPC_ID")
    private Long id;

    @Column(name = "CPC_RATE")
    private BigDecimal rate;

    @Column(name = "CPC_QUANTITY")
    private Integer quantity;

    @Column(name = "CPC_STATUS")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CPC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CPC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CPC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CPC_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "CPC_VERSION")
    private Integer version;

    @Column(name = "CPC_COMMENTS")
    private String comments;

    @Column(name = "CPC_RECEIVED_QTY", columnDefinition = "int default 0")
    private Integer receivedQty;

    @Column(name = "CEC_IS_COMPLETE", columnDefinition = "int default 0")
    private boolean isComplete;

    @ManyToOne
    @JoinColumn(name = "CPC_PCP_ID")
    private PreContractsPlantDtlEntity preContractsPlantDtlEntity;

    @ManyToOne
    @JoinColumn(name = "CPC_PCC_ID")
    private PreContractsCmpEntity preContractsCmpEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public PreContractsPlantDtlEntity getPreContractsPlantDtlEntity() {
        return preContractsPlantDtlEntity;
    }

    public void setPreContractsPlantDtlEntity(PreContractsPlantDtlEntity preContractsPlantDtlEntity) {
        this.preContractsPlantDtlEntity = preContractsPlantDtlEntity;
    }

    public PreContractsCmpEntity getPreContractsCmpEntity() {
        return preContractsCmpEntity;
    }

    public void setPreContractsCmpEntity(PreContractsCmpEntity preContractsCmpEntity) {
        this.preContractsCmpEntity = preContractsCmpEntity;
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