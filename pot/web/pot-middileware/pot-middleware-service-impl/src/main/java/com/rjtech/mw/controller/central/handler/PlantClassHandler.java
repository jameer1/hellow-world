package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class PlantClassHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<PlantClassTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (PlantClassTO plantClassTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(plantClassTO.getId());
                labelKeyTO.setCode(plantClassTO.getCode());
                labelKeyTO.setName(plantClassTO.getName());

                if (CommonUtil.objectNotNull(plantClassTO.getMeasureUnitTO().getName())) {
                    labelKeyTO.setUnitOfMeasure(plantClassTO.getMeasureUnitTO().getName());
                }

                labelKeyMap.put(plantClassTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
