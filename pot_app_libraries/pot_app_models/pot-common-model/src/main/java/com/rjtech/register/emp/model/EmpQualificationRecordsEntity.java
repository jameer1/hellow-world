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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "emp_qualification_records")
public class EmpQualificationRecordsEntity implements Serializable {

    private static final long serialVersionUID = 5127759126297571266L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUAL_ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "RECORD_TYPE")
    private String recordType;

    @Column(name = "INSTITUTE_NAME")
    private String instituteName;

    @Column(name = "INSTITUTE_ADDRESS")
    private String instituteAddress;
    
    @Column(name = "INSTITUTE_CONTACT_DETAILS")
    private String instituteContactDetails;
    
    @Column(name = "CERTIFICATE_NUMBER")
    private String certificateNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_ISSUE")
    private Date dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Column(name = "CREATED_ON", updatable = false)
    private Date createdOn;
    
    @Column(name = "IS_RENEWAL_REQUIRED")
    private Character isRenewalRequired;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NEW_TRAINING_DUE_DATE")
    private Date newTrainingDueDate;

    @ManyToOne
    @JoinColumn(name = "EPM_ID_FK")
    private ProjMstrEntity projMstrEntity;
    
    @ManyToOne
    @JoinColumn(name = "ERD_ID_FK")
    private EmpRegisterDtlEntity empRegisterDtlEntity;
    
    /*@ManyToOne
    @JoinColumn(name = "EMH_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EMH_UPDATED_ON")
    private Timestamp updatedOn;*/

    /*@ManyToOne
    @JoinColumn(name = "ERD_ID_FK")
    private EmpRegisterDtlEntity empRegisterDtlEntity;
    
    @ManyToOne
    @JoinColumn(name = "PER_ID_FK")
    private EmpProjRigisterEntity empProjRigisterEntity;*/
    
    @Column(name = "FILE_NAME")
    private String fileName;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    @Column(name = "STATUS")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }
    
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
    
    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName( String instituteName ) {
        this.instituteName = instituteName;
    }
    
    public String getInstituteAddress() {
        return instituteAddress;
    }

    public void setInstituteAddress( String instituteAddress ) {
        this.instituteAddress = instituteAddress;
    }

    public String getInstituteContactDetails() {
        return instituteContactDetails;
    }

    public void setInstituteContactDetails( String instituteContactDetails ) {
        this.instituteContactDetails = instituteContactDetails;
    }
    
    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber( String certificateNumber ) {
        this.certificateNumber = certificateNumber;
    }
    
    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue( Date dateOfIssue ) {
        this.dateOfIssue = dateOfIssue;
    }
    
    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy( UserMstrEntity createdBy ) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /*public UserMstrEntity getUpdatedBy() {
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
    }*/

    
    public Character getIsRenewalRequired() {
    	return isRenewalRequired;
    }
    
    public void setIsRenewalRequired( Character isRenewalRequired ) {
    	this.isRenewalRequired = isRenewalRequired;
    }
    
    public Date getExpiryDate() {
    	return expiryDate;
    }
    
    public void setExpiryDate( Date expiryDate ) {
    	this.expiryDate = expiryDate;
    }
    
    public Date getNewTrainingDueDate() {
    	return newTrainingDueDate;
    }
    
    public void setNewTrainingDueDate( Date newTrainingDueDate ) {
    	this.newTrainingDueDate = newTrainingDueDate;
    }
    
    /*public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }
    
    public void setEmpRegisterDtlEntity( EmpRegisterDtlEntity empRegisterDtlEntity ) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public EmpProjRigisterEntity getEmpProjRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpProjRigisterEntity( EmpProjRigisterEntity empProjRigisterEntity ) {
        this.empProjRigisterEntity = empProjRigisterEntity;
    }*/
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }
    
    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus( Integer status ) {
        this.status = status;
    }
    
    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }
    
    public ProjMstrEntity getProjMstrEntity() {
        return projMstrEntity;
    }

    public void setProjMstrEntity(ProjMstrEntity projMstrEntity) {
        this.projMstrEntity = projMstrEntity;
    }
    
    public String toString() {
    	return "filename:"+fileName+" code:"+code+" instituteName:"+instituteName;
    }
}
