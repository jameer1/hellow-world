package com.rjtech.register.constans;

import java.io.Serializable;

public class RegisterURLConstants implements Serializable {

    private static final long serialVersionUID = -923474679747855169L;

    public final static String REGISTER_PARH_URL = "/app/register/";

    public final static String GET_MULTI_PROJ_EMP_LIST_MAP = "getMultiProjEmpListMap";
    public final static String GET_MULTI_PROJ_PLANT_LIST_MAP = "getMultiProjPlantListMap";
    public final static String GET_ALL_REG_EMP = "getAllRegEmp";

    public final static String GET_EMP_ENROLLMENTS = "getEmpEnrollments";
    public final static String SAVE_EMP_ENROLLMENTS = "saveEmpEnrollments";
    public final static String DOWNLOAD_ENROLLMENT_CONTRACT = "downloadEnrollmentContract";

    public final static String GET_EMP_REGISTERS = "getEmpregisters";
    public final static String SAVE_EMP_REGISTERS = "saveEmpregisters";
    public final static String EMP_REGISTERS_DEACTIVATE = "EmpRegistersDeactivate";
    public final static String EMP_REGISTERS_ONLOAD = "empRegisterOnLoad";

    public final static String GET_PROJ_EMP_SERVICE_HISTORY_REGISTERS = "getEmpServiceHistory";
    public final static String GET_PROJ_EMP_LATEST_SERVICE_HISTORY = "getEmpLatestServiceHistory";
    public final static String SAVE_PROJ_EMP_SERVICE_HISTORY_REGISTERS = "saveEmpServiceHistory";

    public final static String GET_PROJ_EMPS_FOR_LOGRECORDS = "getProjEmpForLogRecords";

    public final static String PROJ_EMP_REGISTERS_ONLOAD = "projEmpRegistersOnLoad";

    public final static String GET_NONATTENDENCE_EMP_REGISTERS = "getNonAttendenceEmpRegisters";
    public final static String GET_ATTENDENCE_EMP_REGISTERS = "getAttendenceEmpRegisters";

    public final static String GET_EMP_BANK_ACCOUNT_DETAILS = "getEmpBankAccountDetails";
    public final static String SAVE_EMP_BANK_ACCOUNT_DETAILS = "saveEmpBankAccountDetails";
    public final static String DEACTIVATE_EMP_BANK_ACCOUNTS = "deactivateBankAccountDetails";

    // GET_IMOVABLE_ASSETS_DETAILS
    public final static String GET_IMOVABLE_ASSETS_DETAILS = "imovableassetsdetails";

    public final static String SAVE_FIXED_ASSETS_REGISTERS = "saveFixedAssetsRegisters";
    public final static String FIXED_ASSETS_REGISTERS_DEACTIVATE = "fixedAssetRegistersDeactivate";
    public final static String FIXED_ASSETS_REGISTERS_ONLOAD = "fixedAssetsRegisterOnLoad";
    public final static String FIXED_ASSET_LIFE_STATUS_ONLOAD = "fixedAssetLifeStatusOnLoad";

    public final static String GET_ASSET_BY_ID = "getAssetById";
    public final static String GET_ASSET_ONLY = "getAssetOnly";
    public final static String GET_ASSET_SUB_BY_ID = "getAssetSubById";
    public final static String SAVE_SUB_ASSET = "saveSubAsset";

    //Repairs
    public final static String SAVE_REPAIRS_RECORDS = "saveRepairs";
    public final static String GET_REPAIRS_RECORDS = "getRepairsRecords";
    public final static String FIXED_ASSETS_REPAIRS_DELETE = "repairsRecordDeactive";
    public final static String FIXED_ASSETS_REPAIRS_DOWNLOAD_FILE = "repairsRecordDocDownloadFile";

    // purachese order
    public final static String SAVE_PURACHASE_ACQULISITION = "savePurachase";
    public final static String GET_FIXED_ASSETS_PURACHASE_ACQULISITION = "getPurachaseAcqulisition";
    public final static String FIXED_ASSETS_PURACHASE_ACQULISITION_DEACTIVATES = "purachaseAcqulistitonDeactive";
    public final static String FIXED_ASSETS_PURCHASE_ACQULISITION_DELETE = "purchaseRecordDeactive";
    public final static String FIXED_ASSETS_PURACHASE_ACQULISITION_DOWNLOAD_FILE = "purchaseRecordDocDownloadFile";

    // sub asset
    public final static String SAVE_SUBASSETS = "saveSubassets";
    public final static String GET_SUB_ASSETS = "getSubAssets";
    public final static String SUB_ASSETS_DEACTIVATES = "subAssetDeactive";
    public final static String SUB_ASSETS_DELETE = "subAssetDelete";

    // rental value

    public final static String SAVE_RENTALVALUE = "saveRentalValue";
    public final static String GET_RENTALVALUE = "getRentalValue";
    public final static String RENATAL_VALUE_DEACTIVATES = "rentalValueDeactive";
    public final static String RENATAL_VALUE_DELETE = "rentalValueDelete";

    // sales records

    public final static String SAVE_SALESRECORD = "saveSalesRecord";
    public final static String GET_SALESRECORD = "getSalesRecord";
    public final static String SALES_RECORD_DEACTIVATES = "salesRecordDeactive";
    public final static String SALES_RECORD_DELETE = "salesRecordDelete";
    public final static String SALES_RECORD_DOC_DOWNLOAD_FILE = "salesRecordDocDownloadFile";

    // MortgageePayments

    public final static String SAVE_MORTGAGEE_PAYMENTS = "saveMortgageePayments";
    public final static String GET_MORTGAGEE_PAYMENTS = "getMortgageePayments";
    public final static String MORTGAGEE_PAYMENTS_DEACTIVATES = "mortgageePaymentsDeactive";
    public final static String MORTGAGEE_PAYMENTS_DELETE = "mortgageePaymentsDelete";

    // RevenueSales

    public final static String SAVE_REVENUE_SALES = "saveRevenueSales";
    public final static String GET_REVENUE_SALES = "getRevenueSales";
    public final static String REVENUE_SALES_DEACTIVATES = "revenueSalesDeactive";
    public final static String REVENUE_SALES_DELETE = "revenueSalesDelete";

    // LongTermLeasing

    public final static String SAVE_LONG_TERM_LEASING = "savelongTermLeasing";
    public final static String GET_LONG_TERM_LEASING = "getlongTermLeasing";
    public final static String GET_LONG_TERM_LEASING_LAST_RECORD = "getLongtermleaselastrecord";
    public final static String GET_LONG_TERM_LEASING_ACTIVEALL_RECORD = "getLongtermleaseActiveAllRecord";
    public final static String LONG_TERM_LEASING_DEACTIVATES = "longTermLeasingDeactive";
    public final static String LONG_TERM_LEASING_DELETE = "longTermLeasingDelete";
    public final static String LONG_TERM_LEASING_DOC_DOWNLOAD_FILE = "longTermLeasingDocDownloadFile";

    // ShortTermCasualOccupancyRecordsRentalReturns STCORRReturns
    public final static String GET_STCORR_SUBSEQUENT_RENTAL_RECEIPTS = "getStcorrSubSequentRentalReceipts";
    public final static String SAVE_STCORR_RETURNS = "saveStcorrReturns";
    public final static String GET_STCORR_RETURNS = "getStcorrReturns";
    public final static String STCORR_RETURNS_DEACTIVATES = "stcorrReturnsDeactive";
    public final static String STCORR_RETURNS_SEARCH = "getStcorrReturnsSearch";
    public final static String STCORR_RETURNS_DELETE = "stcorrReturnsDelete";
    public final static String STCORR_RETURNS_DOC_DOWNLOAD_FILE = "stcorrReturnsDocDownloadFile";

    // LongTermLeaseActualRetruns
    public final static String SAVE_LONG_TERM_LEASE_ACTUAL_RETRUNS = "saveLongTermLeaseActualRetruns";
    public final static String GET_LONG_TERM_LEASE_ACTUAL_RETRUNS = "getLongTermLeaseActualRetruns";
    public final static String GET_LONG_TERM_LEASE_ACTUAL_RETRUNS_LAST_RECORD = "getLongTermLeaseActualRetrunsLastRecord";
    public final static String LONG_TERM_LEASE_ACTUAL_RETRUNS_DEACTIVATES = "longTermLeaseActualRetrunsDeactive";
    public final static String LONG_TERM_LEASE_ACTUAL_RETRUNS_DELETE = "longTermLeaseActualRetrunsDelete";
    public final static String LONG_TERM_LEASE_ACTUAL_RETRUNS_DOC_DOWNLOAD_FILE = "longTermLeaseActualRetrunsDocDownloadFile";

    // CarParkingTollCollection

    public final static String SAVE_CAR_PARKING_TOLL_COLLECTION = "saveCarParkingTollCollection";
    public final static String GET_CAR_PARKING_TOLL_COLLECTION = "getCarParkingTollCollection";
    //last record is constant
    public final static String GET_CAR_PARKING_TOLL_COLLECTION_LAST_RECORD = "getCarParkingTollCollectionlastrecord";
    
    public final static String CAR_PARKING_TOLL_COLLECTION_DEACTIVATES = "carParkingTollCollectionDeactive";
    public final static String CAR_PARKING_TOLL_COLLECTION_DELETE = "carParkingTollCollectionDelete";
    public final static String CAR_PARKING_TOLL_COLLECTION_DOC_DOWNLOAD_FILE = "carParkingTollCollectionDocDownloadFile";
    
    //TollCollection

    public final static String SAVE_TOLL_COLLECTION = "saveTollCollection";
    public final static String GET_TOLL_COLLECTION = "getTollCollection";
    //last record is constant
    public final static String GET_TOLL_COLLECTION_LAST_RECORD = "getTollCollectionlastrecord";
    
    //public final static String CAR_PARKING_TOLL_COLLECTION_DEACTIVATES = "carParkingTollCollectionDeactive";
    public final static String TOLL_COLLECTION_DELETE = "TollCollectionDelete";
    public final static String TOLL_COLLECTION_DOC_DOWNLOAD_FILE = "TollCollectionDocDownloadFile";

    // OccupancyUtilisationRecords

    public final static String SAVE_OCCUPANCY_UTILISATION_RECORDS = "saveOccupancyUtilisationRecords";
    public final static String GET_OCCUPANCY_UTILISATION_RECORDS = "getOccupancyUtilisationRecords";
    public final static String OCCUPANCY_UTILISATION_RECORDS_DEACTIVATES = "occupancyUtilisationRecordsDeactive";
    public final static String OCCUPANCY_UTILISATION_RECORDS_DELETE = "occupancyUtilisationRecordsDelete";

    // PeriodicalScheduleMaintenance
    public final static String SAVE_PERIODICAL_SCHEDULE_MAINTENANCE = "savePeriodicalScheduleMaintenance";
    public final static String GET_PERIODICAL_SCHEDULE_MAINTENANCE = "getPeriodicalScheduleMaintenance";
    public final static String PERIODICAL_SCHEDULE_MAINTENANCE_DEACTIVATES = "periodicalScheduleMaintenanceDeactive";
    public final static String PERIODICAL_SCHEDULE_MAINTENANCE_DELETE = "periodicalScheduleMaintenanceDelete";
    public final static String PERIODICAL_SCHEDULE_MAINTENANCE_DOC_DOWNLOAD_FILE = "planDocDownloadFile";
    public final static String PERIODICAL_SCHEDULE_COMPLETION_DOC_DOWNLOAD_FILE = "actualDocDownloadFile";

    // AssetLifeStatus
    public final static String SAVE_ASSET_LIFE_STATUS = "saveAssetLifeStatus";
    public final static String GET_ASSET_LIFE_STATUS = "getAssetLifeStatus";
    public final static String ASSET_LIFE_STATUS_DEACTIVATES = "assetLifeStatusDeactive";
    public final static String ASSET_LIFE_STATUS_DELETE = "assetLifeStatusDelete";
    public final static String ASSET_LIFE_STATUS_DOC_DOWNLOAD_FILE = "assetLifeStatusDocDownloadFile";

    // AssetCostValueStatus
    public final static String SAVE_ASSET_COST_VALUE_STATUS = "saveAssetCostValueStatus";
    public final static String GET_ASSET_COST_VALUE_STATUS = "getAssetCostValueStatus";
    public final static String ASSET_COST_VALUE_STATUS_DEACTIVATES = "assetCostValueStatusDeactive";
    public final static String ASSET_COST_VALUE_STATUS_DELETE = "assetCostValueStatusDelete";
    public final static String ASSET_COST_VALUE_STATUS_DOC_DOWNLOAD_FILE = "assetCostValueStatusDocDownloadFile";

    public final static String GET_EMP_CONTACT_DETAILS = "getEmpContactDetails";
    public final static String SAVE_EMP_CONTACT_DETAILS = "saveEmpContactDetails";
    public final static String DEACTIVATE_EMP_CONTACTS_DETAILS = "deactivateEmpContactDetails";
    public final static String GET_EMPS_NOT_IN_USER_PROJECTS = "getEmpsNotInUserProjects";

    public final static String GET_EMP_CHARGEOUT_RATES = "getEmpChargeOutRates";
    public final static String SAVE_EMP_CHARGEOUT_RATES = "saveEmpChargeOutRates";
    public final static String EMP_CHARGEOUT_RATES_ONLOAD = "empChargeOutRatesOnLoad";

    public final static String GET_EMP_NOK = "getEmpNok";
    public final static String SAVE_EMP_NOK = "saveEmpNok";
    public final static String DEACTIVATE_EMP_NOK = "deacctivateEmpNok";

    public final static String GET_EMP_REGULAR_PAYBLE_RATES = "getEmpRegularPaybleRates";
    public final static String GET_EMP_REGULAR_PAYBLE_RATE_DETAILS = "getEmpRegularPaybleRateDetails";
    public final static String SAVE_EMP_REGULAR_PAYBLE_RATES = "saveEmpRegularPaybleRates";

    public final static String GET_EMP_NON_REGULAR_PAYBLE_RATES = "getEmpNonRegularPaybleRates";
    public final static String GET_EMP_NON_REGULAR_PAYBLE_RATE_DETAILS = "getEmpNonRegularPaybleRateDetails";
    public final static String SAVE_EMP_NON_REGULAR_PAYBLE_RATES = "saveEmpNonRegularPaybleRates";

    public final static String GET_EMP_PAY_DEDUCTIONS = "getEmpPayDeductions";
    public final static String GET_EMP_PAY_DEDUCTION_DTLS = "getEmpPayDeductionsDetails";
    public final static String SAVE_EMP_PAY_DEDUCTIONS = "saveEmpPayDeductions";

    public final static String GET_EMP_LEAVE_ATTENDENCE_DTLS = "getEmpLeaveAttendenceDtls";
    public final static String SAVE_EMP_LEAVE_ATTENDENCE_DTLS = "saveEmpLeaveAttendenceDtls";

    public final static String GET_EMP_LEAVE_REQ_APPROVALS = "getEmpLeaveReqApprovals";
    public final static String GET_EMP_LEAVE_REQ_APPROVAL_DETAILS = "getEmpLeaveReqApprovalDetails";
    public final static String SAVE_EMP_LEAVE_REQ_APPROVALS = "saveEmpLeaveReqApprovals";

    public final static String GET_EMP_PROVIDENT_FUNDS = "getEmpProvidentFunds";
    public final static String GET_EMP_PROVIDENT_FUND_DTLS = "getEmpProvidentFundDetails";
    public final static String SAVE_EMP_PROVIDENT_FUNDS = "saveEmpProvidentFunds";

    public final static String GET_EMP_MEDICAL_HISTORY = "getEmpMedicalHistory";
    public final static String SAVE_EMP_MEDICAL_HISTORY = "saveEmpMedicalHistory";
    public final static String GET_PLANT_REGISTERS = "getPlantregisters";
    public final static String SAVE_PLANT_REGISTERS = "savePlantregisters";
    public final static String PLANT_REGISTERS_DEACTIVATE = "deactivatePlantRegisters";
    public final static String PLANT_REGISTERS_ONLOAD = "plantRegistersOnLoad";
    public final static String SAVE_EMP_PAYOUT_DOCUMENTS = "saveEmpPayoutDocuments";

    public final static String GET_PLANT_TRANSFERS = "getPlantTransfers";
    public final static String GET_PLANT_TRANSFER_DETAILS = "getPlantTransferDetails";
    public final static String GET_PLANT_TRANSFER_REQUEST_DETAILS = "getPlantTransferReqDetails";
    public final static String SAVE_PLANT_TRANSFERS = "savePlantTransfers";
    public final static String EMP_PLANT_TRANS_ONLOAD = "plantReqTransOnLoad";
    public final static String GET_PLANT_NOTIFICATIONS = "getPlantNotifications";

    public final static String GET_PLANT_PROJRCT_DTLS = "getPlantProjectDtls";
    public final static String SAVE_PLANT_PROJRCT_PO_DTLS = "savePlantProjectPODtls";

    public final static String PLANT_PROJECT_PO_SEARCH = "plantProjectPOSearch";
    public final static String PLANT_PROJECT_PO_LOOKUP = "plantProjectPODetails";

    public final static String PROJECT_PO_DOCKET_DTL = "getProjectPODocketDtls";

    public final static String GET_PLANT_CHARGEOUT_RATES = "getPlantChargeOutRates";
    public final static String SAVE_PLANT_CHARGEOUT_RATES = "savePlantChargeOutRates";
    
    public final static String SAVE_PLANT_PAYABLE_RATES = "savePlantPayableRates";
    public final static String GET_PLANT_PAYABLE_RATES = "getPlantPayableRates";

    public final static String GET_PLANT_REQ_APPROVAL = "getPlantReqApproval";
    public final static String SAVE_PLANT_REQ_APPROVAL = "savePlantReqApproval";

    public final static String GET_PLANT_LOG_RECORDS = "getPlantLogRecords";

    public final static String SAVE_PLANT_LOG_RECORDS = "savePlantLogRecords";
    public final static String PLANT_LOG_RECORDS_DEACTIVATE = "plantLogRecordsDeactivate";

    public final static String GET_PLANT_PROJECT_DTLS = "getPlantProjectDtls";
    public final static String SAVE_PLANT_PROJECT_DTLS = "savePlantProjectDtls";
    public final static String PLANT_PROJECTS_ONLOAD = "plantProjectsOnLoad";

    public final static String SAVE_PLANT_UTILAIZATION_RECORDS = "savePlantUtilaizationRecords";

    public final static String GET_PLANT_DOCKET_DTLS = "getPlantDocketDtls";
    public final static String SAVE_PLANT_DOCKET_DTLS = "savePlantDocketDtls";
    public final static String PLANT_DOCKET_ONLOAD = "plantDocketOnLoad";

    public final static String GET_EMP_TRANSFERS = "getEmpTransfers";
    public final static String GET_EMP_TRANSFER_DETAILS = "getEmpTransferDetails";
    public final static String GET_EMP_TRANSFER_REQUEST_DETAILS = "getEmpTransferReqDetails";
    public final static String SAVE_EMP_TRANSFERS = "saveEmpTransfers";
    public final static String EMP_REQ_TRANS_ONLOAD = "empReqTransOnLoad";
    public final static String GET_EMP_NOTIFICATIONS = "getEmpNotifications";

    public final static String ASSIGN_PROJECT_TO_PLANT = "assignProjectTOPlant";
    public final static String GET_PROJECT_PLANT_PO_By_PROJID = "getPlantPOByProjId";

    public final static String GET_PLANT_SERVICE_HISTORY = "getPlantServiceHistory";
    public final static String SAVE_PLANT_SERVICE_HISTORY = "savePlantServiceHistory";
    public final static String PLANT_SERVICE_ONLOAD = "plantServiceOnLoad";

    public final static String GET_PLANT_REPAIRS = "getPlantRepairs";
    public final static String GET_PLANT_MATERIAL_PROJ_DOCKET_DETAILS = "getPlantMaterialProjDocketDetails";

    public final static String SAVE_PLANT_REPAIRS = "savePlantRepairs";
    public final static String PLANT_REPAIRS_ONLOAD = "plantRepairsOnLoad";

    public final static String GET_PLANT_DEPRISIATION_SALVAGE = "getPlantDeprisiationSalvage";
    public final static String SAVE_PLANT_DEPRISIATION_SALVAGE = "savePlantDeprisiationSalvage";

    public final static String GET_PURCHASE_ORDERS_BY_PROCURETYPE = "getPurchaseOrdersByProcureType";

    public final static String GET_STATUS_TYPES = "getStatusTypes";
    public final static String PLANT_PROCURE_DELIVERY_DTL = "getPlantProcureDeliveryDtls";

    // procure delivery details

    public final static String GET_REG_PLANT_PROCURE_DELIVERY = "getRegPlantProcureDeliveryDetails";

    public final static String SAVE_REG_PLANT_PROCURE_DELIVERY = "saveRegPlantProcureDelivery";
    public static final String DOWNLOAD_REG_PLANT_PROCURE_DELIVERY = "downloadRegPlantProcureDeliveryDoc";

    public final static String USER_PROJECT_LABELKEYTO = "userProjectLabelKeyTO";
    // Resource Plant current status
    public final static String GET_PLANT_CURRENT_STATUS = "getPlantCurrentStatus";

    // Resource Plant Deployment status

    public final static String GET_PLANT_DEPLOYMENT = "getPlantDeploymentOnLoad";
    public final static String SAVE_PLANT_DEPLOYMENT = "savePlantDeployment";

    public final static String GET_PLANTS_NOT_IN_USER_PROJECTS = "getPlantsNotInUserProjects";
    public final static String GET_PLANTS_IN_USER_PROJECTS = "getPlantsInUserProjects";

    public final static String GET_PLANT_ATTENDENCE = "getPlantAttendence";

    // Resource transfers

    public final static String GET_PROJ_SETTINGS_PLANT_TRANSFER = "getProjSettingsPlantTransferCheck";
    public final static String GET_PROJ_SETTINGS_MATERIAL_TRANSFER = "getProjSettingsMaterialTransferCheck";
    public final static String GET_PROJ_SETTINGS_EMP_LEAVE_CHECK = "getProjSettingsEmpLeaveCheck";

    // ===============================below are material
    // urls===========================================================

    public final static String GET_MATERIAL_TRANESFERS = "getMaterialTransfers";
    public final static String GET_MATERIAL_TRANESFER_DETAILS = "getMaterialTranferDetails";
    public final static String GET_MATERIAL_DETAILS_FOR_TRANESFER = "getMaterialDetailsForTransfer";
    public final static String SAVE_MATERIAL_TRANESFERS = "saveMaterialTransfers";

    public final static String GET_MATERIAL_NOTIFICATIONS = "getMaterialNotification";
    public final static String SAVE_MATERIAL_NOTIFICATIONS = "saveMaterialNotification";

    public final static String GET_PROJ_MATERIAL_SCH_ITEMS = "getProjMaterialSchItems";
    public final static String GET_PROJ_DOCKETS = "getProjectDockets";
    public final static String SAVE_PROJ_MATERIAL_SCH_ITEMS = "saveProjMaterialSchItems";
    public final static String GET_MATERIAL_SCH_ITEMS_BY_PURCHASE_ORDER = "getMaterialSchItemsByPurchaseOrder";
    public final static String SAVE_PROJ_MATERIAL_SCH_DOCKET_DETAILS = "saveProjMaterialSchDocketDetails";
    public final static String GET_PROJ_MATERIAL_SCH_ITEMS_SEARCH = "getProjMaterialSch";
    public final static String GET_PROJ_MATERIAL_SCH_HOME_SEARCH = "materialHomedefaultSearch";

    public final static String GET_PROJ_MATERIAL_CONSUMPTION = "getMaterialProjConsumption";
    public final static String GET_PROJ_MATERIAL_STORE_TRANSIT_CONSUMPTION = "getMaterialStoreTransitConsumption";
    public final static String GET_PROJ_MATERIAL_STOCK_PILED_CONSUMPTION = "getMaterialStockPiledConsumption";
    public final static String GET_PROJ_MATERIAL_DAILY_ISSUE_SCH_ITEMS = "getMaterialDailyIssueSchItems";

    public final static String GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS = "getMaterialSchItemDeliveryDockets";
    public final static String GET_MATERIAL_DELIVERY_DOCKET_DETAILS = "getMaterialDeliveryDocketDetails";
    public final static String GET_MATERIAL_DELIVERY_DOCKETS = "getMaterialDeliveryDockets";

    public final static String GET_MATERIAL_SCH_AVL_DOCKETS_LOCATION = "getMaterialSchAvlByLocation";

    public final static String GET_MATERIAL_PROJ_LEDGERS = "getMaterialProjLedgers";

    public final static String GET_MATERIAL_SCH_FOR_PROJDOCKET = "getMaterialSchForProjDocket";

    public final static String GET_MATERIAL_PROJ_DOCKETS = "getMaterialProjDockets";
    public final static String GET_MATERIAL_PROJ_DOCKETS_BY_DOC_TYPE = "getMaterialProjDocketsByDoctype";
    public final static String GET_MATERIAL_PROJ_DOCKET_SCH_ITEMS = "getMaterialProjDocketSchItems";

    public final static String SAVE_MATERIAL_PROJ_DOCKETS = "saveMaterialProjDockets";

    public final static String SAVE_MATERIAL_PROJ_DOCKETS_AS_DRAFT = "saveMaterialProjDocketsAsDraft";

    public final static String GET_MATERIAL_PROJECTPOS = "getMaterialProjectPos";
    public final static String SAVE_MATERIAL_PROJECTPOS = "saveMaterialProjectPos";

    public final static String GET_MATERIAL_SCH_ITEMS_BY_PROJECT_AND_LOC = "getMaterialSchItemsByProjectAndLoc";
    public final static String GET_MATERIAL_SCH_DETAILS_FOR_TRANESFER = "getMaterialSchDetailsForTransfer";

    public static final String IS_EMPCODE_UNIQUE = "isEmpCodeUnique";
    public static final String GET_PROJ_EMPLOYEES = "getProjEmployees";

    public static final String IS_PLANTCODE_UNIQUE = "isPlantCodeUnique";

    public static final String GET_MANPOWER_GENDER_STATISTICS_REPORT = "getManpowerGenderStatisticsReport";

    public static final String GET_MANPOWER_PERIODICAL_MOBILISATION_REPORT = "getManpowerPeriodicalMobilisationReport"; 
    
    public final static String GET_FIXED_ASSETS_PURACHASE_ACQULISITION_DOCUMENTS = "getPurachaseAcqulisitionDocuments";
    public final static String GET_FIXED_ASSETS_SALE_RECORDS_DOCUMENTS = "getSaleRecordsDocuments";
    public final static String GET_FIXED_ASSETS_RENTAL_HISTORY_DOCUMENTS = "getRentalHistoryDocuments";
    public final static String GET_FIXED_ASSETS_SHORT_TERMS_DOCUMENTS = "getShortTermDocuments";
    public final static String GET_FIXED_ASSETS_LONG_TERMS_DOCUMENTS = "getLongTermDocuments";
    public final static String GET_FIXED_ASSETS_CAR_PARKING_DOCUMENTS = "getCarParkingDocuments";
    public final static String GET_FIXED_ASSETS_DOCUMENTS = "getTollDocuments";
    public final static String GET_FIXED_ASSETS_PERIODICAL_DOCUMENTS = "getPeriodicalDocuments";
    public final static String GET_FIXED_ASSETS_REPAIRS_DOCUMENTS = "getRepairsDocuments";
    public final static String GET_FIXED_ASSETS_ASSET_LIFE_DOCUMENTS = "getAssetLifeDocuments";
    public final static String GET_FIXED_ASSETS_ASSET_COST_DOCUMENTS = "getAssetCostDocuments";
    public final static String GET_MATERIALS_FOR_PROJ_DOCKET = "getMaterialsForProjDocket";
    
    public final static String SAVE_EMPLOYEE_DOCS = "saveEmployeeDocs";
    public final static String GET_EMPLOYEE_DOCS = "getEmployeeDocs";
    public final static String DOWNLOAD_REGISTER_DOCS = "downloadRegisterDocs";

    public final static String SAVE_EMP_QUALIFICATION_RECORDS = "saveEmpQualificationRecords";
    public final static String GET_EMP_QUALIFICATION_RECORDS = "getEmpQualificationRecords";
    
    public final static String GET_MATERIAL_PROJ_DOCKETS_BY_PROJ_ID = "getMaterialProjDocketsByProjId";
    
    public static final String GET_MANPOWER_EMPLOYEE_DETAILS = "getManpowerEmployeeDetail";
}
