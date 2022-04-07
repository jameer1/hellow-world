package com.rjtech.mw.controller.projsettings.handler;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.resp.ProjGeneralsResp;

public class ProjGenHandler {

    public static Map<Long, LabelKeyTO> getLableKeyTO(LabelKeyTO labelKeyTO) {

        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        if (CommonUtil.isNonBlankLong(labelKeyTO.getId())) {
            labelKeyMap.put(labelKeyTO.getId(), labelKeyTO);
        }
        return labelKeyMap;
    }

    public static Map<Long, LabelKeyTO> getUserProjEmpClassify(MWProjLibService mwProjLibService) {
        return mwProjLibService.getUserProjEmpClassMap();
    }

    public static LabelKeyTO populateGeneralTO(Long projId, MWProjectSettingsService mwProjectSettingsService) {

        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projGeneralsGetReq.setProjId(projId);
        ProjGeneralsResp projGenCurrencyResp = mwProjectSettingsService.getProjGenerals(projGeneralsGetReq);

        LabelKeyTO labelKeyTO = new LabelKeyTO();
        ProjGeneralMstrTO projGeneralMstrTO = projGenCurrencyResp.getProjGeneralMstrTO();
        labelKeyTO.setId(projGeneralMstrTO.getId());
        if (CommonUtil.objectNotNull(projGeneralMstrTO.getIsoAlpha3())
                && CommonUtil.objectNotNull(projGeneralMstrTO.getCountryName())) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.COUNTRY, projGeneralMstrTO.getIsoAlpha3());
            if (CommonUtil.objectNotNull(projGeneralMstrTO.getProvisionName())) {
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROVINCE, projGeneralMstrTO.getProvisionName());
            }
            if (CommonUtil.objectNotNull(projGeneralMstrTO.getCurrency())) {
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.CURRENCY, projGeneralMstrTO.getCurrency());
            }
        }
        return labelKeyTO;
    }
}
