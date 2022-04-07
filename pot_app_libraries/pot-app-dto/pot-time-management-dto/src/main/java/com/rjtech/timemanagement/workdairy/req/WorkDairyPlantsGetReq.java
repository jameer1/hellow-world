package com.rjtech.timemanagement.workdairy.req;

import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class WorkDairyPlantsGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private List<Long> projIds;
    private List<Long> crewIds;
    private List<Long> costCodeIds;
    private List<Long> cmpIds;
    private List<Long> plantIds;
    private List<Long> sowIds;
    private String workDairyDate;
    private String fromDate;
    private String toDate;
    private String contractType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getCrewIds() {
        return crewIds;
    }

    public void setCrewIds(List<Long> crewIds) {
        this.crewIds = crewIds;
    }

    public List<Long> getCostCodeIds() {
        return costCodeIds;
    }

    public void setCostCodeIds(List<Long> costCodeIds) {
        this.costCodeIds = costCodeIds;
    }

    public List<Long> getCmpIds() {
        return cmpIds;
    }

    public void setCmpIds(List<Long> cmpIds) {
        this.cmpIds = cmpIds;
    }

    public List<Long> getPlantIds() {
        return plantIds;
    }

    public void setPlantIds(List<Long> plantIds) {
        this.plantIds = plantIds;
    }

    public List<Long> getSowIds() {
        return sowIds;
    }

    public void setSowIds(List<Long> sowIds) {
        this.sowIds = sowIds;
    }

    public String getWorkDairyDate() {
        return workDairyDate;
    }

    public void setWorkDairyDate(String workDairyDate) {
        this.workDairyDate = workDairyDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

}
