package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "emp_pay_questionary_mstr")
public class EmpPayQuestionaryMstrEntity implements Serializable {

    private static final long serialVersionUID = -5863060048205000538L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPQM_ID")
    private Long id;

    @Column(name = "EPQM_DESC")
    private String desc;

    @Column(name = "EPQM_TYPE")
    private boolean type;

    @ManyToOne
    @JoinColumn(name = "EPQM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "EPQM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPQM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPQM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPQM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EPQM_UPDATED_ON")
    private Timestamp updatedOn;

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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

}
