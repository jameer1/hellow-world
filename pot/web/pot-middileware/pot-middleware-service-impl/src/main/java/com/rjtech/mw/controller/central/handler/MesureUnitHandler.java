package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class MesureUnitHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<MeasureUnitTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (MeasureUnitTO measureUnitTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(measureUnitTO.getId());
                labelKeyTO.setCode(measureUnitTO.getCode());
                labelKeyTO.setName(measureUnitTO.getName());
                labelKeyMap.put(measureUnitTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
