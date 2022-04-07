package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;

public class LabelKeyTOResp extends AppResp {

    private static final long serialVersionUID = 6190993318251449155L;

    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();

    public LabelKeyTOResp() {

    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
