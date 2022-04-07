package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.constants.ApplicationConstants;

public class MaterialProjConsumptionResp extends MaterialOnLoadResp {

    private static final long serialVersionUID = -6820238095347272817L;

    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
