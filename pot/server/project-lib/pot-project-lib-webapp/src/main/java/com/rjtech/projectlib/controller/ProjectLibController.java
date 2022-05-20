package com.rjtech.projectlib.controller;

import java.util.List;
import java.util.Map;

import com.rjtech.projectlib.req.*;
import com.rjtech.projectlib.resp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.EmpClassFilterReq;
import com.rjtech.centrallib.req.MesureFilterReq;
import com.rjtech.centrallib.req.PlantClassFilterReq;
import com.rjtech.centrallib.req.costCodeFilterReq;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.service.CentralLibService;
import com.rjtech.common.service.exception.RJSException;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.projectlib.service.ProjLibService;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@RestController
@RequestMapping(ProjLibURLConstants.PARH_URL)
public class ProjectLibController {

    @Autowired
    EPSProjService epsProjService;

    @Autowired
    ProjLibService projLibService;

    @Autowired
    CentralLibService centralLibService;

    @PostMapping(value = ProjLibURLConstants.DEACTIVE_PROJECT_EPS)
    public ResponseEntity<ProjectResp> deactivateProjectEps(@RequestBody ProjDeleteReq projDeleteReq) {
        ProjectResp projectResp = new ProjectResp();
        try {
            epsProjService.deactivateProjectEps(projDeleteReq);
        } catch (RJSException rjsException) {
            AppResp appResp = new AppResp();
            appResp.setMessage("Couldn't deactivate assigned project");
            appResp.setStatus("Warning");
            projectResp.cloneAppResp(appResp);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("message", rjsException.getMessage());
            responseHeaders.set("code", "412");
            return new ResponseEntity<>(projectResp, responseHeaders, HttpStatus.OK);
        }
        projectResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<>(projectResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_EPS_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjEPSOnLoadResp> projEpsOnLoad(@RequestBody ProjGetReq projGetReq) {

        ProjEPSOnLoadResp projEPSOnLoadResp = new ProjEPSOnLoadResp();

        projEPSOnLoadResp.getePSProjectTOs().addAll(epsProjService.getEPSProjects(projGetReq).getEpsProjs());
        projEPSOnLoadResp.getePSProjectTO().setProjId(projGetReq.getProjId());
        return new ResponseEntity<ProjEPSOnLoadResp>(projEPSOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPS_PROJECTS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjEPSOnLoadResp> getEPSProjectsById(@RequestBody ProjGetReq projGetReq) {

        ProjEPSOnLoadResp projEPSOnLoadResp = new ProjEPSOnLoadResp();
        projEPSOnLoadResp.getePSProjectTOs().addAll(epsProjService.getEPSProjectsById(projGetReq).getEpsProjs());
        projEPSOnLoadResp.getePSProjectTO().setProjId(projGetReq.getProjId());
        return new ResponseEntity<ProjEPSOnLoadResp>(projEPSOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPS_ONLY, method = RequestMethod.POST)
    public ResponseEntity<ProjEPSOnLoadResp> getEPSOnly(@RequestBody ProjGetReq projGetReq) {
        ProjEPSOnLoadResp projEPSOnLoadResp = new ProjEPSOnLoadResp();
        projEPSOnLoadResp.getePSProjectTOs().addAll(epsProjService.getEPSOnly(projGetReq).getEpsProjs());
        projEPSOnLoadResp.getePSProjectTO().setProjId(projGetReq.getProjId());
        return new ResponseEntity<ProjEPSOnLoadResp>(projEPSOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_SOE_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEItemResp> deactivateProjSOEDetails(@RequestBody ProjSOEItemDelReq projSOEItemDelReq) {
        projLibService.deactivateProjSOEDetails(projSOEItemDelReq);

        ProjSOEItemGetReq projSOEItemGetReq = new ProjSOEItemGetReq();
        projSOEItemGetReq.setProjId(projSOEItemDelReq.getProjId());

        ProjSOEItemResp projSOEItemResp = projLibService.getProjSOEDetails(projSOEItemGetReq);
        projSOEItemResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ProjSOEItemResp>(projSOEItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_SOW_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjSowOnLoadResp> projSowOnLoad(@RequestBody ProjEmpClassOnLoadReq onLoadReq) {
        ProjSowOnLoadResp projSowOnLoadResp = new ProjSowOnLoadResp();
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projSowOnLoadResp.setMeasureUnitResp(centralLibService.getMeasurements(mesureFilterReq));

        projSowOnLoadResp.getProjSOWItemTO().setProjId(onLoadReq.getProjId());
        return new ResponseEntity<ProjSowOnLoadResp>(projSowOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_STOCK_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjStockStoreOnLoadResp> projStoreStockOnLoad(
            @RequestBody ProjStockStoresOnloadReq projStockStoresOnloadReq) {

        ProjStockStoreOnLoadResp ProjStockStoreOnLoadResp = new ProjStockStoreOnLoadResp();
        ProjStockStoreOnLoadResp.getProjStoreStockTO().setProjId(projStockStoresOnloadReq.getProjId());
        return new ResponseEntity<ProjStockStoreOnLoadResp>(ProjStockStoreOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_MATERAIAL_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialOnLoadResp> ProjMaterialClassOnLoad(
            @RequestBody ProMaterialClassGetReq proMaterialClassGetReq) {

        ProjMaterialOnLoadResp projMaterialOnLoadResp = new ProjMaterialOnLoadResp();
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setProcureClassName(ProcurementCatg.MATERIAL.getDesc());
        mesureFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        projMaterialOnLoadResp.setMeasureUnitResp(centralLibService.getMeasuresByProcureType(mesureFilterReq));

        projMaterialOnLoadResp.setMaterialClassMap(centralLibService.getMaterialClassMap());

        projMaterialOnLoadResp.getProjMaterialClassTO().setProjId(proMaterialClassGetReq.getProjId());

        return new ResponseEntity<ProjMaterialOnLoadResp>(projMaterialOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_PLANT_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantClassOnLoadResp> projPlantClassOnLoad(
            @RequestBody ProjPlantClassGetReq projPlantClassGetReq) {
        ProjPlantClassOnLoadResp projPlantClassOnLoadResp = new ProjPlantClassOnLoadResp();
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setProcureClassName(ProcurementCatg.PLANT.getDesc());
        mesureFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projPlantClassOnLoadResp.setMeasureUnitResp(centralLibService.getMeasuresByProcureType(mesureFilterReq));

        PlantClassFilterReq plantClassFilterReq = new PlantClassFilterReq();
        mesureFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projPlantClassOnLoadResp.setPlantClassResp(centralLibService.getPlantClasses(plantClassFilterReq));

        /*
         * projPlantClassOnLoadResp.getProjPlantClassTOs().addAll(projLibService
         * .getProjPlantClasses(projPlantClassGetReq).getProjPlantClassTOs());
         */

        projPlantClassOnLoadResp.getProjPlantClassTO().setProjId(projPlantClassGetReq.getProjId());

        return new ResponseEntity<ProjPlantClassOnLoadResp>(projPlantClassOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_WORKSHIFT_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkShiftOnLoadResp> projWorkShiftOnLoad(@RequestBody ProjEmpClassOnLoadReq onLoadReq) {
        ProjWorkShiftOnLoadResp projWorkShiftOnLoadResp = new ProjWorkShiftOnLoadResp();
        projWorkShiftOnLoadResp.getProjWorkShiftTO().setProjectId(onLoadReq.getProjId());
        return new ResponseEntity<ProjWorkShiftOnLoadResp>(projWorkShiftOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_CREWLIST_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjCrewListOnLoadResp> projCrewListOnLoad(@RequestBody ProjCrewGetReq projCrewGetReq) {

        ProjCrewListOnLoadResp projCrewListOnLoadResp = new ProjCrewListOnLoadResp();

        ProjWorkShiftGetReq projWorkShiftGetReq = new ProjWorkShiftGetReq();
        projWorkShiftGetReq.setProjId(projCrewGetReq.getProjId());
        projCrewListOnLoadResp.setProjWorkShiftResp(projLibService.getProjWorkShifts(projWorkShiftGetReq));

        projCrewListOnLoadResp.getProjCrewTO().setProjId(projCrewGetReq.getProjId());

        /*
         * projCrewListOnLoadResp.getProjCrewTOs().addAll(projLibService.
         * getProjCrewLists(projCrewGetReq).getProjCrewTOs());
         */
        return new ResponseEntity<ProjCrewListOnLoadResp>(projCrewListOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_SOE_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjSoeOnLoadResp> projSOEOnLoad(@RequestBody ProjSOEItemGetReq projSOEItemGetReq) {

        ProjSoeOnLoadResp projSoeOnLoadResp = new ProjSoeOnLoadResp();
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projSoeOnLoadResp.setMeasureUnitResp(centralLibService.getMeasurements(mesureFilterReq));

        projSoeOnLoadResp.getProjSOEItemTO().setProjId(projSOEItemGetReq.getProjId());

        projSoeOnLoadResp.getProjSOEItemTOs()
                .addAll(projLibService.getProjSOEDetailsById(projSOEItemGetReq).getProjSOEItemTOs());
        return new ResponseEntity<ProjSoeOnLoadResp>(projSoeOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_SOR_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjSorOnLoadResp> projSOROnLoad(@RequestBody ProjSORItemGetReq projSORItemGetReq) {

        ProjSorOnLoadResp projSorOnLoadResp = new ProjSorOnLoadResp();
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projSorOnLoadResp.setMeasureUnitResp(centralLibService.getMeasurements(mesureFilterReq));

        projSorOnLoadResp.getProjSORItemTO().setProjId(projSORItemGetReq.getProjId());

        projSorOnLoadResp.getProjSORItemTOs()
                .addAll(projLibService.getProjSORDetailsById(projSORItemGetReq).getProjSORItemTOs());
        return new ResponseEntity<ProjSorOnLoadResp>(projSorOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_COSTITEM_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemOnLoadResp> projCostItemOnLoad(
            @RequestBody ProjCostItemGetReq projCostItemGetReq) {

        ProjCostItemOnLoadResp projCostItemOnLoadResp = new ProjCostItemOnLoadResp();

        costCodeFilterReq costCodeReq = new costCodeFilterReq();
        costCodeReq.setStatus(projCostItemGetReq.getStatus());
        projCostItemOnLoadResp.getCostCodeTOs().addAll(centralLibService.getCostCodes(costCodeReq).getCostCodeTOs());

        projCostItemOnLoadResp.getProjCostItemTO().setProjId(projCostItemGetReq.getProjId());

        projCostItemOnLoadResp.getProjCostItemTOs()
                .addAll(projLibService.getProjCostDetailsById(projCostItemGetReq).getProjCostItemTOs());
        return new ResponseEntity<ProjCostItemOnLoadResp>(projCostItemOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_USR_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<UserProjResp> getUserProjects(@RequestBody UserProjGetReq usrProjReq) {
        return new ResponseEntity<UserProjResp>(epsProjService.getAllUserProjects(usrProjReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_EPS_USR_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> getEPSUserProjects(@RequestBody UserProjGetReq userProjGetReq) {
        return new ResponseEntity<ProjectResp>(epsProjService.getEPSUserProjects(userProjGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_ALL_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<UserProjResp> getAllProjects() {
        return new ResponseEntity<UserProjResp>(epsProjService.getAllProjects(), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_CLASSIFY_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpClassOnLoadResp> projEmpClassifyOnLoad(@RequestBody ProjEmpClassOnLoadReq onLoadReq) {

        ProjEmpClassOnLoadResp onLoadResp = new ProjEmpClassOnLoadResp();

        EmpClassFilterReq empClassFilterReq = null;
        empClassFilterReq = new EmpClassFilterReq();
        empClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        onLoadResp.setEmpClassTOs(centralLibService.getEmpClasses(empClassFilterReq).getEmpClassTOs());

        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        mesureFilterReq.setProcureClassName(ProcurementCatg.MAN_POWER.getDesc());
        onLoadResp.setMeasureUnitTOs(centralLibService.getMeasuresByProcureType(mesureFilterReq).getMeasureUnitTOs());

        ProjGetReq projGetReq = null;
        projGetReq = new ProjGetReq();
        projGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        //onLoadResp.setProjEmpCatgTOs(projLibService.getProjEmpTypes(projGetReq).getProjEmpCatgTOs());

        onLoadResp.getProjEmpClassTO().setProjId(onLoadReq.getProjId());

        return new ResponseEntity<ProjEmpClassOnLoadResp>(onLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJECTS, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> getProjects(@RequestBody ProjGetReq projGetReq) {

        return new ResponseEntity<ProjectResp>(epsProjService.getEPSProjects(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJECTS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> getProjectsById(@RequestBody ProjFilterReq projFilterReq) {
        return new ResponseEntity<ProjectResp>(epsProjService.getProjectsById(projFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> saveProject(@RequestBody ProjSaveReq projSaveReq) {
        epsProjService.saveProject(projSaveReq);

        ProjFilterReq projFilterReq = new ProjFilterReq();
       // projFilterReq.setProjId(projSaveReq.getProjId());

        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjectResp projectResp = epsProjService.getEPSProjects(projGetReq);

        projectResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjectResp>(projectResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJECT_EPS, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> getProjectEps(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<ProjectResp>(epsProjService.getProjectEps(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOE_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEItemResp> getProjSOEDetails(@RequestBody ProjSOEItemGetReq projSOEItemGetReq) {

        return new ResponseEntity<ProjSOEItemResp>(projLibService.getProjSOEDetails(projSOEItemGetReq), HttpStatus.OK);

    }
    
    @RequestMapping(value = ProjLibURLConstants.GET_SOE_TRACK_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOETrackLogResp> getProjSOETrackDetails(@RequestBody ProjSOETrackGetReq projSOETrackGetReq) {

        return new ResponseEntity<ProjSOETrackLogResp>(projLibService.getProjSOETrackDetails(projSOETrackGetReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOE_ITEMS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEItemResp> getProjSOEDetailsById(@RequestBody ProjSOEItemGetReq projSOEItemGetReq) {
        return new ResponseEntity<ProjSOEItemResp>(projLibService.getProjSOEDetailsById(projSOEItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_SOE_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEItemResp> saveProjSOEDetails(@RequestBody ProjSOEItemSaveReq projSOEItemSaveReq) {
        projLibService.saveProjSOEDetails(projSOEItemSaveReq);

        ProjSOEItemGetReq projSOEItemGetReq = new ProjSOEItemGetReq();
        projSOEItemGetReq.setProjId(projSOEItemSaveReq.getProjId());

        ProjSOEItemResp projSOEItemResp = projLibService.getProjSOEDetails(projSOEItemGetReq);

        projSOEItemResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjSOEItemResp>(projSOEItemResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_SOE_TRACK_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOETrackLogResp> saveProjSOETrackDetails(@RequestBody ProjSOETrackSaveReq projSOETrackSaveReq) {
    	projLibService.saveProjSOETrackDetails(projSOETrackSaveReq);
    	ProjSOETrackGetReq projSOETrackGetReq = new ProjSOETrackGetReq();
    	ProjSOETrackLogResp projSOETrackLogResp = projLibService.getProjSOETrackDetails(projSOETrackGetReq);
        return new ResponseEntity<ProjSOETrackLogResp>(projSOETrackLogResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOR_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSORItemResp> getProjSORDetails(@RequestBody ProjSORItemGetReq projSORItemGetReq) {
        return new ResponseEntity<ProjSORItemResp>(projLibService.getProjSORDetails(projSORItemGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOR_ITEMS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjSORItemResp> getProjSORDetailsById(@RequestBody ProjSORItemGetReq projSORItemGetReq) {
        return new ResponseEntity<ProjSORItemResp>(projLibService.getProjSORDetailsById(projSORItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_SOR_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSORItemResp> saveProjSORDetails(@RequestBody ProjSORItemSaveReq projSORItemSaveReq) {
        projLibService.saveProjSORDetails(projSORItemSaveReq);
        projLibService.saveProjSORTrackDetails(projSORItemSaveReq);
        ProjSORItemGetReq projSORItemGetReq = new ProjSORItemGetReq();
        projSORItemGetReq.setProjId(projSORItemSaveReq.getProjId());

        ProjSORItemResp projSORItemResp = projLibService.getProjSORDetails(projSORItemGetReq);

        projSORItemResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjSORItemResp>(projSORItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_SOR_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSORItemResp> deactivateProjSORDetails(@RequestBody ProjSORItemDelReq projSORItemDelReq) {
        projLibService.deactivateProjSORDetails(projSORItemDelReq);
        ProjSORItemGetReq projSORItemGetReq = new ProjSORItemGetReq();
        projSORItemGetReq.setProjId(projSORItemDelReq.getProjId());

        ProjSORItemResp projSORItemResp = projLibService.getProjSORDetails(projSORItemGetReq);

        projSORItemResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjSORItemResp>(projSORItemResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> getProjSOWDetails(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
        return new ResponseEntity<ProjSOWItemResp>(projLibService.getProjSOWDetails(projSOWItemGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_BY_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> getSOWByCostIds(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
        return new ResponseEntity<ProjSOWItemResp>(projLibService.getProjSOWDetailsByCostCode(projSOWItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_EXCEPT_COST_CODES, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> getSOWExceptCostIds(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
        return new ResponseEntity<ProjSOWItemResp>(projLibService.getProjSOWDetailsExceptCostCode(projSOWItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_SOW_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> getMultiProjSOWDetails(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
        return new ResponseEntity<ProjSOWItemResp>(projLibService.getMultiProjSOWDetails(projSOWItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> getProjSOWDetailsById(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
        return new ResponseEntity<ProjSOWItemResp>(projLibService.getProjSOWDetailsById(projSOWItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_SOW_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> saveProjSOWDetails(@RequestBody ProjSOWItemSaveReq projSOWItemSaveReq) {
        projLibService.saveProjSOWDetails(projSOWItemSaveReq);

        ProjSOWItemGetReq projSOWItemGetReq = new ProjSOWItemGetReq();
        projSOWItemGetReq.setProjId(projSOWItemSaveReq.getProjId());

        ProjSOWItemResp projSOWItemResp = projLibService.getProjSOWDetails(projSOWItemGetReq);

        projSOWItemResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjSOWItemResp>(projSOWItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_SOW_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> deactivateProjSOWDetails(@RequestBody ProjSOWItemDelReq projSOWItemDelReq) {
        projLibService.deactivateProjSOWDetails(projSOWItemDelReq);

        ProjSOWItemGetReq projSOWItemGetReq = new ProjSOWItemGetReq();
        projSOWItemGetReq.setProjId(projSOWItemDelReq.getProjId());

        ProjSOWItemResp projSOWItemResp = projLibService.getProjSOWDetails(projSOWItemGetReq);

        projSOWItemResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjSOWItemResp>(projSOWItemResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemResp> getProjCostDetails(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
        ProjCostItemResp projCostItemResp = projLibService.getProjCostDetails(projCostItemGetReq);
        return new ResponseEntity<ProjCostItemResp>(projCostItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemResp> getProjCostDetailsId(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
        return new ResponseEntity<ProjCostItemResp>(projLibService.getProjCostDetailsById(projCostItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_COST_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemResp> saveProjCostDetails(@RequestBody ProjCostItemSaveReq projCostItemSaveReq) {
        projLibService.saveProjCostDetails(projCostItemSaveReq);

        ProjCostItemGetReq projCostItemGetReq = new ProjCostItemGetReq();
        projCostItemGetReq.setProjId(projCostItemSaveReq.getProjId());

        ProjCostItemResp projCostItemResp = projLibService.getProjCostDetails(projCostItemGetReq);

        projCostItemResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjCostItemResp>(projCostItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_COST_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemResp> deactivateProjCostDetails(
            @RequestBody ProjCostItemDelReq projCostItemDelReq) {
        projLibService.deactivateProjCostDetails(projCostItemDelReq);
        ProjCostItemGetReq projCostItemGetReq = new ProjCostItemGetReq();
        projCostItemGetReq.setProjId(projCostItemDelReq.getProjId());

        ProjCostItemResp projCostItemResp = projLibService.getProjCostDetails(projCostItemGetReq);

        projCostItemResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjCostItemResp>(projCostItemResp, HttpStatus.OK);
    }

    @PostMapping(value = ProjLibURLConstants.GET_LEAVE_TYPES_BY_COUNTRY)
    public ResponseEntity<ProjLeaveTypeResp> getLeaveTypesByCountry(
            @RequestBody ProjLeaveTypeGetReq projLeaveTypeGetReq) {
        return new ResponseEntity<>(projLibService.getLeaveTypesByCountry(projLeaveTypeGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_LEAVE_TYPES, method = RequestMethod.POST)
    public ResponseEntity<ProjLeaveTypeResp> saveProjLeaveTypes(
            @RequestBody ProjLeaveTypeSaveReq projLeaveTypeSaveReq) {
        projLibService.saveProjLeaveTypes(projLeaveTypeSaveReq);

        ProjLeaveTypeResp projLeaveTypeResp = new ProjLeaveTypeResp();
        projLeaveTypeResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(projLeaveTypeResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_EMP_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpClassResp> getProjEmpClasses(@RequestBody ProjEmpClassGetReq projEmpClassGetReq) {
        return new ResponseEntity<ProjEmpClassResp>(projLibService.getProjEmpClasses(projEmpClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_EMP_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpClassResp> saveProjEmpClasses(@RequestBody ProjEmpClassSaveReq empClassSaveReq) {

        projLibService.saveProjEmpClasses(empClassSaveReq);

        ProjEmpClassGetReq projectLibFliterReq = new ProjEmpClassGetReq();
        projectLibFliterReq.setProjId(empClassSaveReq.getProjId());

        ProjEmpClassResp empClassResp = projLibService.getProjEmpClasses(projectLibFliterReq);

        empClassResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjEmpClassResp>(empClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_EMP_CLASSES, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjEmpClassResp> deleteEmpClasses(@RequestBody ProjEmpClassDelReq projEmpClassDelReq) {
        projLibService.deleteEmpClasses(projEmpClassDelReq);
        ProjEmpClassGetReq projectLibFliterReq = new ProjEmpClassGetReq();
        projectLibFliterReq.setProjId(projEmpClassDelReq.getProjId());

        ProjEmpClassResp empClassResp = projLibService.getProjEmpClasses(projectLibFliterReq);

        empClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjEmpClassResp>(empClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_PLANT_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantClassResp> getProjPlantClasses(
            @RequestBody ProjPlantClassGetReq projEmpClassGetReq) {
        return new ResponseEntity<ProjPlantClassResp>(projLibService.getProjPlantClasses(projEmpClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_PLANT_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantClassResp> saveProjPlantClasses(
            @RequestBody ProjPlantClassSaveReq projPlantClassSaveReq) {
        projLibService.saveProjPlantClasses(projPlantClassSaveReq);

        ProjPlantClassGetReq projPlantClassGetReq = new ProjPlantClassGetReq();
        projPlantClassGetReq.setProjId(projPlantClassSaveReq.getProjId());

        ProjPlantClassResp projPlantClassResp = projLibService.getProjPlantClasses(projPlantClassGetReq);

        projPlantClassResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjPlantClassResp>(projPlantClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_PLANT_CLASSES, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjPlantClassResp> deletePlantClasses(@RequestBody ProjPlantClassDelReq projEmpClassDelReq) {
        projLibService.deleteProjPlantClasses(projEmpClassDelReq);
        ProjPlantClassGetReq projPlantClassGetReq = new ProjPlantClassGetReq();
        projPlantClassGetReq.setProjId(projEmpClassDelReq.getProjId());

        ProjPlantClassResp projPlantClassResp = projLibService.getProjPlantClasses(projPlantClassGetReq);

        projPlantClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjPlantClassResp>(projPlantClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = { "getProjCrewLists" }, method = { RequestMethod.POST })
    public ResponseEntity<ProjCrewResp> getProjCrewLists(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjCrewResp>(projLibService.getProjCrewLists(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = { "saveProjCrewLists" }, method = { RequestMethod.POST })
    public ResponseEntity<ProjCrewResp> saveProjCrewLists(@RequestBody ProjCrewSaveReq projCrewSaveReq) {
        projLibService.saveProjCrewLists(projCrewSaveReq);

        ProjCrewGetReq projCrewGetReq = new ProjCrewGetReq();
        projCrewGetReq.setProjId(projCrewSaveReq.getProjId());

        ProjCrewResp projCrewResp = projLibService.getProjCrewLists(projCrewGetReq);

        projCrewResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjCrewResp>(projCrewResp, HttpStatus.OK);
    }

    @RequestMapping(value = { "deleteProjCrewLists" }, method = { RequestMethod.POST }, consumes = {
            "application/json" })
    public ResponseEntity<ProjCrewResp> deleteProjCrewLists(@RequestBody ProjCrewDelReq projCrewDelReq) {
        this.projLibService.deleteProjCrewLists(projCrewDelReq);
        ProjCrewGetReq projCrewGetReq = new ProjCrewGetReq();
        projCrewGetReq.setProjId(projCrewDelReq.getProjId());

        ProjCrewResp projCrewResp = projLibService.getProjCrewLists(projCrewGetReq);

        projCrewResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjCrewResp>(projCrewResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_WORK_SHIFT, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkShiftResp> getProjWorkShifts(@RequestBody ProjWorkShiftGetReq projWorkShifGetReq) {
        return new ResponseEntity<ProjWorkShiftResp>(projLibService.getProjWorkShifts(projWorkShifGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_WORK_SHIFT, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkShiftResp> saveProjWorkShifts(
            @RequestBody ProjWorkShiftSaveReq projWorkShiftSaveReq) {
        projLibService.saveProjWorkShifts(projWorkShiftSaveReq);

        ProjWorkShiftGetReq projWorkShiftGetReq = new ProjWorkShiftGetReq();
        projWorkShiftGetReq.setProjId(projWorkShiftSaveReq.getProjId());

        ProjWorkShiftResp projWorkShiftResp = projLibService.getProjWorkShifts(projWorkShiftGetReq);

        projWorkShiftResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjWorkShiftResp>(projWorkShiftResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_WORK_SHIFT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjWorkShiftResp> deleteProjWorkShifts(
            @RequestBody ProjWorkShiftDelReq projWorkShiftDelReq) {
        projLibService.deleteProjWorkShifts(projWorkShiftDelReq);
        ProjWorkShiftGetReq projWorkShiftGetReq = new ProjWorkShiftGetReq();
        projWorkShiftGetReq.setProjId(projWorkShiftDelReq.getProjId());

        ProjWorkShiftResp projWorkShiftResp = projLibService.getProjWorkShifts(projWorkShiftGetReq);

        projWorkShiftResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjWorkShiftResp>(projWorkShiftResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_STORE_STOCK, method = RequestMethod.POST)
    public ResponseEntity<ProjStoreStockResp> getProjStoreStocks(
            @RequestBody ProjStoreStockGetReq projStoreStockGetReq) {
        return new ResponseEntity<ProjStoreStockResp>(projLibService.getProjStoreStocks(projStoreStockGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_STORE_STOCK, method = RequestMethod.POST)
    public ResponseEntity<ProjStoreStockResp> saveProjStoreStocks(
            @RequestBody ProjStoreStockSaveReq projStoreStockSaveReq) {
        projLibService.saveProjStoreStocks(projStoreStockSaveReq);

        ProjStoreStockGetReq projStoreStockGetReq = new ProjStoreStockGetReq();
        projStoreStockGetReq.setProjId(projStoreStockSaveReq.getProjId());

        ProjStoreStockResp projStoreStockResp = projLibService.getProjStoreStocks(projStoreStockGetReq);

        projStoreStockResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjStoreStockResp>(projStoreStockResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_STORE_STOCK, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjStoreStockResp> deleteProjStoreStocks(
            @RequestBody ProjStoreStockDelReq projStoreStockDelReq) {
        projLibService.deleteProjStoreStocks(projStoreStockDelReq);
        ProjStoreStockGetReq projStoreStockGetReq = new ProjStoreStockGetReq();
        projStoreStockGetReq.setProjId(projStoreStockDelReq.getProjId());

        ProjStoreStockResp projStoreStockResp = projLibService.getProjStoreStocks(projStoreStockGetReq);

        projStoreStockResp.cloneAppResp(CommonUtil.getDeactiveAppResp());

        return new ResponseEntity<ProjStoreStockResp>(projStoreStockResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_MATERIAL_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialClassResp> getProjMaterialClasses(
            @RequestBody ProMaterialClassGetReq proMaterialClassGetReq) {

        return new ResponseEntity<ProjMaterialClassResp>(projLibService.getProjMaterialClasses(proMaterialClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_MATERIAL_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialClassResp> saveProjMaterialClasses(
            @RequestBody ProjMaterialClassSaveReq projMaterialClassSaveReq) {
        projLibService.saveProjMaterialClasses(projMaterialClassSaveReq);

        ProMaterialClassGetReq proMaterialClassGetReq = new ProMaterialClassGetReq();
        proMaterialClassGetReq.setProjId(projMaterialClassSaveReq.getProjId());
        ProjMaterialClassResp projMaterialClassResp = projLibService.getProjMaterialClasses(proMaterialClassGetReq);
        projMaterialClassResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjMaterialClassResp>(projMaterialClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.DEACTIVATE_PROJ_MATERIAL_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialClassResp> deactivateMaterialClasses(
            @RequestBody ProjMaterialDelReq projMaterialDelReq) {
        projLibService.deactivateMaterialClasses(projMaterialDelReq);
        ProMaterialClassGetReq proMaterialClassGetReq = new ProMaterialClassGetReq();
        proMaterialClassGetReq.setProjId(projMaterialDelReq.getProjId());

        ProjMaterialClassResp projMaterialClassResp = projLibService.getProjMaterialClasses(proMaterialClassGetReq);

        projMaterialClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ProjMaterialClassResp>(projMaterialClassResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMSONLY, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemResp> getProjCostItemsOnly(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
        return new ResponseEntity<ProjCostItemResp>(projLibService.getProjCostItemsOnly(projCostItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_COST_USER_PROJECT_CLASSIFICATION, method = RequestMethod.POST)
    public ResponseEntity<String> projectCostUserProjClassify(@RequestBody ProjectLibOnLoadReq projectLibOnLoadReq) {
        ProjectLibOnLoadResp resp = new ProjectLibOnLoadResp();
        resp.setUserProjMap(epsProjService.getUserProjects());
        resp.setProjCostItemMap(projLibService.getProjectCostItems(projectLibOnLoadReq));
        resp.setProjClassMap(getClassification(projectLibOnLoadReq));
        return new ResponseEntity<String>(AppUtils.toJson(resp), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_USER_PROJECT_CLASSIFICATION, method = RequestMethod.POST)
    public ResponseEntity<String> projectUserProjClassify(@RequestBody ProjectLibOnLoadReq projectLibOnLoadReq) {
        ProjectLibOnLoadResp resp = new ProjectLibOnLoadResp();

        resp.setUserProjMap(epsProjService.getUserProjects());
        resp.setProjClassMap(getClassification(projectLibOnLoadReq));
        return new ResponseEntity<String>(AppUtils.toJson(resp), HttpStatus.OK);

    }

    private Map<Long, LabelKeyTO> getClassification(ProjectLibOnLoadReq projectLibOnLoadReq) {
        return projLibService.getProjPlantClasses(projectLibOnLoadReq);
        /*
         * if (ProcurementCatg.MAN_POWER.getDesc().equalsIgnoreCase(
         * projectLibOnLoadReq.getProcureCatg())){ return
         * projLibService.getProjEmpClassMap(projectLibOnLoadReq); }else if
         * (ProcurementCatg.PLANT.getDesc().equalsIgnoreCase(projectLibOnLoadReq
         * .getProcureCatg())){ return
         * projLibService.getProjPlantClasses(projectLibOnLoadReq); } return
         * null;
         */
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_SOW_ITEMS_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjSowItemsMapResp> getProjSowItemsMap(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {

        return new ResponseEntity<ProjSowItemsMapResp>(projLibService.getProjSowItemsMap(projSOWItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMS_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemMapResp> getProjCostItemMap(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
        ProjCostItemMapResp resp = new ProjCostItemMapResp();
        resp.setProjCostItemMap(projLibService.getProjCostItemMap(projCostItemGetReq));
        return new ResponseEntity<ProjCostItemMapResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJS_CREW_MAP, method = RequestMethod.POST)
    public ResponseEntity<ProjCrewMapResp> getMultipleProjsCrewMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
        ProjCrewMapResp resp = new ProjCrewMapResp();
        resp.setProjCrewMap(projLibService.getMultipleProjsCrewMap(projCrewGetReq));
        return new ResponseEntity<ProjCrewMapResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PROJ_MATERIAL_RESTRICTED_CLASSES, method = RequestMethod.POST)
    public ResponseEntity<ProjRestrictedMaterialClassResp> getProjRestrictedMaterialClasses(
            @RequestBody ProMaterialClassGetReq proMaterialClassGetReq) {
        return new ResponseEntity<ProjRestrictedMaterialClassResp>(
                projLibService.getProjRestrictedMaterialClasses(proMaterialClassGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJS_CREW_LIST, method = RequestMethod.POST)
    public ResponseEntity<ProjCrewResp> getMultipleProjsCrewList(@RequestBody ProjCrewGetReq projCrewGetReq) {
        return new ResponseEntity<ProjCrewResp>(projLibService.getMultipleProjsCrewList(projCrewGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_COST_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostItemResp> getMultiProjCostDetails(
            @RequestBody ProjCostItemGetReq projCostItemGetReq) {
        return new ResponseEntity<ProjCostItemResp>(projLibService.getMultiProjCostDetails(projCostItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_USR_PROJ_EMP_CLASSIFY, method = RequestMethod.POST)
    public ResponseEntity<UserProjEmpClassResp> getUserProjEmpClassMap() {
        UserProjEmpClassResp resp = new UserProjEmpClassResp();
        resp.setProjClassMap(projLibService.getUserProjEmpClassMap(epsProjService.getUserProjIds()));
        return new ResponseEntity<UserProjEmpClassResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJS_STORE_LIST, method = RequestMethod.POST)
    public ResponseEntity<ProjStoreStockResp> getMultipleProjsStoreList(
            @RequestBody ProjStoreStockGetReq projStoreStockGetReq) {
        return new ResponseEntity<ProjStoreStockResp>(projLibService.getMultipleProjsStoreList(projStoreStockGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_SOW_TOTAL_ACTUAL_QUANTITIES, method = RequestMethod.POST)
    public ResponseEntity<SOWTotalActualQuantitiesResp> getSOWActualRevisedQuantities(
            @RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
        return new ResponseEntity<SOWTotalActualQuantitiesResp>(
                projLibService.getSOWTotalActualQuantities(projSOWItemGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjLibURLConstants.DEACTIVATE_EPS)
    public ResponseEntity<ProjectResp> deactivateEps(@RequestBody ProjDeleteReq projDeleteReq) {
        ProjectResp projectResp = new ProjectResp();
        try {
            epsProjService.deactivateEps(projDeleteReq);
        } catch (RJSException rjsException) {
            AppResp appResp = new AppResp();
            appResp.setMessage("Couldn't deactivate EPS, Deactivate projects - " + rjsException.getMessage());
            appResp.setStatus("Warning");
            appResp.setMsgCode("412");
            projectResp.cloneAppResp(appResp);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("message", rjsException.getMessage());
            responseHeaders.set("code", "412");
            return new ResponseEntity<>(projectResp, responseHeaders, HttpStatus.OK);
        }
        projectResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<>(projectResp, HttpStatus.OK);
    }

    @PostMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJ_WORK_SHIFT)
    public ResponseEntity<ProjWorkShiftResp> getMultipleProjWorkShifts(
            @RequestBody ProjWorkShiftGetReq projWorkShifGetReq) {
        return new ResponseEntity<>(projLibService.getMultipleProjWorkShifts(projWorkShifGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjLibURLConstants.SAVE_DEFAULT_PROJ_LEAVE_TYPES)
    public void saveDefaultProjLeaveTypes(@RequestParam("clientId") Long clientId) {
        projLibService.saveDefaultProjLeaveTypes(clientId);
    }

    @PostMapping(value = ProjLibURLConstants.GET_GLOBAL_LEAVE_TYPES)
    public ResponseEntity<ProjLeaveTypeResp> getGlobalLeaveTypes() {
        return new ResponseEntity<>(projLibService.getGlobalLeaveTypes(), HttpStatus.OK);
    }

    @GetMapping(value = ProjLibURLConstants.GET_EFFECTIVE_DATES_FOR_COUNTRY)
    public ResponseEntity<List<String>> getEffectiveDatesForCountry(@RequestParam String countryCode) {
        return new ResponseEntity<>(projLibService.getEffectiveDatesForCountry(countryCode), HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.PROJECT_PMCM_POPUP_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCMOnLoadResp> projPMCMOnLoad(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {

        ProjPMCMOnLoadResp projPMCMOnLoadResp = new ProjPMCMOnLoadResp();
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projPMCMOnLoadResp.getProjSORItemTO().setProjId(projPMCMItemGetReq.getProjId());

        projPMCMOnLoadResp.getProjSORItemTOs()
                .addAll(projLibService.getProjPMCMDetailsById(projPMCMItemGetReq).getProjPMCMItemTOs());
        return new ResponseEntity<ProjPMCMOnLoadResp>(projPMCMOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PMCM_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCMItemResp> getProjPMCMDetails(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {
        System.out.println("ProjectLibController:getProjPMCMDetails:ProjId:"+projPMCMItemGetReq.getProjId());
        System.out.println("ProjectLibController:getProjPMCMDetails:ClientId:"+projPMCMItemGetReq.getClientId());
        System.out.println("ProjectLibController:getProjPMCMDetails:ClientId:"+projPMCMItemGetReq.getClientId());

        ProjPMCMItemResp projPMCMItemResp = projLibService.getProjPMCMDetails(projPMCMItemGetReq);
        System.out.println("ProjectLibController:getProjPMCMDetails:ProjPMCMItemTOs Size:"+projPMCMItemResp.getProjPMCMItemTOs().size());
        return new ResponseEntity<ProjPMCMItemResp>(projPMCMItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PMCP_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCPCostStatementsResp> getProjPMCPCostStatements(@RequestBody ProjPMCPItemGetReq projPMCPItemGetReq) {
        System.out.println("ProjectLibController:getProjPMCMDetails:ProjId:"+projPMCPItemGetReq.getProjId());
        System.out.println("ProjectLibController:getProjPMCMDetails:ClientId:"+projPMCPItemGetReq.getClientId());
        System.out.println("ProjectLibController:getProjPMCMDetails:ProjIds : size:"+projPMCPItemGetReq.getProjIds().size());

        ProjPMCPCostStatementsResp projPMCPCostStatementsResp = projLibService.getProjPMCPCostStatements(projPMCPItemGetReq);
        System.out.println("ProjectLibController:getProjPMCMDetails: ProjCostStmtDtlTOs Size:"+projPMCPCostStatementsResp.getProjCostStmtDtlTOs().size());
        return new ResponseEntity<ProjPMCPCostStatementsResp>(projPMCPCostStatementsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_REPORT_PMCM_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCMItemResp> getProjReportPMCMDetails(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {
        System.out.println("ProjectLibController:getProjPMCMDetails:ProjId:"+projPMCMItemGetReq.getProjId());
        System.out.println("ProjectLibController:getProjPMCMDetails:ClientId:"+projPMCMItemGetReq.getClientId());
        System.out.println("ProjectLibController:getProjPMCMDetails:ClientId:"+projPMCMItemGetReq.getClientId());
        System.out.println("ProjectLibController:getProjPMCMDetails:getProjStatusDate:"+projPMCMItemGetReq.getProjStatusDate());
        System.out.println("ProjectLibController:getProjPMCMDetails:getProjIds:"+projPMCMItemGetReq.getProjIds().size());
        System.out.println(projPMCMItemGetReq.getProjIds());

        ProjPMCMItemResp projPMCMItemResp = projLibService.getProjReportPMCMDetails(projPMCMItemGetReq);
        System.out.println("ProjectLibController:getProjPMCMDetails:ProjPMCMItemTOs Size:"+projPMCMItemResp.getProjPMCMItemTOs().size());
        return new ResponseEntity<ProjPMCMItemResp>(projPMCMItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_REPORT_PMCP_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCPItemResp> getProjReportPMCPDetails(@RequestBody ProjPMCPItemGetReq projPMCPItemGetReq) {
        System.out.println("ProjectLibController:getProjReportPMCPDetails:ProjId:"+projPMCPItemGetReq.getProjId());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:ClientId:"+projPMCPItemGetReq.getClientId());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:ClientId:"+projPMCPItemGetReq.getClientId());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:getFromDate:"+projPMCPItemGetReq.getFromDate());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:getToDate:"+projPMCPItemGetReq.getToDate());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:getProjIds:"+projPMCPItemGetReq.getProjIds().size());
        System.out.println(projPMCPItemGetReq.getProjIds());

        ProjPMCPItemResp projPMCPItemResp = projLibService.getProjReportPMCPDetails(projPMCPItemGetReq);
        System.out.println("ProjectLibController:getProjReportPMCPDetails:getCostReportResps Size:"+projPMCPItemResp.getCostReportResps().size());
        return new ResponseEntity<ProjPMCPItemResp>(projPMCPItemResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.GET_PMCM_ITEMS_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCMItemResp> getProjPMCMDetailsById(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {
        return new ResponseEntity<ProjPMCMItemResp>(projLibService.getProjPMCMDetailsById(projPMCMItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjLibURLConstants.SAVE_PMCM_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProjPMCMItemResp> saveProjPMCMDetails(@RequestBody ProjPMCMItemSaveReq projPMCMItemSaveReq) {
        projLibService.saveProjPMCMDetails(projPMCMItemSaveReq);

        ProjPMCMItemGetReq projPMCMItemGetReq = new ProjPMCMItemGetReq();
        projPMCMItemGetReq.setProjId(projPMCMItemSaveReq.getProjId());

        ProjPMCMItemResp projPMCMItemResp = projLibService.getProjPMCMDetails(projPMCMItemGetReq);

        projPMCMItemResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjPMCMItemResp>(projPMCMItemResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjLibURLConstants.SOE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEItemResp> projSOEApproval( @RequestBody ProjSOEItemGetReq projSOEItemGetReq ) {
    	ProjSOEItemResp projSOEItemResp = projLibService.projSOEApproval( projSOEItemGetReq );
        return new ResponseEntity<ProjSOEItemResp>( projSOEItemResp, HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SOE_RETURNED_WITH_COMMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEItemResp> returnSOEWithComments( @RequestBody ProjSOEItemGetReq projSOEItemGetReq ) {
    	ProjSOEItemResp projSOEItemResp = projLibService.returnSOEWithComments( projSOEItemGetReq );
        return new ResponseEntity<ProjSOEItemResp>( projSOEItemResp, HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SOE_VIEW_ACTIVITY_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<ProjSOEActivityLogResp> viewActivityRecords( @RequestBody ProjSOEItemGetReq projSOEItemGetReq ) {
    	ProjSOEActivityLogResp projSOEActivityLogResp = projLibService.viewActivityRecords( projSOEItemGetReq );
        return new ResponseEntity<ProjSOEActivityLogResp>( projSOEActivityLogResp, HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<ProjSORItemResp> projSORApproval( @RequestBody ProjSORItemGetReq projSORItemGetReq ) {
    	ProjSORItemResp projSORItemResp = projLibService.projSORApproval( projSORItemGetReq );
        return new ResponseEntity<ProjSORItemResp>( projSORItemResp, HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SOR_RETURNED_WITH_COMMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjSORItemResp> returnSORWithComments( @RequestBody ProjSORItemGetReq projSORItemGetReq ) {
    	ProjSORItemResp projSORItemResp = projLibService.returnSORWithComments( projSORItemGetReq );
        return new ResponseEntity<ProjSORItemResp>( projSORItemResp, HttpStatus.OK );
    }

    @RequestMapping(value = ProjLibURLConstants.SOR_VIEW_ACTIVITY_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<ProjSORActivityLogResp> viewSORActivityLog( @RequestBody ProjSORItemGetReq projSORItemGetReq ) {
    	ProjSORActivityLogResp projSORActivityLogResp = projLibService.viewSORActivityLog( projSORItemGetReq );
        return new ResponseEntity<ProjSORActivityLogResp>( projSORActivityLogResp, HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_BY_PROJID, method = RequestMethod.POST)
    public ResponseEntity<ProjSOWItemResp> getAllProjSOWDetails( @RequestBody ProjSOWItemGetReq projSOWItemGetReq ) {
        return new ResponseEntity<ProjSOWItemResp>( projLibService.getAllProjSOWDetails( projSOWItemGetReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.GET_SOR_TRACK_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<ProjSORTrackDetailsResp> getProjSORTrackDetails(@RequestBody ProjSORTrackGetReq projSORTrackGetReq){
    	return new ResponseEntity<ProjSORTrackDetailsResp>(projLibService.getProjSORTrackDetails(projSORTrackGetReq),HttpStatus.OK);
    }
}