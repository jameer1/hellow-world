package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleMaterialTO;

public class ProjScheduleMaterialResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6428639300155396451L;
    private List<ProjScheduleMaterialTO> projScheduleMaterialTOs = null;
    private List<LabelKeyTO> labelKeyTOs = null;

    public ProjScheduleMaterialResp() {
        projScheduleMaterialTOs = new ArrayList<ProjScheduleMaterialTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        labelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjScheduleMaterialTO> getProjScheduleMaterialTOs() {
        return projScheduleMaterialTOs;
    }

    public void setProjScheduleMaterialTOs(List<ProjScheduleMaterialTO> projScheduleMaterialTOs) {
        this.projScheduleMaterialTOs = projScheduleMaterialTOs;
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
