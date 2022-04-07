package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.document.dto.ProjDocFileTO;

public class EmpDocumentsTO extends ProjectTO {

    private static final long serialVersionUID = 1428911884811141414L;
    private Long documentId;
    private String documentCode;
    private Long empProjId;
    private Long empId;
    private String documentCategory;
    private ProjDocFileTO projDocFileTO;
    
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    
    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public ProjDocFileTO getProjDocFileTO() {
        return projDocFileTO;
    }

    public void setProjDocFileTO( ProjDocFileTO projDocFileTO ) {
        this.projDocFileTO = projDocFileTO;
    }

    public String getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(String documentCategory) {
        this.documentCategory = documentCategory;
    } 
}
