package com.rjtech.centrallib.constants;

import java.io.Serializable;

public class CentralLibraryConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -923474679747855169L;

    public final static String PARH_URL = "/app/centrallib/";

    public static final String GET_REGISTER_ONLOAD_CMP_PROCATG_CLASS = "getRegisterOnLoadCmpCatgProCatgClass";
    public static final String ONLOAD_DATA_FOR_PROCURECATG = "onLoadDataForProcureCatg";
    public final static String GET_CONTACTS = "getContacts";
    public final static String GET_ADDRESS = "getAddress";
    public final static String GET_CMPCURRENTPROJS = "getCmpCurrentProjs";

    public final static String GET_MEASUREMENTS = "getMeasurements";

    public final static String GET_CENTRALMATERIAL = "getCentralMaterial";

    public final static String GET_MEASURES_BY_PROCURE_TYPE = "getMeasuresByProcureType";

    public final static String SAVE_MEASUREMENTS = "saveMeasurements";
    public final static String DELETE_MEASUREMENTS = "deleteMeasurements";

    public final static String GET_WEATHERS = "getWeathers";
    public final static String SAVE_WEATHERS = "saveWeathers";
    public final static String DELETE_WEATHERS = "deleteWeathers";

    public final static String GET_EMPCLASSES = "getEmpClasses";
    public final static String SAVE_EMPCLASSES = "saveEmpClasses";
    public final static String DELETE_EMPCLASSES = "deleteEmpClasses";

    public static final String ONLOAD_DATA_FOR_COMAPANY = "onLoadDataForCompany";

    public final static String GET_COMPANIES_WITHDEFAULT_ADDRESS_AND_CONTACT = "getCompaniesWithDefaultAddressAndContact";

    public final static String GET_COMPANIES = "getCompanies";
    public final static String GET_COMPANY_DETAILS = "getCompanyDetails";
    public final static String GET_COMPANIES_DETAILS_CMP_IDS = "getCompaniesDetailsByCmpIds";

    public final static String SAVE_COMPANY = "saveCompany";
    public final static String DELETE_COMPANIES = "deleteCompanies";

    public final static String SAVE_ADDRESS = "saveAddress";
    public final static String DELETE_ADDRESS = "deleteAddress";

    public final static String SAVE_CONTACTS = "saveContacts";
    public final static String DELETE_CONTACTS = "deleteContacts";

    public static final String GET_PROJECTS_BY_CLIENT = "getProjectsByClient";

    public final static String SAVE_COMPANY__CLOSED_PROJS = "saveCompanyClosedProjs";
    public final static String DELETE_COMPANY_CLOSED_PROJS = "deleteCompanyClosedProjs";

    public final static String SAVE_COMPANY_CURRENT_PROJS = "saveCompanyCurrentProjs";
    public final static String DELETE_COMPANY_CURRENT_PROJS = "deleteCompanyCurrentProjs";

    public final static String MOVE_TO_COMPANY_CLOSED_PROJS = "moveToCmpClosedProjs";

    public final static String SAVE_STOCKS = "saveStocks";
    public final static String GET_STOCKS = "getStocks";
    public final static String DELETE_STOCK = "deleteStocks";

    public final static String SAVE_PROCURES = "saveProcures";
    public final static String GET_PROCURES = "getprocures";
    public final static String DELETE_PROCURES = "deleteprocures";

    public final static String SAVE_BUSINESSCATGS = "saveBusinessCatgs";
    public final static String DELETE_BUSINESSCATGS = "deleteBusinessCatgs";

    public final static String SAVE_EMPWAGES = "saveEmpWages";
    public final static String GET_EMPWAGES = "getEmpWages";
    public final static String DELETE_EMPWAGES = "deleteEmpWages";

    public final static String SAVE_COSTCODES = "saveCostCodes";
    public final static String GET_COSTCODES = "getCostCodes";
    public final static String DELETE_COSTCODES = "deleteCostCodes";

    public final static String SAVE_PROCURECATGS = "saveProcureCatgs";
    public final static String GET_PROCURECATGS = "getProcureCatgs";
    public final static String DELETE_PROCURECATGS = "deleteProcureCatgs";

    public final static String SAVE_PLANTCLASSES = "savePlantClasses";
    public final static String GET_PLANTCLASSES = "getPlantClasses";
    public final static String DELETE_PLANTCLASSES = "deletePlantClasses";

    public final static String SAVE_SERVICECLASSES = "saveServiceClasses";
    public final static String GET_SERVICECLASSES = "getServiceClasses";
    public final static String DELETE_SERVICECLASSES = "deleteServiceClasses";

    public final static String SAVE_PLANT_CLASS_SERVICE = "savePlantClassService";
    public final static String GET_PLANT_CLASS_SERVICE = "getPlantClassService";
    public final static String GET_PLANT_CLASS_SERVICE_ITENS_ONLY = "getPlantClassServiceItemsOnly";
    public final static String DEACTIVATE_PLANT_CLASS_SERVICE = "deactivatePlantClassService";
    public final static String GET_PLANT_SERVICE_MATERIAL_CLASS = "getPlantServiceMaterialClass";

    public final static String GET_MATERIAL_GROUPS = "getMaterialGroups";
    public final static String SAVE_MATERIAL_GROUPS = "saveMaterialGroups";
    public final static String DELETE_MATERIAL_GROUPS = "deleteMaterialGroups";
    
    public static final String GET_TANGIBLE_ITEMS = "getTangibleItems";
    public final static String GET_TANGIBLE_GROUPS = "getTangibleGroups";
    public final static String SAVE_TANGIBLE_GROUPS = "saveTangibleGroups";
    public final static String DELETE_TANGIBLE_GROUPS = "deleteTangibleGroups";
    public final static String GET_CENTRAL_TANGIBLE = "getCentralTangible";

    public final static String SUCCESS = "Success";

    public final static String FAILURE = "Failure";

    public final static String GET_ASSET_CATEGORY = "getAssetCategory";
    public final static String SAVE_ASSET_CATEGORY = "saveAssetCategory";
    public final static String DELETE_ASSET_CATEGORY = "deleteAssetCategory";

    public final static String GET_ASSET_MAINTENANCE_CATEGORY = "getAssetMaintenanceCategory";
    public final static String SAVE_ASSET_MAINTENANCE_CATEGORY = "saveAssetMaintenanceCategory";
    public final static String DELETE_ASSET_MAINTENANCE_CATEGORY = "deleteAssetMaintenanceCategory";
    
    public static final String SAVE_COUNTRY_PROVISION_CODES="saveCountryProvisionCodes";
    public static final String GET_COUNTRY_PROVISION_CODES="getCountryProvisionCodes";
    public static final String DEACTIVATE_COUNTRY_PROVISION_CODES = "deactivateCountryProvisionCodes";
    
    //financecenter
    
    public static final String SAVE_FINANCE_CENTER = "savefinanceCenterRecords";
    public static final String GET_FINANCE_CENTER = "getFinanceCenterRecords";
    public static final String DEACTIVATE_FINANCE_CENTER ="deactivateFinanceCenterRecords";
    public static final String GET_EMPLOYEE_TYPES= "getEmployeeTypes";
    public static final String GET_UNT_OF_RATE="getUnitOfRate";
    public static final String SAVE_TAX_AND_RULES="saveTaxAndRules";
    
    
    // ================================CenterLibMapUrlConstants==================================================

    public final static String GET_COMPANY_MAP = "getCompanyMap";
    public final static String GET_MEASUREMENT_MAP = "getMeasurementMap";
    public final static String GET_WEATHERS_MAP = "getWeathersMap";
    public final static String GET_EMP_CLASS_MAP = "getEmpClassMap";
    public final static String GET_PLANT_CLASS_MAP = "getPlantClassMap";
    public final static String GET_MATERIAL_CLASS_MAP = "getMaterialClassMap";
    public final static String GET_COST_CODE_CLASS_MAP = "getCostCodeClassMap";
    public final static String GET_EMP_WAGE_FACTOR_MAP = "getEmpWageFactorMap";
    public final static String GET_PROCURE_CATG_CLASS_MAP = "getProcureCatgClassMap";
    public final static String GET_SERVICE_CLASS_MAP = "getServiceClassMap";
    public final static String GET_WARE_HOUSE_MAP = "getWareHouseMap";
    public final static String GET_PLANT_SERVICE_HISTORY_MAP = "getPlantServiceHistoryMap";

    public static final String GET_MATERIAL_ITEMS = "getMaterialItems";

    public final static String GET_COMPANIES_MAP = "getCompaniesMap";

    public final static String GET_EMP_CLASSIFICATION_MAP = "getEmpClassificationMap";

    public final static String GET_PLANT_CLASSIFICATION_MAP = "getPlantClassificationMap";
    
    public final static String SAVE_COMPANY_BANK_DETAILS = "saveBankDetails";
    public final static String GET_BANK_DETAILS = "getBank";
    public final static String DELETE_BANK_DETAILS = "deleteBank";
    
}
