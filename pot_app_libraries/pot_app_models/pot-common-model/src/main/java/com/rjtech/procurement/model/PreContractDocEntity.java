package com.rjtech.procurement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;

/**
 * The persistent class for the pre_contract_documents database table.
 * 
 */
@Entity
@Table(name = "pre_contract_documents")
public class PreContractDocEntity implements Serializable {

    private static final long serialVersionUID = 4058405634746232984L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PDOC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDOC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDOC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDOC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PDOC_PRC_ID")
    private PreContractEntity preContractId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PDOC_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PDOC_STATUS")
    private Integer status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity(ProjDocFileEntity projDocFileEntity) {
        this.projDocFileEntity = projDocFileEntity;
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

    public PreContractEntity getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(PreContractEntity preContractId) {
        this.preContractId = preContractId;
    }

}