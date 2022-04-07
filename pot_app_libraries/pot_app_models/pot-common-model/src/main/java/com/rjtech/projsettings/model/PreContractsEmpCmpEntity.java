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
 * The persistent class for the pre_contracts_emp_cmp database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_emp_cmp")
public class PreContractsEmpCmpEntity implements Serializable {

    private static final long serialVersionUID = 1337977295280027434L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CEC_ID")
    private Long id;

    @Column(name = "CEC_RATE")
    private BigDecimal rate;

    @Column(name = "CEC_QUANTITY")
    private Integer quantity;

    @Column(name = "CEC_STATUS")
    private Integer status;

    @Column(name = "CEC_RECEIVED_QTY")
    private Integer receivedQty;

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

    @Column(name = "CEC_IS_COMPLETE", columnDefinition = "int default 0")
    private boolean isComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CEC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CEC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CEC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CEC_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "CEC_VERSION")
    private Integer version;

    @Column(name = "CEC_COMMENTS")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "CEC_PCE_ID")
    private PreContractsEmpDtlEntity contractsEmpDtlEntity;

    @ManyToOne
    @JoinColumn(name = "CEC_PCC_ID")
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PreContractsEmpDtlEntity getContractsEmpDtlEntity() {
        return contractsEmpDtlEntity;
    }

    public void setContractsEmpDtlEntity(PreContractsEmpDtlEntity contractsEmpDtlEntity) {
        this.contractsEmpDtlEntity = contractsEmpDtlEntity;
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