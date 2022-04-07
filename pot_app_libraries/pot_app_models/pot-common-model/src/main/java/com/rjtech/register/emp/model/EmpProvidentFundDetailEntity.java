package com.rjtech.register.emp.model;

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
@Table(name = "emp_provident_fund_dtl")
public class EmpProvidentFundDetailEntity implements Serializable {

    private static final long serialVersionUID = -6711592227267110617L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPFM_ID")
    private Long id;

    @Column(name = "EPFM_DESC")
    private String desc;

    @Column(name = "EPFM_COMMENTS")
    private String comments;

    @Column(name = "EPFM_DETAILS")
    private String details;

    @Column(name = "EPFM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPFM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPFM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPFM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPFM_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "EPFM_EPFD_ID")
    private EmpProvidentFundEntity empProvidentFundEntity;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public EmpProvidentFundEntity getEmpProvidentFundEntity() {
        return empProvidentFundEntity;
    }

    public void setEmpProvidentFundEntity(EmpProvidentFundEntity empProvidentFundEntity) {
        this.empProvidentFundEntity = empProvidentFundEntity;
    }

}
