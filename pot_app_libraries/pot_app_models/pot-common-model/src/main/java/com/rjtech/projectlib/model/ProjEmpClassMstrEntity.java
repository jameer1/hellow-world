package com.rjtech.projectlib.model;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

/**
 * The persistent class for the proj_emp_classsfication_mstr database table.
 * 
 */

@Entity
@Table(name = "proj_emp_classsfication_mstr")
public class ProjEmpClassMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PEM_ECM_ID")
    private EmpClassMstrEntity empClassMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEM_EPM_ID")
    private ProjMstrEntity projectId;

    @Column(name = "PEM_EMPLOYEE_CATEGORY")
    private String projEmpCategory;

    @Column(name = "PEM_TRADE_CONTR_NAME")
    private String tradeContrName;

    @Column(name = "PEM_UNION_WORKER_NAME")
    private String unionName;

    @Column(name = "PEM_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEM_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpClassMstrEntity getEmpClassMstrEntity() {
        return empClassMstrEntity;
    }

    public void setEmpClassMstrEntity(EmpClassMstrEntity empClassMstrEntity) {
        this.empClassMstrEntity = empClassMstrEntity;
    }

    public String getProjEmpCategory() {
        return projEmpCategory;
    }

    public void setProjEmpCategory(String projEmpCategory) {
        this.projEmpCategory = projEmpCategory;
    }

    public String getTradeContrName() {
        return tradeContrName;
    }

    public void setTradeContrName(String tradeContrName) {
        this.tradeContrName = tradeContrName;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
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

    public ProjMstrEntity getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjMstrEntity projectId) {
        this.projectId = projectId;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

}