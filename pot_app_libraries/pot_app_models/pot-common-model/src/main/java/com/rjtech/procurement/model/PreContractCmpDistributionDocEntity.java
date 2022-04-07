package com.rjtech.procurement.model;

import java.io.Serializable;
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

/**
 * The persistent class for the pre_contract_cmp_distribution_doc database
 * table.
 * 
 */
@Entity
@Table(name = "pre_contract_cmp_distribution_doc")
public class PreContractCmpDistributionDocEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCDD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PCDD_PDD_ID")
    private PreContractDistributionDocEntity preContractDistributionDocEntity;

    @ManyToOne
    @JoinColumn(name = "PCDD_PCC_ID")
    private PreContractsCmpEntity preContractCmpId;

    @Column(name = "PCDD_IS_TRANSMIT")
    private boolean transmit;

    @Column(name = "PCDD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PCDD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCDD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PCDD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCDD_UPDATED_ON")
    private Date updatedOn;

    public PreContractCmpDistributionDocEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PreContractDistributionDocEntity getPreContractDistributionDocEntity() {
        return preContractDistributionDocEntity;
    }

    public void setPreContractDistributionDocEntity(PreContractDistributionDocEntity preContractDistributionDocEntity) {
        this.preContractDistributionDocEntity = preContractDistributionDocEntity;
    }

    public boolean isTransmit() {
        return transmit;
    }

    public void setTransmit(boolean transmit) {
        this.transmit = transmit;
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

    public PreContractsCmpEntity getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(PreContractsCmpEntity preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
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