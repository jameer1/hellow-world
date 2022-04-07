package com.rjtech.register.emp.model;

import java.io.Serializable;
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
@Table(name = "emp_provident_fund")
public class EmpProvidentFundEntity implements Serializable {

    private static final long serialVersionUID = -2821835794214477618L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EPFD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EPFD_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "EPFD_PGV_ID")
    private ProjGeneralMstrEntity projGeneralMstrEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPFD_FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPFD_TO_DATE")
    private Date toDate;

    @Column(name = "EPFD_IS_LATEST")
    private boolean latest;

    @Column(name = "EPFD_PAY_CYCLE")
    private String payCycle;

    @Column(name = "EPFD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EPFD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPFD_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EPFD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EPFD_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "EPFD_PER_ID")
    private EmpProjRigisterEntity empProjRigisterEntity;

    @OneToMany(mappedBy = "empProvidentFundEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpProvidentFundDetailEntity> empProvidentFundDetailEntities = new ArrayList<>();

    @OneToMany(mappedBy = "empProvidentFundEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpProvidentFundTaxEntity> empProvidentFundTaxEntities = new ArrayList<>();

    @OneToMany(mappedBy = "empProvidentFundEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpProvidentFundContributionEntity> empProvidentFundContributionEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjGeneralMstrEntity getProjGeneralMstrEntity() {
        return projGeneralMstrEntity;
    }

    public void setProjGeneralMstrEntity(ProjGeneralMstrEntity projGeneralMstrEntity) {
        this.projGeneralMstrEntity = projGeneralMstrEntity;
    }

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
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

    public EmpProjRigisterEntity getEmpProjRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpProjRigisterEntity(EmpProjRigisterEntity empProjRigisterEntity) {
        this.empProjRigisterEntity = empProjRigisterEntity;
    }

    public List<EmpProvidentFundDetailEntity> getEmpProvidentFundDetailEntities() {
        return empProvidentFundDetailEntities;
    }

    public void setEmpProvidentFundDetailEntities(List<EmpProvidentFundDetailEntity> empProvidentFundDetailEntities) {
        this.empProvidentFundDetailEntities = empProvidentFundDetailEntities;
    }

    public List<EmpProvidentFundContributionEntity> getEmpProvidentFundContributionEntities() {
        return empProvidentFundContributionEntities;
    }

    public void setEmpProvidentFundContributionEntities(
            List<EmpProvidentFundContributionEntity> empProvidentFundContributionEntities) {
        this.empProvidentFundContributionEntities = empProvidentFundContributionEntities;
    }

    public List<EmpProvidentFundTaxEntity> getEmpProvidentFundTaxEntities() {
        return empProvidentFundTaxEntities;
    }

    public void setEmpProvidentFundTaxEntities(List<EmpProvidentFundTaxEntity> empProvidentFundTaxEntities) {
        this.empProvidentFundTaxEntities = empProvidentFundTaxEntities;
    }

}
