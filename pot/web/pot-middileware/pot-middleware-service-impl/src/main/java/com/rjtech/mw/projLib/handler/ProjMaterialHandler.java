package com.rjtech.mw.projLib.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.dto.ProjMaterialClassTO;

public class ProjMaterialHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<ProjMaterialClassTO> inputList) {

        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (ProjMaterialClassTO projMaterialClassTO : inputList) {

                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(projMaterialClassTO.getId());
                labelKeyTO.setCode(projMaterialClassTO.getCode());
                labelKeyTO.setName(projMaterialClassTO.getName());

                if (CommonUtil.objectNotNull(projMaterialClassTO.getMeasureUnitTO())) {
                    labelKeyTO.setUnitOfMeasure(projMaterialClassTO.getMeasureUnitTO().getName());
                }
                labelKeyMap.put(projMaterialClassTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
