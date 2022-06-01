package com.rjtech.projectlib.constans;

import java.io.Serializable;

public class ProjLibURLConstants implements Serializable {

    private static final long serialVersionUID = -923474679747855169L;

    public final static String PARH_URL = "/app/projectlib/";

    public final static String GET_PROJ_SOW_ITEMS_MAP = "getProjSowItemsMap";
    public final static String GET_MULTI_PROJ_COST_MAP = "getMultiProjCostMap";
    public final static String GET_PROJ_COST_ITEMSONLY = "getCostItemsOnly";

    public final static String PROJECT_SOW_POPUP_ONLOAD = "projSowifOnLoad";
    public final static String PROJECT_MATERAIAL_POPUP_ONLOAD = "projMaterialClassifyOnLoad";
    public final static String PROJECT_STOCK_POPUP_ONLOAD = "projStoreStockifOnLoad";
    public final static String PROJECT_PLANT_POPUP_ONLOAD = "projPlantClassifOnLoad";
    public final static String PROJECT_WORKSHIFT_POPUP_ONLOAD = "projWorkShiftOnLoad";
    public final static String PROJECT_CREWLIST_POPUP_ONLOAD = "projCrewListifOnLoad";
    public final static String PROJECT_SOE_POPUP_ONLOAD = "projSoeifOnLoad";
    public final static String PROJECT_SOR_POPUP_ONLOAD = "projSorifOnLoad";
    public final static String PROJECT_COSTITEM_POPUP_ONLOAD = "projCostItemifOnLoad";
    public final static String PROJECT_EPS_POPUP_ONLOAD = "projEPSOnLoad";

    public final static String GET_EPS_PROJECTS_BY_ID = "getEPSProjectsById";
    public final static String GET_EPS_ONLY = "getEPSOnly";

    public final static String GET_PROJ_LEAVE_CODES = "getProjLeaveCodes";

    public final static String GET_PROJECTS = "getProjects";
    public final static String GET_PROJECT_EPS = "getProjectEps";
    public final static String GET_PROJECTS_BY_ID = "getProjectsById";

    public final static String SAVE_PROJECT = "saveProject";
    public final static String SAVE_EPS_STACTURE = "saveEpsStacture";
    public final static String DELETE_PROJECT = "deleteProject";
    public final static String DEACTIVE_PROJECT_EPS = "deactivateEPSProject";
    public final static String DEACTIVATE_EPS = "deactivateEPS";

    public final static String GET_SOE_ITEMS = "getSOEItems";
    public final static String SAVE_SOE_ITEMS = "saveSOEItems";
    public final static String SAVE_SOE_TRACK_RECORDS = "saveSOETrackDetails";
    public final static String GET_SOE_TRACK_RECORDS = "getProjSOETrackDetails";
    public final static String GET_SOR_TRACK_RECORDS = "getProjSORTrackDetails";
    public final static String DELETE_SOE_ITEMS = "deleteSOEItems";

    public final static String GET_SOR_ITEMS = "getSORItems";
    public final static String SAVE_SOR_ITEMS = "saveSORItems";
    public final static String DELETE_SOR_ITEMS = "deleteSORItems";

    public final static String GET_SOW_ITEMS = "getSOWItems";

    public final static String GET_SOW_ITEMS_BY_COST_CODES = "getSOWItemsByCostCodes";

    public final static String GET_SOW_ITEMS_EXCEPT_COST_CODES = "getSOWItemsExceptCostCodes";

    public final static String GET_MULTI_PROJ_SOW_ITEMS = "getMultiProjSOWDetails";
    public final static String SAVE_SOW_ITEMS = "saveSOWItems";
    public final static String DELETE_SOW_ITEMS = "deleteSOWItems";

    public final static String GET_MULTI_PROJ_COST_ITEMS = "getMultiProjCostDetails";
    public final static String GET_PROJ_COST_ITEMS = "getProjCostItems";
    public final static String SAVE_PROJ_COST_ITEMS = "saveProjCostItems";
    public final static String DELETE_PROJ_COST_ITEMS = "deleteProjCostItems";

    public final static String GET_SOE_ITEMS_BY_ID = "getProjSOEItemsById";
    public final static String GET_SOR_ITEMS_BY_ID = "getProjSORItemsById";
    public final static String GET_SOW_ITEMS_BY_ID = "getProjSOWItemsById";
    public final static String GET_PROJ_COST_ITEMS_BY_ID = "getProjCostItemsById";

    public final static String GET_LEAVE_TYPES_BY_COUNTRY = "getLeaveTypesByCountry";
    public final static String DELETE_PROJ_LEAVE_TYPES = "deleteProjLeaveTypes";
    public final static String SAVE_PROJ_LEAVE_TYPES = "saveProjLeaveTypes";
    public final static String GET_EPS_USR_PROJECTS = "getEPSUserProjects";

    public final static String GET_USR_PROJECTS = "getUserProjects";
    public final static String PROJECT_CLASSIFY_POPUP_ONLOAD = "projEmpClassifyOnLoad";
    public final static String GET_PROJ_EMP_CLASSES = "getProjEmpClasses";
    public final static String SAVE_PROJ_EMP_CLASSES = "saveProjEmpClasses";
    public final static String DELETE_PROJ_EMP_CLASSES = "deleteProjEmpClasses";

    public final static String GET_PROJ_MATERIAL_CLASSES = "getProjMaterialClasses";
    public final static String SAVE_PROJ_MATERIAL_CLASSES = "saveProjMaterialClasses";
    public final static String DEACTIVATE_PROJ_MATERIAL_CLASSES = "deactivateProjMaterials";
    public final static String GET_PROJ_MATERIAL_RESTRICTED_CLASSES = "getProjRestrictedMaterialClasses";

    public final static String GET_PROJ_PLANT_CLASSES = "getProjPlantClasses";
    public final static String SAVE_PROJ_PLANT_CLASSES = "saveProjPlantClasses";
    public final static String DELETE_PROJ_PLANT_CLASSES = "deleteProjPlantClasses";

    public final static String GET_PROJ_WORK_SHIFT = "getProjWorkShifts";
    public final static String GET_MULTIPLE_PROJ_WORK_SHIFT = "getMultipleProjWorkShifts";
    public final static String SAVE_PROJ_WORK_SHIFT = "saveProjWorkShifts";
    public final static String DELETE_PROJ_WORK_SHIFT = "deleteprojWorkShifts";

    public final static String GET_MULTIPLE_PROJS_STORE_LIST = "getMultipleProjsStoreList";
    public final static String GET_PROJ_STORE_STOCK = "getProjStoreStocks";
    public final static String SAVE_PROJ_STORE_STOCK = "saveProjStoreStocks";
    public final static String DELETE_PROJ_STORE_STOCK = "deleteProjStoreStocks";

    public final static String GET_MULTIPLE_PROJS_CREW_LIST = "getMultipleProjsCrewList";
    public final static String GET_PROJ_CREW_LIST = "getProjCrewLists";
    public final static String SAVE_PROJ_CREW_LIST = "saveProjCrewLists";
    public final static String DELETE_PROJ_CREW_LIST = "deleteProjCrewLists";

    public final static String PROJECT_COST_USER_PROJECT_CLASSIFICATION = "projectCostUserProjClassify";
    public final static String PROJECT_USER_PROJECT_CLASSIFICATION = "projectUserProjClassify";

    // ================================ProjLibMapUrlConstants==================================================

    public final static String GET_PROJEMPCLASS_MAP = "getProjEmpClassMap";
    public final static String GET_PROJSTOCKPILE_MAP = "getProjStockPileMap";
    public final static String GET_PROJWORKSHIFT_MAP = "getProjWorkShiftMap";
    public final static String GET_PROJCREWLIST_MAP = "getProjCrewListMap";
    public final static String GET_PROJSOE_MAP = "getProjSOEMap";
    public final static String GET_PROJSOR_MAP = "getProjSORMap";
    public final static String GET_PROJCOSTCODE_MAP = "getProjCostCodeMap";
    public final static String GET_EPSPROJ_MAP = "getEpsProjMap";
    public final static String GET_EPSLIST_MAP = "getEpsListMap";
    public final static String GET_PROJPLANTCLASS_MAP = "getProjPlantClassMap";

    public final static String GET_ALL_PROJECTS = "getAllProjects";

    public final static String GET_USR_PROJ_EMP_CLASSIFY = "getUserProjEmpClassMap";

    public final static String GET_PROJ_COST_ITEMS_MAP = "getProjCostItemMap";

    public final static String GET_MULTIPLE_PROJS_CREW_MAP = "getMultipleProjsCrewMap";

    public final static String GET_PROJS_CREW_MAP = "getProjsCrewMap";

    public final static String GET_EPS_USER_PROJECTS_FOR_CREWS = "getEPSUserProjectsForCrews";

    public static final String GET_MULTI_PROJ_CODE_MAP = "getMultiProjCodeMap";
    public static final String GET_MULTI_EPS_CODE_MAP = "getMultiEpsCodeMap";
    public static final String GET_MULTI_PROJ_SOW_ITEMS_MAP = "getMultiProjSOWItemMap";
    public static final String GET_MULTI_PROJ_COST_CODE_MAP = "getMultiProjCostCodeMap";
    public static final String GET_MULTI_PROJ_SOR_ITEMS_MAP = "getMultiProjSORItemMap";

    public final static String GET_SOW_TOTAL_ACTUAL_QUANTITIES = "getSOWTotalActualQuantities";

    public static final String SAVE_DEFAULT_PROJ_LEAVE_TYPES = "saveDefaultProjLeaveTypes";
    public static final String GET_GLOBAL_LEAVE_TYPES = "getGlobalLeaveTypes";
    public static final String GET_EFFECTIVE_DATES_FOR_COUNTRY = "getEffectiveDatesForCountry";

    public final static String GET_PMCM_ITEMS = "getPMCMItems";
    public final static String SAVE_PMCM_ITEMS = "savePMCMItems";
    public final static String DELETE_PMCM_ITEMS = "deletePMCMItems";
    public final static String GET_PMCM_ITEMS_BY_ID = "getProjPMCMItemsById";
    public final static String PROJECT_PMCM_POPUP_ONLOAD = "projPMCMifOnLoad";
    public final static String GET_REPORT_PMCM_ITEMS = "getReportPMCMItems";
    public final static String GET_REPORT_PMCP_ITEMS = "getReportPMCPItems";

    public final static String GET_PMCP_ITEMS = "getPMCPItems";
    
    public final static String SOE_APPROVAL = "approveProjSoe";
    public final static String SOE_RETURNED_WITH_COMMENTS = "returnWithComments";
    public final static String SOE_VIEW_ACTIVITY_RECORDS = "viewActivityRecords";
    public final static String SOE_VIEW_TRACK_RECORDS = "viewTrackSoeRecords";
    
    public final static String SOR_APPROVAL = "approveProjSor";
    public final static String SOR_RETURNED_WITH_COMMENTS = "sorReturnWithComments";
    public final static String SOR_VIEW_ACTIVITY_RECORDS = "viewSORActivityRecords";
    
    public final static String GET_SOW_ITEMS_BY_PROJID = "getSOWItemsByProjId";
    public final static String SAVE_CO_DETAILS = "saveChangeOrderDetails"; // this constant is for saving the change order details
    public final static String GET_CO_DETAILS = "getChangeOrderDetails";
    public final static String SAVE_CO_SOW = "saveCoScopeOfWork";
    public final static String SAVE_CO_MATERIAL_DETAILS = "saveCoMaterialDetails";
    public final static String SAVE_CO_COST_DETAILS = "saveCoCostDetails";
    
    public final static String SAVE_CO_MANPOWER_DETAILS = "saveCoManpowerDetails";
    public final static String GET_CO_DETAILS_BY_CO_ID = "getChangeOrderDetailsByCoId";
    public final static String SAVE_CO_PLANT_DETAILS = "saveCoPlantDetails";
    public final static String GET_CO_PLANT_DETAILS = "getCoPlantDetails";
    public final static String UPDATE_CO_APPROVER_DETAILS = "updateCoApproverDetails";
}
