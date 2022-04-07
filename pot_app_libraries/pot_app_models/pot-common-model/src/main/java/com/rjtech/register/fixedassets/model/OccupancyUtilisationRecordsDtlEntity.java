package com.rjtech.register.fixedassets.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

@Entity
@Table(name = "occupancy_utilisation_dtl")
@NamedQuery(name = "OccupancyUtilisationRecordsDtlEntity.findAll", query = "SELECT a FROM OccupancyUtilisationRecordsDtlEntity a")
public class OccupancyUtilisationRecordsDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "occupany_utilisation_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date pastYear;

    private String unit;
    private String occupied;
    private String vacant;
    private String underRepair;
    private Long Total;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fda_updated_on")
    private Date updatedOn;

    private Integer status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "subid", insertable = false, updatable = false)
    private SubAssetDtlEntity subAssetDtlEntity;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientRegEntity clientRegMstrEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPastYear() {
        return pastYear;
    }

    public void setPastYear(Date pastYear) {
        this.pastYear = pastYear;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOccupied() {
        return occupied;
    }

    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }

    public String getVacant() {
        return vacant;
    }

    public void setVacant(String vacant) {
        this.vacant = vacant;
    }

    public String getUnderRepair() {
        return underRepair;
    }

    public void setUnderRepair(String underRepair) {
        this.underRepair = underRepair;
    }

    public Long getTotal() {
        return Total;
    }

    public void setTotal(Long total) {
        Total = total;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ClientRegEntity getClientRegMstrEntity() {
        return clientRegMstrEntity;
    }

    public void setClientRegMstrEntity(ClientRegEntity clientRegMstrEntity) {
        this.clientRegMstrEntity = clientRegMstrEntity;
    }

    public SubAssetDtlEntity getSubAssetDtlEntity() {
        return subAssetDtlEntity;
    }

    public void setSubAssetDtlEntity(SubAssetDtlEntity subAssetDtlEntity) {
        this.subAssetDtlEntity = subAssetDtlEntity;
    }

    @PrePersist
    public void onPrePersist() {
        this.setCreatedOn(CommonUtil.getNow());
        this.setCreatedBy(AppUserUtils.getUserName());
        this.setUpdatedOn(CommonUtil.getNow());
        this.setUpdatedBy(AppUserUtils.getUserName());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedOn(CommonUtil.getNow());
        this.setUpdatedBy(AppUserUtils.getUserName());
    }

}
