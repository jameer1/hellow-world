package com.rjtech.mw.projLib.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.dto.ProjPlantClassTO;

public class ProjPlantClassHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<ProjPlantClassTO> inputList) {

        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (ProjPlantClassTO projPlantClassTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(projPlantClassTO.getId());

                if (CommonUtil.objectNotNull(projPlantClassTO.getPlantClassTO())) {
                    labelKeyTO.setCode(projPlantClassTO.getPlantClassTO().getCode());
                    labelKeyTO.setName(projPlantClassTO.getPlantClassTO().getName());
                }

                labelKeyMap.put(projPlantClassTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
