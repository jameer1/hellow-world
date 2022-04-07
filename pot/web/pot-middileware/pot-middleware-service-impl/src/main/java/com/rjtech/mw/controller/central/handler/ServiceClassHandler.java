package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.ServiceClassTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class ServiceClassHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<ServiceClassTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (ServiceClassTO serviceClassTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(serviceClassTO.getId());
                labelKeyTO.setCode(serviceClassTO.getCode());
                labelKeyTO.setName(serviceClassTO.getName());

                labelKeyMap.put(serviceClassTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
