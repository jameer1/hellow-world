package com.rjtech.finance.model;

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

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "finance_payroll_cycle_mstr")
public class PayRollEntity implements Serializable {

    private static final long serialVersionUID = -7048542791119494549L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FPCM_ID")
    private Long id;

    @Column(name = "FPCM_PAY_ROLL_CYCLE")
    private String payRollCycle;

    @Column(name = "FPCM_PAY_COUNT")
    private String selectYear;

    @ManyToOne
    @JoinColumn(name = "FPCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "FPCM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FPCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FPCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FPCM_UPDATED_ON")
    private Date updatedOn;

    public PayRollEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayRollCycle() {
        return payRollCycle;
    }

    public void setPayRollCycle(String payRollCycle) {
        this.payRollCycle = payRollCycle;
    }

    public String getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(String selectYear) {
        this.selectYear = selectYear;
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

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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
