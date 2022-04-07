package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.StockAndStoreTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class CentralStoreStockHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<StockAndStoreTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (StockAndStoreTO stockAndStoreTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(stockAndStoreTO.getId());
                labelKeyTO.setCode(stockAndStoreTO.getCode());
                labelKeyTO.setName(stockAndStoreTO.getDesc());
                labelKeyMap.put(stockAndStoreTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
