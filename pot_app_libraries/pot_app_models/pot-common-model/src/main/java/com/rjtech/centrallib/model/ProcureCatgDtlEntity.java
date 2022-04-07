package com.rjtech.centrallib.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the stock_mstr database table.
 * 
 */
@Entity
@Table(name = "procurement_catg_dtl")
public class ProcureCatgDtlEntity implements Serializable {

    private static final long serialVersionUID = -8133708296192133547L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PCD_MCM_ID", updatable = false)
    private MaterialClassMstrEntity materialClassMstrEntity;

    @Column(name = "PCD_PROCURE_TYPE")
    private String procureType;

    @Column(name = "PCD_CODE")
    private String code;

    @Column(name = "PCD_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PCD_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "PCD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PCD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "PCD_UPDATED_BY", updatable = false)
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCD_UPDATED_ON")
    private Date updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCD_CREATED_ON", updatable = false)
    private Date createdOn;

    public ProcureCatgDtlEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaterialClassMstrEntity getMaterialClassMstrEntity() {
        return materialClassMstrEntity;
    }

    public void setMaterialClassMstrEntity(MaterialClassMstrEntity materialClassMstrEntity) {
        this.materialClassMstrEntity = materialClassMstrEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

}