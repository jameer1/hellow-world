package com.rjtech.notification.model;

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
@Table(name = "emp_public_holdiays")
public class EmpPublicHolidayEntity implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPH_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EPH_ELRA_ID")
    private EmpLeaveReqApprEntity empLeaveReqApprEntity;

    @Column(name = "EPH_DESC")
    private String desc;

    @Column(name = "EPH_TYPE")
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPH_DATE")
    private Date date;

    @Column(name = "EPH_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPH_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPH_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPH_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPH_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpLeaveReqApprEntity getEmpLeaveReqApprEntity() {
        return empLeaveReqApprEntity;
    }

    public void setEmpLeaveReqApprEntity(EmpLeaveReqApprEntity empLeaveReqApprEntity) {
        this.empLeaveReqApprEntity = empLeaveReqApprEntity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

}
