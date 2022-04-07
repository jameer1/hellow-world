package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpQualificationRecordsTO extends ProjectTO {

    private static final long serialVersionUID = 1428911884811141414L;
    private Long id;
    private Long empRegId;
    private Long empProjId;
    private String dateOfIssue;
    private String newTrainingDueDate;
    //private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();
    private Integer fileObjectIndex;
    private String fileName;
    private String instituteName;
    private String instituteAddress;
    private String instituteContactDetails;
    private String certificateNumber;
    private String expiryDate;
    private Character isRenewalRequired;
    private String recordType;
    private String code;
    private String recordId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue( String dateOfIssue ) {
        this.dateOfIssue = dateOfIssue;
    }
    
    public String getNewTrainingDueDate() {
        return newTrainingDueDate;
    }

    public void setNewTrainingDueDate( String newTrainingDueDate ) {
        this.newTrainingDueDate = newTrainingDueDate;
    }
    
    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public Integer getFileObjectIndex() {
        return fileObjectIndex;
    }

    public void setFileObjectIndex(Integer fileObjectIndex) {
        this.fileObjectIndex = fileObjectIndex;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
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
    
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate( String expiryDate ) {
        this.expiryDate = expiryDate;
    }
    
    public Character getIsRenewalRequired() {
        return isRenewalRequired;
    }

    public void setIsRenewalRequired( Character isRenewalRequired ) {
        this.isRenewalRequired = isRenewalRequired;
    }
    
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType( String recordType ) {
        this.recordType = recordType;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }
    
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId( String recordId ) {
        this.recordId = recordId;
    }
    
    public String toString() {
    	return "certificateNumber:"+certificateNumber+" recordType:"+recordType+" fileName:"+fileName;
    }
}
