package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.EmployeeWageRateTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class WagFactorHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTOs(List<EmployeeWageRateTO> employeeWageRateTOs) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(employeeWageRateTOs)) {
            for (EmployeeWageRateTO employeeWageRateTO : employeeWageRateTOs) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(employeeWageRateTO.getWageRateId());
                labelKeyTO.setCode(employeeWageRateTO.getCode());
                labelKeyTO.setName(employeeWageRateTO.getName());
                labelKeyMap.put(employeeWageRateTO.getWageRateId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
