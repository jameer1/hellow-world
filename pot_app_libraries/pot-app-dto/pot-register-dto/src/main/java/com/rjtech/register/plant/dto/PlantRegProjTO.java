package com.rjtech.register.plant.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PlantRegProjTO extends ProjectTO {

    private static final long serialVersionUID = -1210735657272420352L;

    private Long id;
    private Long plantId;
    private Long plantPOId;
    private Long reqForTransId;
    private String commissionDate;
    private String mobDate;
    private String anticipatedDeMobDate;
    private String deMobDate;
    private BigDecimal odoMeter;
    private BigDecimal deMobOdoMeter;
    private String deMobStatus;
    private String comments;
    private Long plantTransId;
    private Long deploymentId;
    private String latest;
    private String assignStatus;
    private String plantDeliveryStatus;
    private String postDeMobStatus;
    private String assertId;
    private String plantMasterName;

    public PlantRegProjTO() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getPlantPOId() {
        return plantPOId;
    }

    public void setPlantPOId(Long plantPOId) {
        this.plantPOId = plantPOId;
    }

    public Long getReqForTransId() {

        return reqForTransId;
    }

    public void setReqForTransId(Long reqForTransId) {
        this.reqForTransId = reqForTransId;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(String commissionDate) {
        this.commissionDate = commissionDate;
    }

    public String getComments() {
        return comments;
    }

    public Long getPlantTransId() {
        return plantTransId;
    }

    public Long getDeploymentId() {
        return deploymentId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setPlantTransId(Long plantTransId) {
        this.plantTransId = plantTransId;
    }

    public void setDeploymentId(Long deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getLatest() {
        return latest;
    }

    public Long getPlantId() {
        return plantId;
    }

    public String getPlantDeliveryStatus() {
        return plantDeliveryStatus;
    }

    public void setPlantDeliveryStatus(String plantDeliveryStatus) {
        this.plantDeliveryStatus = plantDeliveryStatus;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getMobDate() {
        return mobDate;
    }

    public void setMobDate(String mobDate) {
        this.mobDate = mobDate;
    }

    public String getAnticipatedDeMobDate() {
        return anticipatedDeMobDate;
    }

    public void setAnticipatedDeMobDate(String anticipatedDeMobDate) {
        this.anticipatedDeMobDate = anticipatedDeMobDate;
    }

    public String getDeMobDate() {
        return deMobDate;
    }

    public void setDeMobDate(String deMobDate) {
        this.deMobDate = deMobDate;
    }

    public BigDecimal getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(BigDecimal odoMeter) {
        this.odoMeter = odoMeter;
    }

    public BigDecimal getDeMobOdoMeter() {
        return deMobOdoMeter;
    }

    public void setDeMobOdoMeter(BigDecimal deMobOdoMeter) {
        this.deMobOdoMeter = deMobOdoMeter;
    }

    public String getDeMobStatus() {
        return deMobStatus;
    }

    public void setDeMobStatus(String deMobStatus) {
        this.deMobStatus = deMobStatus;
    }

    public String getPostDeMobStatus() {
        return postDeMobStatus;
    }

    public void setPostDeMobStatus(String postDeMobStatus) {
        this.postDeMobStatus = postDeMobStatus;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }
    
    public String getAssertId() {
        return assertId;
    }

    public void setAssertId(String assertId) {
        this.assertId = assertId;
    }
    
    public String getPlantMasterName() {
        return plantMasterName;
    }

    public void setPlantMasterName(String plantMasterName) {
        this.plantMasterName = plantMasterName;
    }

}
