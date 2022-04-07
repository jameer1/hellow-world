package com.rjtech.projectlib.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the schedule_of_rates database table.
 * 
 */
@Entity
@Table(name = "schedule_of_rates")
public class ProjSORItemEntityCopy1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOR_ID")
    private Long id;

    @Column(name = "SOR_CODE")
    private String code;

    @Column(name = "SOR_NAME")
    private String name;

    @Column(name = "SOR_IS_ITEM")
    private boolean item;

    @Column(name = "SOR_QUANTITY")
    private BigDecimal quantity;

    @Column(name = "SOR_COMMENTS")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOR_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "SOR_MAN_HRS")
    private Long manPowerHrs;

    @Column(name = "SOR_LABOUR_RATE")
    private BigDecimal labourRate;

    @Column(name = "SOR_MATERIAL_RATE")
    private BigDecimal materialRate;

    @Column(name = "SOR_PLANT_RATE")
    private BigDecimal plantRate;

    @Column(name = "SOR_OTHER_RATE")
    private BigDecimal othersRate;

    @ManyToOne
    @JoinColumn(name = "SOR_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "SOR_PARENT_ID")
    private ProjSORItemEntityCopy1 projSORItemEntity;

    @OneToMany(mappedBy = "projSORItemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjSORItemEntityCopy1> childEntities = new ArrayList<ProjSORItemEntityCopy1>();

    @Column(name = "SOR_STATUS")
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
    
    @Column(name= "TOTAL_RATE_IF_NO_SUB_CATEGORY")
    private BigDecimal totalRateIfNoSubCategory;

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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public MeasurmentMstrEntity getMeasurmentMstrEntity() {
        return measurmentMstrEntity;
    }

    public void setMeasurmentMstrEntity(MeasurmentMstrEntity measurmentMstrEntity) {
        this.measurmentMstrEntity = measurmentMstrEntity;
    }

    public Long getManPowerHrs() {
        return manPowerHrs;
    }

    public void setManPowerHrs(Long manPowerHrs) {
        this.manPowerHrs = manPowerHrs;
    }

    public BigDecimal getLabourRate() {
        return labourRate;
    }

    public void setLabourRate(BigDecimal labourRate) {
        this.labourRate = labourRate;
    }

    public BigDecimal getMaterialRate() {
        return materialRate;
    }

    public void setMaterialRate(BigDecimal materialRate) {
        this.materialRate = materialRate;
    }

    public BigDecimal getPlantRate() {
        return plantRate;
    }

    public void setPlantRate(BigDecimal plantRate) {
        this.plantRate = plantRate;
    }

    public BigDecimal getOthersRate() {
        return othersRate;
    }

    public void setOthersRate(BigDecimal othersRate) {
        this.othersRate = othersRate;
    }

    public ProjSORItemEntityCopy1 getProjSORItemEntity() {
        return projSORItemEntity;
    }

    public void setProjSORItemEntity(ProjSORItemEntityCopy1 projSORItemEntity) {
        this.projSORItemEntity = projSORItemEntity;
    }

    public List<ProjSORItemEntityCopy1> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ProjSORItemEntityCopy1> childEntities) {
        this.childEntities = childEntities;
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

	public BigDecimal getTotalRateIfNoSubCategory() {
		return totalRateIfNoSubCategory;
	}

	public void setTotalRateIfNoSubCategory(BigDecimal totalRateIfNoSubCategory) {
		this.totalRateIfNoSubCategory = totalRateIfNoSubCategory;
	}

}
