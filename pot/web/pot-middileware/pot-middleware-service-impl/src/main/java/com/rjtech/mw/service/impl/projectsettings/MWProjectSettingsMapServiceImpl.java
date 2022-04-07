package com.rjtech.mw.service.impl.projectsettings;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.resp.ProjGeneralMapResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwProjectSettingsMapService")
@RJSService(modulecode = "mwProjectSettingsMapService")
@Transactional
public class MWProjectSettingsMapServiceImpl extends RestConfigServiceImpl implements MWProjectSettingsMapService {

    public ProjGeneralMapResp ProjGeneralMap(ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralOnLoadReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_GENERALMAP);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralMapResp.class);
    }

    public ProjGeneralMapResp getProjManpowerMap(ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralOnLoadReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_MANPOWER_MAP);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralMapResp.class);
    }

}
