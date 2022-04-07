package com.rjtech.common.service;

import java.util.Map;

import com.rjtech.centrallib.req.AddressDelReq;
import com.rjtech.centrallib.req.AddressSaveReq;
import com.rjtech.centrallib.req.AssetCategoryDelReq;
import com.rjtech.centrallib.req.AssetCategoryGetReq;
import com.rjtech.centrallib.req.AssetCategorySaveReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategoryDelReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategoryGetReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategorySaveReq;
import com.rjtech.centrallib.req.CompanyDelReq;
import com.rjtech.centrallib.req.CompanyFilterReq;
import com.rjtech.centrallib.req.CompanyGetReq;
import com.rjtech.centrallib.req.CompanyProjDelReq;
import com.rjtech.centrallib.req.CompanyProjSaveReq;
import com.rjtech.centrallib.req.CompanySaveReq;
import com.rjtech.centrallib.req.ContactDelReq;
import com.rjtech.centrallib.req.ContactsSaveReq;
import com.rjtech.centrallib.req.CostCodeDelReq;
import com.rjtech.centrallib.req.CostCodeSaveReq;
import com.rjtech.centrallib.req.CountryProvinceCodeDeactiveReq;
import com.rjtech.centrallib.req.CountryProvinceCodeGetReq;
import com.rjtech.centrallib.req.CountryProvinceCodeSaveReq;
import com.rjtech.centrallib.req.EmpClassDelReq;
import com.rjtech.centrallib.req.EmpClassFilterReq;
import com.rjtech.centrallib.req.EmpClassesSaveReq;
import com.rjtech.centrallib.req.EmpWageDelReq;
import com.rjtech.centrallib.req.EmpWageSaveReq;
import com.rjtech.centrallib.req.EmpWagesFilterReq;
import com.rjtech.centrallib.req.FinanceCenterCodeGetReq;
import com.rjtech.centrallib.req.FinanceCenterDeactiveReq;
import com.rjtech.centrallib.req.FinanceCenterSaveReq;
import com.rjtech.centrallib.req.FinanceCenterFilterReq;
import com.rjtech.centrallib.req.MaterialClassDelReq;
import com.rjtech.centrallib.req.MaterialClassFilterReq;
import com.rjtech.centrallib.req.MaterialClassGetReq;
import com.rjtech.centrallib.req.MaterialClassSaveReq;
import com.rjtech.centrallib.req.MeasureDelReq;
import com.rjtech.centrallib.req.MeasureUnitReq;
import com.rjtech.centrallib.req.MesureFilterReq;
import com.rjtech.centrallib.req.PlantClassDelReq;
import com.rjtech.centrallib.req.PlantClassFilterReq;
import com.rjtech.centrallib.req.PlantClassSavereq;
import com.rjtech.centrallib.req.PlantServiceClassDeactivateReq;
import com.rjtech.centrallib.req.PlantServiceClassGetReq;
import com.rjtech.centrallib.req.PlantServiceClassSaveReq;
import com.rjtech.centrallib.req.ProcureCatgDelReq;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.req.ProcureCatgSaveReq;
import com.rjtech.centrallib.req.RegisterOnLoadReq;
import com.rjtech.centrallib.req.ServiceClassDelReq;
import com.rjtech.centrallib.req.ServiceFiltterReq;
import com.rjtech.centrallib.req.ServiceSaveReq;
import com.rjtech.centrallib.req.StockDelReq;
import com.rjtech.centrallib.req.StockFilterReq;
import com.rjtech.centrallib.req.StockSaveReq;
import com.rjtech.centrallib.req.WeatherDelReq;
import com.rjtech.centrallib.req.WeatherFilterReq;
import com.rjtech.centrallib.req.WeatherReq;
import com.rjtech.centrallib.req.costCodeFilterReq;
import com.rjtech.centrallib.resp.AddressResp;
import com.rjtech.centrallib.resp.AssetCategoryResp;
import com.rjtech.centrallib.resp.AssetMaintenanceCategoryResp;
import com.rjtech.centrallib.resp.CmpCurrentProjsResp;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.ContactsResp;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.centrallib.resp.CountryProvinceCodeResp;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.EmpWageResp;
import com.rjtech.centrallib.resp.EmployeeTypeResp;
import com.rjtech.centrallib.resp.FinanceCenterResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.centrallib.resp.PlantServiceClassResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.centrallib.resp.ProcurementCompanyResp;
import com.rjtech.centrallib.resp.ServiceResp;
import com.rjtech.centrallib.resp.StockResp;
import com.rjtech.centrallib.resp.WeatherResp;
import com.rjtech.common.dto.FinanceCenterRecordTo;
import com.rjtech.common.dto.LabelKeyTO;

import com.rjtech.centrallib.req.TangibleClassGetReq;
import com.rjtech.centrallib.resp.TangibleClassResp;
import com.rjtech.centrallib.req.TangibleClassSaveReq;
import com.rjtech.centrallib.req.TangibleClassFilterReq;
import com.rjtech.centrallib.req.TangibleClassDelReq;

import com.rjtech.centrallib.req.TaxRatesRulesCodeDtlSaveReq;

import com.rjtech.centrallib.resp.CmpBankDetailsResp;
import com.rjtech.centrallib.req.CompanyBankSaveReq;
import com.rjtech.centrallib.req.BankDelReq;

public interface CentralLibService {
	
	

    MeasureUnitResp getMeasurements(MesureFilterReq mesureFilterReq);

    MeasureUnitResp getMeasuresByProcureType(MesureFilterReq mesureFilterReq);

    void saveMeasurements(MeasureUnitReq measureUnitReq);

    void deleteMeasurements(MeasureDelReq measureDelReq);

    WeatherResp getWeatherDetails(WeatherFilterReq WeatherFilterReq);

    void saveWeatherDetails(WeatherReq weatherReq);

    void deleteWeatherDetails(WeatherDelReq weatherDelReq);

    EmpClassesResp getEmpClasses(EmpClassFilterReq empClassFilterReq);

    void deleteEmpClasses(EmpClassDelReq empClassDelReq);

    void saveEmpClasses(EmpClassesSaveReq empClassesSaveReq);

    PlantClassResp getPlantClasses(PlantClassFilterReq plantClassFilterReq);

    void deletePlantClasses(PlantClassDelReq plantClassDelReq);

    void savePlantClasses(PlantClassSavereq plantClassSavereq);

    CompanyResp getCompanies(CompanyFilterReq companyFilterReq);

    ProcurementCompanyResp getCompaniesDetailsByCmpIds(CompanyGetReq companyGetReq);

    ProcurementCompanyResp getCompaniesWithDefaultAddressAndContact(CompanyFilterReq companyFilterReq);

    CompanyResp getCompanyDetails(CompanyGetReq companyGetReq);

    void saveCompany(CompanySaveReq companySaveReq);

    void deleteCompanies(CompanyDelReq companyDelReq);

    void saveAddress(AddressSaveReq addressSaveReq);

    void deleteAddress(AddressDelReq addressDelReq);

    void saveContacts(ContactsSaveReq contactsSaveReq);

    void deleteContacts(ContactDelReq contactDelReq);

    void saveCompanyCurrentProjs(CompanyProjSaveReq companyProjSaveReq);

    void deleteCompanyCurrentProjs(CompanyProjDelReq companyProjDelReq);

    void deleteCompanyClosedProjs(CompanyProjDelReq companyProjDelReq);

    void moveToCmpClosProjs(CompanyProjSaveReq companyProjSaveReq);

    void saveStock(StockSaveReq stockSaveReq);

    StockResp getStock(StockFilterReq stockFilterReq);

    void deleteStock(StockDelReq stockDelReq);

    void saveEmpWages(EmpWageSaveReq empWageSaveReq);

    EmpWageResp getEmpWages(EmpWagesFilterReq empWagesFilterReq);

    void deleteEmpWages(EmpWageDelReq empWageDelReq);

    void saveCostCodes(CostCodeSaveReq costCodeSaveReq);

    CostCodeResp getCostCodes(costCodeFilterReq costCodeFilterReq);

    void deleteCostCodes(CostCodeDelReq costCodeDelReq);

    void saveProcureCatgs(ProcureCatgSaveReq procureCatgSaveReq);

    ProcureCatgResp getProcureCatgs(ProcureCatgFilterReq procureCatgFilterReq);

    void deleteProcureCatgs(ProcureCatgDelReq procureCatgDelReq);

    void saveServiceClasses(ServiceSaveReq serviceSaveReq);

    ServiceResp getServiceClasses(ServiceFiltterReq serviceFiltterReq);

    void deleteServiceClasses(ServiceClassDelReq serviceClassDelReq);

    ContactsResp getContacts(CompanyGetReq userReq);

    AddressResp getAddress(CompanyGetReq userReq);

    CmpCurrentProjsResp getCmpCurrentProjs(CompanyGetReq userReq);

    PlantServiceClassResp getPlantServiceClass(PlantServiceClassGetReq plantServiceClassGetReq);

    void savePlantServiceClass(PlantServiceClassSaveReq plantServiceClassSaveReq);

    PlantServiceClassResp getPlantServiceClassItemsOnly(PlantServiceClassGetReq plantServiceClassGetReq);

    void deactivatePlantServiceClass(PlantServiceClassDeactivateReq plantServiceClassDeactivateReq);

    MaterialClassResp getMaterialGroups(MaterialClassGetReq MaterialClassGetReq);

    MaterialClassResp getMaterialItems(MaterialClassGetReq materialClassGetReq);

    void saveMaterialGroups(MaterialClassSaveReq materialClassSaveReq);

    void deleteMaterialGroups(MaterialClassDelReq materialClassDelReq);

    AssetCategoryResp getAssetCategory(AssetCategoryGetReq assetCategoryGetReq);

    void saveAssetCategory(AssetCategorySaveReq assetCategorySaveReq);

    void deleteAssetCategory(AssetCategoryDelReq assetCategoryDelReq);

    AssetMaintenanceCategoryResp getAssetMaintenanceCategory(
            AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq);

    void saveAssetMaintenanceCategory(AssetMaintenanceCategorySaveReq assetMaintenanceCategorySaveReq);

    void deleteAssetMaintenanceCategory(AssetMaintenanceCategoryDelReq assetMaintenanceCategoryDelReq);

    Map<Long, LabelKeyTO> getCompanyMap();

    Map<Long, LabelKeyTO> getProcureCatgMap(RegisterOnLoadReq registerOnLoadReq);

    Map<Long, LabelKeyTO> getEmpClassMap();

    Map<Long, LabelKeyTO> getPlantClassMap();

    Map<Long, LabelKeyTO> getMaterialClassMap();

    Map<Long, LabelKeyTO> getPlantServiceClassItemsMap();

    MaterialClassResp getCentralMaterial(MaterialClassFilterReq materialClassFilterReq);
    
   
    
    FinanceCenterRecordTo savefinanceCenterRecords(FinanceCenterSaveReq financeCenterSaveReq);

    FinanceCenterResp getfinanceCenterRecords(FinanceCenterCodeGetReq financeCenterCodeGetReq);
    
    Map<Long, LabelKeyTO> getEmployeeTypes(FinanceCenterCodeGetReq financeCenterCodeGetReq);
    
    void deactivateFinanceCenterRecords(FinanceCenterDeactiveReq financeCenterDeactiveReq);
    
    FinanceCenterResp getUnitOfRate(FinanceCenterFilterReq financeCenterFilterReq);
    
    
    void saveCountryProvinceCode(CountryProvinceCodeSaveReq countryProvinceCodeSaveReq);
    CountryProvinceCodeResp getCountryProvinceCodes(CountryProvinceCodeGetReq countryProvinceCodeGetReq);
    void deactivateCountryProvinceCodes(CountryProvinceCodeDeactiveReq countryProvinceCodeDeactiveReq);
    
    
    void saveTangibleGroups(TangibleClassSaveReq tangibleClassSaveReq);
    TangibleClassResp getTangibleGroups(TangibleClassGetReq tangibleClassGetReq);
    TangibleClassResp getCentralTangible(TangibleClassFilterReq tangibleClassFilterReq);
    void deleteTangibleGroups(TangibleClassDelReq tangibleClassDelReq);
    
    void saveTaxAndRules(TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq);
    
    void saveCompanyBankDetails(CompanyBankSaveReq companyBankSaveReq);
    
    CmpBankDetailsResp getBank(CompanyGetReq userReq);
    
    void deleteBank(BankDelReq bankDelReq);
}
