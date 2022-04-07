package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.RegisterProjectLibOnLoadTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantDeploymentResp extends AppResp {

    private static final long serialVersionUID = 3368741959489588610L;

    private List<String> deMobStatus = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<PlantRegProjTO> plantRegProjTOs = new ArrayList<PlantRegProjTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private RegisterProjectLibOnLoadTO projectLibTO = new RegisterProjectLibOnLoadTO();

    public List<String> getDeMobStatus() {
        return deMobStatus;
    }

    public void setDeMobStatus(List<String> deMobStatus) {
        this.deMobStatus = deMobStatus;
    }

    public List<PlantRegProjTO> getPlantRegProjTOs() {
        return plantRegProjTOs;
    }

    public void setPlantRegProjTOs(List<PlantRegProjTO> plantRegProjTOs) {
        this.plantRegProjTOs = plantRegProjTOs;
    }

    public RegisterProjectLibOnLoadTO getProjectLibTO() {
        return projectLibTO;
    }

    public void setProjectLibTO(RegisterProjectLibOnLoadTO projectLibTO) {
        this.projectLibTO = projectLibTO;
    }

}
