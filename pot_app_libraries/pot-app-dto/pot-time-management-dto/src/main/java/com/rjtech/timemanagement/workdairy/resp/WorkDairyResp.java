package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyCostCodeTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private WorkDairyTO workDairyTO = new WorkDairyTO();
    private List<WorkDairyCostCodeTO> workDairyCostCodeTOs = new ArrayList<WorkDairyCostCodeTO>();
    private List<LabelKeyTO> empRegDetails = new ArrayList<LabelKeyTO>();
    private List<LabelKeyTO> plantRegDetails = new ArrayList<LabelKeyTO>();

    private Map<Long, LabelKeyTO> costCodeMap = null;
    private Map<Long, LabelKeyTO> empWageFactorMap = null;
    private Map<Long, LabelKeyTO> empRegMap = null;
    private Map<Long, LabelKeyTO> plantRegMap = null;
    private Map<Long, LabelKeyTO> materialRegMap = null;
    private Map<Long, LabelKeyTO> sowMap = null;
    private Map<Long, LabelKeyTO> weatherMap = null;
    private Map<Long, LabelKeyTO> projShiftMap = null;
    private MaterialProjResp materialProjResp = null;

    public WorkDairyResp() {
        materialProjResp = new MaterialProjResp();
        costCodeMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        empWageFactorMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        empRegMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        plantRegMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        materialRegMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        sowMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        weatherMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projShiftMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

    public List<WorkDairyCostCodeTO> getWorkDairyCostCodeTOs() {
        return workDairyCostCodeTOs;
    }

    public void setWorkDairyCostCodeTOs(List<WorkDairyCostCodeTO> workDairyCostCodeTOs) {
        this.workDairyCostCodeTOs = workDairyCostCodeTOs;
    }

    public List<LabelKeyTO> getEmpRegDetails() {
        return empRegDetails;
    }

    public void setEmpRegDetails(List<LabelKeyTO> empRegDetails) {
        this.empRegDetails = empRegDetails;
    }

    public List<LabelKeyTO> getPlantRegDetails() {
        return plantRegDetails;
    }

    public void setPlantRegDetails(List<LabelKeyTO> plantRegDetails) {
        this.plantRegDetails = plantRegDetails;
    }

    public Map<Long, LabelKeyTO> getCostCodeMap() {
        return costCodeMap;
    }

    public void setCostCodeMap(Map<Long, LabelKeyTO> costCodeMap) {
        this.costCodeMap = costCodeMap;
    }

    public Map<Long, LabelKeyTO> getEmpWageFactorMap() {
        return empWageFactorMap;
    }

    public void setEmpWageFactorMap(Map<Long, LabelKeyTO> empWageFactorMap) {
        this.empWageFactorMap = empWageFactorMap;
    }

    public Map<Long, LabelKeyTO> getEmpRegMap() {
        return empRegMap;
    }

    public void setEmpRegMap(Map<Long, LabelKeyTO> empRegMap) {
        this.empRegMap = empRegMap;
    }

    public Map<Long, LabelKeyTO> getPlantRegMap() {
        return plantRegMap;
    }

    public void setPlantRegMap(Map<Long, LabelKeyTO> plantRegMap) {
        this.plantRegMap = plantRegMap;
    }

    public Map<Long, LabelKeyTO> getMaterialRegMap() {
        return materialRegMap;
    }

    public void setMaterialRegMap(Map<Long, LabelKeyTO> materialRegMap) {
        this.materialRegMap = materialRegMap;
    }

    public Map<Long, LabelKeyTO> getSowMap() {
        return sowMap;
    }

    public void setSowMap(Map<Long, LabelKeyTO> sowMap) {
        this.sowMap = sowMap;
    }

    public Map<Long, LabelKeyTO> getWeatherMap() {
        return weatherMap;
    }

    public void setWeatherMap(Map<Long, LabelKeyTO> weatherMap) {
        this.weatherMap = weatherMap;
    }

    public Map<Long, LabelKeyTO> getProjShiftMap() {
        return projShiftMap;
    }

    public void setProjShiftMap(Map<Long, LabelKeyTO> projShiftMap) {
        this.projShiftMap = projShiftMap;
    }

    public MaterialProjResp getMaterialProjResp() {
        return materialProjResp;
    }

    public void setMaterialProjResp(MaterialProjResp materialProjResp) {
        this.materialProjResp = materialProjResp;
    }

}
