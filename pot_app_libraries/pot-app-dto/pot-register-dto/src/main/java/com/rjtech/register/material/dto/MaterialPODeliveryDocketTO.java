package com.rjtech.register.material.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialPODeliveryDocketTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long regMaterialId;
    private String docketNumber;
    private String docketDate;
    private BigDecimal receivedQty;
    private boolean supplierDocket;
    private String receivedBy;
    private String defectComments;
    private String comments;
    private Long projDocketSchId;
    private String locType;
    private String sourceType;
    private String supplyDeliveryDate;
    private LabelKeyTO projStockLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO stockLabelKeyTO = new LabelKeyTO();
    private BigDecimal transitQty;
    
    private String name;
    private String fileType;
    private String fileSize;
    private String fileKey;
    
    public BigDecimal getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(BigDecimal transitQty) {
        this.transitQty = transitQty;
    }

    public Long getProjDocketSchId() {
        return projDocketSchId;
    }

    public void setProjDocketSchId(Long projDocketSchId) {
        this.projDocketSchId = projDocketSchId;
    }

    public boolean isSupplierDocket() {
        return supplierDocket;
    }

    public void setSupplierDocket(boolean supplierDocket) {
        this.supplierDocket = supplierDocket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegMaterialId() {
        return regMaterialId;
    }

    public void setRegMaterialId(Long regMaterialId) {
        this.regMaterialId = regMaterialId;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
    }

    public String getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(String docketDate) {
        this.docketDate = docketDate;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getDefectComments() {
        return defectComments;
    }

    public void setDefectComments(String defectComments) {
        this.defectComments = defectComments;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public LabelKeyTO getProjStockLabelKeyTO() {
        return projStockLabelKeyTO;
    }

    public void setProjStockLabelKeyTO(LabelKeyTO projStockLabelKeyTO) {
        this.projStockLabelKeyTO = projStockLabelKeyTO;
    }

    public LabelKeyTO getStockLabelKeyTO() {
        return stockLabelKeyTO;
    }

    public void setStockLabelKeyTO(LabelKeyTO stockLabelKeyTO) {
        this.stockLabelKeyTO = stockLabelKeyTO;
    }

    public String getSupplyDeliveryDate() {
        return supplyDeliveryDate;
    }

    public void setSupplyDeliveryDate(String supplyDeliveryDate) {
        this.supplyDeliveryDate = supplyDeliveryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}
