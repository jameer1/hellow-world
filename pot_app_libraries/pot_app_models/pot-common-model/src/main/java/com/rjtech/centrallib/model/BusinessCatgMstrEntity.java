package com.rjtech.centrallib.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.rjtech.common.model.ClientRegEntity;

/**
 * The persistent class for the stock_mstr database table.
 * 
 */
@Entity
@Table(name = "comp_bus_category_mstr")
public class BusinessCatgMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BCM_ID")
    private Long id;

    @Column(name = "BCM_DESC")
    private String desc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "BCM_STATUS")
    private Integer status;

    @Column(name = "BCM_CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "BCM_UPDATED_BY")
    private String updatedBy;

    @Column(name = "BCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "BCM_UPDATED_ON")
    private Date updatedOn;

    public BusinessCatgMstrEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}