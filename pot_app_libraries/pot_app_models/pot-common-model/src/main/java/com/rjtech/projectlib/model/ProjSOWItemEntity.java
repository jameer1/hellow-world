package com.rjtech.projectlib.model;

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
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.centrallib.model.TangibleClassificationEntity;

/**
 * The persistent class for the scope_of_work database table.
 * 
 */
@Entity
@Table(name = "scope_of_work")
public class ProjSOWItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOW_ID")
    private Long id;

    @Column(name = "SOW_CODE")
    private String code;

    @Column(name = "SOW_NAME")
    private String name;

    @Column(name = "SOW_IS_ITEM")
    private boolean item;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOW_START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOW_FINISH_DATE")
    private Date finishDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOW_ACTUAL_FINISH_DATE")
    private Date actualFinishDate;

    @Column(name = "SOW_ACTUAL_QTY")
    private BigDecimal actualQty;

    @Column(name = "SOW_REVISED_ESD_QTY")
    private BigDecimal revisedQty;

    @Column(name = "SOW_ORIGINAL_ESD_QTY")
    private BigDecimal originalQty;

    @Column(name = "SOW_COMMENTS")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOW_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @ManyToOne
    @JoinColumn(name = "SOW_SOE_ID")
    private ProjSOEItemEntity projSOEItemEntity;

    @ManyToOne
    @JoinColumn(name = "SOW_SOR_ID")
    private ProjSORItemEntity projSORItemEntity;
    
    @ManyToOne
    @JoinColumn(name = "SOW_TANGIBLE_ITEM_ID")
    private TangibleClassificationEntity tangibleClassificationEntity;

    @ManyToOne
    @JoinColumn(name = "SOW_CCS_ID")
    private ProjCostItemEntity projCostItemEntity;

    @Column(name = "SOW_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOE_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOE_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOE_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOE_UPDATED_ON")
    private Date updatedOn;
    
    public Date getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Date actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public ProjSOEItemEntity getProjSOEItemEntity() {
        return projSOEItemEntity;
    }

    public void setProjSOEItemEntity(ProjSOEItemEntity projSOEItemEntity) {
        this.projSOEItemEntity = projSOEItemEntity;
    }

    public ProjSORItemEntity getProjSORItemEntity() {
        return projSORItemEntity;
    }

    public void setProjSORItemEntity(ProjSORItemEntity projSORItemEntity) {
        this.projSORItemEntity = projSORItemEntity;
    }
    
    public TangibleClassificationEntity getTangibleClassificationEntity() {
    	return tangibleClassificationEntity;
    }
    
    public void setTangibleClassificationEntity(TangibleClassificationEntity tangibleClassificationEntity) {
    	this.tangibleClassificationEntity = tangibleClassificationEntity;
    }

    public ProjCostItemEntity getProjCostItemEntity() {
        return projCostItemEntity;
    }

    public void setProjCostItemEntity(ProjCostItemEntity projCostItemEntity) {
        this.projCostItemEntity = projCostItemEntity;
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

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(BigDecimal revisedQty) {
        this.revisedQty = revisedQty;
    }

    public BigDecimal getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(BigDecimal originalQty) {
        this.originalQty = originalQty;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
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

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
    public String toString() {
    	return "id:"+id+" SOW_SOE_ID:"+projSOEItemEntity.getId();
    }
}
