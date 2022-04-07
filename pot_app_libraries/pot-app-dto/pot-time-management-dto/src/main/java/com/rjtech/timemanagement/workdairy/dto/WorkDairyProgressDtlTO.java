package com.rjtech.timemanagement.workdairy.dto;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.document.dto.ProjDocFileTO;

public class WorkDairyProgressDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long workDairyId;
    private Long sowId;
    private String sowCode;
    private String sowDesc;
    private Long soeId;
    private String soeCode;
    private Long sorId;
    private String sorCode;
    private Long parentId;
    private Long userId;
    private String apprStatus;
    private String apprComments;
    private Long costId;
    private String costCode;
    private Long measureId;
    private String measureCode;
    private double value;
    private Integer fileObjectIndex;
    private Integer actualQty;
    private Integer total;
    private Integer quantity;
    private Boolean errorFlag;
    private String fileName;
    private ProjDocFileTO projDocFileTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public String getSowCode() {
        return sowCode;
    }

    public void setSowCode(String sowCode) {
        this.sowCode = sowCode;
    }

    public String getSowDesc() {
        return sowDesc;
    }

    public void setSowDesc(String sowDesc) {
        this.sowDesc = sowDesc;
    }

    public Long getSoeId() {
        return soeId;
    }

    public void setSoeId(Long soeId) {
        this.soeId = soeId;
    }

    public String getSoeCode() {
        return soeCode;
    }

    public void setSoeCode(String soeCode) {
        this.soeCode = soeCode;
    }

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public String getSorCode() {
        return sorCode;
    }

    public void setSorCode(String sorCode) {
        this.sorCode = sorCode;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public String getMeasureCode() {
        return measureCode;
    }

    public void setMeasureCode(String measureCode) {
        this.measureCode = measureCode;
    }
    
    public Integer getFileObjectIndex() {
        return fileObjectIndex;
    }

    public void setFileObjectIndex( Integer fileObjectIndex ) {
        this.fileObjectIndex = fileObjectIndex;
    }
    
    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty( Integer actualQty ) {
        this.actualQty = actualQty;
    }
    
    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity( Integer quantity ) {
        this.quantity = quantity;
    }
    
    public Boolean getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag( Boolean errorFlag ) {
        this.errorFlag = errorFlag;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public ProjDocFileTO getProjDocFileTO() {
        return projDocFileTO;
    }

    public void setProjDocFileTO(ProjDocFileTO projDocFileTO) {
        this.projDocFileTO = projDocFileTO;
    }
    
    public String toString() {
    	return "sowCode:"+sowCode+" measureId:"+measureId+" costCode:"+costCode+" measureCode:"+measureCode+" fileName:"+fileName;
    }
}