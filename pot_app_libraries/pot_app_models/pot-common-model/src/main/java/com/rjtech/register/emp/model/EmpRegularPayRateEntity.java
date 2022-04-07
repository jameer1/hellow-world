package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;

@Entity
@Table(name = "emp_regular_payble_rate")
public class EmpRegularPayRateEntity implements Serializable {

    private static final long serialVersionUID = -8094325916021743862L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ERPR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ECR_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @Column(name = "ERPR_BASIC_PAY")
    private BigDecimal basicPay;

    @Column(name = "ERPR_IS_LATEST")
    private boolean latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERPR_EFFECTIVE_TO")
    private Date toDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERPR_EFFECTIVE_FROM")
    private Date fromDate;

    @ManyToOne
    @JoinColumn(name = "ERPR_PGV_ID")
    private ProjGeneralMstrEntity projGeneralMstrEntity;

    @Column(name = "ERPR_UNIT_PAY_RATE")
    private String unitPayRate;

    @Column(name = "ERPR_PAY_CYCLE")
    private String payCycle;

    @Column(name = "ERPR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ERPR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERPR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ERPR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ERPR_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "ERPR_PER_ID")
    private EmpProjRigisterEntity empProjRigisterEntity;

    @OneToMany(mappedBy = "empRegularPayRateEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpRegularPayRateDetailEntity> empRegularPayRateDetailEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public ProjGeneralMstrEntity getProjGeneralMstrEntity() {
        return projGeneralMstrEntity;
    }

    public void setProjGeneralMstrEntity(ProjGeneralMstrEntity projGeneralMstrEntity) {
        this.projGeneralMstrEntity = projGeneralMstrEntity;
    }

    public BigDecimal getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(BigDecimal basicPay) {
        this.basicPay = basicPay;
    }

    public List<EmpRegularPayRateDetailEntity> getEmpRegularPayRateDetailEntities() {
        return empRegularPayRateDetailEntities;
    }

    public void setEmpRegularPayRateDetailEntities(
            List<EmpRegularPayRateDetailEntity> empRegularPayRateDetailEntities) {
        this.empRegularPayRateDetailEntities = empRegularPayRateDetailEntities;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
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

    public String getUnitPayRate() {
        return unitPayRate;
    }

    public void setUnitPayRate(String unitPayRate) {
        this.unitPayRate = unitPayRate;
    }

    public EmpProjRigisterEntity getEmpProjRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpProjRigisterEntity(EmpProjRigisterEntity empProjRigisterEntity) {
        this.empProjRigisterEntity = empProjRigisterEntity;
    }

}
