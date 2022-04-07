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

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "emp_register_dtl")
public class EmpRegisterDtlEntity implements Serializable {

    private static final long serialVersionUID = -8795406421033703994L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ERD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ERD_EPM_ID")
    private ProjMstrEntity projMstrEntity;

    @Column(name = "ERD_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "ERD_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "ERD_FNAME")
    private String firstName;

    @Column(name = "ERD_LNAME")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "ERD_CMP_ID")
    private CompanyMstrEntity companyMstrEntity;

    @Column(name = "ERD_GENDER")
    private String gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERD_DOB")
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "ERD_ECM_ID")
    private EmpClassMstrEntity empClassMstrEntity;

    @ManyToOne
    @JoinColumn(name = "ERD_PCD_ID")
    private ProcureCatgDtlEntity procureCatgDtlEntity;

    @Column(name = "ERD_LOC")
    private String location;

    @Column(name = "ERD_EMP_STATUS")
    private String empStatus;

    @Column(name = "ERD_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ERD_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Column(name = "ERD_CREATED_ON", updatable = false)
    private Timestamp createdOn;

    @ManyToOne
    @JoinColumn(name = "ERD_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ERD_UPDATED_ON")
    private Timestamp updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERD_CONTRACT_DATE")
    private Date contractDate;

    @Column(name = "ERD_CONTRACT_NUMBER")
    private String contractNumber;

    @OneToMany(mappedBy = "empRegisterDtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpProjRigisterEntity> empProjRigisterEntities = new ArrayList<>();

    @OneToMany(mappedBy = "empRegisterDtlEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmpEnrollmentDtlEntity> empEnrollmentDtlEntities = new ArrayList<>();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CompanyMstrEntity getCompanyMstrEntity() {
        return companyMstrEntity;
    }

    public void setCompanyMstrEntity(CompanyMstrEntity companyMstrEntity) {
        this.companyMstrEntity = companyMstrEntity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public EmpClassMstrEntity getEmpClassMstrEntity() {
        return empClassMstrEntity;
    }

    public void setEmpClassMstrEntity(EmpClassMstrEntity empClassMstrEntity) {
        this.empClassMstrEntity = empClassMstrEntity;
    }

    public ProcureCatgDtlEntity getProcureCatgDtlEntity() {
        return procureCatgDtlEntity;
    }

    public void setProcureCatgDtlEntity(ProcureCatgDtlEntity procureCatgDtlEntity) {
        this.procureCatgDtlEntity = procureCatgDtlEntity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
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

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public List<EmpProjRigisterEntity> getProjEmpRigisterEntities() {
        return empProjRigisterEntities;
    }

    public void setProjEmpRigisterEntities(List<EmpProjRigisterEntity> empProjRigisterEntities) {
        this.empProjRigisterEntities = empProjRigisterEntities;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public List<EmpEnrollmentDtlEntity> getEmpEnrollmentDtlEntities() {
        return empEnrollmentDtlEntities;
    }

    public void setEmpEnrollmentDtlEntities(List<EmpEnrollmentDtlEntity> empEnrollmentDtlEntities) {
        this.empEnrollmentDtlEntities = empEnrollmentDtlEntities;
    }

}
