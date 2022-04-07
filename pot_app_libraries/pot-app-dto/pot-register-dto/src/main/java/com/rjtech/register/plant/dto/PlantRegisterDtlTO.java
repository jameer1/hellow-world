package com.rjtech.register.plant.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PlantRegisterDtlTO extends ProjectTO {
    /**
     * 
     */
    private static final long serialVersionUID = -718977934667231956L;
    private Long id;
    private Long plantClassMstrId;
    private String plantClassMstrCode;
    private String plantClassMstrName;
    private Long cmpId;
    private String cmpCode;
    private String cmpName;
    private Long ownerCmpId;
    private String ownerCmpCode;
    private String ownerCmpName;
    private String desc;
    private Long procurecatgId;
    private String procurecatgCode;
    private String procurecatgName;
    private String assertId;
    private String regNumber;
    private String manfacture;
    private String model;
    private String assetType;
    private BigDecimal currentOdoMeter;
    private String plantClassMeasureName;

    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantClassMstrId() {
        return plantClassMstrId;
    }

    public void setPlantClassMstrId(Long plantClassMstrId) {
        this.plantClassMstrId = plantClassMstrId;
    }

    public String getPlantClassMstrCode() {
        return plantClassMstrCode;
    }

    public void setPlantClassMstrCode(String plantClassMstrCode) {
        this.plantClassMstrCode = plantClassMstrCode;
    }

    public String getPlantClassMstrName() {
        return plantClassMstrName;
    }

    public void setPlantClassMstrName(String plantClassMstrName) {
        this.plantClassMstrName = plantClassMstrName;
    }

    public Long getCmpId() {
        return cmpId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public Long getOwnerCmpId() {
        return ownerCmpId;
    }

    public void setOwnerCmpId(Long ownerCmpId) {
        this.ownerCmpId = ownerCmpId;
    }

    public String getOwnerCmpCode() {
        return ownerCmpCode;
    }

    public void setOwnerCmpCode(String ownerCmpCode) {
        this.ownerCmpCode = ownerCmpCode;
    }

    public String getOwnerCmpName() {
        return ownerCmpName;
    }

    public void setOwnerCmpName(String ownerCmpName) {
        this.ownerCmpName = ownerCmpName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getProcurecatgId() {
        return procurecatgId;
    }

    public void setProcurecatgId(Long procurecatgId) {
        this.procurecatgId = procurecatgId;
    }

    public String getProcurecatgCode() {
        return procurecatgCode;
    }

    public void setProcurecatgCode(String procurecatgCode) {
        this.procurecatgCode = procurecatgCode;
    }

    public String getProcurecatgName() {
        return procurecatgName;
    }

    public void setProcurecatgName(String procurecatgName) {
        this.procurecatgName = procurecatgName;
    }

    public String getAssertId() {
        return assertId;
    }

    public void setAssertId(String assertId) {
        this.assertId = assertId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getManfacture() {
        return manfacture;
    }

    public void setManfacture(String manfacture) {
        this.manfacture = manfacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public BigDecimal getCurrentOdoMeter() {
        return currentOdoMeter;
    }

    public void setCurrentOdoMeter(BigDecimal currentOdoMeter) {
        this.currentOdoMeter = currentOdoMeter;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public String getPlantClassMeasureName() {
        return plantClassMeasureName;
    }

    public void setPlantClassMeasureName(String plantClassMeasureName) {
        this.plantClassMeasureName = plantClassMeasureName;
    }

}
