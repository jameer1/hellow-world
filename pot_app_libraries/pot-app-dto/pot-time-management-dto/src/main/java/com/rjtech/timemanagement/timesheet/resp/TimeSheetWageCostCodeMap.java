package com.rjtech.timemanagement.timesheet.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class TimeSheetWageCostCodeMap extends AppResp {
    private static final long serialVersionUID = -4308743727232811944L;

    private Map<String, Long> labelKeyTOList = new HashMap<String, Long>();
    private List<LabelKeyTO> labelKeyTOs = new ArrayList<LabelKeyTO>();

    public TimeSheetWageCostCodeMap() {

    }

    public Map<String, Long> getLabelKeyTOList() {
        return labelKeyTOList;
    }

    public void setLabelKeyTOList(Map<String, Long> labelKeyTOList) {
        this.labelKeyTOList = labelKeyTOList;
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

}
