package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.eps.model.ProjMstrEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOWItemEntity;

// TODO remove this, if not required

@Entity
@Table(name = "project_progress_measure_dtl")
public class ProjProgressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PJMD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PJMD_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJMD_FROM_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJMD_TO_DATE")
    private Date finishDate;

    @Column(name = "PJMD_ORIGINAL_QTY")
    private BigDecimal originalQuantity;

    @Column(name = "PJMD_REVISED_QTY")
    private BigDecimal revicedquantity;

    @Column(name = "PJMD_STATUS")
    private Integer status;

    @Column(name = "PJMD_CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJMD_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "PJMD_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJMD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PJMD_SOW_ID", referencedColumnName = "SOW_ID")
    private ProjSOWItemEntity projSOWItemEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public BigDecimal getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public BigDecimal getRevicedquantity() {
        return revicedquantity;
    }

    public void setRevicedquantity(BigDecimal revicedquantity) {
        this.revicedquantity = revicedquantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ProjSOWItemEntity getProjSOWItemEntity() {
        return projSOWItemEntity;
    }

    public void setProjSOWItemEntity(ProjSOWItemEntity projSOWItemEntity) {
        this.projSOWItemEntity = projSOWItemEntity;
    }

}
