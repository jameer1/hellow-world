package com.rjtech.projsettings.service;

import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.resp.ProjGeneralMapResp;

public interface ProjSettingsMapService {

    ProjGeneralMapResp ProjGeneralMap(ProjGeneralOnLoadReq projGeneralOnLoadReq);

    ProjGeneralMapResp getProjManpowerMap(ProjGeneralOnLoadReq projGeneralOnLoadReq);

}
