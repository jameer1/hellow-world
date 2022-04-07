package com.rjtech.projectlib.dto;

import com.rjtech.common.utils.CommonUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class CostReportTO implements Serializable, Cloneable {

    private static final long serialVersionUID = 5L;

    private String date;

    private Long epsId;
    private String epsName;

    private Long projId;
    private String projName;

    private Long cmpId;
    private String cmpName;

    private Long costSubGroupId;
    private String costSubGroupCode;
    private String costSubGroupName;

    private Long costItemId;
    private String costItemCode;
    private String costItemName;

    /**
     * Resource ID should be code of emp, plant, mat
     */
    private String resourceId;

    private Long empId;
    private String empFirstName;
    private String empLastName;
    private String category;

    private String type;

    private Long plantId;
    private String plantDesc;
    private String plantRegNumber;
    private String plantMake;
    private String plantModel;
    private String plantRateType;

    private Long matId;
    private Long materialSubGroupId;
    private String materialSubGroupCode;
    private String materialSubGroupName;

    private Long invoiceNumber;
    private String invoiceDate;

    private Double ratePerUnit = Double.valueOf(0);
    private String unitOfMesure;
    private Double quantity = Double.valueOf(0);
    private Double costAmount = Double.valueOf(0);
    private String currencyCode;
    private Double earnedValue = Double.valueOf(0);

    private Date dateObj;

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }

    public String getDate() {
        return date;
    }

    public Date getDateObj() {
        return CommonUtil.convertStringToDate(date);
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Long getEpsId() {
        return epsId;
    }

    public void setEpsId(Long epsId) {
        this.epsId = epsId;
    }

    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public Long getCmpId() {
        return cmpId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public Long getCostSubGroupId() {
        return costSubGroupId;
    }

    public void setCostSubGroupId(Long costSubGroupId) {
        this.costSubGroupId = costSubGroupId;
    }

    public String getCostSubGroupCode() {
        return costSubGroupCode;
    }

    public void setCostSubGroupCode(String costSubGroupCode) {
        this.costSubGroupCode = costSubGroupCode;
    }

    public String getCostSubGroupName() {
        return costSubGroupName;
    }

    public void setCostSubGroupName(String costSubGroupName) {
        this.costSubGroupName = costSubGroupName;
    }

    public Long getCostItemId() {
        return costItemId;
    }

    public void setCostItemId(Long costItemId) {
        this.costItemId = costItemId;
    }

    public String getCostItemCode() {
        return costItemCode;
    }

    public void setCostItemCode(String costItemCode) {
        this.costItemCode = costItemCode;
    }

    public String getCostItemName() {
        return costItemName;
    }

    public void setCostItemName(String costItemName) {
        this.costItemName = costItemName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getPlantDesc() {
        return plantDesc;
    }

    public void setPlantDesc(String plantDesc) {
        this.plantDesc = plantDesc;
    }

    public String getPlantRegNumber() {
        return plantRegNumber;
    }

    public void setPlantRegNumber(String plantRegNumber) {
        this.plantRegNumber = plantRegNumber;
    }

    public String getPlantMake() {
        return plantMake;
    }

    public void setPlantMake(String plantMake) {
        this.plantMake = plantMake;
    }

    public String getPlantModel() {
        return plantModel;
    }

    public void setPlantModel(String plantModel) {
        this.plantModel = plantModel;
    }

    public String getPlantRateType() {
        return plantRateType;
    }

    public void setPlantRateType(String plantRateType) {
        this.plantRateType = plantRateType;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public Long getMaterialSubGroupId() {
        return materialSubGroupId;
    }

    public void setMaterialSubGroupId(Long materialSubGroupId) {
        this.materialSubGroupId = materialSubGroupId;
    }

    public String getMaterialSubGroupCode() {
        return materialSubGroupCode;
    }

    public void setMaterialSubGroupCode(String materialSubGroupCode) {
        this.materialSubGroupCode = materialSubGroupCode;
    }

    public String getMaterialSubGroupName() {
        return materialSubGroupName;
    }

    public void setMaterialSubGroupName(String materialSubGroupName) {
        this.materialSubGroupName = materialSubGroupName;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getRatePerUnit() {
        return ratePerUnit;
    }

    public void setRatePerUnit(Double ratePerUnit) {
        this.ratePerUnit = ratePerUnit;
    }

    public String getUnitOfMesure() {
        return unitOfMesure;
    }

    public void setUnitOfMesure(String unitOfMesure) {
        this.unitOfMesure = unitOfMesure;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getCostAmount() {
        return Double.valueOf(new DecimalFormat("#.##").format(costAmount));
    }

    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getEarnedValue() {
        return Double.valueOf(new DecimalFormat("#.##").format(earnedValue));
    }

    public void setEarnedValue(Double earnedValue) {
        this.earnedValue = earnedValue;
    }

}
