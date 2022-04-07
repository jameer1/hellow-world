package com.rjtech.mw.projLib.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.dto.ProjCostItemTO;

public class ProjLibCostItemHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<ProjCostItemTO> inputList) {

        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (ProjCostItemTO projCostItemTO : inputList) {

                if (CommonUtil.isNonBlankLong(projCostItemTO.getId())) {
                    labelKeyTO = new LabelKeyTO();
                    labelKeyTO.setId(projCostItemTO.getId());
                    labelKeyTO.setCode(projCostItemTO.getCode());
                    labelKeyTO.setName(projCostItemTO.getName());
                    labelKeyMap.put(projCostItemTO.getId(), labelKeyTO);
                }
            }
        }
        return labelKeyMap;
    }
}
