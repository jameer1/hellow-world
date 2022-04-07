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
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.copy.ProjSOWItemEntityCopy;

/**
 * The persistent class for the pre_contracts database table.
 * 
 */
@Entity
@Table(name = "sow_invoice_items")
public class SowInvoiceDocketItemEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 726779931065154060L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCID_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SCID_PUR_ID")
    private PurchaseOrderEntity purId;

    @ManyToOne
    @JoinColumn(name = "SCID_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "SCID_SOW_ID")
    private ProjSOWItemEntity sowId;

    @Column(name = "SCID_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "SCID_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SCID_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "SCID_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SCID_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PurchaseOrderEntity getPurId() {
        return purId;
    }

    public void setPurId(PurchaseOrderEntity purId) {
        this.purId = purId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public ProjSOWItemEntity getSowId() {
        return sowId;
    }

    public void setSowId(ProjSOWItemEntity sowId) {
        this.sowId = sowId;
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