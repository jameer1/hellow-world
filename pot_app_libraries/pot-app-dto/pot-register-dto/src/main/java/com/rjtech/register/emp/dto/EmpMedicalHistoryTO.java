package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;
import org.springframework.web.multipart.MultipartFile;

public class EmpMedicalHistoryTO extends ProjectTO {

    private static final long serialVersionUID = 1428911884811141414L;
    private Long id;
    private Long empRegId;
    private Long empProjId;
    private String fromDate;
    private String recordId;
    private String item;
    private String particular;
    private String comments;
    private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();
    private MultipartFile documents;
    private Integer fileObjectIndex;
    private String fileName;
    private Boolean selected;

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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }
    
    public void setDocuments( MultipartFile documents ) {
    	this.documents = documents;
    }
    
    public MultipartFile getDocuments() {
    	return documents;
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
    
    public Boolean getSelected() {
        return selected;
    }

    public void setSelected( Boolean selected ) {
        this.selected = selected;
    }
}
