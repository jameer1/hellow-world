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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the terms_and_conditions database table.
 * 
 */
@Entity
@Table(name = "terms_and_conditions")
public class TermsAndConditionsEntity implements Serializable {

    private static final long serialVersionUID = -2657695613025354545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "conditions_message")
    private String conditionsText;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TERMS_AND_CONDITIONS_ID_FK")
    private List<TermsAndConditionsFileEntity> termsAndConditionsFiles = new ArrayList<TermsAndConditionsFileEntity>();

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "PRE_CONTRACT_ID_FK")
    private PreContractEntity preContractEntity;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "PO_ID_FK")
    private PurchaseOrderEntity purchaseOrderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConditionsText() {
        return conditionsText;
    }

    public void setConditionsText(String conditionsText) {
        this.conditionsText = conditionsText;
    }

    public List<TermsAndConditionsFileEntity> getTermsAndConditionsFiles() {
        return termsAndConditionsFiles;
    }

    public void setTermsAndConditionsFiles(List<TermsAndConditionsFileEntity> termsAndConditionsFiles) {
        this.termsAndConditionsFiles = termsAndConditionsFiles;
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

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
    }

    public PurchaseOrderEntity getPurchaseOrderEntity() {
        return purchaseOrderEntity;
    }

    public void setPurchaseOrderEntity(PurchaseOrderEntity purchaseOrderEntity) {
        this.purchaseOrderEntity = purchaseOrderEntity;
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
