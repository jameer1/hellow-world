package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class PlantUtilisationRecordsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -2436721296049842612L;
    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

}
