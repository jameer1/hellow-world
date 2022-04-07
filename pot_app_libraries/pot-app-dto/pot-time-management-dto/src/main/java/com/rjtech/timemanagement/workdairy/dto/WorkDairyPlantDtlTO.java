package com.rjtech.timemanagement.workdairy.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyPlantDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long workDairyId;
    private Long plantRegId;
    private String shiftName;
    private Long parentId;
    private Long apprUserId;
    private String apprStatus;
    private String apprComments;

    private String code;
    private String name;
    private String plantRegNum;
    private String classType;
    private String manufacture;
    private String model;
    private String cmpCategoryName;
    private String procureCatg;
    private String measureUnit;
    private Long plantClassId;

    private List<WorkDairyPlantStatusTO> workDairyPlantStatusTOs = new ArrayList<WorkDairyPlantStatusTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getPlantRegId() {
        return plantRegId;
    }

    public void setPlantRegId(Long plantRegId) {
        this.plantRegId = plantRegId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public List<WorkDairyPlantStatusTO> getWorkDairyPlantStatusTOs() {
        return workDairyPlantStatusTOs;
    }

    public void setWorkDairyPlantStatusTOs(List<WorkDairyPlantStatusTO> workDairyPlantStatusTOs) {
        this.workDairyPlantStatusTOs = workDairyPlantStatusTOs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlantRegNum() {
        return plantRegNum;
    }

    public void setPlantRegNum(String plantRegNum) {
        this.plantRegNum = plantRegNum;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCmpCategoryName() {
        return cmpCategoryName;
    }

    public void setCmpCategoryName(String cmpCategoryName) {
        this.cmpCategoryName = cmpCategoryName;
    }

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Long getPlantClassId() {
        return plantClassId;
    }

    public void setPlantClassId(Long plantClassId) {
        this.plantClassId = plantClassId;
    }

}