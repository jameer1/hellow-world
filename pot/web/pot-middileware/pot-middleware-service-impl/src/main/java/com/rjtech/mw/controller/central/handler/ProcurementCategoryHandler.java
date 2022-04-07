package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.req.RegisterOnLoadReq;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;

public class ProcurementCategoryHandler {
    public static Map<Long, LabelKeyTO> getLableKeyTO(List<ProcureMentCatgTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (ProcureMentCatgTO procureMentCatgTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(procureMentCatgTO.getProCatgId());
                labelKeyTO.setCode(procureMentCatgTO.getCode());
                labelKeyTO.setName(procureMentCatgTO.getDesc());
                labelKeyTO.getDisplayNamesMap().put("ProcureType", String.valueOf(procureMentCatgTO.getProcureId()));
                labelKeyMap.put(procureMentCatgTO.getProCatgId(), labelKeyTO);
            }
        }

        return labelKeyMap;
    }

    public static RegisterOnLoadReq getRegisterOnLoadReq(ProcurementCatg procureCatg) {
        RegisterOnLoadReq req = new RegisterOnLoadReq();
        req.setProcureCatg(procureCatg.getDesc());
        req.setProcureCategoryDbConstant(procureCatg.getDbConstValue());
        return req;
    }
}
