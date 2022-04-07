package com.rjtech.procurement.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.rjtech.procurement.model.MaterialProjDtlEntityCopy;

@Entity
@Table(name = "material_po_delivery_docket")
public class MaterialPODeliveryDocketEntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MADD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MADD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MADD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MADD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "MADD_UPDATED_ON")
    private Timestamp updatedOn;
    
    @ManyToOne
    @JoinColumn(name = "MADD_MAP_ID")
    private MaterialProjDtlEntityCopy materialProjDtlEntityCopy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MADD_SUPPLY_DELIVERY_DATE")
    private Date supplyDeliveryDate;

    @Column(name = "MADD_DOCT_NUM")
    private String docketNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MADD_DOCT_DATE")
    private Date docketDate;
    
    @Column(name = "MADD_RECEIVED_QTY")
    private BigDecimal receivedQty;
    
    @Column(name = "MADD_COMMENTS")
    private String comments;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    public MaterialProjDtlEntityCopy getMaterialProjDtlEntityCopy(){
    	return materialProjDtlEntityCopy;
    }
    public MaterialProjDtlEntityCopy setMaterialProjDtlEntityCopy(MaterialProjDtlEntityCopy materialProjDtlEntityCopy){
    	return materialProjDtlEntityCopy;
    }
    
    public Date getSupplyDeliveryDate() {
        return supplyDeliveryDate;
    }

    public void setSupplyDeliveryDate(Date supplyDeliveryDate) {
        this.supplyDeliveryDate = supplyDeliveryDate;
    }
    
    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
    }
    
    public Date getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(Date docketDate) {
        this.docketDate = docketDate;
    }
    
    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
