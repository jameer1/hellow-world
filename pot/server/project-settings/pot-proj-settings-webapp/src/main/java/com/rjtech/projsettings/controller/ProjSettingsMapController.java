package com.rjtech.projsettings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.resp.ProjGeneralMapResp;
import com.rjtech.projsettings.service.ProjSettingsMapService;

@RestController
@RequestMapping(ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL)
public class ProjSettingsMapController {

    @Autowired
    private ProjSettingsMapService projSettingsMapService;

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_GENERALMAP, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralMapResp> ProjGeneralMap(@RequestBody ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        return new ResponseEntity<ProjGeneralMapResp>(projSettingsMapService.ProjGeneralMap(projGeneralOnLoadReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MANPOWER_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralMapResp> getProjManpowerMap(
            @RequestBody ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        return new ResponseEntity<ProjGeneralMapResp>(projSettingsMapService.getProjManpowerMap(projGeneralOnLoadReq),
                HttpStatus.OK);
    }

}
