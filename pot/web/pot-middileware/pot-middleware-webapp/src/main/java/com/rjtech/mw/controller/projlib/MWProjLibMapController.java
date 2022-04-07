package com.rjtech.mw.controller.projlib;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.projlib.MWProjLibMapService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.req.ProjCostItemGetReq;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjLibMapResp;
import com.rjtech.projectlib.resp.ProjLibUniqueMapResp;
import com.rjtech.reports.resp.ReportsMapResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@RestController
@RequestMapping(ProjLibURLConstants.PARH_URL)
public class MWProjLibMapController {

    @Autowired
    private MWProjLibMapService mwProjLibMapService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = ProjLibURLConstants.GET_PROJSTOCKPILE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjStockPileMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjStockPileMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJWORKSHIFT_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjWorkShiftMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjWorkShiftMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJCREWLIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjCrewListMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjCrewListMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJSOE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjSOEMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjSOEMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJSOR_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjSORMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjSORMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJCOSTCODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjCostCodeMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjCostCodeMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPSLIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> getEpsListMap(@RequestBody ProjGetReq ProjGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.getEpsListMap(ProjGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJEMPCLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> ProjEmpClassMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.ProjEmpClassMap(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJPLANTCLASS_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibUniqueMapResp> ProjPlantClassMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjLibUniqueMapResp>(mwProjLibMapService.ProjPlantClassMap(projCrewGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_COST_MAP, method = RequestMethod.POST)
    public ReportsMapResp getMultiProjCostMap(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
        ReportsMapResp reportsMapResp = new ReportsMapResp();
        reportsMapResp.setUserProjMap(getUsersProjectMap(projCostItemGetReq));
        reportsMapResp.setCostCodeMap(getCostCodeMap(projCostItemGetReq));
        return reportsMapResp;
    }

    private Map<Long, LabelKeyTO> getUsersProjectMap(ProjCostItemGetReq projCostItemGetReq) {
        UserProjGetReq userProjGetReq = new UserProjGetReq();
        userProjGetReq.setClientId(AppUserUtils.getClientId());
        userProjGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        UserProjResp userProjResp = mwProjLibService.getUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        return userProjMap;
    }

    private Map<Long, ProjCostItemTO> getCostCodeMap(ProjCostItemGetReq projCostItemGetReq) {
        Map<Long, ProjCostItemTO> projCostItemMap = new HashMap<Long, ProjCostItemTO>();
        projCostItemMap = mwProjLibService.getProjCostItemMap(projCostItemGetReq);
        return projCostItemMap;
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPSPROJ_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjLibMapResp> getEpsProjectMap(@RequestBody ProjGetReq ProjGetReq) {
        return new ResponseEntity<ProjLibMapResp>(mwProjLibMapService.getEpsProjectMap(ProjGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_CODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getMultiProjCodeMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(mwProjLibMapService.getMultiProjCodeMap(projGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_SOW_ITEMS_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getMultiProjSOWItemMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(mwProjLibMapService.getMultiProjSOWItemMap(projGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_COST_CODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOMapResp> getMultiProjCostCodeMap(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<LabelKeyTOMapResp>(mwProjLibMapService.getMultiProjCostCodeMap(projGetReq),
                HttpStatus.OK);
    }

}
