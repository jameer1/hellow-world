package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class PlantRegisterGetReq extends ProjectTO {

    private static final long serialVersionUID = 2090289923177189581L;    
    private List<Long> projIds = new ArrayList<Long>();
    private String purchaseCommissionefromDate;
    private String purchaseCommissionetoDate;
    private String actualMobilisationfromDate;
    private String actualMobilisationtoDate;
    private String deMobilisationfromDate;
    private String deMobilisationtoDate;
    private String plantCurrentStatus;
    private List<Long> companyNameDisplay = new ArrayList<Long>();
    private List<Long> procurecatgId = new ArrayList<Long>();
    private List<Long> plantNameDisplay = new ArrayList<Long>();
    
    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }
    
    public void setCompanyNameDisplay(List<Long> companyNameDisplay) {
        this.companyNameDisplay = companyNameDisplay;
    }
    
    public List<Long> getCompanyNameDisplay() {
        return companyNameDisplay;
    }
    
    public void setPlantNameDisplay(List<Long> plantNameDisplay) {
        this.plantNameDisplay = plantNameDisplay;
    }
    
    public List<Long> getPlantNameDisplay() {
        return plantNameDisplay;
    }
    
    public void setProcurecatgId(List<Long> procurecatgId) {
    	this.procurecatgId = procurecatgId; 
    }
    
    public List<Long> getProcurecatgId(){
    	return procurecatgId;
    }
    
    public void setPurchaseCommissionefromDate(String purchaseCommissionefromDate) {
    	this.purchaseCommissionefromDate = purchaseCommissionefromDate;
    }
      
    public String getPurchaseCommissionefromDate() {
        return purchaseCommissionefromDate;
    }
    
    public void setPurchaseCommissionetoDate(String purchaseCommissionetoDate) {
    	this.purchaseCommissionetoDate = purchaseCommissionetoDate;
    }
    
    public String getPurchaseCommissionetoDate() {
        return purchaseCommissionetoDate;
    }
    
    public void setActualMobilisationfromDate(String actualMobilisationfromDate) {
    	this.actualMobilisationfromDate = actualMobilisationfromDate;
    }
    
    public String getActualMobilisationfromDate() {
    	return actualMobilisationfromDate;
    }
    
    public void setActualMobilisationtoDate(String actualMobilisationtoDate) {
    	this.actualMobilisationtoDate = actualMobilisationtoDate;
    }
    
    public String getActualMobilisationtoDate() {
    	return actualMobilisationtoDate;
    }
    
    public void setDeMobilisationfromDate(String deMobilisationfromDate) {
    	this.deMobilisationfromDate = deMobilisationfromDate;
    }
    
    public String getDeMobilisationfromDate() {
    	return deMobilisationfromDate;
    }
    
    public void setDeMobilisationtoDate(String deMobilisationtoDate) {
    	this.deMobilisationtoDate = deMobilisationtoDate;
    }
    
    public String getDeMobilisationtoDate() {
    	return deMobilisationtoDate;
    }
    
    public void setPlantCurrentStatus(String plantCurrentStatus) {
    	this.plantCurrentStatus = plantCurrentStatus;
    }
    
    public String getPlantCurrentStatus() {
    	return plantCurrentStatus;
    }
    public String toString() {
    	return "projIds:"+projIds+"companyNameDisplay:"+companyNameDisplay+"plantCurrentStatus"+plantCurrentStatus+"procurecatgId"+procurecatgId+"plantNameDisplay"+plantNameDisplay+"purchaseCommissionefromDate:"+purchaseCommissionefromDate+"purchaseCommissionetoDate:"+purchaseCommissionetoDate+"actualMobilisationfromDate:"+actualMobilisationfromDate+"actualMobilisationtoDate:"+actualMobilisationtoDate+"deMobilisationfromDate"+deMobilisationfromDate+"deMobilisationtoDate"+deMobilisationtoDate;
    }
}
