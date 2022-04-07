package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantLogRecordsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5456644052289229021L;

    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
    private List<PlantLogRecordsTO> plantLogRecordsTOs = new ArrayList<PlantLogRecordsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private String fromDate;
    private String toDate;
    private Map<Long, LabelKeyTO> userProjMap;

    public List<PlantLogRecordsTO> getPlantLogRecordsTOs() {
        return plantLogRecordsTOs;
    }

    public void setPlantLogRecordsTOs(List<PlantLogRecordsTO> plantLogRecordsTOs) {
        this.plantLogRecordsTOs = plantLogRecordsTOs;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
