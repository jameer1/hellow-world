package com.rjtech.timemanagement.workdairy.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.material.dto.MaterialProjDtlTO;

public class WorkDairyMaterialDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long workDairyId;
    private Long projDocketSchId;
    private Long[] deliveryDocketId;
    private String sourceType;
    private String docketType;
    private String docketNum;
    private String docketDate;
    private Long[] scheduleItemId;
    private String apprStatus;
    private String apprComments;
    private BigDecimal receivedQty;
    private boolean consumpitonUpdated;
    private double totalConsumpiton;
    private String defectComments;
    private String deliveryPlace;
    private String receivedQuantity;
    private boolean supplierDocket;
    private double openingStock;
    private double closingStock;
    private BigDecimal transitQty;
    private BigDecimal availableQty;
    private List<WorkDairyMaterialStatusTO> workDairyMaterialStatusTOs = new ArrayList<WorkDairyMaterialStatusTO>();

    private List<MaterialProjDtlTO> materialProjDtlTO = new ArrayList<MaterialProjDtlTO>();

    public BigDecimal getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(BigDecimal transitQty) {
        this.transitQty = transitQty;
    }

    public double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(double openingStock) {
        this.openingStock = openingStock;
    }

    public double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(double closingStock) {
        this.closingStock = closingStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getProjDocketSchId() {
        return projDocketSchId;
    }

    public void setProjDocketSchId(Long projDocketSchId) {
        this.projDocketSchId = projDocketSchId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDocketType() {
        return docketType;
    }

    public void setDocketType(String docketType) {
        this.docketType = docketType;
    }

    public String getDocketNum() {
        return docketNum;
    }

    public void setDocketNum(String docketNum) {
        this.docketNum = docketNum;
    }

    public String getDocketDate() {
        return docketDate;
    }

    public void setDocketDate(String docketDate) {
        this.docketDate = docketDate;
    }

    public Long[] getDeliveryDocketId() {
        return deliveryDocketId;
    }

    public void setDeliveryDocketId(Long[] deliveryDocketId) {
        this.deliveryDocketId = deliveryDocketId;
    }

    public Long[] getScheduleItemId() {
        return scheduleItemId;
    }

    public void setScheduleItemId(Long[] scheduleItemId) {
        this.scheduleItemId = scheduleItemId;
    }

    public boolean isConsumpitonUpdated() {
        return consumpitonUpdated;
    }

    public void setConsumpitonUpdated(boolean consumpitonUpdated) {
        this.consumpitonUpdated = consumpitonUpdated;
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
    
    public List<MaterialProjDtlTO> getMaterialProjDtlTO() {
        return materialProjDtlTO;
    }

    public void setMaterialProjDtlTO(List<MaterialProjDtlTO> materialProjDtlTO) {
        this.materialProjDtlTO = materialProjDtlTO;
    }

    public List<WorkDairyMaterialStatusTO> getWorkDairyMaterialStatusTOs() {
        return workDairyMaterialStatusTOs;
    }

    public void setWorkDairyMaterialStatusTOs(List<WorkDairyMaterialStatusTO> workDairyMaterialStatusTOs) {
        this.workDairyMaterialStatusTOs = workDairyMaterialStatusTOs;
    }

    public double getTotalConsumpiton() {
        return totalConsumpiton;
    }

    public void setTotalConsumpiton(double totalConsumpiton) {
        this.totalConsumpiton = totalConsumpiton;
    }

    public String getDefectComments() {
        return defectComments;
    }

    public void setDefectComments(String defectComments) {
        this.defectComments = defectComments;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(String receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public boolean isSupplierDocket() {
        return supplierDocket;
    }

    public void setSupplierDocket(boolean supplierDocket) {
        this.supplierDocket = supplierDocket;
    }

    public BigDecimal getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(BigDecimal availableQty) {
        this.availableQty = availableQty;
    }
}