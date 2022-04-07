package com.rjtech.timemanagement.constants;

import java.io.Serializable;

public class WorkDairyURLConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -923474679747855169L;

    public final static String WORK_DAIRY_PARH_URL = "/app/workdairy/";

    public final static String GET_WORK_DAIRY_BY_ID = "getWorkDairyById";
    public final static String GET_WORK_DAIRY_FOR_CLIENT_APPROVAL = "getWorkDairyForClientApproval";
    public final static String GET_WORK_DAIRY_FOR_INTERNAL_APPROVAL = "getWorkDairyForInternalApproval";

    public final static String CREATE_WORK_DAIRY = "createWorkDairy";

    public final static String GET_PROJ_SETTINGS_FOR_WORK_DAIRY = "getProjSettingsForWorkDairy";

    public final static String GET_WORK_DAIRY = "getWorkDairy";
    public final static String GET_EMP_REG_DETAILS = "getEmpRegDetails";
    public final static String SAVE_EMP_REG_DETAILS = "saveEmpRegDetails";

    public final static String GET_PLANT_REG_DETAILS = "getPlantRegDetails";
    public final static String SAVE_PLANT_REG_DETAILS = "savePlantRegDetails";

    public final static String GET_MATERIAL_REG_DETAILS = "getMaterialRegDetails";
    public final static String SAVE_MATERIAL_REG_DETAILS = "saveMaterialRegDetails";

    public final static String GET_SOW_DETAILS = "getSowDetails";

    public final static String GET_WORK_DAIRY_DETAILS = "getWorkDairyDetails";
    public final static String SAVE_WORK_DAIRY_DETAILS = "saveWorkDairyDetails";

    public final static String SUBMIT_WORK_DAIRY = "submitWorkDairy";

    public final static String APPROVE_WORK_DAIRY = "approveWorkDairy";

    public final static String GET_WORK_DAIRY_COST_CODES = "getWorkDairyCostCodes";

    public final static String SAVE_WORK_DAIRY_COST_CODES = "saveWorkDairyCostCodes";

    public final static String SAVE_WORK_DAIRY_CREW_COST_CODES = "saveWorkDairyCrewCostCodes";
    public final static String GET_WORK_DAIRY_CREW_COST_CODES = "getWorkDairyCrewCostCodes";

    public final static String GET_WORK_DAIRY_EMP_DETAILS = "getWorkDairyEmpDetails";
    public final static String SAVE_WORK_DAIRY_EMP_DETAILS = "saveWorkDairyEmpDetails";

    public final static String GET_WORK_DAIRY_PLANT_DETAILS = "getWorkDairyPlantDetails";
    public final static String SAVE_WORK_DAIRY_PLANT_DETAILS = "saveWorkDairyPlantDetails";

    public final static String GET_WORK_DAIRY_MATERIAL_DETAILS = "getWorkDairyMaterialDetails";
    public final static String SAVE_WORK_DAIRY_MATERIAL_DETAILS = "saveWorkDairyMaterialDetails";

    public final static String GET_WORK_DAIRY_PROGRESS_DETAILS = "getWorkDairyProgressDetails";
    public final static String SAVE_WORK_DAIRY_PROGRESS_DETAILS = "saveWorkDairyProgressDetails";
    public final static String SAVE_MORE_SOW_COST_CODES = "saveMoreSowCostCodes";

    public final static String COPY_WORK_DAIRY = "copyWorkDairy";

    public final static String GET_PROJ_SETTINGS_WORK_DAIRY_DETAILS = "getProjSettingsWorkDairyDetails";

    public final static String GET_MATERIAL_LEDGER = "getMaterialLedger";
    
    public final static String GET_IN_TRANSIT_MATERIALS = "getInTransitMaterials";
    
    public final static String GET_STOCK_PILED_MATERIALS = "getStockPiledMaterials";

    public static final String GET_PLANT_UTILAIZATION_RECORDS = "getPlantUtilaizationRecords";

    // Plant Reports
    public static final String GET_PLANT_COSTCODE_WISE_REPORT = "getPlantCostCodeWiseReport";
    public static final String GET_PLANT_DATE_WISE_REPORT = "getPlantDateWiseReport";
    public static final String GET_CURRENT_ACTIVE_PLANTS = "getCurrentActivePlants";
    public static final String GET_PLANTS_STANDARD_ACTUAL = "getPlantsStandardActual";
    public static final String GET_PLANTS_PERIODICAL_REPORT = "getPlantsPeriodicalReport";
    public static final String GET_PLANTS_IDLE_PERIODICAL_REPORT = "getPlantsIdlePeriodicalReport";

    // Progress Reports
    public static final String GET_PROGRESS_DATEWISE_RECORDS = "getProgressDateWiseRecords";
    public static final String GET_PROGRESS_PERIODICAL_RECORDS = "getProgressPeriodicalRecords";
    public static final String GET_PROGRESS_ACTUAL_RECORDS = "getProgressActualRecords";
    public static final String GET_SOR_PROGRESS_CLAIM_RECORDS = "getSorProgressClaimRecords";

    // Work Dairy Reports
    public static final String GET_WORK_DAIRY_APPROVAL_REPORT = "getWorkDairyApprovalReport";
    public final static String GET_CREATED_WORK_DIARIES = "getCreatedWorkDiaries";
    public final static String GET_SUBMITTED_WORK_DIARIES = "getSubmittedWorkDiaries";
    
    public final static String DELETE_WORK_DAIRY = "deleteWorkDairy";

    public final static String GET_PROJ_MATERIAL_CONSUMPTION = "getMaterialProjConsumption";
}
