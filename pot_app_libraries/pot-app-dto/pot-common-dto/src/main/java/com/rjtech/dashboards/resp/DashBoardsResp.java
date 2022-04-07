package com.rjtech.dashboards.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;


public class DashBoardsResp extends ProjectTO {

    private static final long serialVersionUID = -5362402028612363675L;

    private List<LabelKeyTO> labelKeyTOs = null;

    public DashBoardsResp() {
        labelKeyTOs = new ArrayList<LabelKeyTO>(5);
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
