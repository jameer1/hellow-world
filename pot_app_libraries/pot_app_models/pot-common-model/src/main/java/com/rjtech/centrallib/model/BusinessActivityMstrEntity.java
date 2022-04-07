package com.rjtech.centrallib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

/**
 * The persistent class for the stock_mstr database table.
 * 
 */

// Removed this table as part of DB cleanup

//@Entity
//@Table(name = "comp_bus_activity_mstr")
//@NamedQuery(name = "BusinessActivityMstrEntity.findAll", query = "SELECT s FROM BusinessActivityMstrEntity s")
public class BusinessActivityMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BAM_ID")*/
    private Long id;

    /*@Column(name = "BAM_CODE")*/
    private String code;

    /*@Column(name = "BAM_NAME")*/
    private String name;

    // TODO map to ClientRegEntity(ManyToOne)
    /*@Column(name = "BAM_CRM_ID",updatable = false)*/
    private Long clientId;

    // TODO make it enum, based on requirement and functionality
    /*@Column(name = "BAM_STATUS")*/
    private Integer status;

    // TODO map to UserMstrEntity(ManyToOne)
    /*@Column(name = "BAM_CREATED_BY",updatable = false)*/
    private String createdBy;

    // TODO map to UserMstrEntity(ManyToOne)
    /*@Column(name = "BAM_UPDATED_BY")*/
    private String updatedBy;

    /*@Column(name = "BAM_CREATED_ON",updatable = false)*/
    private Date createdOn;

    /*@Column(name = "BAM_UPDATED_ON")*/
    private Date updatedOn;

    public BusinessActivityMstrEntity() {
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
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

    @PrePersist
    public void onPrePersist() {
        this.setCreatedOn(CommonUtil.getNow());
        this.setCreatedBy(AppUserUtils.getUserName());
        this.setUpdatedOn(CommonUtil.getNow());
        this.setUpdatedBy(AppUserUtils.getUserName());
        this.setClientId(AppUserUtils.getClientId());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedOn(CommonUtil.getNow());
        this.setUpdatedBy(AppUserUtils.getUserName());
    }

}