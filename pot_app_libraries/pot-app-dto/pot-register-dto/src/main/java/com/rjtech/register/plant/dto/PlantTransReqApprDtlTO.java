package com.rjtech.register.plant.dto;

import com.rjtech.common.dto.ProjectTO;

public class PlantTransReqApprDtlTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long plantId;
    private String plantAssetId;
    private String plantDesc;
    private String plantRegNo;
    private String plantManfature;
    private String plantModel;
    private Long plantTransId;
    private String expectedTransDate;
    private String transDate;
    private String plantTransDate;
    private String actualDeliveryDate;
    private String comments;
    private String plantTransComments;
    private String apprStatus;
    private String receivedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getPlantAssetId() {
        return plantAssetId;
    }

    public void setPlantAssetId(String plantAssetId) {
        this.plantAssetId = plantAssetId;
    }

    public String getPlantDesc() {
        return plantDesc;
    }

    public void setPlantDesc(String plantDesc) {
        this.plantDesc = plantDesc;
    }

    public String getPlantRegNo() {
        return plantRegNo;
    }

    public void setPlantRegNo(String plantRegNo) {
        this.plantRegNo = plantRegNo;
    }

    public String getPlantManfature() {
        return plantManfature;
    }

    public void setPlantManfature(String plantManfature) {
        this.plantManfature = plantManfature;
    }

    public String getPlantModel() {
        return plantModel;
    }

    public void setPlantModel(String plantModel) {
        this.plantModel = plantModel;
    }

    public Long getPlantTransId() {
        return plantTransId;
    }

    public void setPlantTransId(Long plantTransId) {
        this.plantTransId = plantTransId;
    }

    public String getExpectedTransDate() {
        return expectedTransDate;
    }

    public void setExpectedTransDate(String expectedTransDate) {
        this.expectedTransDate = expectedTransDate;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getPlantTransDate() {
        return plantTransDate;
    }

    public void setPlantTransDate(String plantTransDate) {
        this.plantTransDate = plantTransDate;
    }

    public String getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(String actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPlantTransComments() {
        return plantTransComments;
    }

    public void setPlantTransComments(String plantTransComments) {
        this.plantTransComments = plantTransComments;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

}
