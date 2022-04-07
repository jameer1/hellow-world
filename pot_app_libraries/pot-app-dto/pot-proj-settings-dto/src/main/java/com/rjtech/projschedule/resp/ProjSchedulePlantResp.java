package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjSchedulePlantTO;

public class ProjSchedulePlantResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6428639300155396451L;
    private List<ProjSchedulePlantTO> projSchedulePlantTOs = null;
    private List<LabelKeyTO> labelKeyTOs = null;

    public ProjSchedulePlantResp() {
        projSchedulePlantTOs = new ArrayList<ProjSchedulePlantTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        labelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjSchedulePlantTO> getProjSchedulePlantTOs() {
        return projSchedulePlantTOs;
    }

    public void setProjSchedulePlantTOs(List<ProjSchedulePlantTO> projSchedulePlantTOs) {
        this.projSchedulePlantTOs = projSchedulePlantTOs;
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
