package com.rjtech.material.ledger;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MaterialLedgerResTo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 399693663566531325L;

    private Date date;
    private Long epsId;
    private List<Long> projId;
    private String eps;
    private String projName;
    private String storeLocation;
    private String resourceSubGroup;
    private String resourceMaterial;
    private String supplierName;
    private String purchaseOrderCode;
    private String scheduleItemId;
    private String docketType;
    private String docketNumber;
    private String workDairyId;
    private String unitOfMeasure;
    private String currency;
    private String rate;
    private String openingStock;
    private String supplied;
    private String issued;
    private String consumption;
    private String consume;
    private String stockOnTransit;
    private String externalProjTransfer;
    private String closingStock;
    private Long materialId;
    private Long materialClassId;
    private String docketId;
    private String originProject;
    private String destinationProject;
    private String originLocation;
    private String destinationLocation;
    private String issuerEmpName;
    private String receiverEmpName;
    private String cumulativeConsumption;
    private Long stockId;
    private Long projStockId;
    private Long cumulativeSupply;
    private Long transferQty;
    private String costCodeDesc;
    private String costCodeName;
    private String costCodeGroup;
    private Long costCodeId;
    private double previousPeriod;
    private double upToDatePeriod;

    public Long getCostCodeId() {
        return costCodeId;
    }

    public void setCostCodeId(Long costCodeId) {
        this.costCodeId = costCodeId;
    }

    public Long getCumulativeSupply() {
        return cumulativeSupply;
    }

    public void setCumulativeSupply(Long cumulativeSupply) {
        this.cumulativeSupply = cumulativeSupply;
    }

    public Long getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(Long transferQty) {
        this.transferQty = transferQty;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getProjStockId() {
        return projStockId;
    }

    public void setProjStockId(Long projStockId) {
        this.projStockId = projStockId;
    }

    public Long getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(Long materialClassId) {
        this.materialClassId = materialClassId;
    }

    public String getCumulativeConsumption() {
        return cumulativeConsumption;
    }

    public void setCumulativeConsumption(String cumulativeConsumption) {
        this.cumulativeConsumption = cumulativeConsumption;
    }

    public String getOriginProject() {
        return originProject;
    }

    public void setOriginProject(String originProject) {
        this.originProject = originProject;
    }

    public String getDestinationProject() {
        return destinationProject;
    }

    public void setDestinationProject(String destinationProject) {
        this.destinationProject = destinationProject;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getIssuerEmpName() {
        return issuerEmpName;
    }

    public void setIssuerEmpName(String issuerEmpName) {
        this.issuerEmpName = issuerEmpName;
    }

    public String getReceiverEmpName() {
        return receiverEmpName;
    }

    public void setReceiverEmpName(String receiverEmpName) {
        this.receiverEmpName = receiverEmpName;
    }

    public String getDocketId() {
        return docketId;
    }

    public void setDocketId(String docketId) {
        this.docketId = docketId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getResourceSubGroup() {
        return resourceSubGroup;
    }

    public void setResourceSubGroup(String resourceSubGroup) {
        this.resourceSubGroup = resourceSubGroup;
    }

    public String getResourceMaterial() {
        return resourceMaterial;
    }

    public void setResourceMaterial(String resourceMaterial) {
        this.resourceMaterial = resourceMaterial;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public String getScheduleItemId() {
        return scheduleItemId;
    }

    public void setScheduleItemId(String scheduleItemId) {
        this.scheduleItemId = scheduleItemId;
    }

    public String getDocketType() {
        return docketType;
    }

    public void setDocketType(String docketType) {
        this.docketType = docketType;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
    }

    public String getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(String workDairyId) {
        this.workDairyId = workDairyId;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(String openingStock) {
        this.openingStock = openingStock;
    }

    public String getSupplied() {
        return supplied;
    }

    public void setSupplied(String supplied) {
        this.supplied = supplied;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
    
    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getStockOnTransit() {
        return stockOnTransit;
    }

    public void setStockOnTransit(String stockOnTransit) {
        this.stockOnTransit = stockOnTransit;
    }

    public String getExternalProjTransfer() {
        return externalProjTransfer;
    }

    public void setExternalProjTransfer(String externalProjTransfer) {
        this.externalProjTransfer = externalProjTransfer;
    }

    public String getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(String closingStock) {
        this.closingStock = closingStock;
    }

    public String getCostCodeDesc() {
        return costCodeDesc;
    }

    public void setCostCodeDesc(String costCodeDesc) {
        this.costCodeDesc = costCodeDesc;
    }

    public String getCostCodeName() {
        return costCodeName;
    }

    public void setCostCodeName(String costCodeName) {
        this.costCodeName = costCodeName;
    }

    public String getCostCodeGroup() {
        return costCodeGroup;
    }

    public void setCostCodeGroup(String costCodeGroup) {
        this.costCodeGroup = costCodeGroup;
    }

    public Long getEpsId() {
        return epsId;
    }

    public void setEpsId(Long epsId) {
        this.epsId = epsId;
    }

    public List<Long> getProjId() {
        return projId;
    }

    public void setProjId(List<Long> projId) {
        this.projId = projId;
    }

    public double getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(double previousPeriod) {
        this.previousPeriod = previousPeriod;
    }

    public double getUpToDatePeriod() {
        return upToDatePeriod;
    }

    public void setUpToDatePeriod(double upToDatePeriod) {
        this.upToDatePeriod = upToDatePeriod;
    }
}