package com.rjtech.mw.service.projlib;

import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.projectlib.req.*;
import com.rjtech.projectlib.resp.*;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MWProjLibService {

    ProjectResp deactivateProjectEps(ProjDeleteReq projDeleteReq);

    ProjEPSOnLoadResp projEpsOnLoad(ProjGetReq projGetReq);

    ProjectResp getEPSUserProjects(UserProjGetReq userProjGetReq);

    ProjEPSOnLoadResp getEPSProjectsById(ProjGetReq projGetReq);

    ProjSOEItemResp deactivateProjSOEDetails(ProjSOEItemDelReq projSOEItemDelReq);

    ProjSowOnLoadResp projSowOnLoad(ProjEmpClassOnLoadReq onLoadReq);

    ProjStockStoreOnLoadResp projStoreStockOnLoad(ProjStockStoresOnloadReq projStockStoresOnloadReq);

    ProjMaterialOnLoadResp ProjMaterialClassOnLoad(ProMaterialClassGetReq proMaterialClassGetReq);

    ProjPlantClassOnLoadResp projPlantClassOnLoad(ProjPlantClassGetReq projPlantClassGetReq);

    ProjWorkShiftOnLoadResp projWorkShiftOnLoad(ProjEmpClassOnLoadReq onLoadReq);

    ProjCrewListOnLoadResp projCrewListOnLoad(ProjCrewGetReq projCrewGetReq);

    ProjSoeOnLoadResp projSOEOnLoad(ProjSOEItemGetReq projSOEItemGetReq);

    ProjSorOnLoadResp projSOROnLoad(ProjSORItemGetReq projSORItemGetReq);

    ProjCostItemOnLoadResp projCostItemOnLoad(ProjCostItemGetReq projCostItemGetReq);

    UserProjResp getUserProjects(UserProjGetReq UserProjGetReq);

    ProjEmpClassOnLoadResp projEmpClassifyOnLoad(ProjEmpClassOnLoadReq onLoadReq);

    ProjectResp getProjects(ProjGetReq projGetReq);

    ProjectResp getProjectsById(ProjFilterReq projFilterReq);

    ProjectResp saveProject(ProjSaveReq projSaveReq);

    ProjectResp getProjectEps(ProjGetReq projGetReq);

    ProjSOEItemResp getProjSOEDetails(ProjSOEItemGetReq projSOEItemGetReq);

    ProjSOEItemResp getProjSOEDetailsById(ProjSOEItemGetReq projSOEItemGetReq);

    ProjSOEItemResp saveProjSOEDetails(ProjSOEItemSaveReq projSOEItemSaveReq);

    ProjSORItemResp getProjSORDetails(ProjSORItemGetReq projSORItemGetReq);

    ProjSORItemResp getProjSORDetailsById(ProjSORItemGetReq projSORItemGetReq);

    ProjSORItemResp saveProjSORDetails(ProjSORItemSaveReq projSORItemSaveReq);

    ProjSORItemResp deactivateProjSORDetails(ProjSORItemDelReq projSORItemDelReq);

    ProjSOWItemResp getProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq);

    ProjSOWItemResp getMultiProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq);

    ProjSOWItemResp getProjSOWDetailsById(ProjSOWItemGetReq projSOWItemGetReq);

    ProjSOWItemResp saveProjSOWDetails(ProjSOWItemSaveReq projSOWItemSaveReq);

    ProjSOWItemResp deactivateProjSOWDetails(ProjSOWItemDelReq projSOWItemDelReq);

    ProjCostItemResp getProjCostDetails(ProjCostItemGetReq projCostItemGetReq);

    ProjCostItemResp getProjCostDetailsId(ProjCostItemGetReq projCostItemGetReq);

    ProjCostItemResp saveProjCostDetails(ProjCostItemSaveReq projCostItemSaveReq);

    ProjCostItemResp deactivateProjCostDetails(ProjCostItemDelReq projCostItemDelReq);

    ProjLeaveTypeResp saveProjLeaveTypes(ProjLeaveTypeSaveReq projLeaveTypeSaveReq);

    ProjLeaveTypeResp deleteProjLeaveTypes(ProjLeaveTypeDelReq projLeaveTypeDelReq);

    ProjEmpClassResp getProjEmpClasses(ProjEmpClassGetReq projEmpClassGetReq);

    ProjEmpClassResp saveProjEmpClasses(ProjEmpClassSaveReq empClassSaveReq);

    ProjEmpClassResp deleteEmpClasses(ProjEmpClassDelReq projEmpClassDelReq);

    ProjPlantClassResp getProjPlantClasses(ProjPlantClassGetReq projPlantClassGetReq);

    ProjPlantClassResp saveProjPlantClasses(ProjPlantClassSaveReq projPlantClassSaveReq);

    ProjPlantClassResp deletePlantClasses(ProjPlantClassDelReq projPlantClassDelReq);

    ProjCrewResp getProjCrewLists(ProjCrewGetReq projCrewGetReq);

    ProjCrewResp saveProjCrewLists(ProjCrewSaveReq projCrewSaveReq);

    ProjCrewResp deleteProjCrewLists(ProjCrewDelReq projCrewDelReq);

    ProjWorkShiftResp getProjWorkShifts(ProjWorkShiftGetReq projWorkShifGetReq);

    ProjWorkShiftResp saveProjWorkShifts(ProjWorkShiftSaveReq projWorkShiftSaveReq);

    ProjWorkShiftResp deleteProjWorkShifts(ProjWorkShiftDelReq projWorkShiftDelReq);

    ProjStoreStockResp getProjStoreStocks(ProjStoreStockGetReq projStoreStockGetReq);

    ProjStoreStockResp saveProjStoreStocks(ProjStoreStockSaveReq projStoreStockSaveReq);

    ProjStoreStockResp deleteProjStoreStocks(ProjStoreStockDelReq projStoreStockDelReq);

    ProjMaterialClassResp getProjMaterialClasses(ProMaterialClassGetReq proMaterialClassGetReq);

    ProjMaterialClassResp saveProjMaterialClasses(ProjMaterialClassSaveReq projMaterialClassSaveReq);

    ProjMaterialClassResp deactivateMaterialClasses(ProjMaterialDelReq projMaterialDelReq);

    ProjCostItemResp getProjCostItemsOnly(ProjCostItemGetReq projCostItemGetReq);

    Map<Long, LabelKeyTO> getUserProjects();

    ProjEPSOnLoadResp getEPSOnly(ProjGetReq projGetReq);

    ProjectLibOnLoadResp projectCostUserProjClassify(ProjectLibOnLoadReq req);

    ProjectLibOnLoadResp projectUserProjClassify(ProjectLibOnLoadReq req);

    ProjSowItemsMapResp getProjSowItemsMap(ProjGetReq projGetReq);

    ProjSowItemsMapResp getProjSorItemsMap(ProjGetReq projGetReq);

    ProjRestrictedMaterialClassResp getProjRestrictedMaterialClasses(ProMaterialClassGetReq proMaterialClassGetReq);

    ProjCrewResp getMultipleProjsCrewList(ProjCrewGetReq projCrewGetReq);

    ProjCostItemResp getMultiProjCostDetails(ProjCostItemGetReq projCostItemGetReq);

    UserProjResp getAllProjects();

    Map<Long, LabelKeyTO> getUserProjEmpClassMap();

    ProjStoreStockResp getMultipleProjsStoreList(ProjStoreStockGetReq projStoreStockGetReq);

    public Map<Long, ProjCostItemTO> getProjCostItemMap(ProjCostItemGetReq projCostItemGetReq);

    public Map<Long, ProjCrewTO> getMultipleProjsCrewMap(ProjCrewGetReq projCrewGetReq);

    public Map<Long, ProjCrewTO> getProjsCrewMap(ProjCrewGetReq projCrewGetReq);

    SOWTotalActualQuantitiesResp getSOWTotalActualQuantities(ProjSOWItemGetReq projSOWItemGetReq);

    void saveDefaultProjLeaveTypes(Long clientId);
    
    public ProjSOWItemResp getProjSOWDetailsByCostCode(ProjSOWItemGetReq projSOWItemGetReq);

    public ProjSOWItemResp getProjSOWDetailsExceptCostCode(ProjSOWItemGetReq projSOWItemGetReq);

    ProjPMCMOnLoadResp projPMCMOnLoad(ProjPMCMItemGetReq projPMCMItemGetReq);

    ProjPMCMItemResp getProjPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq);

    ProjPMCMItemResp getProjReportPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq);

    ProjPMCPItemResp getProjReportPMCPDetails(ProjPMCPItemGetReq projPMCPItemGetReq);

    ProjPMCMItemResp getProjPMCMDetailsById(ProjPMCMItemGetReq projPMCMItemGetReq);

    ProjPMCMItemResp saveProjPMCMDetails(ProjPMCMItemSaveReq projPMCMItemSaveReq);

    ProjPMCPCostStatementsResp getProjPMCPCostStatements(ProjPMCPItemGetReq projPMCPItemGetReq);
    
    ProjSOEItemResp projSOEApproval( ProjSOEItemGetReq projSOEItemGetReq );
    
    ProjSOEItemResp returnSOEWithComments( ProjSOEItemGetReq projSOEItemGetReq );
    
    ProjSOEActivityLogResp viewActivityRecords( ProjSOEItemGetReq projSOEItemGetReq );
    
    ProjSORItemResp projSORApproval( ProjSORItemGetReq projSORItemGetReq );
    
    ProjSORItemResp returnSORWithComments( ProjSORItemGetReq projSORItemGetReq );
    
    ProjSORActivityLogResp viewSORActivityRecords( ProjSORItemGetReq projSORItemGetReq );
    
    ProjSOWItemResp getAllProjSOWDetails( ProjSOWItemGetReq projSOWItemGetReq );
    
    ChangeOrderResp saveChangeOrderDetails( ChangeOrderReq changeOrderReq );
    
    ChangeOrderResp getChangeOrderDetails( ChangeOrderReq changeOrderReq );
    
    ChangeOrderResp saveCoManpowerDetails( ChangeOrderReq changeOrderReq );
        
    ChangeOrderResp saveCoPlantDetails( ChangeOrderReq changeOrderReq );
        
    ChangeOrderResp updateCoApproverDetails( ChangeOrderReq changeOrderReq );
    
    ProjSOETrackLogResp saveProjSOETrackDetails(ProjSOETrackSaveReq projSOETrackSaveReq);
    
    ProjSOETrackLogResp getProjSOETrackDetails(ProjSOETrackGetReq projSOETrackGetReq);
    
    ProjSORTrackDetailsResp getProjSORTrackDetails(ProjSORTrackGetReq projSORTrackGetReq);

	ChangeOrderResp saveCoScopeOfWork(ChangeOrderReq changeOrderReq);

	ChangeOrderResp saveCoMaterialDetails(ChangeOrderReq changeOrderReq);

	ChangeOrderResp saveCoCostDetails(ChangeOrderReq changeOrderReq);

	ChangeOrderResp getChangeOrderDetailsByCoId(ChangeOrderReq changeOrderReq);
}
