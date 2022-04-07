package com.rjtech.register.plant.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantTransferReqApprTO;

public class PlantTranferReqApprSaveReq extends ProjectTO {

    private static final long serialVersionUID = -3398478566463023016L;

    private List<PlantTransferReqApprTO> plantTransferReqApprTOs = new ArrayList<>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PlantTransferReqApprTO> getPlantTransferReqApprTOs() {
        return plantTransferReqApprTOs;
    }

    public void setPlantTransferReqApprTOs(List<PlantTransferReqApprTO> plantTransferReqApprTOs) {
        this.plantTransferReqApprTOs = plantTransferReqApprTOs;
    }

}
