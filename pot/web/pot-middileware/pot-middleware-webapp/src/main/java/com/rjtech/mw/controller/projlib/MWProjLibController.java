package com.rjtech.mw.controller.projlib;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.projectlib.req.*;
import com.rjtech.projectlib.resp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@RestController
@RequestMapping(ProjLibURLConstants.PARH_URL)
public class MWProjLibController {

	@Autowired
	private MWProjLibService mwProjLibService;

	@RequestMapping(value = ProjLibURLConstants.DEACTIVE_PROJECT_EPS, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> deactivateProjectEps(@RequestBody ProjDeleteReq projDeleteReq) {
		return new ResponseEntity<ProjectResp>(mwProjLibService.deactivateProjectEps(projDeleteReq), HttpStatus.OK);

	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_EPS_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjEPSOnLoadResp> projEpsOnLoad(@RequestBody ProjGetReq projGetReq) {
		return new ResponseEntity<ProjEPSOnLoadResp>(mwProjLibService.projEpsOnLoad(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_EPS_PROJECTS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjEPSOnLoadResp> getEPSProjectsById(@RequestBody ProjGetReq projGetReq) {
		return new ResponseEntity<ProjEPSOnLoadResp>(mwProjLibService.getEPSProjectsById(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_EPS_USR_PROJECTS, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> getEPSUserProjects(@RequestBody UserProjGetReq userProjGetReq) {
		System.out.println("getEPSUserProjects function");
		return new ResponseEntity<ProjectResp>(mwProjLibService.getEPSUserProjects(userProjGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_SOE_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEItemResp> deactivateProjSOEDetails(@RequestBody ProjSOEItemDelReq projSOEItemDelReq) {
		return new ResponseEntity<ProjSOEItemResp>(mwProjLibService.deactivateProjSOEDetails(projSOEItemDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_SOW_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjSowOnLoadResp> projSowOnLoad(@RequestBody ProjEmpClassOnLoadReq onLoadReq) {
		return new ResponseEntity<ProjSowOnLoadResp>(mwProjLibService.projSowOnLoad(onLoadReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_STOCK_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjStockStoreOnLoadResp> projStoreStockOnLoad(
			@RequestBody ProjStockStoresOnloadReq projStockStoresOnloadReq) {
		return new ResponseEntity<ProjStockStoreOnLoadResp>(
				mwProjLibService.projStoreStockOnLoad(projStockStoresOnloadReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_MATERAIAL_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjMaterialOnLoadResp> ProjMaterialClassOnLoad(
			@RequestBody ProMaterialClassGetReq proMaterialClassGetReq) {
		return new ResponseEntity<ProjMaterialOnLoadResp>(
				mwProjLibService.ProjMaterialClassOnLoad(proMaterialClassGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_PLANT_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjPlantClassOnLoadResp> projPlantClassOnLoad(
			@RequestBody ProjPlantClassGetReq projPlantClassGetReq) {
		return new ResponseEntity<ProjPlantClassOnLoadResp>(mwProjLibService.projPlantClassOnLoad(projPlantClassGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_WORKSHIFT_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjWorkShiftOnLoadResp> projWorkShiftOnLoad(@RequestBody ProjEmpClassOnLoadReq onLoadReq) {
		return new ResponseEntity<ProjWorkShiftOnLoadResp>(mwProjLibService.projWorkShiftOnLoad(onLoadReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_CREWLIST_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjCrewListOnLoadResp> projCrewListOnLoad(@RequestBody ProjCrewGetReq projCrewGetReq) {
		return new ResponseEntity<ProjCrewListOnLoadResp>(mwProjLibService.projCrewListOnLoad(projCrewGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_SOE_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjSoeOnLoadResp> projSOEOnLoad(@RequestBody ProjSOEItemGetReq projSOEItemGetReq) {
		return new ResponseEntity<ProjSoeOnLoadResp>(mwProjLibService.projSOEOnLoad(projSOEItemGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_SOR_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjSorOnLoadResp> projSOROnLoad(@RequestBody ProjSORItemGetReq projSORItemGetReq) {
		return new ResponseEntity<ProjSorOnLoadResp>(mwProjLibService.projSOROnLoad(projSORItemGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_COSTITEM_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemOnLoadResp> projCostItemOnLoad(
			@RequestBody ProjCostItemGetReq projCostItemGetReq) {
		return new ResponseEntity<ProjCostItemOnLoadResp>(mwProjLibService.projCostItemOnLoad(projCostItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_USR_PROJECTS, method = RequestMethod.POST)
	public ResponseEntity<UserProjResp> getUserProjects(@RequestBody UserProjGetReq userProjGetReq) {
		return new ResponseEntity<UserProjResp>(mwProjLibService.getUserProjects(userProjGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_CLASSIFY_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjEmpClassOnLoadResp> projEmpClassifyOnLoad(@RequestBody ProjEmpClassOnLoadReq onLoadReq) {
		return new ResponseEntity<ProjEmpClassOnLoadResp>(mwProjLibService.projEmpClassifyOnLoad(onLoadReq),
				HttpStatus.OK);

	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJECTS, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> getProjects(@RequestBody ProjGetReq projGetReq) {
		return new ResponseEntity<ProjectResp>(mwProjLibService.getProjects(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJECTS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> getProjectsById(@RequestBody ProjFilterReq projFilterReq) {
		return new ResponseEntity<ProjectResp>(mwProjLibService.getProjectsById(projFilterReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJECT, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> saveProject(@RequestBody ProjSaveReq projSaveReq) {

		ProjectResp projectResp = mwProjLibService.saveProject(projSaveReq);

		return new ResponseEntity<ProjectResp>(projectResp, HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_EPS_STACTURE, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> saveEpsStacture(@RequestBody ProjSaveReq projSaveReq) {

		return new ResponseEntity<ProjectResp>(mwProjLibService.saveProject(projSaveReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJECT_EPS, method = RequestMethod.POST)
	public ResponseEntity<ProjectResp> getProjectEps(@RequestBody ProjGetReq projGetReq) {
		return new ResponseEntity<ProjectResp>(mwProjLibService.getProjectEps(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOE_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEItemResp> getProjSOEDetails(@RequestBody ProjSOEItemGetReq projSOEItemGetReq) {
		return new ResponseEntity<ProjSOEItemResp>(mwProjLibService.getProjSOEDetails(projSOEItemGetReq),
				HttpStatus.OK);

	}
	
	@RequestMapping(value = ProjLibURLConstants.GET_SOE_TRACK_RECORDS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOETrackLogResp> getProjSOETrackDetails(@RequestBody ProjSOETrackGetReq projSOETrackGetReq) {
		return new ResponseEntity<ProjSOETrackLogResp>(mwProjLibService.getProjSOETrackDetails(projSOETrackGetReq),
				HttpStatus.OK);

	}
	@RequestMapping(value = ProjLibURLConstants.GET_SOE_ITEMS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEItemResp> getProjSOEDetailsById(@RequestBody ProjSOEItemGetReq projSOEItemGetReq) {
		return new ResponseEntity<ProjSOEItemResp>(mwProjLibService.getProjSOEDetailsById(projSOEItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_SOE_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEItemResp> saveProjSOEDetails(@RequestBody ProjSOEItemSaveReq projSOEItemSaveReq) {
		return new ResponseEntity<ProjSOEItemResp>(mwProjLibService.saveProjSOEDetails(projSOEItemSaveReq),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = ProjLibURLConstants.SAVE_SOE_TRACK_RECORDS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOETrackLogResp> saveProjSOETrackDetails(@RequestBody ProjSOETrackSaveReq projSOETrackSaveReq) {
		return new ResponseEntity<ProjSOETrackLogResp>(mwProjLibService.saveProjSOETrackDetails(projSOETrackSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOR_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSORItemResp> getProjSORDetails(@RequestBody ProjSORItemGetReq projSORItemGetReq) {
		return new ResponseEntity<ProjSORItemResp>(mwProjLibService.getProjSORDetails(projSORItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOR_ITEMS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjSORItemResp> getProjSORDetailsById(@RequestBody ProjSORItemGetReq projSORItemGetReq) {
		return new ResponseEntity<ProjSORItemResp>(mwProjLibService.getProjSORDetailsById(projSORItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_SOR_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSORItemResp> saveProjSORDetails(@RequestBody ProjSORItemSaveReq projSORItemSaveReq) {
		return new ResponseEntity<ProjSORItemResp>(mwProjLibService.saveProjSORDetails(projSORItemSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_SOR_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSORItemResp> deactivateProjSORDetails(@RequestBody ProjSORItemDelReq projSORItemDelReq) {
		return new ResponseEntity<ProjSORItemResp>(mwProjLibService.deactivateProjSORDetails(projSORItemDelReq),
				HttpStatus.OK);

	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> getProjSOWDetails(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.getProjSOWDetails(projSOWItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_SOW_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> getMultiProjSOWDetails(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.getMultiProjSOWDetails(projSOWItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> getProjSOWDetailsById(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.getProjSOWDetailsById(projSOWItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_SOW_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> saveProjSOWDetails(@RequestBody ProjSOWItemSaveReq projSOWItemSaveReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.saveProjSOWDetails(projSOWItemSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_SOW_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> deactivateProjSOWDetails(@RequestBody ProjSOWItemDelReq projSOWItemDelReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.deactivateProjSOWDetails(projSOWItemDelReq),
				HttpStatus.OK);

	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemResp> getProjCostDetails(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
		return new ResponseEntity<ProjCostItemResp>(mwProjLibService.getProjCostDetails(projCostItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemResp> getProjCostDetailsId(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
		return new ResponseEntity<ProjCostItemResp>(mwProjLibService.getProjCostDetailsId(projCostItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_COST_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemResp> saveProjCostDetails(@RequestBody ProjCostItemSaveReq projCostItemSaveReq) {
		return new ResponseEntity<ProjCostItemResp>(mwProjLibService.saveProjCostDetails(projCostItemSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_COST_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemResp> deactivateProjCostDetails(
			@RequestBody ProjCostItemDelReq projCostItemDelReq) {
		return new ResponseEntity<ProjCostItemResp>(mwProjLibService.deactivateProjCostDetails(projCostItemDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_LEAVE_TYPES, method = RequestMethod.POST)
	public ResponseEntity<ProjLeaveTypeResp> saveProjLeaveTypes(
			@RequestBody ProjLeaveTypeSaveReq projLeaveTypeSaveReq) {
		return new ResponseEntity<ProjLeaveTypeResp>(mwProjLibService.saveProjLeaveTypes(projLeaveTypeSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_LEAVE_TYPES, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjLeaveTypeResp> deleteProjLeaveTypes(
			@RequestBody ProjLeaveTypeDelReq projLeaveTypeDelReq) {
		return new ResponseEntity<ProjLeaveTypeResp>(mwProjLibService.deleteProjLeaveTypes(projLeaveTypeDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_EMP_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjEmpClassResp> getProjEmpClasses(@RequestBody ProjEmpClassGetReq projEmpClassGetReq) {
		return new ResponseEntity<ProjEmpClassResp>(mwProjLibService.getProjEmpClasses(projEmpClassGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_EMP_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjEmpClassResp> saveProjEmpClasses(@RequestBody ProjEmpClassSaveReq empClassSaveReq) {
		return new ResponseEntity<ProjEmpClassResp>(mwProjLibService.saveProjEmpClasses(empClassSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_EMP_CLASSES, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjEmpClassResp> deleteEmpClasses(@RequestBody ProjEmpClassDelReq projEmpClassDelReq) {
		return new ResponseEntity<ProjEmpClassResp>(mwProjLibService.deleteEmpClasses(projEmpClassDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_PLANT_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjPlantClassResp> getProjPlantClasses(
			@RequestBody ProjPlantClassGetReq projEmpClassGetReq) {
		return new ResponseEntity<ProjPlantClassResp>(mwProjLibService.getProjPlantClasses(projEmpClassGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_PLANT_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjPlantClassResp> saveProjPlantClasses(
			@RequestBody ProjPlantClassSaveReq projPlantClassSaveReq) {
		return new ResponseEntity<ProjPlantClassResp>(mwProjLibService.saveProjPlantClasses(projPlantClassSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_PLANT_CLASSES, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjPlantClassResp> deletePlantClasses(@RequestBody ProjPlantClassDelReq projEmpClassDelReq) {
		return new ResponseEntity<ProjPlantClassResp>(mwProjLibService.deletePlantClasses(projEmpClassDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_CREW_LIST, method = { RequestMethod.POST })
	public ResponseEntity<ProjCrewResp> getProjCrewLists(@RequestBody ProjCrewGetReq projCrewGetReq) {
		return new ResponseEntity<ProjCrewResp>(mwProjLibService.getProjCrewLists(projCrewGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_CREW_LIST, method = { RequestMethod.POST })
	public ResponseEntity<ProjCrewResp> saveProjCrewLists(@RequestBody ProjCrewSaveReq projCrewSaveReq) {
		return new ResponseEntity<ProjCrewResp>(mwProjLibService.saveProjCrewLists(projCrewSaveReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_CREW_LIST, method = { RequestMethod.POST }, consumes = {
			"application/json" })
	public ResponseEntity<ProjCrewResp> deleteProjCrewLists(@RequestBody ProjCrewDelReq projCrewDelReq) {
		return new ResponseEntity<ProjCrewResp>(mwProjLibService.deleteProjCrewLists(projCrewDelReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_WORK_SHIFT, method = RequestMethod.POST)
	public ResponseEntity<ProjWorkShiftResp> getProjWorkShifts(@RequestBody ProjWorkShiftGetReq projWorkShifGetReq) {
		return new ResponseEntity<ProjWorkShiftResp>(mwProjLibService.getProjWorkShifts(projWorkShifGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_WORK_SHIFT, method = RequestMethod.POST)
	public ResponseEntity<ProjWorkShiftResp> saveProjWorkShifts(
			@RequestBody ProjWorkShiftSaveReq projWorkShiftSaveReq) {
		return new ResponseEntity<ProjWorkShiftResp>(mwProjLibService.saveProjWorkShifts(projWorkShiftSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_WORK_SHIFT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjWorkShiftResp> deleteProjWorkShifts(
			@RequestBody ProjWorkShiftDelReq projWorkShiftDelReq) {
		return new ResponseEntity<ProjWorkShiftResp>(mwProjLibService.deleteProjWorkShifts(projWorkShiftDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_STORE_STOCK, method = RequestMethod.POST)
	public ResponseEntity<ProjStoreStockResp> getProjStoreStocks(
			@RequestBody ProjStoreStockGetReq projStoreStockGetReq) {
		return new ResponseEntity<ProjStoreStockResp>(mwProjLibService.getProjStoreStocks(projStoreStockGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_STORE_STOCK, method = RequestMethod.POST)
	public ResponseEntity<ProjStoreStockResp> saveProjStoreStocks(
			@RequestBody ProjStoreStockSaveReq projStoreStockSaveReq) {
		return new ResponseEntity<ProjStoreStockResp>(mwProjLibService.saveProjStoreStocks(projStoreStockSaveReq),
				HttpStatus.OK);

	}

	@RequestMapping(value = ProjLibURLConstants.DELETE_PROJ_STORE_STOCK, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjStoreStockResp> deleteProjStoreStocks(
			@RequestBody ProjStoreStockDelReq projStoreStockDelReq) {
		return new ResponseEntity<ProjStoreStockResp>(mwProjLibService.deleteProjStoreStocks(projStoreStockDelReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_MATERIAL_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjMaterialClassResp> getProjMaterialClasses(
			@RequestBody ProMaterialClassGetReq proMaterialClassGetReq) {
		return new ResponseEntity<ProjMaterialClassResp>(
				mwProjLibService.getProjMaterialClasses(proMaterialClassGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PROJ_MATERIAL_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjMaterialClassResp> saveProjMaterialClasses(
			@RequestBody ProjMaterialClassSaveReq projMaterialClassSaveReq) {
		return new ResponseEntity<ProjMaterialClassResp>(
				mwProjLibService.saveProjMaterialClasses(projMaterialClassSaveReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.DEACTIVATE_PROJ_MATERIAL_CLASSES, method = RequestMethod.POST)
	public ResponseEntity<ProjMaterialClassResp> deactivateMaterialClasses(
			@RequestBody ProjMaterialDelReq projMaterialDelReq) {
		return new ResponseEntity<ProjMaterialClassResp>(mwProjLibService.deactivateMaterialClasses(projMaterialDelReq),
				HttpStatus.OK);

	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMSONLY, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemResp> getProjCostItemsOnly(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
		return new ResponseEntity(AppUtils.toJson(mwProjLibService.getProjCostItemsOnly(projCostItemGetReq)),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_EPS_ONLY, method = RequestMethod.POST)
	public ResponseEntity<ProjEPSOnLoadResp> getEPSOnly(@RequestBody ProjGetReq projGetReq) {

		return new ResponseEntity<ProjEPSOnLoadResp>(mwProjLibService.getEPSOnly(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_SOW_ITEMS_MAP, method = RequestMethod.POST)
	public ResponseEntity<ProjSowItemsMapResp> getProjSowItemsMap(@RequestBody ProjGetReq projGetReq) {

		return new ResponseEntity<ProjSowItemsMapResp>(mwProjLibService.getProjSowItemsMap(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_SOR_ITEMS_MAP, method = RequestMethod.POST)
	public ResponseEntity<ProjSowItemsMapResp> getProjSorItemsMap(@RequestBody ProjGetReq projGetReq) {
		return new ResponseEntity<ProjSowItemsMapResp>(mwProjLibService.getProjSorItemsMap(projGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJS_CREW_LIST, method = RequestMethod.POST)
	public ResponseEntity<ProjCrewResp> getMultipleProjsCrewList(@RequestBody ProjCrewGetReq projCrewGetReq) {

		return new ResponseEntity<ProjCrewResp>(mwProjLibService.getMultipleProjsCrewList(projCrewGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_MULTI_PROJ_COST_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemResp> getMultiProjCostDetails(
			@RequestBody ProjCostItemGetReq projCostItemGetReq) {

		return new ResponseEntity<ProjCostItemResp>(mwProjLibService.getMultiProjCostDetails(projCostItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_ALL_PROJECTS, method = RequestMethod.POST)
	public ResponseEntity<String> getAllProjects() {
		return new ResponseEntity<String>(AppUtils.toJson(mwProjLibService.getAllProjects()), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJS_STORE_LIST, method = RequestMethod.POST)
	public ResponseEntity<ProjStoreStockResp> getMultipleProjsStoreList(
			@RequestBody ProjStoreStockGetReq projStoreStockGetReq) {

		return new ResponseEntity<ProjStoreStockResp>(mwProjLibService.getMultipleProjsStoreList(projStoreStockGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJ_COST_ITEMS_MAP, method = RequestMethod.POST)
	public ResponseEntity<ProjCostItemMapResp> getProjCostItemMap(@RequestBody ProjCostItemGetReq projCostItemGetReq) {
		ProjCostItemMapResp resp = new ProjCostItemMapResp();
		resp.setProjCostItemMap(mwProjLibService.getProjCostItemMap(projCostItemGetReq));
		return new ResponseEntity<ProjCostItemMapResp>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_MULTIPLE_PROJS_CREW_MAP, method = RequestMethod.POST)
	public ResponseEntity<ProjCrewMapResp> getMultipleProjsCrewMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
		ProjCrewMapResp resp = new ProjCrewMapResp();
		resp.setProjCrewMap(mwProjLibService.getMultipleProjsCrewMap(projCrewGetReq));
		return new ResponseEntity<ProjCrewMapResp>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PROJS_CREW_MAP, method = RequestMethod.POST)
	public ResponseEntity<ProjCrewMapResp> getProjsCrewMap(@RequestBody ProjCrewGetReq projCrewGetReq) {
		ProjCrewMapResp resp = new ProjCrewMapResp();
		ProjCrewResp projCrewResp = mwProjLibService.getMultipleProjsCrewList(projCrewGetReq);
		Map<Long, ProjCrewTO> map = new HashMap<Long, ProjCrewTO>();
		for (ProjCrewTO projCrewTO : projCrewResp.getProjCrewTOs()) {
			map.put(projCrewTO.getId(), projCrewTO);
		}
		resp.setProjCrewMap(map);
		return new ResponseEntity<ProjCrewMapResp>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOW_TOTAL_ACTUAL_QUANTITIES, method = RequestMethod.POST)
	public ResponseEntity<SOWTotalActualQuantitiesResp> getSOWTotalActualQuantities(
			@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
		return new ResponseEntity<SOWTotalActualQuantitiesResp>(
				mwProjLibService.getSOWTotalActualQuantities(projSOWItemGetReq), HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_BY_COST_CODES, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> getSOWByCostIds(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.getProjSOWDetailsByCostCode(projSOWItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_EXCEPT_COST_CODES, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> getSOWExceptCostIds(@RequestBody ProjSOWItemGetReq projSOWItemGetReq) {
		return new ResponseEntity<ProjSOWItemResp>(mwProjLibService.getProjSOWDetailsExceptCostCode(projSOWItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PMCM_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCMItemResp> getProjPMCMDetails(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {
		return new ResponseEntity<ProjPMCMItemResp>(mwProjLibService.getProjPMCMDetails(projPMCMItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PMCP_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCPCostStatementsResp> getProjPMCPCostStatements(@RequestBody ProjPMCPItemGetReq projPMCPItemGetReq) {
		return new ResponseEntity<ProjPMCPCostStatementsResp>(mwProjLibService.getProjPMCPCostStatements(projPMCPItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_REPORT_PMCM_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCMItemResp> getProjReportPMCMDetails(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {

		System.out.println("MWProjLibController getProjReportPMCMDetails ProjPMCMItemGetReq");
		System.out.println(projPMCMItemGetReq);
		return new ResponseEntity<ProjPMCMItemResp>(mwProjLibService.getProjReportPMCMDetails(projPMCMItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_REPORT_PMCP_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCPItemResp> getProjReportPMCPDetails(@RequestBody ProjPMCPItemGetReq projPMCPItemGetReq) {

		System.out.println("MWProjLibController getProjReportPMCPDetails ProjPMCMItemGetReq");
		System.out.println(projPMCPItemGetReq);
		return new ResponseEntity<ProjPMCPItemResp>(mwProjLibService.getProjReportPMCPDetails(projPMCPItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.GET_PMCM_ITEMS_BY_ID, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCMItemResp> getProjPMCMDetailsById(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {
		return new ResponseEntity<ProjPMCMItemResp>(mwProjLibService.getProjPMCMDetailsById(projPMCMItemGetReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.SAVE_PMCM_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCMItemResp> saveProjPMCMDetails(@RequestBody ProjPMCMItemSaveReq projPMCMItemSaveReq) {
		System.out.println("MWProjLibController:saveProjPMCMDetails:projPMCMItemSaveReq Size: "
				+ projPMCMItemSaveReq.getProjSORItemTOs().size());
		return new ResponseEntity<ProjPMCMItemResp>(mwProjLibService.saveProjPMCMDetails(projPMCMItemSaveReq),
				HttpStatus.OK);
	}

	@RequestMapping(value = ProjLibURLConstants.PROJECT_PMCM_POPUP_ONLOAD, method = RequestMethod.POST)
	public ResponseEntity<ProjPMCMOnLoadResp> projPMCMOnLoad(@RequestBody ProjPMCMItemGetReq projPMCMItemGetReq) {
		return new ResponseEntity<ProjPMCMOnLoadResp>(mwProjLibService.projPMCMOnLoad(projPMCMItemGetReq),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = ProjLibURLConstants.SOE_APPROVAL, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEItemResp> projSoeApproval( @RequestBody ProjSOEItemGetReq projSOEItemGetReq ) {
		System.out.println("projSoeApproval function from MWProjLibController class");		
        return new ResponseEntity<ProjSOEItemResp>( mwProjLibService.projSOEApproval( projSOEItemGetReq ), HttpStatus.OK );
	}

	@RequestMapping(value = ProjLibURLConstants.SOE_RETURNED_WITH_COMMENTS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEItemResp> returnSOEWithComments( @RequestBody ProjSOEItemGetReq projSOEItemGetReq ) {
		System.out.println("returnSOEWithComments function from MWProjLibController class");		
        return new ResponseEntity<ProjSOEItemResp>( mwProjLibService.returnSOEWithComments( projSOEItemGetReq ), HttpStatus.OK );
	}
	
	@RequestMapping(value = ProjLibURLConstants.SOE_VIEW_ACTIVITY_RECORDS, method = RequestMethod.POST)
	public ResponseEntity<ProjSOEActivityLogResp> viewActivityRecords( @RequestBody ProjSOEItemGetReq projSOEItemGetReq ) {
		System.out.println("viewActivityRecords function from MWProjLibController class");		
		ProjSOEActivityLogResp projSOEActivityLogResp = new ProjSOEActivityLogResp();
		projSOEActivityLogResp = mwProjLibService.viewActivityRecords( projSOEItemGetReq );
		System.out.println(projSOEActivityLogResp.getProjSOEActivityLogTOs().size());		
        return new ResponseEntity<ProjSOEActivityLogResp>( projSOEActivityLogResp, HttpStatus.OK );
	}
	
	@RequestMapping(value = ProjLibURLConstants.SOR_APPROVAL, method = RequestMethod.POST)
	public ResponseEntity<ProjSORItemResp> projSorApproval( @RequestBody ProjSORItemGetReq projSORItemGetReq ) {
		System.out.println("projSorApproval function from MWProjLibController class");
        return new ResponseEntity<ProjSORItemResp>( mwProjLibService.projSORApproval( projSORItemGetReq ), HttpStatus.OK );
	}

	@RequestMapping(value = ProjLibURLConstants.SOR_RETURNED_WITH_COMMENTS, method = RequestMethod.POST)
	public ResponseEntity<ProjSORItemResp> returnSORWithComments( @RequestBody ProjSORItemGetReq projSORItemGetReq ) {
		System.out.println("returnSORWithComments function from MWProjLibController class");		
        return new ResponseEntity<ProjSORItemResp>( mwProjLibService.returnSORWithComments( projSORItemGetReq ), HttpStatus.OK );
	}
	
	@RequestMapping(value = ProjLibURLConstants.SOR_VIEW_ACTIVITY_RECORDS, method = RequestMethod.POST)
	public ResponseEntity<ProjSORActivityLogResp> viewSORActivityRecords( @RequestBody ProjSORItemGetReq projSORItemGetReq ) {
		System.out.println("viewSORActivityRecords function from MWProjLibController class");		
		ProjSORActivityLogResp projSORActivityLogResp = new ProjSORActivityLogResp();
		projSORActivityLogResp = mwProjLibService.viewSORActivityRecords( projSORItemGetReq );
        return new ResponseEntity<ProjSORActivityLogResp>( projSORActivityLogResp, HttpStatus.OK );
	}
	
	@RequestMapping(value = ProjLibURLConstants.GET_SOW_ITEMS_BY_PROJID, method = RequestMethod.POST)
	public ResponseEntity<ProjSOWItemResp> getAllProjSOWDetails( @RequestBody ProjSOWItemGetReq projSOWItemGetReq ) {
		return new ResponseEntity<ProjSOWItemResp>( mwProjLibService.getAllProjSOWDetails( projSOWItemGetReq ), HttpStatus.OK );
	}
	
	@RequestMapping(value = ProjLibURLConstants.SAVE_CO_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveChangeOrderDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.saveChangeOrderDetails( changeOrderReq ), HttpStatus.OK );
    }
	
	@RequestMapping(value = ProjLibURLConstants.SAVE_CO_SOW, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoScopeOfWork( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.saveCoScopeOfWork( changeOrderReq ), HttpStatus.OK );
    }
	
	@RequestMapping(value = ProjLibURLConstants.SAVE_CO_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoMaterialDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.saveCoMaterialDetails( changeOrderReq ), HttpStatus.OK );
    }
	
	@RequestMapping(value = ProjLibURLConstants.SAVE_CO_COST_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoCostDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.saveCoCostDetails( changeOrderReq ), HttpStatus.OK );
    }
	
	@RequestMapping(value = ProjLibURLConstants.GET_CO_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> getChangeOrderDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.getChangeOrderDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_MANPOWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoManpowerDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.saveCoManpowerDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.GET_CO_DETAILS_BY_CO_ID, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> getChangeOrderDetailsByCoId( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.getChangeOrderDetailsByCoId( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoPlantDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.saveCoPlantDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.UPDATE_CO_APPROVER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> updateCoApproverDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( mwProjLibService.updateCoApproverDetails( changeOrderReq ), HttpStatus.OK );
    }
    @RequestMapping(value = ProjLibURLConstants.GET_SOR_TRACK_RECORDS, method = RequestMethod.POST)
	public ResponseEntity<ProjSORTrackDetailsResp> getProjSORTrackDetails(@RequestBody ProjSORTrackGetReq projSORTrackGetReq) {
		return new ResponseEntity<ProjSORTrackDetailsResp>(mwProjLibService.getProjSORTrackDetails(projSORTrackGetReq),
				HttpStatus.OK);
	}
}
