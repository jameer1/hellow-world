package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class PlantProjectSearchResp extends AppResp {
    private static final long serialVersionUID = -5740539835965433671L;

    private Map<Long, LabelKeyTO> userProjectMap = new HashMap<Long, LabelKeyTO>();

    private List<PlantProjPODeliveryResp> projectPODtlTOs = new ArrayList<PlantProjPODeliveryResp>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public PlantProjectSearchResp() {

    }

    public List<PlantProjPODeliveryResp> getProjectPODtlTOs() {
        return projectPODtlTOs;
    }

    public void setProjectPODtlTOs(List<PlantProjPODeliveryResp> projectPODtlTOs) {
        this.projectPODtlTOs = projectPODtlTOs;
    }

    public Map<Long, LabelKeyTO> getUserProjectMap() {
        return userProjectMap;
    }

    public void setUserProjectMap(Map<Long, LabelKeyTO> userProjectMap) {
        this.userProjectMap = userProjectMap;
    }

}
