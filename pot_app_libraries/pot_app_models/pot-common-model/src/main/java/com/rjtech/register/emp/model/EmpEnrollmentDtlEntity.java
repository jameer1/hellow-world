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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "emp_enrollment_dtl")
public class EmpEnrollmentDtlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EED_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EED_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EED_EFFECTIVE_TO")
    private Date effectiveTo;

    @Column(name = "EED_IS_LATEST")
    private String isLatest;

    @Column(name = "EED_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EED_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EED_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EED_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EED_UPDATED_ON")
    private Timestamp updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EED_CONTRACT_DATE")
    private Date contractDate;

    @Column(name = "EED_CONTRACT_NUMBER")
    private String contractNumber;

    @Column(name = "EED_LOCATION")
    private String empLocation;

    @ManyToOne
    @JoinColumn(name = "EED_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @ManyToOne
    @JoinColumn(name = "EED_PER_ID")
    private EmpProjRigisterEntity empProjRigisterEntity;

    @ManyToOne
    @JoinColumn(name = "EED_REPORT_MANGER_ERD_ID")
    private EmpRegisterDtlEntity reportManagerEmpEntity;

    @ManyToOne
    @JoinColumn(name = "EED_ECM_ID")
    private EmpClassMstrEntity empClassMstrEntity;

    @Column(name = "EED_PEC_ID")
    private Long empCategoryTypeId;

    @Lob
    @Column(name = "EED_DOC_DETAILS")
    private byte[] contractDocument;

    @Column(name = "EED_DOC_FILE_NAME")
    private String contractDocumentFileName;

    @Column(name = "EED_DOC_FILE_TYPE")
    private String contractDocumentFileType;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getEmpLocation() {
        return empLocation;
    }

    public void setEmpLocation(String empLocation) {
        this.empLocation = empLocation;
    }

    public EmpClassMstrEntity getEmpClassMstrEntity() {
        return empClassMstrEntity;
    }

    public void setEmpClassMstrEntity(EmpClassMstrEntity empClassMstrEntity) {
        this.empClassMstrEntity = empClassMstrEntity;
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

    public EmpProjRigisterEntity getProjEmpRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public void setProjEmpRigisterEntity(EmpProjRigisterEntity empProjRigisterEntity) {
        this.empProjRigisterEntity = empProjRigisterEntity;
    }

    public EmpRegisterDtlEntity getReportManagerEmpEntity() {
        return reportManagerEmpEntity;
    }

    public void setReportManagerEmpEntity(EmpRegisterDtlEntity reportManagerEmpEntity) {
        this.reportManagerEmpEntity = reportManagerEmpEntity;
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

    public Long getEmpCategoryTypeId() {
        return empCategoryTypeId;
    }

    public void setEmpCategoryTypeId(Long empCategoryTypeId) {
        this.empCategoryTypeId = empCategoryTypeId;
    }

    public byte[] getContractDocument() {
        return contractDocument;
    }

    public void setContractDocument(byte[] contractDocument) {
        this.contractDocument = contractDocument;
    }

    public String getContractDocumentFileName() {
        return contractDocumentFileName;
    }

    public void setContractDocumentFileName(String contractDocumentFileName) {
        this.contractDocumentFileName = contractDocumentFileName;
    }

    public String getContractDocumentFileType() {
        return contractDocumentFileType;
    }

    public void setContractDocumentFileType(String contractDocumentFileType) {
        this.contractDocumentFileType = contractDocumentFileType;
    }

    public ProjDocFileEntity getProjDocFileEntity() {
        return projDocFileEntity;
    }

    public void setProjDocFileEntity(ProjDocFileEntity projDocFileEntity) {
        this.projDocFileEntity = projDocFileEntity;
    }
}
