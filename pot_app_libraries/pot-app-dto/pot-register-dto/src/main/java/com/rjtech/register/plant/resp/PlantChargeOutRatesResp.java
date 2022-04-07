package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.RegisterProjectLibOnLoadTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantChargeOutRatesTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;

public class PlantChargeOutRatesResp extends AppResp {

    private static final long serialVersionUID = 5055584907501923796L;

    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
    private List<PlantChargeOutRatesTO> plantChargeOutRatesTOs = new ArrayList<PlantChargeOutRatesTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<String> category = new ArrayList<String>();
    private RegisterProjectLibOnLoadTO projectLibTO = new RegisterProjectLibOnLoadTO();
    private LabelKeyTO projGeneralLabelKeyTO = null;

    public PlantChargeOutRatesResp() {

    }

    public List<PlantChargeOutRatesTO> getPlantChargeOutRatesTOs() {
        return plantChargeOutRatesTOs;
    }

    public void setPlantChargeOutRatesTOs(List<PlantChargeOutRatesTO> plantChargeOutRatesTOs) {
        this.plantChargeOutRatesTOs = plantChargeOutRatesTOs;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public LabelKeyTO getProjGeneralLabelKeyTO() {
        return projGeneralLabelKeyTO;
    }

    public void setProjGeneralLabelKeyTO(LabelKeyTO projGeneralLabelKeyTO) {
        this.projGeneralLabelKeyTO = projGeneralLabelKeyTO;
    }

    public RegisterProjectLibOnLoadTO getProjectLibTO() {
        return projectLibTO;
    }

    public void setProjectLibTO(RegisterProjectLibOnLoadTO projectLibTO) {
        this.projectLibTO = projectLibTO;
    }

}
