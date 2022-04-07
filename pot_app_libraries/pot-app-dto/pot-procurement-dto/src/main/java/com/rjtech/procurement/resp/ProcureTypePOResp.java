package com.rjtech.procurement.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class ProcureTypePOResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();

    public ProcureTypePOResp() {

    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
