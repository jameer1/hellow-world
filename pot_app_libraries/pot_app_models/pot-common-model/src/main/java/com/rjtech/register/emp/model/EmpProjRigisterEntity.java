package com.rjtech.register.emp.model;

import java.io.Serializable;
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

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

// TODO rename this entity

@Entity
@Table(name = "proj_emp_register")
public class EmpProjRigisterEntity implements Serializable {

    private static final long serialVersionUID = 6748107126190129896L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_ID")
    private Long id;

    @Column(name = "PER_DEPLOYMENT_ID")
    private Long deploymentId;

    @ManyToOne
    @JoinColumn(name = "PER_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PER_ENROLLMENT_DATE")
    private Date enrollmentDate;

    @Column(name = "PER_ENROLLMENT_LOC")
    private String enrollmentLoc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PER_MOBILIZATION_DATE")
    private Date mobilaizationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PER_DEMOBILIZATION_DATE")
    private Date deMobilaizationDate;

    @Column(name = "PER_DEMOB_STATUS")
    private String demobilizationStatus;

    @Column(name = "PER_IS_LATEST")
    private String isLatest;

    @Column(name = "PER_ASSIGN_STATUS")
    private String assignStatus;

    @Column(name = "PER_TAX_FILE_NUM")
    private String taxFileNum;

    @Column(name = "PER_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PER_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PER_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PER_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "PER_UPDATED_ON")
    private Timestamp updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PER_EXPECTED_DEMOBILIZATION_DATE")
    private Date expectedDemobilaizationDate;

    @ManyToOne
    @JoinColumn(name = "PER_ECM_ID")
    private EmpClassMstrEntity empClassMstrEntity;

    @Column(name = "PER_COMMENTS")
    private String notes;

    @Column(name = "PER_PROJ_WORK_TYPE")
    private String workType;

    @ManyToOne
    @JoinColumn(name = "PER_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "PER_PEP_ID", referencedColumnName = "PEP_ID")
    private EmpProjRegisterPODtlEntity empProjRegisterPODtlEntity;

    @OneToMany(mappedBy = "empProjRigisterEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpEnrollmentDtlEntity> empEnrollmentDtlEntities = new ArrayList<>();

    @OneToMany(mappedBy = "empProjRigisterEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpChargeOutRateEntity> empchargeOutRateEntities = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getEnrollmentLoc() {
        return enrollmentLoc;
    }

    public void setEnrollmentLoc(String enrollmentLoc) {
        this.enrollmentLoc = enrollmentLoc;
    }

    public Date getMobilaizationDate() {
        return mobilaizationDate;
    }

    public void setMobilaizationDate(Date mobilaizationDate) {
        this.mobilaizationDate = mobilaizationDate;
    }

    public Date getDeMobilaizationDate() {
        return deMobilaizationDate;
    }

    public void setDeMobilaizationDate(Date deMobilaizationDate) {
        this.deMobilaizationDate = deMobilaizationDate;
    }

    public String getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
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

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public Date getExpectedDemobilaizationDate() {
        return expectedDemobilaizationDate;
    }

    public void setExpectedDemobilaizationDate(Date expectedDemobilaizationDate) {
        this.expectedDemobilaizationDate = expectedDemobilaizationDate;
    }

    public String getDemobilizationStatus() {
        return demobilizationStatus;
    }

    public void setDemobilizationStatus(String demobilizationStatus) {
        this.demobilizationStatus = demobilizationStatus;
    }

    public EmpClassMstrEntity getEmpClassMstrEntity() {
        return empClassMstrEntity;
    }

    public void setEmpClassMstrEntity(EmpClassMstrEntity empClassMstrEntity) {
        this.empClassMstrEntity = empClassMstrEntity;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public EmpProjRegisterPODtlEntity getEmpProjRegisterPODtlEntity() {
        return empProjRegisterPODtlEntity;
    }

    public void setEmpProjRegisterPODtlEntity(EmpProjRegisterPODtlEntity empProjRegisterPODtlEntity) {
        this.empProjRegisterPODtlEntity = empProjRegisterPODtlEntity;
    }

    public List<EmpEnrollmentDtlEntity> getEmpEnrollmentDtlEntities() {
        return empEnrollmentDtlEntities;
    }

    public void setEmpEnrollmentDtlEntities(List<EmpEnrollmentDtlEntity> empEnrollmentDtlEntities) {
        this.empEnrollmentDtlEntities = empEnrollmentDtlEntities;
    }

    public String getTaxFileNum() {
        return taxFileNum;
    }

    public void setTaxFileNum(String taxFileNum) {
        this.taxFileNum = taxFileNum;
    }

	public List<EmpChargeOutRateEntity> getEmpchargeOutRateEntities() {
		return empchargeOutRateEntities;
	}

	public void setEmpchargeOutRateEntities(List<EmpChargeOutRateEntity> empchargeOutRateEntities) {
		this.empchargeOutRateEntities = empchargeOutRateEntities;
	}

}
