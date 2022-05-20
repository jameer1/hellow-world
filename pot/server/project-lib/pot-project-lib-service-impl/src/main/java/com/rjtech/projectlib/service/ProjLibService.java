package com.rjtech.projectlib.service;

import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.projectlib.req.*;
import com.rjtech.projectlib.resp.*;

public interface ProjLibService {

    public ProjSOEItemResp getProjSOEDetails(ProjSOEItemGetReq projSOEItemGetReq);

    public ProjSOEItemResp getProjSOEDetailsById(ProjSOEItemGetReq projSOEItemGetReq);

    public void saveProjSOEDetails(ProjSOEItemSaveReq projSOEItemSaveReq);

    public void deactivateProjSOEDetails(ProjSOEItemDelReq projSOEItemDelReq);

    public ProjSORItemResp getProjSORDetails(ProjSORItemGetReq projSORItemGetReq);

    public ProjSORItemResp getProjSORDetailsById(ProjSORItemGetReq projSORItemGetReq);

    public void saveProjSORDetails(ProjSORItemSaveReq projSORItemSaveReq);

    public void deactivateProjSORDetails(ProjSORItemDelReq projSORItemDelReq);

    public ProjSOWItemResp getProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq);

    public ProjSOWItemResp getProjSOWDetailsByCostCode(ProjSOWItemGetReq projSOWItemGetReq);

    public ProjSOWItemResp getProjSOWDetailsExceptCostCode(ProjSOWItemGetReq projSOWItemGetReq);

    public ProjSOWItemResp getMultiProjSOWDetails(ProjSOWItemGetReq projSOWItemGetReq);

    public ProjSOWItemResp getProjSOWDetailsById(ProjSOWItemGetReq projSOWItemGetReq);

    public void saveProjSOWDetails(ProjSOWItemSaveReq projSOWItemSaveReq);

    public void deactivateProjSOWDetails(ProjSOWItemDelReq projSOWItemDelReq);

    public ProjCostItemResp getProjCostDetails(ProjCostItemGetReq projCostItemGetReq);

    public ProjCostItemResp getProjCostDetailsById(ProjCostItemGetReq projCostItemGetReq);

    public void saveProjCostDetails(ProjCostItemSaveReq projCostItemSaveReq);

    public void deactivateProjCostDetails(ProjCostItemDelReq projCostItemDelReq);

    public void saveProjLeaveTypes(ProjLeaveTypeSaveReq projLeaveTypeSaveReq);

    public void deleteProjLeaveTypes(ProjLeaveTypeDelReq projLeaveTypeDelReq);

    public ProjEmpClassResp getProjEmpClasses(ProjEmpClassGetReq projEmpClassGetReq);

    public void saveProjEmpClasses(ProjEmpClassSaveReq empClassSaveReq);

    public void deleteEmpClasses(ProjEmpClassDelReq projEmpClassDelReq);

    public ProjPlantClassResp getProjPlantClasses(ProjPlantClassGetReq projEmpClassGetReq);

    public void saveProjPlantClasses(ProjPlantClassSaveReq projPlantClassSaveReq);

    public void deleteProjPlantClasses(ProjPlantClassDelReq projEmpClassDelReq);

    public ProjWorkShiftResp getProjWorkShifts(ProjWorkShiftGetReq projWorkShiftGetReq);

    public void saveProjWorkShifts(ProjWorkShiftSaveReq projWorkShiftSaveReq);

    public void deleteProjWorkShifts(ProjWorkShiftDelReq projWorkShiftDelReq);

    public ProjStoreStockResp getProjStoreStocks(ProjStoreStockGetReq projStoreStockGetReq);

    public void saveProjStoreStocks(ProjStoreStockSaveReq projStoreStockSaveReq);

    public void deleteProjStoreStocks(ProjStoreStockDelReq projStoreStockDelReq);

    public ProjCrewResp getProjCrewLists(ProjCrewGetReq projCrewGetReq);

    public void saveProjCrewLists(ProjCrewSaveReq projCrewSaveReq);

    public void deleteProjCrewLists(ProjCrewDelReq projCrewDelReq);

    public ProjMaterialClassResp getProjMaterialClasses(ProMaterialClassGetReq proMaterialClassGetReq);

    ProjRestrictedMaterialClassResp getProjRestrictedMaterialClasses(ProMaterialClassGetReq proMaterialClassGetReq);

    public void saveProjMaterialClasses(ProjMaterialClassSaveReq projMaterialClassSaveReq);

    public void deactivateMaterialClasses(ProjMaterialDelReq projMaterialDelReq);

    public ProjCostItemResp getProjCostItemsOnly(ProjCostItemGetReq projCostItemGetReq);

    Map<Long, LabelKeyTO> getProjectCostItems(ProjectLibOnLoadReq req);

    Map<Long, LabelKeyTO> getProjPlantClasses(ProjectLibOnLoadReq req);

    public Map<Long, LabelKeyTO> getProjEmpClassMap(ProjectLibOnLoadReq projectLibOnLoadReq);

    public ProjSowItemsMapResp getProjSowItemsMap(ProjSOWItemGetReq projSOWItemGetReq);

    public ProjCrewResp getMultipleProjsCrewList(ProjCrewGetReq projCrewGetReq);

    public ProjCostItemResp getMultiProjCostDetails(ProjCostItemGetReq projCostItemGetReq);

    public Map<Long, LabelKeyTO> getUserProjEmpClassMap(List<Long> projId);

    public ProjStoreStockResp getMultipleProjsStoreList(ProjStoreStockGetReq projStoreStockGetReq);

    public Map<Long, ProjCostItemTO> getProjCostItemMap(ProjCostItemGetReq ProjCostItemGetReq);

    public Map<Long, ProjCrewTO> getMultipleProjsCrewMap(ProjCrewGetReq projCrewGetReq);

    public SOWTotalActualQuantitiesResp getSOWTotalActualQuantities(ProjSOWItemGetReq projSOWItemGetReq);

    ProjWorkShiftResp getMultipleProjWorkShifts(ProjWorkShiftGetReq workShiftGetReq);

    void saveDefaultProjLeaveTypes(Long clientId);

    ProjLeaveTypeResp getLeaveTypesByCountry(ProjLeaveTypeGetReq projLeaveTypeGetReq);

    ProjLeaveTypeResp getGlobalLeaveTypes();

    List<String> getEffectiveDatesForCountry(String countryCode);

    public ProjPMCMItemResp getProjPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq);

    public ProjPMCMItemResp getProjReportPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq);

    public ProjPMCPItemResp getProjReportPMCPDetails(ProjPMCPItemGetReq projPMCMItemGetReq);

    public ProjPMCMItemResp getProjPMCMDetailsById(ProjPMCMItemGetReq projPMCMItemGetReq);

    public void saveProjPMCMDetails(ProjPMCMItemSaveReq projPMCMItemSaveReq);

    public ProjPMCPCostStatementsResp getProjPMCPCostStatements(ProjPMCPItemGetReq projPMCPItemGetReq);
    
    public ProjSOEItemResp projSOEApproval( ProjSOEItemGetReq projSOEItemGetReq );
    
    public ProjSOEItemResp returnSOEWithComments( ProjSOEItemGetReq projSOEItemGetReq );
    
    public ProjSOEActivityLogResp viewActivityRecords( ProjSOEItemGetReq projSOEItemGetReq );

    public ProjSORItemResp projSORApproval( ProjSORItemGetReq projSORItemGetReq );
    
    public ProjSORItemResp returnSORWithComments( ProjSORItemGetReq projSORItemGetReq );
    
    public ProjSORActivityLogResp viewSORActivityLog( ProjSORItemGetReq projSORItemGetReq );
    
    public ProjSOWItemResp getAllProjSOWDetails( ProjSOWItemGetReq projSOWItemGetReq );
    
    public void saveProjSOETrackDetails(ProjSOETrackSaveReq projSOETrackSaveReq);
    
    public ProjSOETrackLogResp getProjSOETrackDetails(ProjSOETrackGetReq projSOETrackGetReq);
    
    public void saveProjSORTrackDetails(ProjSORItemSaveReq projSORItemSaveReq);
    
    public ProjSORTrackDetailsResp getProjSORTrackDetails(ProjSORTrackGetReq projSORTrackGetReq);
    
    public void deactivateProjPMCMDetails(ProjPMCMItemGetReq projPMCMItemGetReq);
}
