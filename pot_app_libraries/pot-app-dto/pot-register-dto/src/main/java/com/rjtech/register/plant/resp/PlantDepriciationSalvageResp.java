package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.RegisterProjectLibOnLoadTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantDepriciationSalvageTO;

public class PlantDepriciationSalvageResp extends AppResp {

    private static final long serialVersionUID = -44823344120798234L;
    private List<PlantDepriciationSalvageTO> plantDepriciationSalvageTOs = new ArrayList<PlantDepriciationSalvageTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private LabelKeyTO projGenCurrencyLabelKeyTO = null;
    private RegisterProjectLibOnLoadTO projectLibTO = new RegisterProjectLibOnLoadTO();

    public List<PlantDepriciationSalvageTO> getPlantDepriciationSalvageTOs() {
        return plantDepriciationSalvageTOs;
    }

    public void setPlantDepriciationSalvageTOs(List<PlantDepriciationSalvageTO> plantDepriciationSalvageTOs) {
        this.plantDepriciationSalvageTOs = plantDepriciationSalvageTOs;
    }

    public LabelKeyTO getProjGenCurrencyLabelKeyTO() {
        return projGenCurrencyLabelKeyTO;
    }

    public void setProjGenCurrencyLabelKeyTO(LabelKeyTO projGenCurrencyLabelKeyTO) {
        this.projGenCurrencyLabelKeyTO = projGenCurrencyLabelKeyTO;
    }

    public RegisterProjectLibOnLoadTO getProjectLibTO() {
        return projectLibTO;
    }

    public void setProjectLibTO(RegisterProjectLibOnLoadTO projectLibTO) {
        this.projectLibTO = projectLibTO;
    }

}
