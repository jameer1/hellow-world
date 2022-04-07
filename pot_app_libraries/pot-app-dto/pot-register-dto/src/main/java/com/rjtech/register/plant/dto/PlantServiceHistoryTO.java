package com.rjtech.register.plant.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PlantServiceHistoryTO extends ProjectTO {

    private static final long serialVersionUID = -5866623752170254546L;
    private Long id;
    private Long plantId;
    private String date;
    private Long currentPlantServiceId;
    private String currentPlantServiceName;
    private String currentPlantServiceParentName;
    private Long prevPlantServiceId;
    private String prevPlantServiceName;
    private String prevPlantServiceParentName;
    private BigDecimal currentOdoMeter;
    private BigDecimal prevOdoMeter;
    private String comments;
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCurrentPlantServiceId() {
        return currentPlantServiceId;
    }

    public void setCurrentPlantServiceId(Long currentPlantServiceId) {
        this.currentPlantServiceId = currentPlantServiceId;
    }

    public String getCurrentPlantServiceName() {
        return currentPlantServiceName;
    }

    public void setCurrentPlantServiceName(String currentPlantServiceName) {
        this.currentPlantServiceName = currentPlantServiceName;
    }

    public String getCurrentPlantServiceParentName() {
        return currentPlantServiceParentName;
    }

    public void setCurrentPlantServiceParentName(String currentPlantServiceParentName) {
        this.currentPlantServiceParentName = currentPlantServiceParentName;
    }

    public Long getPrevPlantServiceId() {
        return prevPlantServiceId;
    }

    public void setPrevPlantServiceId(Long prevPlantServiceId) {
        this.prevPlantServiceId = prevPlantServiceId;
    }

    public String getPrevPlantServiceName() {
        return prevPlantServiceName;
    }

    public void setPrevPlantServiceName(String prevPlantServiceName) {
        this.prevPlantServiceName = prevPlantServiceName;
    }

    public String getPrevPlantServiceParentName() {
        return prevPlantServiceParentName;
    }

    public void setPrevPlantServiceParentName(String prevPlantServiceParentName) {
        this.prevPlantServiceParentName = prevPlantServiceParentName;
    }

    public BigDecimal getCurrentOdoMeter() {
        return currentOdoMeter;
    }

    public void setCurrentOdoMeter(BigDecimal currentOdoMeter) {
        this.currentOdoMeter = currentOdoMeter;
    }

    public BigDecimal getPrevOdoMeter() {
        return prevOdoMeter;
    }

    public void setPrevOdoMeter(BigDecimal prevOdoMeter) {
        this.prevOdoMeter = prevOdoMeter;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

}
