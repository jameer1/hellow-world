package com.rjtech.register.material.req;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

public class MaterialGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long purId;
    private Long docketId;
    private String docType;
    private Long materialId;
    private String sourceType;
    private Integer stockType;
    private Long notifyId;
    private boolean supplierDocket;
    private String workDairyDate;
    private LabelKeyTO stockLocLabelKeyTO = new LabelKeyTO();

    public Long getDocketId() {
        return docketId;
    }

    public void setDocketId(Long docketId) {
        this.docketId = docketId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getStockType() {
        return stockType;
    }

    public LabelKeyTO getStockLocLabelKeyTO() {
        return stockLocLabelKeyTO;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public void setStockLocLabelKeyTO(LabelKeyTO stockLocLabelKeyTO) {
        this.stockLocLabelKeyTO = stockLocLabelKeyTO;
    }

    public Long getPurId() {
        return purId;
    }

    public boolean isSupplierDocket() {
        return supplierDocket;
    }

    public void setSupplierDocket(boolean supplierDocket) {
        this.supplierDocket = supplierDocket;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Long getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
    }

}
