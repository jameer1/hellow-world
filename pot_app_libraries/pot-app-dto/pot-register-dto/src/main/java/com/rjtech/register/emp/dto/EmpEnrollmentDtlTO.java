package com.rjtech.register.emp.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.register.dto.RegisterProjPurchaseOrderTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpEnrollmentDtlTO extends EmpProjectRegisterTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String effectiveFrom;
    private String effectiveTo;
    private Long empClassId;
    private String empClassName;
    private String enrollmentLatest;
    private String contractNumber;
    private String contractDate;
    private String empLocation;
    private LabelKeyTO locationLabelKeyTO;

    private RegisterProjPurchaseOrderTO projectPOTO;

    private LabelKeyTO reportingManagerLabelKeyTO = null;

    private String action;

    private String assignStatus;

    private Long empCategoryTypeId;

    private ProjEmpRegisterTO projEmpRegisterTO = null;

    private byte[] contractDocument;
    private String contractDocumentFileName;
    private String contractDocumentFileType;
    private String contractDocumentFileSize;
    private String uploadFullPath;

    public EmpEnrollmentDtlTO() {

        locationLabelKeyTO = new LabelKeyTO();
        reportingManagerLabelKeyTO = new LabelKeyTO();
        projectPOTO = new RegisterProjPurchaseOrderTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public String getEmpClassName() {
        return empClassName;
    }

    public void setEmpClassName(String empClassName) {
        this.empClassName = empClassName;
    }

    public String getEnrollmentLatest() {
        return enrollmentLatest;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public String getContractDate() {
        return contractDate;
    }

    public LabelKeyTO getLocationLabelKeyTO() {
        return locationLabelKeyTO;
    }

    public void setEnrollmentLatest(String enrollmentLatest) {
        this.enrollmentLatest = enrollmentLatest;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getEmpLocation() {
        return empLocation;
    }

    public void setEmpLocation(String empLocation) {
        this.empLocation = empLocation;
    }

    public void setLocationLabelKeyTO(LabelKeyTO locationLabelKeyTO) {
        this.locationLabelKeyTO = locationLabelKeyTO;
    }

    public LabelKeyTO getReportingManagerLabelKeyTO() {
        return reportingManagerLabelKeyTO;
    }

    public void setReportingManagerLabelKeyTO(LabelKeyTO reportingManagerLabelKeyTO) {
        this.reportingManagerLabelKeyTO = reportingManagerLabelKeyTO;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public RegisterProjPurchaseOrderTO getProjectPOTO() {
        return projectPOTO;
    }

    public void setProjectPOTO(RegisterProjPurchaseOrderTO projectPOTO) {
        this.projectPOTO = projectPOTO;
    }

    public Long getEmpCategoryTypeId() {
        return empCategoryTypeId;
    }

    public void setEmpCategoryTypeId(Long empCategoryTypeId) {
        this.empCategoryTypeId = empCategoryTypeId;
    }

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
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

    public String getContractDocumentFileSize() {
        return contractDocumentFileSize;
    }

    public void setContractDocumentFileSize( String contractDocumentFileSize ) {
        this.contractDocumentFileSize = contractDocumentFileSize;
    }
    
    public String getUploadFullPath() {
        return uploadFullPath;
    }

    public void setUploadFullPath( String uploadFullPath ) {
        this.uploadFullPath = uploadFullPath;
    }
}
