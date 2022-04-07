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

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "finance_regular_allowances")
public class RegularPayAllowanceEntity implements Serializable {

    private static final long serialVersionUID = 2186393360061824184L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRA_ID")
    private Long id;

    @Column(name = "FRA_CODE")
    private String code;

    @Column(name = "FRA_NAME")
    private String name;

    @Column(name = "FRA_TAX_APPLICABLE")
    private String taxStatus;

    @Column(name = "FRA_COMMENTS")
    private String comments;

    @Column(name = "FRA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FRA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "FRA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FRA_CREATED_ON", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FRA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "FRA_FTCT_ID")
    private CodeTypesEntity codeTypesEntity;

    public RegularPayAllowanceEntity() {

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

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public CodeTypesEntity getCodeTypesEntity() {
        return codeTypesEntity;
    }

    public void setCodeTypesEntity(CodeTypesEntity codeTypesEntity) {
        this.codeTypesEntity = codeTypesEntity;
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
