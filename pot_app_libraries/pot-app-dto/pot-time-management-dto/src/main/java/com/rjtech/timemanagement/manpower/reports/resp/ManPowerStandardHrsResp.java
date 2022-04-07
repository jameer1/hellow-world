package com.rjtech.timemanagement.manpower.reports.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.timemanagement.manpower.reports.dto.ManPowerActualHrsTO;

public class ManPowerStandardHrsResp implements Serializable {

    private static final long serialVersionUID = 1L;

    List<ManPowerActualHrsTO> manPowerActualHrsTOs = new ArrayList<>();
    
    Map<Object, Object> standardHrsMap = new HashMap<>();

    public List<ManPowerActualHrsTO> getManPowerActualHrsTOs() {
        return manPowerActualHrsTOs;
    }

    public void setManPowerActualHrsTOs(List<ManPowerActualHrsTO> manPowerActualHrsTOs) {
        this.manPowerActualHrsTOs = manPowerActualHrsTOs;
    }

    public Map<Object, Object> getStandardHrsMap() {
        return standardHrsMap;
    }

    public void setStandardHrsMap(Map<Object, Object> standardHrsMap) {
        this.standardHrsMap = standardHrsMap;
    }

}
