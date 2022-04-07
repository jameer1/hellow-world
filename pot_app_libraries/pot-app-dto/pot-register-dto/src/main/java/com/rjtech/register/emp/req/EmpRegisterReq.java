package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.register.emp.dto.EmpDocumentsTO;

public class EmpRegisterReq extends ProjectTO {

    private static final long serialVersionUID = -7764588560956931548L;
    private Long empId;
    private Long empProjId;
    private String fromDate;
    private String toDate;
    private Long id;
    private String countryId;
    private String provinceId;
    private Integer payTypeId;
    private String effectiveDate;
    //private ProjDocFileTO projDocFileTO;
    private List<ProjDocFileTO> projDocFileTOs = new ArrayList<ProjDocFileTO>();
    private List<EmpDocumentsTO> empDocumentsTOs = new ArrayList<EmpDocumentsTO>();
    private String folderCategory;
    
    
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /*public ProjDocFileTO getProjDocFileTO() {
        return projDocFileTO;
    }

    public void setProjDocFileTO(ProjDocFileTO projDocFileTO) {
        this.projDocFileTO = projDocFileTO;
    }
    */
    
    public List<ProjDocFileTO> getProjDocFileTOs() {
        return projDocFileTOs;
    }

    public void setProjDocFileTOs(List<ProjDocFileTO> projDocFileTOs) {
        this.projDocFileTOs = projDocFileTOs;
    }

    public List<EmpDocumentsTO> getEmpDocumentsTOs() {
        return empDocumentsTOs;
    }

    public void setEmpDocumentsTOs( List<EmpDocumentsTO> empDocumentsTOs ) {
        this.empDocumentsTOs = empDocumentsTOs;
    }
    
    public String getFolderCategory() {
        return folderCategory;
    }
    
    public void setFolderCategory( String folderCategory ) {
        this.folderCategory = folderCategory;
    }
    
    public String toString() {
    	return "empId:"+empId+"folderCategory:"+folderCategory+"projId:"+getProjId();
    }
}
