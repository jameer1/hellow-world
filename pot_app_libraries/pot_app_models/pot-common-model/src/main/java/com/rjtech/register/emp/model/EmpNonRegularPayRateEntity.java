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
@Table(name = "emp_nregular_payble_rate")
public class EmpNonRegularPayRateEntity implements Serializable {

    private static final long serialVersionUID = -4748375028162826687L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENPR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ENPR_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "ENPR_PGV_ID")
    private ProjGeneralMstrEntity projGeneralMstrEntity;

    @Column(name = "ENPR_IS_LATEST")
    private boolean latest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENPR_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENPR_TO_DATE")
    private Date toDate;

    @Column(name = "ENPR_PAY_CYCLE")
    private String payCycle;

    @Column(name = "ENPR_UNIT_OF_PAY_RATE")
    private String unitOfPayRate;

    @Column(name = "ENPR_BASIC_PAY")
    private BigDecimal basicPay;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENPR_PAYROLL_DATE")
    private Date payRollDate;

    @Column(name = "ENPR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ENPR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENPR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ENPR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ENPR_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "ENPR_PER_ID")
    private EmpProjRigisterEntity empProjRigisterEntity;

    @OneToMany(mappedBy = "empNonRegularPayRateEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpNonRegularPayDetailEntity> empNonRegularPayDetailEntities = new ArrayList<>();

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

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
    }

    public Date getPayRollDate() {
        return payRollDate;
    }

    public void setPayRollDate(Date payRollDate) {
        this.payRollDate = payRollDate;
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

    public BigDecimal getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(BigDecimal basicPay) {
        this.basicPay = basicPay;
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

    public String getUnitOfPayRate() {
        return unitOfPayRate;
    }

    public void setUnitOfPayRate(String unitOfPayRate) {
        this.unitOfPayRate = unitOfPayRate;
    }

    public EmpProjRigisterEntity getEmpProjRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpProjRigisterEntity(EmpProjRigisterEntity empProjRigisterEntity) {
        this.empProjRigisterEntity = empProjRigisterEntity;
    }

    public List<EmpNonRegularPayDetailEntity> getEmpNonRegularPayDetailEntities() {
        return empNonRegularPayDetailEntities;
    }

    public void setEmpNonRegularPayDetailEntities(List<EmpNonRegularPayDetailEntity> empNonRegularPayDetailEntities) {
        this.empNonRegularPayDetailEntities = empNonRegularPayDetailEntities;
    }

}
