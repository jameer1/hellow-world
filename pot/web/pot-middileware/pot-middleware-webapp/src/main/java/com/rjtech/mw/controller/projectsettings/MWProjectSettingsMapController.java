package com.rjtech.mw.controller.projectsettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.projectsettings.MWProjectSettingsMapService;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.resp.ProjGeneralMapResp;

@RestController
@RequestMapping(ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL)
public class MWProjectSettingsMapController {

    @Autowired
    private MWProjectSettingsMapService mwProjectSettingsMapService;

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_GENERALMAP, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralMapResp> ProjGeneralMap(@RequestBody ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        return new ResponseEntity<ProjGeneralMapResp>(mwProjectSettingsMapService.ProjGeneralMap(projGeneralOnLoadReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MANPOWER_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralMapResp> getProjManpowerMap(
            @RequestBody ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        return new ResponseEntity<ProjGeneralMapResp>(
                mwProjectSettingsMapService.getProjManpowerMap(projGeneralOnLoadReq), HttpStatus.OK);
    }

}
