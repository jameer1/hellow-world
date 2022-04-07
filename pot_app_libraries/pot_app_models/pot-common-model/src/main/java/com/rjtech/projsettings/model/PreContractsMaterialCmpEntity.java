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
@Table(name = "pre_contracts_mat_cmp")
public class PreContractsMaterialCmpEntity implements Serializable {

    private static final long serialVersionUID = 6525251766466351606L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMC_ID")
    private Long id;

    @Column(name = "CMC_RATE")
    private BigDecimal rate;

    @Column(name = "CMC_QUANTITY")
    private Integer quantity;

    @Column(name = "CMC_VERSION")
    private Integer version;

    @Column(name = "CMC_COMMENTS")
    private String comments;

    @Column(name = "CMC_STATUS")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CMC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CMC_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "CMC_PCMD_ID")
    private PreContractsMaterialDtlEntity preContractsMaterialDtlEntity;

    @ManyToOne
    @JoinColumn(name = "CMC_PCC_ID")
    private PreContractsCmpEntity preContractsCmpEntity;

    @Column(name = "CMC_RECEIVED_QTY")
    private Long recievedQty;
    
    @Column(name = "CMC_IS_COMPLETE", columnDefinition = "int default 0")
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public PreContractsMaterialDtlEntity getPreContractsMaterialDtlEntity() {
        return preContractsMaterialDtlEntity;
    }

    public void setPreContractsMaterialDtlEntity(PreContractsMaterialDtlEntity preContractsMaterialDtlEntity) {
        this.preContractsMaterialDtlEntity = preContractsMaterialDtlEntity;
    }

    public PreContractsCmpEntity getPreContractsCmpEntity() {
        return preContractsCmpEntity;
    }

    public void setPreContractsCmpEntity(PreContractsCmpEntity preContractsCmpEntity) {
        this.preContractsCmpEntity = preContractsCmpEntity;
    }

    public Long getRecievedQty() {
        return recievedQty;
    }

    public void setRecievedQty(Long recievedQty) {
        this.recievedQty = recievedQty;
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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}