package com.rjtech.common.constants;

import java.io.Serializable;

public class CommonConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -923474679747855169L;

    public final static String PARH_URL = "/app/common/";

    public final static Integer EPS_PROJ_VALUE = 0;
    public final static Integer PROJ_VALUE = 1;

    public final static String PROJ_CAL_TYPE = "PCAL";

    public final static String PROJ_SOW_SEPARATOR = "-";
    public final static int ITEM_VALUE = 1;
    public final static Integer GROUP_VALUE = 0;
    public final static Boolean INTERNAL_APPROVED = true;
    public final static Boolean EXTERNAL_APPROVED = true;

    public final static Integer INTERNAL_IAPPROVED = 1;
    public final static Integer EXTERNAL_IAPPROVED = 1;
    public static final String EMP_CATG_NAME = "empCatgName";

    public final static String PROJECT_NAME = "projectName";

    public static final String COMPANY_CATG_NAME = "cmpCatgName";
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String DISPLAY_NAME = "displayName";
    public final static String DESIGNATION = "designation";
    public final static String GENDER = "gender";
    public final static String PROCURE_CATG = "procureCatg";
    public final static String CLASS_TYPE = "classType";
    public final static String CLASS_NAME = "className";
    public final static String PLANT_REG_NO = "plntRegNo";
    public final static String PLANT_MANFACTURE = "plntManfature";
    public final static String PLANT_MODEL = "plntModel";
    public final static String EXPECTED_DATE = "expectedDate";
    public static final String EXPECTED_TRANS_DATE = "expectedTransDate";

    public static final String WORK_DAIRY_PO = "WPO";
    public static final String SPM = "SPM";
    public static final String SM = "SM";
    public static final String DELIVERY_PLACE = "deliveryPlace";
    public static final String DELIVERY_CATAGORY = "deliveryCatagory";

    public final static String BUDGET_AMOUNT = "budgetAmount";
    public final static String ESTIMATE_AMOUNT = "estimateAmount";
    public final static String ACTUAL_QUANTITY = "actualQty";

    public final static String ACTUAL_HRS = "actualHrs";
    public final static String ACTUAL_DATE = "actualDate";

    public static final String PUR_ID = "purId";
    public static final String PUR_CODE = "purCode";
    public static final String PUR_TYPE = "purType";
    public static final String PUR_START_DATE = "purStartDate";
    public static final String PUR_FINISH_DATE = "purFinishDate";
    public static final String PUR_CLASS_ID = "purClassId";
    public static final String PUR_AMOUNT = "purAmount";
    public static final String PUR_PAYMENT_IN_DAYS = "purPaymentDays";
    public final static String CMP_ID = "cmpId";
    public final static String SCH_ITEM_CMP_ID = "schItemCmpId";

    public final static String PROJ_DOCKET_TYPE = "Project Docket";
    public final static String SUPPLY_DELIVRY_DATE = "supplyDeliveryDate";

    public final static String DELIVERY_DOCKET_ID = "deliveryDocketId";
    public final static String DELIVERY_DOCKET_TYPE = "Delivery Docket";
    public final static String DELIVERY_DOCKET_NO = "deliveryDockNo";
    public final static String DELIVERY_DOCKET_DATE = "deliveryDockDate";
    public final static String LOCATION = "location";
    public final static String TRANSIT_QTY = "transsitQty";
    public final static String DEFECT_COMMENTS = "defectComments";
    public final static String COMMENTS = "comments";
    public final static String RECEIVED_BY = "receivedBy";

    public static final String CMP_CODE = "cmpCode";

    public static final String CMP_NAME = "cmpName";
    public final static String CREW_ID = "crewId";
    public final static String PROJ_ID = "projId";

    public final static String UNIT_OF_RATE = "unitOfRate";
    public final static String QTY = "qty";

    public final static String REM_QTY = "remQty";

    public final static String ATTENDANCE_ID = "attendanceId";
    public final static String TIME_SHEET_ID = "timeSheetId";

    public final static String DAY1_BOOKED_HRS = "day1Hrs";
    public final static String DAY2_BOOKED_HRS = "day2Hrs";
    public final static String DAY3_BOOKED_HRS = "day3Hrs";
    public final static String DAY4_BOOKED_HRS = "day4Hrs";
    public final static String DAY5_BOOKED_HRS = "day5Hrs";
    public final static String DAY6_BOOKED_HRS = "day6Hrs";
    public final static String DAY7_BOOKED_HRS = "day7Hrs";

    public final static String BOOKED_HRS = "bookedHrs";
    public final static String TOTAL_HRS = "toalHrs";
    public final static String TIME_EMP_ID = "timeEmpId";

    public final static String EMP = "EMP";
    public final static String PLANT = "PLANT";
    public final static String SUBMIT = "SUBMIT";
    public final static String APPROVE = "APPROVE";

    public final static String WEEK_START_DAY = "weekStartDay";
    public final static String WEEK_END_DAY = "weekenddDay";
    public final static String MAX_TOTAL_HRS = "maxHrs";

    public final static String MMM_YYYY_FORMAT = "mmm-yyyy";
    public final static String DD_MMM_YYYY_FORMAT = "dd-mmm-yyyy";
    public final static String DD_MM_YYYY_FORMAT = "dd-mm-yyyy";

    public final static String GET_SYSTEM_DATE = "getSystemDate";

    public final static String COUNTRY = "country";
    public final static String PROVINCE = "province";
    public final static String CURRENCY = "currency";

    public final static String GET_COUNTRY_DETAILS = "getCountryDetails";
    public final static String GET_COUNTRY_SEARCH_DETAILS = "getSearchCountries";
    public final static String GET_PROVISIONS = "getProvisions";
    public final static String GET_COUNTRY_DETAILS_BY_ID = "getCountryDetailsById";
    public final static String SAVE_COUNTRY_DETAILS = "saveCountries";
    public final static String GET_COUNTRY_PROVISIONS = "getCountryProvisions";
    public final static String SAVE_COUNTRY_PROVISIONS = "saveCountryProvisions";
    public final static String SAVE_COUNTRY_DETAILS_BY_ID = "saveCountryDetailsById";

    public final static String GET_TIMEZONES = "getTimeZones";

    public final static String GET_RESOURCECURVES = "getResourceCurves";
    public final static String SAVE_RESOURCECURVES = "saveResourceCurves";
    public final static String DEACTIVATE_RESOURCECURVES = "deactivateResourceCurves";

    public final static String PRECONTRACT_ID = "preContractId";

    public final static String GET_PAYCYCLES = "getPaycycles";
    public final static String SAVE_PAYCYCLES = "savePaycycles";
    public final static String GET_TIMESHEET_WEEK = "getTimeSheetWeek";

    /*
     * The below codes are defined in Register
     */
    public final static String PROJECT_PARENT_NAME_KEY = "ParentName";

    public final static String PROJECT_PARENT_EPSCODE_KEY = "ParentEPSCode";

    public final static String EMP_FIRST_NAME_KEY = "ParentName";

    public final static String CLASSIFICATION_MEASURE_UNIT = "UnitOfMeasure";

    public final static String PROCUREMENT_MASTER_TYPE = "ProcureType";

    public final static String SCH_CMP_ID = "scheduleCmpId";

    public final static String COST_ID = "costCode";
    public final static String STOCK_ID = "stockId";
    public final static String PROJ_STOCK_ID = "projStockId";

    public final static String COST_CODE = "costCode";
    public final static String COST_NAME = "costName";

    public final static String SOE_CODE = "soeCode";
    public final static String SOE_NAME = "soeName";

    public final static String SOR_CODE = "sorCode";
    public final static String SOR_NAME = "sorName";

    public static final String SOW_CODE = "sowCode";
    public static final String SOW_NAME = "sowName";

    public final static String GET_EMP_USERS_ONLY = "getEmpUsersOnly";
    public final static String GET_PROJ_USERS_ONLY = "getProjUsersOnly";
    public final static String GET_ALL_PROJ_USERS = "getAllProjUsers";
    public final static String USR_EMAIL = "userEmail";

    public static final String MOB_DATE = "MOB_DATE";
    public static final String DEMOB_DATE = "DEMOB_DATE";
    public static final String USED_TIME = "USED_TIME";
    public static final String IDLE_TIME = "IDLE_TIME";
    public static final String USED_TOTAL_TIME = "USED_TOTAL_TIME";
    public static final String IDLE_TOTAL_TIME = "IDLE_TOTAL_TIME";

    public static final String DEPLOYMENT_ID = "DEPLOYMENT_ID";
    public static final String MOB_ODO_METER = "MOB_ODO_METER";
    public static final String DEMOB_ODO_METER = "DEMOB_ODO_METER";

    public static final String PLANT_ID = "PLANT_ID";

    public static final String PLANT_YEAR = "PLANT_YEAR";
    public static final String WORKING_DAYS = "WORKING_DAYS";
    public static final String NONWORKING_DAYS = "NONWORKING_DAYS";
    public static final String IDLE_DAYS = "IDLE_DAYS";

    public final static String APPR_STATUS_PENDING = "Pending";
    public final static String APPR_STATUS_APPROVED = "APPROVED";
    public final static String APPR_STATUS_REJECTED = "REJECTED";

    public final static String LOCATIONID_KEY = "locationId";
    public final static String STOCK_SM_ID = "SM_ID";
    public final static String STOCK_SM_CODE = "SM_CODE";
    public final static String STOCK_SM_CODE_CATEGORY = "SM_CODE_CATEGORY";
    public final static String PROJ_STOCK_SPM_ID = "SPM_ID";
    public final static String PROJ_STOCK_SPM_CODE = "SPM_CODE";
    public final static String PROJ_STOCK_SPM_CATEGORY = "SPM_CODE_CATEGORY";
    public final static String STOCK_SOURCE_TYPE = "SOURCE_TYPE";
    public final static String EMP_NAME_COMMA_SEPARATOR = ",";
    public final static String EMP_CENTRAL_CLASSIFY_KEY = "classifyId";
    public final static String EMP_CENTRAL_PROJ_CLASSIFY_KEY = "projClassifyId";

    public final static String PROJ_MATERIAL_DOCKET_TYPE_INTERNAL = "INTERNAL";
    public final static String PROJ_MATERIAL_DOCKET_TYPE_EXTERNAL = "EXTERNAL";

    public final static String SET_IS_LATEST_Y = "Y";
    public final static String SET_IS_LATEST_N = "N";
    public final static Integer SET_LATEST_1 = 1;

    public final static String PROCURE_CODE = "procureCode";
    public final static String PROCURE_NAME = "procureName";

    public final static String BIDDING_OPEN = "Open";
    public final static String BIDDING_CLOSED = "Closed";

    public final static String FAX = "fax";
    public final static String PHONE = "phone";
    public final static String MOBILE = "mobile";

    public final static String REQ_USERID = "reqUserId";
    public final static String APPR_USERID = "apprUserId";

    public final static String CURRENCY_ID = "currencyId";

    public final static String GET_COUNTRY_CODE_MAP = "countryCodeMap";

    public final static String GET_COUNTRY_PRIVISION_CODE_MAP = "provisionCodeMap";

    //Project Invoice
    public static final String PENDING_APPROVAL = "Pending Approval";
    public static final String APPROVED = "Approved";
    public static final String APPROVAL_ON_HOLD = "Approval On Hold";
    public static final String PAID = "Paid";
    public static final String ALL = "All";
    public static final String RELEASED = "Paid";
    public static final String YET_TO_RELEASE = "Yet To Release";

    public static final String ACTUAL_START_DATE = "actualStartDate";
    public static final String ACTUAL_FINISH_DATE = "actualFinishDate";
    public static final String ACTUAL_BUDGET = "actualBudget";
    public static final String REVISED_BUDGET = "revisedBudget";
    public static final String ESTIMATE_TO_COMPLETE = "estimateToComplate";
    public static final String ESTIMATION_TO_COMPLETTION = "estimatationOfComplate";

    public static final String WDM_DATE = "wdmDate";
    public static final String ACTUAL_QTY = "actualQty";

    public static final String EMP_CONTRACT_NAME = "empContractName";
    public static final String EMP_WORK_UNION_NAME = "empWorkUnionName";

    // documents
    public final static String READ_VALUE = "readvalue";
    public final static String WRITE_VALUE = "writevalue";

    // URLs to get country related information from api.geonames.org
    public final static String COUNTRY_INFO_JSON = "countryInfoJSON";
    public final static String GET_COUNTRY_PROVISIONS_JSON = "getCountryProvisionsJSON";
    public final static String GET_TIMEZONE_JSON = "getTimezoneJSON";

    public final static String EMP_CODE = "empCode";
    public final static String ECM_NAME = "ecmName";
    public final static String PEC_DESC = "pecDesc";
    
    public final static String GET_TANGIBLES_OF_PROJECTS = "getTangiblesOfProjects";
    // procurement contractTypes
    public final static String PRE_CONTRACT_MANPOWER = "Manpower";
    public final static String PRE_CONTRACT_MATERIALS = "Materials";
    public final static String PRE_CONTRACT_PLANTS = "Plants";
    public final static String PRE_CONTRACT_SERVICES = "Services";
    public final static String PRE_CONTRACT_PROJECT_SUB_CONTRACT = "Project Sub Contract";
    
    //cutoff hours current phase
    
    public final static String STAGE_ONE_CUTOFF = "Stage 1 Internal Approval";
    public final static String STAGE_TWO_CUTOFF = "Stage 2 Internal Approval";

}
