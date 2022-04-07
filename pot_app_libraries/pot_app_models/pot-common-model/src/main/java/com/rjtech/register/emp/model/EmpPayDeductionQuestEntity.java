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

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "emp_pay_deduction_quest")
public class EmpPayDeductionQuestEntity implements Serializable {

    private static final long serialVersionUID = -5863060048205000538L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPDQ_ID")
    private Long id;

    @Column(name = "EPDQ_ANSWER")
    private String answer;

    @Column(name = "EPDQ_IS_LATEST")
    private String latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPDQ_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPDQ_TO_DATE")
    private Date toDate;

    @Column(name = "EPDQ_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPDQ_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPDQ_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPDQ_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EPDQ_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "EPDQ_EPD_ID")
    private EmpPayDeductionEntity empPayDeductionEntity;

    @ManyToOne
    @JoinColumn(name = "EPDQ_EPQM_ID")
    private EmpPayQuestionaryMstrEntity empPayQuestionaryMstrEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
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

    public EmpPayDeductionEntity getEmpPayDeductionEntity() {
        return empPayDeductionEntity;
    }

    public void setEmpPayDeductionEntity(EmpPayDeductionEntity empPayDeductionEntity) {
        this.empPayDeductionEntity = empPayDeductionEntity;
    }

    public EmpPayQuestionaryMstrEntity getEmpPayQuestionaryMstrEntity() {
        return empPayQuestionaryMstrEntity;
    }

    public void setEmpPayQuestionaryMstrEntity(EmpPayQuestionaryMstrEntity empPayQuestionaryMstrEntity) {
        this.empPayQuestionaryMstrEntity = empPayQuestionaryMstrEntity;
    }

}
