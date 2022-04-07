package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialPODeliveryDocketTO;

public class MaterialDeliveryDocketResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<MaterialPODeliveryDocketTO> materialPODeliveryDocketTOs = new ArrayList<MaterialPODeliveryDocketTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private RegisterOnLoadTO registerOnLoadTO;
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MaterialPODeliveryDocketTO> getMaterialPODeliveryDocketTOs() {
        return materialPODeliveryDocketTOs;
    }

    public void setMaterialPODeliveryDocketTOs(List<MaterialPODeliveryDocketTO> materialPODeliveryDocketTOs) {
        this.materialPODeliveryDocketTOs = materialPODeliveryDocketTOs;
    }

    public RegisterOnLoadTO getRegisterOnLoadTO() {
        return registerOnLoadTO;
    }

    public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
        this.registerOnLoadTO = registerOnLoadTO;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
