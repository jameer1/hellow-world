package com.rjtech.common.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class LabelKeyTOMapResp extends AppResp {

    private static final long serialVersionUID = -4308743727232811944L;

    private Map<Long, LabelKeyTO> labelKeyTOList = new HashMap<Long, LabelKeyTO>();

    public LabelKeyTOMapResp() {

    }

    public Map<Long, LabelKeyTO> getLabelKeyTOList() {
        return labelKeyTOList;
    }

    public void setLabelKeyTOList(Map<Long, LabelKeyTO> labelKeyTOList) {
        this.labelKeyTOList = labelKeyTOList;
    }

}
