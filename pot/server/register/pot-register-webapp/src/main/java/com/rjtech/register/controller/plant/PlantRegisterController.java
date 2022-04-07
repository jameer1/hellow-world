package com.rjtech.register.controller.plant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRegisterDeactivateReq;
import com.rjtech.register.plant.req.PlantRegisterDtlSaveReq;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.plant.resp.PlantCurrentStatusResp;
import com.rjtech.register.plant.resp.PlantRegisterDtlResp;
import com.rjtech.register.plant.resp.PlantRegistersDtlOnLoadResp;
import com.rjtech.register.plant.resp.ProjPlantRegMapResp;
import com.rjtech.register.service.plant.PlantRegisterService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantRegisterController {

    @Autowired
    private PlantRegisterService plantRegisterService;

    @Autowired
    private EPSProjService epsProjService;

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegisterDtlResp> getPlantRegisterDtls(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {
        return new ResponseEntity<PlantRegisterDtlResp>(plantRegisterService.getPlantRegisterDtls(plantRegisterGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PLANT_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegistersDtlOnLoadResp> savePlantRegisterDtls(
            @RequestBody PlantRegisterDtlSaveReq plantRegisterDtlSaveReq) {

        PlantRegistersDtlOnLoadResp plantRegistersDtlOnLoadResp = new PlantRegistersDtlOnLoadResp();
        plantRegisterService.savePlantRegisterDtls(plantRegisterDtlSaveReq);

        PlantRegisterGetReq plantRegisterGetReq = new PlantRegisterGetReq();
        plantRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantRegisterDtlResp plantRegisterDtlResp = plantRegisterService.getPlantRegisterDtls(plantRegisterGetReq);

        plantRegistersDtlOnLoadResp.setAssertTypes(plantRegisterService.getPlantAssertType());
        plantRegistersDtlOnLoadResp.setPlantRegisterDtlTOs(plantRegisterDtlResp.getPlantRegisterDtlTOs());

        return new ResponseEntity<PlantRegistersDtlOnLoadResp>(plantRegistersDtlOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_REGISTERS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<PlantRegisterDtlResp> plantRegistersDeactivate(
            @RequestBody PlantRegisterDeactivateReq plantRegisterDeactivateReq) {
        plantRegisterService.plantRegistersDeactivate(plantRegisterDeactivateReq);

        PlantRegisterGetReq plantRegisterGetReq = new PlantRegisterGetReq();
        plantRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantRegisterDtlResp plantRegisterDtlResp = plantRegisterService.getPlantRegisterDtls(plantRegisterGetReq);
        return new ResponseEntity<PlantRegisterDtlResp>(plantRegisterDtlResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.PLANT_REGISTERS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<PlantRegistersDtlOnLoadResp> plantRegistersOnLoad(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {
        PlantRegistersDtlOnLoadResp plantRegistersDtlOnLoadResp = new PlantRegistersDtlOnLoadResp();

        PlantRegisterDtlResp plantRegisterDtlResp = plantRegisterService.getPlantRegisterDtls(plantRegisterGetReq);
        plantRegistersDtlOnLoadResp.setAssertTypes(plantRegisterService.getPlantAssertType());
        plantRegistersDtlOnLoadResp.setPlantRegisterDtlTOs(plantRegisterDtlResp.getPlantRegisterDtlTOs());
        return new ResponseEntity<>(plantRegistersDtlOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_CURRENT_STATUS, method = RequestMethod.POST)
    public ResponseEntity<PlantCurrentStatusResp> getPlantCurrentStatus(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        return new ResponseEntity<PlantCurrentStatusResp>(
                plantRegisterService.getPlantCurrentStatus(plantProjectDtlGetReq), HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANTS_NOT_IN_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegistersDtlOnLoadResp> getPlantsNotInUserProjects(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {
        PlantRegistersDtlOnLoadResp plantRegistersDtlOnLoadResp = new PlantRegistersDtlOnLoadResp();

        List<Long> projIds = epsProjService.getUserProjIds();
        plantRegisterGetReq.setProjIds(projIds);

        PlantRegisterDtlResp plantRegisterDtlResp = plantRegisterService
                .getPlantsNotInUserProjects(plantRegisterGetReq);
        plantRegistersDtlOnLoadResp.setPlantRegisterDtlTOs(plantRegisterDtlResp.getPlantRegisterDtlTOs());
        return new ResponseEntity<PlantRegistersDtlOnLoadResp>(plantRegistersDtlOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANTS_IN_USER_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<PlantRegisterDtlResp> getPlantsInUserProjects(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {

        return new ResponseEntity<PlantRegisterDtlResp>(
                plantRegisterService.getPlantsInUserProjects(plantRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PLANT_ATTENDENCE, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getPlantAttendence(@RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {

        return new ResponseEntity<LabelKeyTOResp>(plantRegisterService.getPlantAttendence(plantProjectDtlGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MULTI_PROJ_PLANT_LIST_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantRegMapResp> getMultiProjPlantListMap(
            @RequestBody PlantRegisterGetReq plantRegisterGetReq) {

        return new ResponseEntity<ProjPlantRegMapResp>(
                plantRegisterService.getMultiProjPlantListMap(plantRegisterGetReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.IS_PLANTCODE_UNIQUE)
    public boolean isPlantCodeUnique(@RequestParam("assertId") String assertId) {
        return plantRegisterService.isPlantCodeUnique(assertId);
    }

}
