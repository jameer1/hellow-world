package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantTransferReqApprTO;

public class PlantTransferReqApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -8154796138493442541L;

    private List<PlantTransferReqApprTO> plantTransferReqApprTOs = null;
    private Map<Long, LabelKeyTO> transferPlantMap = null;
    private Map<Long, LabelKeyTO> userMap = null;
    private List<String> statusType = null;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, LabelKeyTO> plantClassMap = null;
    private Map<Long, LabelKeyTO> plantCompanyMap = null;

    public PlantTransferReqApprResp() {
        plantTransferReqApprTOs = new ArrayList<PlantTransferReqApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        transferPlantMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userMap = new HashMap<Long, LabelKeyTO>();
        statusType = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        plantClassMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        plantCompanyMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PlantTransferReqApprTO> getPlantTransferReqApprTOs() {
        return plantTransferReqApprTOs;
    }

    public void setPlantTransferReqApprTOs(List<PlantTransferReqApprTO> plantTransferReqApprTOs) {
        this.plantTransferReqApprTOs = plantTransferReqApprTOs;
    }

    public Map<Long, LabelKeyTO> getTransferPlantMap() {
        return transferPlantMap;
    }

    public void setTransferPlantMap(Map<Long, LabelKeyTO> transferPlantMap) {
        this.transferPlantMap = transferPlantMap;
    }

    public Map<Long, LabelKeyTO> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Long, LabelKeyTO> userMap) {
        this.userMap = userMap;
    }

    public List<String> getStatusType() {
        return statusType;
    }

    public void setStatusType(List<String> statusType) {
        this.statusType = statusType;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getPlantClassMap() {
        return plantClassMap;
    }

    public void setPlantClassMap(Map<Long, LabelKeyTO> plantClassMap) {
        this.plantClassMap = plantClassMap;
    }

    public Map<Long, LabelKeyTO> getPlantCompanyMap() {
        return plantCompanyMap;
    }

    public void setPlantCompanyMap(Map<Long, LabelKeyTO> plantCompanyMap) {
        this.plantCompanyMap = plantCompanyMap;
    }

}
