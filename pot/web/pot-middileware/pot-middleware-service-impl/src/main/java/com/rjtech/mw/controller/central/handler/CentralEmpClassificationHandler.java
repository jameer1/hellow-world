package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class CentralEmpClassificationHandler {
    public static Map<Long, LabelKeyTO> getLableKeyTO(List<EmpClassTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (EmpClassTO empClassTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(empClassTO.getId());
                labelKeyTO.setCode(empClassTO.getCode());
                labelKeyTO.setName(empClassTO.getName());
                labelKeyMap.put(empClassTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
