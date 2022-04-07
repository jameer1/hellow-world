package com.rjtech.procurement.dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.dto.WorkFlowStatusTO;

public class ProcurementNormalTimeTO extends ProjectTO {

    private static final long serialVersionUID = -6748335769511761914L;

    private Long id;
    private Integer cutOffDays;
    private Integer cutOffHours;
    private Integer cutOffMinutes;
    private Time cutOffTime;
    private String defaultStatus;
    private String isDefault;
    private String type;
    private Integer status;
    private Long typeId;
    private Boolean normalTimeFlag;

    private List<ProcurementNormalTimeTO> procurementNormalTimeTOs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public Integer getCutOffDays() {
        return cutOffDays;
    }

    public void setCutOffDays(Integer cutOffDays) {
        this.cutOffDays = cutOffDays;
    }
    
    public Integer getCutOffHours() {
        return cutOffHours;
    }

    public void setCutOffHours(Integer cutOffHours) {
        this.cutOffHours = cutOffHours;
    }

    public Integer getCutOffMinutes() {
        return cutOffMinutes;
    }

    public void setCutOffMinutes(Integer cutOffMinutes) {
        this.cutOffMinutes = cutOffMinutes;
    }

    public Time getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Time cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }
    
    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getType() {
        return type;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public List<ProcurementNormalTimeTO> procurementNormalTimeTOs() {
        return procurementNormalTimeTOs;
    }

    public void procurementNormalTimeTOs(List<ProcurementNormalTimeTO> procurementNormalTimeTOs) {
        this.procurementNormalTimeTOs = procurementNormalTimeTOs;
    }
    
    public Boolean getNormalTimeFlag() {
        return normalTimeFlag;
    }

    public void setNormalTimeFlag(Boolean normalTimeFlag) {
        this.normalTimeFlag = normalTimeFlag;
    }
}
