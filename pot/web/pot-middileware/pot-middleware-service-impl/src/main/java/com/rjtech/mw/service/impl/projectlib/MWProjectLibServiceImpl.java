package com.rjtech.mw.service.impl.projectlib;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.projectlib.req.*;
import com.rjtech.projectlib.resp.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.projLib.handler.ProjLibUserProjHandler;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@Service(value = "mwProjLiblService")
@RJSService(modulecode = "mwProjLiblService")
@Transactional
public class MWProjectLibServiceImpl extends RestConfigServiceImpl implements MWProjLibService {

    public ProjectResp deactivateProjectEps(ProjDeleteReq projDeleteReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DEACTIVE_PROJECT_EPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDeleteReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
    }

    public ProjEPSOnLoadResp projEpsOnLoad(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_EPS_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEPSOnLoadResp.class);
    }

    public ProjectResp getEPSUserProjects(UserProjGetReq userProjGetReq) {
        System.out.println("MWProjectLibServiceImpl:getEPSUserProjects:userProjGetReq");
        System.out.println(userProjGetReq);
        System.out.println("getClientCode : "+userProjGetReq.getClientCode());
        System.out.println("getContractType : "+userProjGetReq.getContractType());
        System.out.println("getUserId : "+userProjGetReq.getUserId());
        //System.out.println("getProjId : "+userProjGetReq.getProjId());
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_EPS_USR_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userProjGetReq));
        ProjectResp projectResp = AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
        System.out.println("projectResp : "+projectResp);
        if(projectResp.getEpsProjs()!=null)
        {
            System.out.println("projectResp EpsProjs : "+projectResp.getEpsProjs().size());
            System.out.println(projectResp.getEpsProjs());
        }
        if(projectResp.getNewProjIds()!=null)
        {
            System.out.println("projectResp NewProjIds : "+projectResp.getNewProjIds().size());
        }

        return projectResp;
    }

    public ProjEPSOnLoadResp getEPSProjectsById(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_EPS_PROJECTS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEPSOnLoadResp.class);
    }

    public ProjSOEItemResp deactivateProjSOEDetails(ProjSOEItemDelReq projSOEItemDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_SOE_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOEItemDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEItemResp.class);
    }

    public ProjSowOnLoadResp projSowOnLoad(ProjEmpClassOnLoadReq onLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_SOW_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(onLoadReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSowOnLoadResp.class);
    }

    public ProjStockStoreOnLoadResp projStoreStockOnLoad(ProjStockStoresOnloadReq projStockStoresOnloadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_STOCK_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projStockStoresOnloadReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjStockStoreOnLoadResp.class);
    }

    public ProjMaterialOnLoadResp ProjMaterialClassOnLoad(ProMaterialClassGetReq proMaterialClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_MATERAIAL_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(proMaterialClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialOnLoadResp.class);
    }

    public ProjPlantClassOnLoadResp projPlantClassOnLoad(ProjPlantClassGetReq projPlantClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_PLANT_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPlantClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantClassOnLoadResp.class);
    }

    public ProjWorkShiftOnLoadResp projWorkShiftOnLoad(ProjEmpClassOnLoadReq onLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_WORKSHIFT_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(onLoadReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkShiftOnLoadResp.class);
    }

    public ProjCrewListOnLoadResp projCrewListOnLoad(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_CREWLIST_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCrewListOnLoadResp.class);
    }

    public ProjSoeOnLoadResp projSOEOnLoad(ProjSOEItemGetReq projSOEItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_SOE_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOEItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSoeOnLoadResp.class);
    }

    public ProjSorOnLoadResp projSOROnLoad(ProjSORItemGetReq projSORItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_SOR_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSORItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSorOnLoadResp.class);
    }

    public ProjCostItemOnLoadResp projCostItemOnLoad(ProjCostItemGetReq projCostItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_COSTITEM_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemOnLoadResp.class);
    }

    public UserProjResp getUserProjects(UserProjGetReq UserProjGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_USR_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(UserProjGetReq));
        return AppUtils.fromJson(strResponse.getBody(), UserProjResp.class);
    }

    public ProjEmpClassOnLoadResp projEmpClassifyOnLoad(ProjEmpClassOnLoadReq onLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_CLASSIFY_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(onLoadReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpClassOnLoadResp.class);
    }

    public ProjectResp getProjects(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
    }

    public ProjectResp getProjectsById(ProjFilterReq projFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJECTS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
    }

    public ProjectResp saveProject(ProjSaveReq projSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJECT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
    }

    public ProjectResp getProjectEps(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJECT_EPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
    }

    public ProjSOEItemResp getProjSOEDetails(ProjSOEItemGetReq projSOEItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOE_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOEItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEItemResp.class);
    }
    
    public ProjSOETrackLogResp getProjSOETrackDetails(ProjSOETrackGetReq projSOETrackGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOE_TRACK_RECORDS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOETrackGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOETrackLogResp.class);
    }

    public ProjSOEItemResp getProjSOEDetailsById(ProjSOEItemGetReq projSOEItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOE_ITEMS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOEItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEItemResp.class);
    }

    public ProjSOEItemResp saveProjSOEDetails(ProjSOEItemSaveReq projSOEItemSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_SOE_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOEItemSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEItemResp.class);
    }
    
    public ProjSOETrackLogResp saveProjSOETrackDetails(ProjSOETrackSaveReq projSOETrackSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_SOE_TRACK_RECORDS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOETrackSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOETrackLogResp.class);
    }

    public ProjSORItemResp getProjSORDetails(ProjSORItemGetReq projSORItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOR_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSORItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSORItemResp.class);
    }

    public ProjSORItemResp getProjSORDetailsById(ProjSORItemGetReq projSORItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOR_ITEMS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSORItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSORItemResp.class);
    }

    public ProjSORItemResp saveProjSORDetails(ProjSORItemSaveReq projSORItemSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_SOR_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSORItemSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSORItemResp.class);
    }

    public ProjSORItemResp deactivateProjSORDetails(ProjSORItemDelReq projSORItemDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_SOR_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSORItemDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSORItemResp.class);
    }

    public ProjSOWItemResp getProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOW_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }

    public ProjSOWItemResp getMultiProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_SOW_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }

    public ProjSOWItemResp getProjSOWDetailsById(ProjSOWItemGetReq projSOWItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOW_ITEMS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }

    public ProjSOWItemResp saveProjSOWDetails(ProjSOWItemSaveReq projSOWItemSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_SOW_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }

    public ProjSOWItemResp deactivateProjSOWDetails(ProjSOWItemDelReq projSOWItemDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_SOW_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }

    public ProjCostItemResp getProjCostDetails(ProjCostItemGetReq projCostItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_COST_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemResp.class);
    }

    public ProjCostItemResp getProjCostDetailsId(ProjCostItemGetReq projCostItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_COST_ITEMS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemResp.class);
    }

    public ProjCostItemResp saveProjCostDetails(ProjCostItemSaveReq projCostItemSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_COST_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemResp.class);
    }

    public ProjCostItemResp deactivateProjCostDetails(ProjCostItemDelReq projCostItemDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_COST_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemResp.class);
    }

    public ProjLeaveTypeResp saveProjLeaveTypes(ProjLeaveTypeSaveReq projLeaveTypeSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_LEAVE_TYPES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projLeaveTypeSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLeaveTypeResp.class);
    }

    public ProjLeaveTypeResp deleteProjLeaveTypes(ProjLeaveTypeDelReq projLeaveTypeDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_LEAVE_TYPES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projLeaveTypeDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjLeaveTypeResp.class);
    }

    public ProjEmpClassResp getProjEmpClasses(ProjEmpClassGetReq projEmpClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_EMP_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projEmpClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpClassResp.class);
    }

    public ProjEmpClassResp saveProjEmpClasses(ProjEmpClassSaveReq empClassSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_EMP_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empClassSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpClassResp.class);
    }

    public ProjEmpClassResp deleteEmpClasses(ProjEmpClassDelReq projEmpClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_EMP_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projEmpClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpClassResp.class);
    }

    public ProjPlantClassResp getProjPlantClasses(ProjPlantClassGetReq projPlantClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_PLANT_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPlantClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantClassResp.class);
    }

    public ProjPlantClassResp saveProjPlantClasses(ProjPlantClassSaveReq projPlantClassSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_PLANT_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPlantClassSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantClassResp.class);
    }

    public ProjPlantClassResp deletePlantClasses(ProjPlantClassDelReq projEmpClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_PLANT_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projEmpClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantClassResp.class);
    }

    public ProjCrewResp getProjCrewLists(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_CREW_LIST);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCrewResp.class);
    }

    public ProjCrewResp saveProjCrewLists(ProjCrewSaveReq projCrewSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_CREW_LIST);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCrewResp.class);
    }

    public ProjCrewResp deleteProjCrewLists(ProjCrewDelReq projCrewDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_CREW_LIST);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCrewResp.class);
    }

    public ProjWorkShiftResp getProjWorkShifts(ProjWorkShiftGetReq projWorkShifGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_WORK_SHIFT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projWorkShifGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkShiftResp.class);
    }

    public ProjWorkShiftResp saveProjWorkShifts(ProjWorkShiftSaveReq projWorkShiftSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_WORK_SHIFT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projWorkShiftSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkShiftResp.class);
    }

    public ProjWorkShiftResp deleteProjWorkShifts(ProjWorkShiftDelReq projWorkShiftDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_WORK_SHIFT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projWorkShiftDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkShiftResp.class);
    }

    public ProjStoreStockResp getProjStoreStocks(ProjStoreStockGetReq projStoreStockGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_STORE_STOCK);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projStoreStockGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjStoreStockResp.class);
    }

    public ProjStoreStockResp saveProjStoreStocks(ProjStoreStockSaveReq projStoreStockSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_STORE_STOCK);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projStoreStockSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjStoreStockResp.class);
    }

    public ProjStoreStockResp deleteProjStoreStocks(ProjStoreStockDelReq projStoreStockDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DELETE_PROJ_STORE_STOCK);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projStoreStockDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjStoreStockResp.class);
    }

    public ProjMaterialClassResp getProjMaterialClasses(ProMaterialClassGetReq proMaterialClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_MATERIAL_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(proMaterialClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialClassResp.class);
    }

    public ProjMaterialClassResp saveProjMaterialClasses(ProjMaterialClassSaveReq projMaterialClassSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PROJ_MATERIAL_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projMaterialClassSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialClassResp.class);
    }

    public ProjMaterialClassResp deactivateMaterialClasses(ProjMaterialDelReq projMaterialDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.DEACTIVATE_PROJ_MATERIAL_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projMaterialDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialClassResp.class);
    }

    public ProjCostItemResp getProjCostItemsOnly(ProjCostItemGetReq projCostItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_COST_ITEMSONLY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemResp.class);
    }

    public ProjEPSOnLoadResp getEPSOnly(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;

        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_EPS_ONLY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjEPSOnLoadResp.class);
    }

    public Map<Long, LabelKeyTO> getUserProjects() {
        ResponseEntity<String> strResponse = null;
        UserProjGetReq userProjGetReq = new UserProjGetReq();
        userProjGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_USR_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userProjGetReq));

        UserProjResp resp = AppUtils.fromJson(strResponse.getBody(), UserProjResp.class);
        return ProjLibUserProjHandler.getLableKeyTO(resp.getUserProjDetailsTOs());
    }

    public ProjectLibOnLoadResp projectCostUserProjClassify(ProjectLibOnLoadReq req) {
        req.setStatus(StatusCodes.ACTIVE.getValue());
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_COST_USER_PROJECT_CLASSIFICATION);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(req));

        ProjectLibOnLoadResp resp = AppUtils.fromJson(strResponse.getBody(), ProjectLibOnLoadResp.class);
        return resp;
    }

    public ProjectLibOnLoadResp projectUserProjClassify(ProjectLibOnLoadReq req) {
        req.setStatus(StatusCodes.ACTIVE.getValue());
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_USER_PROJECT_CLASSIFICATION);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(req));

        ProjectLibOnLoadResp resp = AppUtils.fromJson(strResponse.getBody(), ProjectLibOnLoadResp.class);
        return resp;
    }

    public ProjSowItemsMapResp getProjSowItemsMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_SOW_ITEMS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSowItemsMapResp.class);
    }

    public ProjSowItemsMapResp getProjSorItemsMap(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_SOR_ITEMS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSowItemsMapResp.class);
    }

    public ProjRestrictedMaterialClassResp getProjRestrictedMaterialClasses(
            ProMaterialClassGetReq proMaterialClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_MATERIAL_RESTRICTED_CLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(proMaterialClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjRestrictedMaterialClassResp.class);
    }

    public ProjCrewResp getMultipleProjsCrewList(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTIPLE_PROJS_CREW_LIST);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCrewResp.class);
    }

    public ProjCostItemResp getMultiProjCostDetails(ProjCostItemGetReq projCostItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTI_PROJ_COST_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjCostItemResp.class);
    }

    public UserProjResp getAllProjects() {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_ALL_PROJECTS);
        strResponse = constructPOSTRestTemplate(url, null);
        return AppUtils.fromJson(strResponse.getBody(), UserProjResp.class);

    }

    public Map<Long, LabelKeyTO> getUserProjEmpClassMap() {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_USR_PROJ_EMP_CLASSIFY);
        strResponse = constructPOSTRestTemplate(url, null);
        UserProjEmpClassResp resp = AppUtils.fromJson(strResponse.getBody(), UserProjEmpClassResp.class);
        return resp.getProjClassMap();
    }

    public Map<Long, ProjCostItemTO> getProjCostItemMap(ProjCostItemGetReq projCostItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJ_COST_ITEMS_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCostItemGetReq));
        ProjCostItemMapResp resp = AppUtils.fromJson(strResponse.getBody(), ProjCostItemMapResp.class);
        return resp.getProjCostItemMap();
    }

    public Map<Long, ProjCrewTO> getMultipleProjsCrewMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTIPLE_PROJS_CREW_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        ProjCrewMapResp resp = AppUtils.fromJson(strResponse.getBody(), ProjCrewMapResp.class);
        return resp.getProjCrewMap();
    }

    public ProjStoreStockResp getMultipleProjsStoreList(ProjStoreStockGetReq projStoreStockGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_MULTIPLE_PROJS_STORE_LIST);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projStoreStockGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjStoreStockResp.class);
    }

    public Map<Long, ProjCrewTO> getProjsCrewMap(ProjCrewGetReq projCrewGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PROJS_CREW_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projCrewGetReq));
        getMultipleProjsCrewMap(projCrewGetReq);
        ProjCrewMapResp resp = AppUtils.fromJson(strResponse.getBody(), ProjCrewMapResp.class);
        return resp.getProjCrewMap();
    }

    public SOWTotalActualQuantitiesResp getSOWTotalActualQuantities(ProjSOWItemGetReq projSOWItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOW_TOTAL_ACTUAL_QUANTITIES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), SOWTotalActualQuantitiesResp.class);
    }

    @Override
    public void saveDefaultProjLeaveTypes(Long clientId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("clientId", clientId);
        String url = AppUtils.getUrl(getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_DEFAULT_PROJ_LEAVE_TYPES), paramsMap);
        constructPOSTRestTemplate(url, "");
    }
    
    public ProjSOWItemResp getProjSOWDetailsByCostCode(ProjSOWItemGetReq projSOWItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOW_ITEMS_BY_COST_CODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }

    public ProjSOWItemResp getProjSOWDetailsExceptCostCode(ProjSOWItemGetReq projSOWItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOW_ITEMS_EXCEPT_COST_CODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSOWItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSOWItemResp.class);
    }
    
    public ProjPMCMOnLoadResp projPMCMOnLoad(ProjPMCMItemGetReq projPMCMItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_PMCM_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCMItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCMOnLoadResp.class);
    }

    public ProjSorOnLoadResp projPMCMOnLoad(ProjSORItemGetReq projPMCMItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
                ProjLibURLConstants.PARH_URL + ProjLibURLConstants.PROJECT_PMCM_POPUP_ONLOAD);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCMItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSorOnLoadResp.class);
    }
    
    public ProjPMCMItemResp getProjPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq) {
        System.out.println("MWProjectLibServiceImpl:getProjPMCMDetails:ProjId:"+projPMCMItemGetReq.getProjId());
        System.out.println("MWProjectLibServiceImpl:getProjPMCMDetails:ClientId:"+projPMCMItemGetReq.getClientId());
        System.out.println("MWProjectLibServiceImpl:getProjPMCMDetails:PmId:"+projPMCMItemGetReq.getPmId());

        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PMCM_ITEMS);

        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCMItemGetReq));
        System.out.println("MWProjectLibServiceImpl:strResponse:"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCMItemResp.class);
    }

    public ProjPMCPCostStatementsResp getProjPMCPCostStatements(ProjPMCPItemGetReq projPMCPItemGetReq) {
        System.out.println("MWProjectLibServiceImpl:getProjPMCPCostStatements:ProjId:"+projPMCPItemGetReq.getProjId());
        System.out.println("MWProjectLibServiceImpl:getProjPMCPCostStatements:ClientId:"+projPMCPItemGetReq.getClientId());
        System.out.println("MWProjectLibServiceImpl:getProjPMCPCostStatements:PmId:"+projPMCPItemGetReq.getProjId());
        System.out.println("MWProjectLibServiceImpl:getProjPMCPCostStatements:ProjIds  size :"+projPMCPItemGetReq.getProjIds().size());

        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PMCP_ITEMS);

        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCPItemGetReq));
        System.out.println("MWProjectLibServiceImpl:strResponse:"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCPCostStatementsResp.class);
    }

    public ProjPMCMItemResp getProjReportPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq) {

        System.out.println("MWProjectLibServiceImpl:getProjReportPMCMDetails:ProjId:"+projPMCMItemGetReq.getProjId());
        System.out.println("MWProjectLibServiceImpl:getProjReportPMCMDetails:ClientId:"+projPMCMItemGetReq.getClientId());
        System.out.println("MWProjectLibServiceImpl:getProjReportPMCMDetails:PmId:"+projPMCMItemGetReq.getPmId());
        System.out.println("ProjLibServiceImpl:getProjPMCMDetails:Status:"+projPMCMItemGetReq.getStatus());
        System.out.println("ProjectLibController:getProjPMCMDetails:getProjStatusDate:"+projPMCMItemGetReq.getProjStatusDate());
        System.out.println("ProjectLibController:getProjPMCMDetails:getProjIds:"+projPMCMItemGetReq.getProjIds().size());
        System.out.println(projPMCMItemGetReq.getProjIds());

        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_REPORT_PMCM_ITEMS);

        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCMItemGetReq));
        System.out.println("MWProjectLibServiceImpl:getProjReportPMCMDetails:strResponse:"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCMItemResp.class);
    }

    public ProjPMCPItemResp getProjReportPMCPDetails(ProjPMCPItemGetReq projPMCPItemGetReq) {

        System.out.println("MWProjectLibServiceImpl:getProjReportPMCPDetails:ProjId:"+projPMCPItemGetReq.getProjId());
        System.out.println("MWProjectLibServiceImpl:getProjReportPMCPDetails:ClientId:"+projPMCPItemGetReq.getClientId());
        System.out.println("MWProjectLibServiceImpl:getProjReportPMCPDetails:PmId:"+projPMCPItemGetReq.getProjId());
        System.out.println("ProjLibServiceImpl:getProjReportPMCPDetails:Status:"+projPMCPItemGetReq.getStatus());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:getProjStatusDate:"+projPMCPItemGetReq.getToDate());
        System.out.println("ProjectLibController:getProjReportPMCPDetails:getProjIds:"+projPMCPItemGetReq.getProjIds().size());
        System.out.println(projPMCPItemGetReq.getProjIds());

        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_REPORT_PMCP_ITEMS);

        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCPItemGetReq));
        System.out.println("MWProjectLibServiceImpl:getProjReportPMCPDetails:strResponse:"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCPItemResp.class);
    }

    public ProjPMCMItemResp getProjPMCMDetailsById(ProjPMCMItemGetReq projPMCMItemGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_PMCM_ITEMS_BY_ID);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCMItemGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCMItemResp.class);
    }

    public ProjPMCMItemResp saveProjPMCMDetails(ProjPMCMItemSaveReq projPMCMItemSaveReq) {
        System.out.println("MWProjectLibServiceImpl:saveProjPMCMDetails:"+projPMCMItemSaveReq.getProjSORItemTOs().size());
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_PMCM_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projPMCMItemSaveReq));
        System.out.println("MWProjectLibServiceImpl:strResponse:"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjPMCMItemResp.class);
    }
    
    public ProjSOEItemResp projSOEApproval( ProjSOEItemGetReq projSOEItemGetReq ) 
    {
    	System.out.println("projSOEApproval function from MWProjectLibServiceImpl class");
    	ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
        		ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SOE_APPROVAL );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSOEItemGetReq ) );
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEItemResp.class);
    }
    
    public ProjSOEItemResp returnSOEWithComments( ProjSOEItemGetReq projSOEItemGetReq ) 
    {
    	System.out.println("returnSOEWithComments function from MWProjectLibServiceImpl class");
    	ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SOE_RETURNED_WITH_COMMENTS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSOEItemGetReq ) );
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEItemResp.class);
    }
    
    public ProjSOEActivityLogResp viewActivityRecords( ProjSOEItemGetReq projSOEItemGetReq ) 
    {
    	System.out.println("viewActivityRecords function from MWProjectLibServiceImpl class");
    	ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SOE_VIEW_ACTIVITY_RECORDS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSOEItemGetReq ) );
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjSOEActivityLogResp.class);
    }
    
    public ProjSORItemResp projSORApproval( ProjSORItemGetReq projSORItemGetReq ) 
    {
    	System.out.println("projSORApproval function from MWProjectLibServiceImpl class");
    	ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(
        		ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SOR_APPROVAL );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSORItemGetReq ) );
        return AppUtils.fromJson(strResponse.getBody(), ProjSORItemResp.class);
    }
    
    public ProjSORItemResp returnSORWithComments( ProjSORItemGetReq projSORItemGetReq ) 
    {
    	System.out.println("returnSORWithComments function from MWProjectLibServiceImpl class");
    	ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SOR_RETURNED_WITH_COMMENTS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSORItemGetReq ) );
        return AppUtils.fromJson(strResponse.getBody(), ProjSORItemResp.class);
    }
    
    public ProjSORActivityLogResp viewSORActivityRecords( ProjSORItemGetReq projSORItemGetReq )
    {
    	System.out.println("viewSORActivityRecords function from MWProjectLibServiceImpl class");
    	ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SOR_VIEW_ACTIVITY_RECORDS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSORItemGetReq ) );
        System.out.println(strResponse);
        return AppUtils.fromJson(strResponse.getBody(), ProjSORActivityLogResp.class);
    }
    
    public ProjSOWItemResp getAllProjSOWDetails( ProjSOWItemGetReq projSOWItemGetReq ) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOW_ITEMS_BY_PROJID );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projSOWItemGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ProjSOWItemResp.class );
    }
    
    public ChangeOrderResp saveChangeOrderDetails( ChangeOrderReq changeOrderReq ) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_CO_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
    
    public ChangeOrderResp getChangeOrderDetails( ChangeOrderReq changeOrderReq ) {
    	System.out.println("getChangeOrderDetails function");
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_CO_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        System.out.println(strResponse.getBody());
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
    
    public ChangeOrderResp saveCoManpowerDetails( ChangeOrderReq changeOrderReq ) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_CO_MANPOWER_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
    @Override
    public ChangeOrderResp getChangeOrderDetailsByCoId( ChangeOrderReq changeOrderReq ) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_CO_DETAILS_BY_CO_ID );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
    
    public ChangeOrderResp saveCoPlantDetails( ChangeOrderReq changeOrderReq ) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_CO_PLANT_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
    
    public ChangeOrderResp updateCoApproverDetails( ChangeOrderReq changeOrderReq ) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.UPDATE_CO_APPROVER_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
    
    public ProjSORTrackDetailsResp getProjSORTrackDetails(ProjSORTrackGetReq projSORTrackGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl(ProjLibURLConstants.PARH_URL + ProjLibURLConstants.GET_SOR_TRACK_RECORDS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projSORTrackGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjSORTrackDetailsResp.class);
    }

	@Override
	public ChangeOrderResp saveCoScopeOfWork(ChangeOrderReq changeOrderReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_CO_SOW );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
	
	@Override
	public ChangeOrderResp saveCoMaterialDetails(ChangeOrderReq changeOrderReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_CO_MATERIAL_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
	
	@Override
	public ChangeOrderResp saveCoCostDetails(ChangeOrderReq changeOrderReq) {
        ResponseEntity<String> strResponse = null;
        String url = getProjectLibExchangeUrl( ProjLibURLConstants.PARH_URL + ProjLibURLConstants.SAVE_CO_COST_DETAILS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( changeOrderReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ChangeOrderResp.class );
    }
}
