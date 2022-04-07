package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.mw.controller.central.handler.ProcurementCategoryHandler;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRegisterDeactivateReq;
import com.rjtech.register.plant.req.PlantRegisterDtlSaveReq;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.plant.resp.PlantCurrentStatusResp;
import com.rjtech.register.plant.resp.PlantPOResp;
import com.rjtech.register.plant.resp.PlantRegisterDtlResp;
import com.rjtech.register.plant.resp.PlantRegistersDtlOnLoadResp;
import com.rjtech.register.plant.resp.ProjPlantRegMapResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantRegisterController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegistersDtlOnLoadResp> savePlantRegisterDtls(
            @RequestBody PlantRegisterDtlSaveReq plantRegisterDtlSaveReq) {
        PlantRegistersDtlOnLoadResp plantRegistersDtlOnLoadResp = mwPlantRegisterService
                .savePlantRegisterDtls(plantRegisterDtlSaveReq);
        plantRegistersDtlOnLoadResp
                .setRegisterOnLoadTO(mwCentralLiblService
                        .getRegisterOnLoadCmpCatgProCatgClass(
                                ProcurementCategoryHandler.getRegisterOnLoadReq(ProcurementCatg.PLANT))
                        .getRegisterOnLoadTO());
        plantRegistersDtlOnLoadResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PlantRegistersDtlOnLoadResp>(plantRegistersDtlOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_REGISTERS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<PlantRegisterDtlResp> plantRegistersDeactivate(
            @RequestBody PlantRegisterDeactivateReq plantRegisterDeactivateReq) {
        PlantRegisterDtlResp resp = mwPlantRegisterService.plantRegistersDeactivate(plantRegisterDeactivateReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PlantRegisterDtlResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_REGISTERS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PlantRegistersDtlOnLoadResp> plantRegistersOnLoad(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {
        PlantRegistersDtlOnLoadResp plantRegistersDtlOnLoadResp = mwPlantRegisterService
                .plantRegistersOnLoad(plantRegisterGetReq);
        return new ResponseEntity<>(plantRegistersDtlOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJECT_PLANT_PO_By_PROJID, method = RequestMethod.POST)
    public ResponseEntity<PlantPOResp> getPlantPOByProjId(@RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {

        return new ResponseEntity<PlantPOResp>(mwPlantRegisterService.getPlantPOByProjId(plantProjectDtlGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PURCHASE_ORDERS_BY_PROCURETYPE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPOByProcureType(@RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {

        return new ResponseEntity<LabelKeyTOResp>(mwPlantRegisterService.getPOByProcureType(plantProjectDtlGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.USER_PROJECT_LABELKEYTO, method = RequestMethod.POST)
    public ResponseEntity<String> userProjectLabelKeyTO() {
        LabelKeyTOMapResp resp = new LabelKeyTOMapResp();
        resp.setLabelKeyTOList(mwProjLibService.getUserProjects());
        return new ResponseEntity<String>(AppUtils.toJson(resp), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_CURRENT_STATUS, method = RequestMethod.POST)
    public ResponseEntity<PlantCurrentStatusResp> getPlantCurrentStatus(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantCurrentStatusResp resp = mwPlantRegisterService.getPlantCurrentStatus(plantProjectDtlGetReq);
        resp.setUserProjMap(mwProjLibService.getUserProjects());
        return new ResponseEntity<PlantCurrentStatusResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANTS_NOT_IN_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegistersDtlOnLoadResp> getPlantsNotInUserProjects(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {
        PlantRegistersDtlOnLoadResp plantRegistersDtlOnLoadResp = mwPlantRegisterService
                .getPlantsNotInUserProjects(plantRegisterGetReq);

        plantRegistersDtlOnLoadResp
                .setRegisterOnLoadTO(mwCentralLiblService
                        .getRegisterOnLoadCmpCatgProCatgClass(
                                ProcurementCategoryHandler.getRegisterOnLoadReq(ProcurementCatg.PLANT))
                        .getRegisterOnLoadTO());
        return new ResponseEntity<PlantRegistersDtlOnLoadResp>(plantRegistersDtlOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANTS_IN_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegisterDtlResp> getPlantsInUserProjects(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {

        return new ResponseEntity<PlantRegisterDtlResp>(
                mwPlantRegisterService.getPlantsInUserProjects(plantRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_ATTENDENCE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantAttendence(@RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {

        return new ResponseEntity<LabelKeyTOResp>(mwPlantRegisterService.getPlantAttendence(plantProjectDtlGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MULTI_PROJ_PLANT_LIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantRegMapResp> getMultiProjPlantListMap(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {

        return new ResponseEntity<ProjPlantRegMapResp>(
                mwPlantRegisterService.getMultiProjPlantListMap(plantRegisterGetReq), HttpStatus.OK);
    }
}
