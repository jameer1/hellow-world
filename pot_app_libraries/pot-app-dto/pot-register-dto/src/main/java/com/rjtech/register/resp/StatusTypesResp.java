package com.rjtech.register.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class StatusTypesResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 4748810643868509371L;
    private List<LabelKeyTO> statusTypeLabelKeyTOs = new ArrayList<LabelKeyTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LabelKeyTO> getStatusTypeLabelKeyTOs() {
        return statusTypeLabelKeyTOs;
    }

    public void setStatusTypeLabelKeyTOs(List<LabelKeyTO> statusTypeLabelKeyTOs) {
        this.statusTypeLabelKeyTOs = statusTypeLabelKeyTOs;
    }

}
