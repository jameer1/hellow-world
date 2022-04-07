package com.rjtech.procurement.model;

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
 * The persistent class for the pre_contracts_ser_cmp database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_ser_cmp")
public class PreContractsServiceCmpEntity implements Serializable {

    private static final long serialVersionUID = 832399553107514118L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSC_ID")
    private Long id;

    @Column(name = "CSC_RATE")
    private BigDecimal rate;

    @Column(name = "CSC_QUANTITY")
    private Integer quantity;

    @Column(name = "CSC_STATUS")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CSC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CSC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CSC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CSC_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "CSC_VERSION")
    private Integer version;

    @Column(name = "CSC_COMMENTS")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "CSC_PCS_ID")
    private PreContractsServiceDtlEntity preContractsServiceDtlEntity;

    @ManyToOne
    @JoinColumn(name = "CSC_PCC_ID")
    private PreContractsCmpEntity preContractsCmpEntity;

    @Column(name = "CSC_RECEIVED_QUANTITY")
    private Integer receivedQuantity;

    @Column(name = "CSC_IS_COMPLETE", columnDefinition = "int default 0")
    private boolean complete = false;

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

    public PreContractsServiceDtlEntity getPreContractsServiceDtlEntity() {
        return preContractsServiceDtlEntity;
    }

    public void setPreContractsServiceDtlEntity(PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        this.preContractsServiceDtlEntity = preContractsServiceDtlEntity;
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

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}