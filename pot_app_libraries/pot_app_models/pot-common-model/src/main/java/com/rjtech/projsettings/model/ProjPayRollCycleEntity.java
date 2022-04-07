package com.rjtech.projsettings.model;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = " proj_payroll_cycle")
public class ProjPayRollCycleEntity implements Serializable {

    private static final long serialVersionUID = -6869418901239921115L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PYR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PYR_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "PYR_TYPE")
    private String payRollType;

    @Column(name = "PYR_TYPE_VALUE")
    private String payRollTypeValue;

    @Column(name = "PYR_PAY_ROLL")
    private String payRoll;

    @Column(name = "PYR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PYR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PYR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PYR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PYR_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "PYR_PCD_ID")
    private ProcureCatgDtlEntity procureCatgDtlEntity;

    @Column(name = "EMP_CATEGORY")
    private String empCatg;

    public ProjPayRollCycleEntity() {

    }

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

    public String getPayRollType() {
        return payRollType;
    }

    public void setPayRollType(String payRollType) {
        this.payRollType = payRollType;
    }

    public String getPayRollTypeValue() {
        return payRollTypeValue;
    }

    public void setPayRollTypeValue(String payRollTypeValue) {
        this.payRollTypeValue = payRollTypeValue;
    }

    public String getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(String payRoll) {
        this.payRoll = payRoll;
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

    public ProcureCatgDtlEntity getProcureCatgDtlEntity() {
        return procureCatgDtlEntity;
    }

    public void setProcureCatgDtlEntity(ProcureCatgDtlEntity procureCatgDtlEntity) {
        this.procureCatgDtlEntity = procureCatgDtlEntity;
    }

    public String getEmpCatg() {
        return empCatg;
    }

    public void setEmpCatg(String empCatg) {
        this.empCatg = empCatg;
    }

}
