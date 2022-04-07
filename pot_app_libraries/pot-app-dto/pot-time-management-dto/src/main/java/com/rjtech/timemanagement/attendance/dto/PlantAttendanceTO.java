package com.rjtech.timemanagement.attendance.dto;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;

public class PlantAttendanceTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long attandanceId;
    private Long plantId;
    private Long crewId;
    private String plantName;
    private String plantCode;
    private String classType;
    private String cmpCatgName;
    private String plntManfature;
    private String plntModel;
    private String plntRegNo;
    private String procureCatg;

    private Map<String, PlantAttendanceDtlTO> plantAttendenceDtlMap = new HashMap<String, PlantAttendanceDtlTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttandanceId() {
        return attandanceId;
    }

    public void setAttandanceId(Long attandanceId) {
        this.attandanceId = attandanceId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCmpCatgName() {
        return cmpCatgName;
    }

    public void setCmpCatgName(String cmpCatgName) {
        this.cmpCatgName = cmpCatgName;
    }

    public String getPlntManfature() {
        return plntManfature;
    }

    public void setPlntManfature(String plntManfature) {
        this.plntManfature = plntManfature;
    }

    public String getPlntModel() {
        return plntModel;
    }

    public void setPlntModel(String plntModel) {
        this.plntModel = plntModel;
    }

    public String getPlntRegNo() {
        return plntRegNo;
    }

    public void setPlntRegNo(String plntRegNo) {
        this.plntRegNo = plntRegNo;
    }

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

    public Map<String, PlantAttendanceDtlTO> getPlantAttendenceDtlMap() {
        return plantAttendenceDtlMap;
    }

    public void setPlantAttendenceDtlMap(Map<String, PlantAttendanceDtlTO> plantAttendenceDtlMap) {
        this.plantAttendenceDtlMap = plantAttendenceDtlMap;
    }

}
