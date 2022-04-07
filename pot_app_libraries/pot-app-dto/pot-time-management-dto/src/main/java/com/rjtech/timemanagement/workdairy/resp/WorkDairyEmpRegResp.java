package com.rjtech.timemanagement.workdairy.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class WorkDairyEmpRegResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<LabelKeyTO> empRegLabelKeyTOs = null;

    public WorkDairyEmpRegResp() {
        empRegLabelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<LabelKeyTO> getEmpRegLabelKeyTOs() {
        return empRegLabelKeyTOs;
    }

    public void setEmpRegLabelKeyTOs(List<LabelKeyTO> empRegLabelKeyTOs) {
        this.empRegLabelKeyTOs = empRegLabelKeyTOs;
    }

}
