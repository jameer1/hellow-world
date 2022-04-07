package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;

@Entity
@Table(name = "emp_chargeout_rate")
public class EmpChargeOutRateEntityCopy implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5413865765579372974L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ECR_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ECR_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "ECR_PER_ID")
    private EmpProjRigisterEntity empProjRigisterEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECR_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECR_TO_DATE")
    private Date toDate;

    @Column(name = "ECR_IS_LATEST")
    private boolean latest;

    @ManyToOne
    @JoinColumn(name = "ECR_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ECR_PGV_ID")
    private ProjGeneralMstrEntity projGeneralMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ECR_PAID_LEAVE_CCS_ID")
    private ProjCostItemEntity leaveCostItemEntity;

    @ManyToOne
    @JoinColumn(name = "ECR_MOB_CCS_ID")
    private ProjCostItemEntity mobCostItemEntity;

    @ManyToOne
    @JoinColumn(name = "ECR_DEMOB_CCS_ID")
    private ProjCostItemEntity deMobCostItemEntity;

    @Column(name = "ECR_NORMAL_RATE")
    private BigDecimal normalRate;

    @Column(name = "ECR_IDLE_RATE")
    private BigDecimal idleRate;

    @Column(name = "ECR_PAID_LEAVE_RATE")
    private BigDecimal paidLeaveRate;

    @Column(name = "ECR_MOB_RATE")
    private BigDecimal mobRate;

    @Column(name = "ECR_DEMOB_RATE")
    private BigDecimal deMobRate;

    @Column(name = "ECR_COMMENTS")
    private String comments;

    @Column(name = "ECR_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ECR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECR_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ECR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECR_UPDATED_ON")
    private Date updatedOn;

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

    public EmpProjRigisterEntity getEmpProjRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpProjRigisterEntity(EmpProjRigisterEntity empProjRigisterEntity) {
        this.empProjRigisterEntity = empProjRigisterEntity;
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

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public MeasurmentMstrEntity getMeasurmentMstrEntity() {
        return measurmentMstrEntity;
    }

    public void setMeasurmentMstrEntity(MeasurmentMstrEntity measurmentMstrEntity) {
        this.measurmentMstrEntity = measurmentMstrEntity;
    }

    public ProjGeneralMstrEntity getProjGeneralMstrEntity() {
        return projGeneralMstrEntity;
    }

    public void setProjGeneralMstrEntity(ProjGeneralMstrEntity projGeneralMstrEntity) {
        this.projGeneralMstrEntity = projGeneralMstrEntity;
    }

    public ProjCostItemEntity getLeaveCostItemEntity() {
        return leaveCostItemEntity;
    }

    public void setLeaveCostItemEntity(ProjCostItemEntity leaveCostItemEntity) {
        this.leaveCostItemEntity = leaveCostItemEntity;
    }

    public ProjCostItemEntity getMobCostItemEntity() {
        return mobCostItemEntity;
    }

    public void setMobCostItemEntity(ProjCostItemEntity mobCostItemEntity) {
        this.mobCostItemEntity = mobCostItemEntity;
    }

    public ProjCostItemEntity getDeMobCostItemEntity() {
        return deMobCostItemEntity;
    }

    public void setDeMobCostItemEntity(ProjCostItemEntity deMobCostItemEntity) {
        this.deMobCostItemEntity = deMobCostItemEntity;
    }

    public BigDecimal getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(BigDecimal normalRate) {
        this.normalRate = normalRate;
    }

    public BigDecimal getIdleRate() {
        return idleRate;
    }

    public void setIdleRate(BigDecimal idleRate) {
        this.idleRate = idleRate;
    }

    public BigDecimal getPaidLeaveRate() {
        return paidLeaveRate;
    }

    public void setPaidLeaveRate(BigDecimal paidLeaveRate) {
        this.paidLeaveRate = paidLeaveRate;
    }

    public BigDecimal getMobRate() {
        return mobRate;
    }

    public void setMobRate(BigDecimal mobRate) {
        this.mobRate = mobRate;
    }

    public BigDecimal getDeMobRate() {
        return deMobRate;
    }

    public void setDeMobRate(BigDecimal deMobRate) {
        this.deMobRate = deMobRate;
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
