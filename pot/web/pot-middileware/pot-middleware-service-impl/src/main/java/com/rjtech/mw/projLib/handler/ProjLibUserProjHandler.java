package com.rjtech.mw.projLib.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.user.dto.UserProjDetailsTO;

public class ProjLibUserProjHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(List<UserProjDetailsTO> inputList) {
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO labelKeyTO = null;
        if (CommonUtil.isListHasData(inputList)) {
            for (UserProjDetailsTO userProjDetailsTO : inputList) {
                labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(userProjDetailsTO.getProjId());
                labelKeyTO.setName(userProjDetailsTO.getProjName());
                labelKeyTO.setCode(userProjDetailsTO.getProjCode());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_EPSCODE_KEY,
                        userProjDetailsTO.getParentEPSCode());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_NAME_KEY,
                        userProjDetailsTO.getParentName());
                labelKeyMap.put(userProjDetailsTO.getProjId(), labelKeyTO);
            }
        }
        return labelKeyMap;
    }
}
