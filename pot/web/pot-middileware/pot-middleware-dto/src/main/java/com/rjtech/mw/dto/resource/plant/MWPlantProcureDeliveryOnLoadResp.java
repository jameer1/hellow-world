package com.rjtech.mw.dto.resource.plant;

import com.rjtech.centrallib.dto.RegisterProjectLibOnLoadTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;

public class MWPlantProcureDeliveryOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MWPlantProcureDeliveryOnLoadResp() {

    }

    private PlantProjPODtlTO plantProjPODtlTO = new PlantProjPODtlTO();
    private RegisterProjectLibOnLoadTO projectLibTO = new RegisterProjectLibOnLoadTO();

    public RegisterProjectLibOnLoadTO getProjectLibTO() {
        return projectLibTO;
    }

    public void setProjectLibTO(RegisterProjectLibOnLoadTO projectLibTO) {
        this.projectLibTO = projectLibTO;
    }

    public PlantProjPODtlTO getPlantProjPODtlTO() {
        return plantProjPODtlTO;
    }

    public void setPlantProjPODtlTO(PlantProjPODtlTO plantProjPODtlTO) {
        this.plantProjPODtlTO = plantProjPODtlTO;
    }

}
