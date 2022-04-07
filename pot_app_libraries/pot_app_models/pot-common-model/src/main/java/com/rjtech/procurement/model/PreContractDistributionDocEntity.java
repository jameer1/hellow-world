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

/**
 * The persistent class for the pre_contract_distribution_doc database table.
 * 
 */
@Entity
@Table(name = "pre_contract_distribution_doc")
public class PreContractDistributionDocEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDD_ID")
    private Long id;

    @Column(name = "PDD_NAME")
    private String name;

    @Column(name = "PDD_CODE")
    private String code;

    @Column(name = "PDD_VERSION")
    private Integer version;

    @Column(name = "PDD_TYPE")
    private String mimeType;

    @Column(name = "PDD_MODE_TYPE")
    private String modeType;

    @Column(name = "PDD_SEND_STATUS")
    private boolean distributionStatus;

    @Column(name = "PDD_STATUS")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDD_DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "PDD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PDD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @ManyToOne
    @JoinColumn(name = "PDD_PRC_ID")
    private PreContractEntity preContractId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDD_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "preContractDistributionDocEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<PreContractCmpDistributionDocEntity> preContractCmpDistributionDocEntities = new ArrayList<PreContractCmpDistributionDocEntity>();

    public PreContractDistributionDocEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public boolean isDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(boolean distributionStatus) {
        this.distributionStatus = distributionStatus;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public PreContractEntity getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(PreContractEntity preContractId) {
        this.preContractId = preContractId;
    }

    public List<PreContractCmpDistributionDocEntity> getPreContractCmpDistributionDocEntities() {
        return preContractCmpDistributionDocEntities;
    }

    public void setPreContractCmpDistributionDocEntities(
            List<PreContractCmpDistributionDocEntity> preContractCmpDistributionDocEntities) {
        this.preContractCmpDistributionDocEntities = preContractCmpDistributionDocEntities;
    }

}