package com.rjtech.notification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "emp_leave_req_appr_dtl")
public class EmpLeaveReqApprDetailEntity implements Serializable {

    private static final long serialVersionUID = 5062424744624375145L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELRAD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ELRAD_ELRA_ID")
    private EmpLeaveReqApprEntity empLeaveReqApprEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRAD_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRAD_TO_DATE")
    private Date toDate;

    @Column(name = "ELRAD_TOTAL_DAYS")
    private Integer totalDays;

    @Column(name = "ELRAD_COMMENTS")
    private String comments;

    @Column(name = "ELRAD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ELRAD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRAD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ELRAD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ELRAD_UPDATED_ON")
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
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
