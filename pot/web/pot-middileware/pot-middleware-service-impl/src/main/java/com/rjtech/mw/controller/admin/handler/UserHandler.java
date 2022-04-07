package com.rjtech.mw.controller.admin.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class UserHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<LabelKeyTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();

        if (CommonUtil.isListHasData(inputList)) {
            for (LabelKeyTO labelKeyTO : inputList) {

                labelKeyMap.put(labelKeyTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
