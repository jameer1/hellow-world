package com.rjtech.register.plant.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.ProjectTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantPODocketDtlTO extends ProjectTO {

    private static final long serialVersionUID = -7222660301619008267L;

    private Long id;
    private Long plantReqForApprId;
    private String startDate;
    private String endDate;
    private String docketNum;
    private String receivedBy;
    private String receivedCode;
    private String receiverComments;
    private String commissionDate;
    private BigDecimal odoMeter;
    private String deliveryLocation;
    private BigDecimal quantity;
    private String deliveryType;
    private String docKey;
    private String docName;
    private String docType;
    private byte[] docContent;
    private Integer fileObjectIndex;
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantReqForApprId() {
        return plantReqForApprId;
    }

    public void setPlantReqForApprId(Long plantReqForApprId) {
        this.plantReqForApprId = plantReqForApprId;
    }

    public String getDocketNum() {
        return docketNum;
    }

    public void setDocketNum(String docketNum) {
        this.docketNum = docketNum;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getReceivedCode() {
        return receivedCode;
    }

    public void setReceivedCode(String receivedCode) {
        this.receivedCode = receivedCode;
    }

    public String getReceiverComments() {
        return receiverComments;
    }

    public void setReceiverComments(String receiverComments) {
        this.receiverComments = receiverComments;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(String commissionDate) {
        this.commissionDate = commissionDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public byte[] getDocContent() {
        return docContent;
    }

    public void setDocContent(byte[] docContent) {
        this.docContent = docContent;
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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String toString() {
    	return "id"+id+" fileName:"+fileName+" docketNum:"+docketNum;
    }
}
