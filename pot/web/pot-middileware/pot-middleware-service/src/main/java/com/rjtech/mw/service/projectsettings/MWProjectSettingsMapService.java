package com.rjtech.mw.service.projectsettings;

import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.resp.ProjGeneralMapResp;

public interface MWProjectSettingsMapService {

    ProjGeneralMapResp ProjGeneralMap(ProjGeneralOnLoadReq projGeneralOnLoadReq);

    ProjGeneralMapResp getProjManpowerMap(ProjGeneralOnLoadReq projGeneralOnLoadReq);

}
