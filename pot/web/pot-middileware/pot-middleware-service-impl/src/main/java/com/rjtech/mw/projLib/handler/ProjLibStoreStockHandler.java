package com.rjtech.mw.projLib.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.dto.ProjStoreStockTO;

public class ProjLibStoreStockHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<ProjStoreStockTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (ProjStoreStockTO projStoreStockTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(projStoreStockTO.getId());
                labelKeyTO.setCode(projStoreStockTO.getCode());
                labelKeyTO.setName(projStoreStockTO.getDesc());
                labelKeyMap.put(projStoreStockTO.getId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
