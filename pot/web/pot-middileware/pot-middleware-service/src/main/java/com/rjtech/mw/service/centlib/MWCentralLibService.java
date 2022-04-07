package com.rjtech.mw.service.centlib;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rjtech.centrallib.req.AddressDelReq;
import com.rjtech.centrallib.req.AddressSaveReq;
import com.rjtech.centrallib.req.AssetCategoryDelReq;
import com.rjtech.centrallib.req.AssetCategoryGetReq;
import com.rjtech.centrallib.req.AssetCategorySaveReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategoryDelReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategoryGetReq;
import com.rjtech.centrallib.req.AssetMaintenanceCategorySaveReq;
import com.rjtech.centrallib.req.AttendancePlantStatusGetReq;
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
import com.rjtech.centrallib.req.EmpClassDelReq;
import com.rjtech.centrallib.req.EmpClassFilterReq;
import com.rjtech.centrallib.req.EmpClassesSaveReq;
import com.rjtech.centrallib.req.EmpWageDelReq;
import com.rjtech.centrallib.req.EmpWageSaveReq;
import com.rjtech.centrallib.req.EmpWagesFilterReq;
import com.rjtech.centrallib.req.MaterialClassDelReq;
import com.rjtech.centrallib.req.MaterialClassFilterReq;
import com.rjtech.centrallib.req.MaterialClassGetReq;
import com.rjtech.centrallib.req.MaterialClassSaveReq;
import com.rjtech.centrallib.req.MeasureDelReq;
import com.rjtech.centrallib.req.MeasureUnitReq;
import com.rjtech.centrallib.req.MesureFilterReq;
import com.rjtech.centrallib.req.OnLoadFilterReq;
import com.rjtech.centrallib.req.PlantClassDelReq;
import com.rjtech.centrallib.req.PlantClassFilterReq;
import com.rjtech.centrallib.req.PlantClassSavereq;
import com.rjtech.centrallib.req.PlantServiceClassDeactivateReq;
import com.rjtech.centrallib.req.PlantServiceClassGetReq;
import com.rjtech.centrallib.req.PlantServiceClassSaveReq;
import com.rjtech.centrallib.req.ProcureCatgDelReq;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.req.ProcureCatgSaveReq;
import com.rjtech.centrallib.req.ProcureDelReq;
import com.rjtech.centrallib.req.ProcureFilterReq;
import com.rjtech.centrallib.req.ProcureSaveReq;
import com.rjtech.centrallib.req.RegisterOnLoadReq;
import com.rjtech.centrallib.req.ServiceClassDelReq;
import com.rjtech.centrallib.req.ServiceFiltterReq;
import com.rjtech.centrallib.req.ServiceSaveReq;
import com.rjtech.centrallib.req.StockDelReq;
import com.rjtech.centrallib.req.StockFilterReq;
import com.rjtech.centrallib.req.StockSaveReq;
import com.rjtech.centrallib.req.UserReq;
import com.rjtech.centrallib.req.WeatherDelReq;
import com.rjtech.centrallib.req.WeatherFilterReq;
import com.rjtech.centrallib.req.WeatherReq;
import com.rjtech.centrallib.req.costCodeFilterReq;
import com.rjtech.centrallib.resp.AddressResp;
import com.rjtech.centrallib.resp.AssetCategoryResp;
import com.rjtech.centrallib.resp.AssetMaintenanceCategoryResp;
import com.rjtech.centrallib.resp.AttendancePlantStatusResp;
import com.rjtech.centrallib.resp.CentLibPlantRepairsOnLoadResp;
import com.rjtech.centrallib.resp.CmpCurrentProjsResp;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.ContactsResp;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.EmpWageResp;
import com.rjtech.centrallib.resp.LeaveCodeResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.centrallib.resp.PlantServiceClassResp;
import com.rjtech.centrallib.resp.ProcureCatgOnLoadResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.centrallib.resp.ProcureResp;
import com.rjtech.centrallib.resp.ProcurementCompanyResp;
import com.rjtech.centrallib.resp.RegisterOnLoadResp;
import com.rjtech.centrallib.resp.ServiceResp;
import com.rjtech.centrallib.resp.StockResp;
import com.rjtech.centrallib.resp.WeatherResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjectResp;

import com.rjtech.centrallib.req.TangibleClassGetReq;
import com.rjtech.centrallib.req.TangibleClassSaveReq;
import com.rjtech.centrallib.req.TangibleClassDelReq;
import com.rjtech.centrallib.resp.TangibleClassResp;
import com.rjtech.centrallib.req.TangibleClassFilterReq;

import com.rjtech.centrallib.req.CountryProvinceCodeSaveReq;
import com.rjtech.centrallib.req.CountryProvinceCodeGetReq;
import com.rjtech.centrallib.req.CountryProvinceCodeDeactiveReq;
import com.rjtech.centrallib.resp.CountryProvinceCodeResp;

import com.rjtech.centrallib.req.FinanceCenterDeactiveReq;
import com.rjtech.centrallib.req.FinanceCenterSaveReq;
import com.rjtech.centrallib.req.FinanceCenterCodeGetReq;
import com.rjtech.centrallib.req.FinanceCenterFilterReq;
import com.rjtech.centrallib.resp.FinanceCenterResp;
import com.rjtech.centrallib.req.TaxRatesRulesCodeDtlSaveReq;
import com.rjtech.centrallib.resp.TaxRatesRulesCodeDtlResp;

import com.rjtech.centrallib.resp.CmpBankDetailsResp;
import com.rjtech.centrallib.req.CompanyBankSaveReq;
import com.rjtech.centrallib.req.BankDelReq;
public interface MWCentralLibService {
	
	  

    MeasureUnitResp getMeasurements(MesureFilterReq mesureFilterReq);

    MeasureUnitResp getMeasuresByProcureType(MesureFilterReq mesureFilterReq);

    MeasureUnitResp saveMeasurements(MeasureUnitReq measureUnitReq);

    MeasureUnitResp deleteMeasurements(MeasureDelReq measureDelReq);

    WeatherResp getWeatherDetails(WeatherFilterReq weatherFilterReq);

    WeatherResp saveWeatherDetails(WeatherReq weatherReq);

    WeatherResp deleteWeatherDetails(WeatherDelReq weatherDelReq);

    EmpClassesResp getEmpClasses(EmpClassFilterReq empClassFilterReq);

    EmpClassesResp saveEmpClasses(EmpClassesSaveReq empClassesSaveReq);

    EmpClassesResp deleteEmpClasses(EmpClassDelReq empClassDelReq);

    CompanyResp getCompanies(CompanyFilterReq companyFilterReq);

    ProcurementCompanyResp getCompaniesDetailsByCmpIds(CompanyGetReq companyGetReq);

    ProcurementCompanyResp getCompaniesWithDefaultAddressAndContact(CompanyFilterReq companyFilterReq);

    CompanyResp getCompanyDetails(CompanyGetReq companyGetReq);

    CompanyResp saveCompanyDetails(CompanySaveReq companySaveReq);

    CompanyResp deleteCompanies(CompanyDelReq companyDelReq);

    AddressResp saveAddress(AddressSaveReq addressSaveReq);

    AddressResp deleteAddress(AddressDelReq addressDelReq);

    ContactsResp saveContacts(ContactsSaveReq contactsSaveReq);

    ContactsResp deleteContacts(ContactDelReq contactDelReq);

    CmpCurrentProjsResp deleteCurrentProjs(CompanyProjDelReq companyProjDelReq);

    ProjectResp getProjectsByClient(ProjGetReq projGetReq);

    CmpCurrentProjsResp saveCompanyCurrentProjs(CompanyProjSaveReq companyProjSaveReq);

    ResponseEntity<Void> moveToCmpCloseProjs(CompanyProjSaveReq companyProjSaveReq);

    StockResp getStock(StockFilterReq stockFilterReq);

    StockResp saveStock(StockSaveReq stockSaveReq);

    StockResp deleteStock(StockDelReq stockDelReq);

    ProcureResp getprocures(ProcureFilterReq procureFilterReq);

    ProcureResp saveProcures(ProcureSaveReq ProcureSaveReq);

    ProcureResp deleteProcures(ProcureDelReq procureDelReq);

    EmpWageResp getEmpWages(EmpWagesFilterReq empWagesFilterReq);

    EmpWageResp saveEmpWages(EmpWageSaveReq empWageSaveReq);

    EmpWageResp deleteEmpWages(EmpWageDelReq empWagesDelReq);

    CostCodeResp getCostCodes(costCodeFilterReq costCodeFilterReq);

    CostCodeResp saveCostCodes(CostCodeSaveReq costCodeSaveReq);

    CostCodeResp deleteCostCodes(CostCodeDelReq costCodeDelReq);

    ProcureCatgResp getProcureCatgs(ProcureCatgFilterReq procureCatgFilterReq);

    ProcureCatgResp saveProcureCatgs(ProcureCatgSaveReq procureCatgSaveReq);

    ProcureCatgResp deleteProcureCatgs(ProcureCatgDelReq procureCatgDelReq);

    PlantClassResp getPlantClasses(PlantClassFilterReq plantClassFilterReq);

    PlantClassResp savePlantClasses(PlantClassSavereq plantClassSavereq);

    PlantClassResp deletePlantClasses(PlantClassDelReq plantClassDelReq);

    ServiceResp getServiceClasses(ServiceFiltterReq serviceFiltterReq);

    ServiceResp saveServiceClasses(ServiceSaveReq serviceSaveReq);

    ServiceResp deleteServiceClasses(ServiceClassDelReq serviceClassDelReq);

    ContactsResp getContacts(CompanyGetReq userReq);

    AddressResp getAddress(CompanyGetReq userReq);

    CmpCurrentProjsResp getCmpCurrentProjs(CompanyGetReq userReq);

    ProcureCatgOnLoadResp onLoadDataForProcureCatg(OnLoadFilterReq onLoadFilterReq);

    PlantServiceClassResp getPlantServiceClass(PlantServiceClassGetReq plantServiceClassGetReq);

    PlantServiceClassResp savePlantServiceClass(PlantServiceClassSaveReq plantServiceClassSaveReq);

    CentLibPlantRepairsOnLoadResp getPlantServiceClassItemsOnly(RegisterOnLoadReq registerOnLoadReq);

    PlantServiceClassResp deactivatePlantServiceClass(PlantServiceClassDeactivateReq plantServiceClassDeactivateReq);

    Map<Long, LabelKeyTO> getPlantClasses();

    RegisterOnLoadResp getRegisterOnLoadCmpCatgProCatgClass(RegisterOnLoadReq registerOnLoadReq);

    CentLibPlantRepairsOnLoadResp getPlantServiceMaterialClassMap(RegisterOnLoadReq registerOnLoadReq);

    MaterialClassResp getMaterialGroups(MaterialClassGetReq MaterialClassGetReq);

    MaterialClassResp getMaterialItems(MaterialClassGetReq materialClassGetReq);

    MaterialClassResp saveMaterialGroups(MaterialClassSaveReq materialClassSaveReq);

    MaterialClassResp deleteMaterialGroups(MaterialClassDelReq materialClassDelReq);

    AssetCategoryResp getAssetCategory(AssetCategoryGetReq assetCategoryGetReq);

    AssetCategoryResp saveAssetCategory(AssetCategorySaveReq assetCategorySaveReq);

    AssetCategoryResp deleteAssetCategory(AssetCategoryDelReq assetCategoryDelReq);

    AssetMaintenanceCategoryResp getAssetMaintenanceCategory(
            AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq);

    AssetMaintenanceCategoryResp saveAssetMaintenanceCategory(
            AssetMaintenanceCategorySaveReq assetMaintenanceCategorySaveReq);

    AssetMaintenanceCategoryResp deleteAssetMaintenanceCategory(
            AssetMaintenanceCategoryDelReq assetMaintenanceCategoryDelReq);

    MaterialClassResp getCentralMaterial(MaterialClassFilterReq materialClassFilterReq);

    TangibleClassResp getTangibleGroups(TangibleClassGetReq tangibleClassGetReq);
    TangibleClassResp getTangibleItems(TangibleClassGetReq tangibleClassGetReq);
    TangibleClassResp saveTangibleGroups(TangibleClassSaveReq tangibleClassSaveReq);
    TangibleClassResp deleteTangibleGroups(TangibleClassDelReq tangibleClassDelReq);
    TangibleClassResp getCentralTangible(TangibleClassFilterReq tangibleClassFilterReq);
    
    CountryProvinceCodeResp saveCountryProvinceCodes(CountryProvinceCodeSaveReq countryProvinceCodeSaveReq);
    CountryProvinceCodeResp getCountryProvinceCodes(CountryProvinceCodeGetReq countryProvinceCodeGetReq);
    CountryProvinceCodeResp deactivateCountryProvinceCodes(CountryProvinceCodeDeactiveReq countryProvinceCodeDeactiveReq);
    
    FinanceCenterResp getfinanceCenterRecords(FinanceCenterCodeGetReq financeCenterCodeGetReq);
    FinanceCenterResp savefinanceCenterRecords(FinanceCenterSaveReq financeCenterSaveReq);
    FinanceCenterResp deactivatefinanceCenterRecords(FinanceCenterDeactiveReq financeCenterDeactiveReq);
    FinanceCenterResp getEmployeeTypes(FinanceCenterCodeGetReq financeCenterCodeGetReq);
    FinanceCenterResp getUnitOfRate(FinanceCenterFilterReq financeCenterFilterReq);
    
    TaxRatesRulesCodeDtlResp saveTaxAndRules(TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq);
    
    CmpBankDetailsResp saveCompanyBankDetails(CompanyBankSaveReq companyBankSaveReq);
    
    CmpBankDetailsResp getBank(CompanyGetReq userReq);
    
    CmpBankDetailsResp deleteBank(BankDelReq bankDelReq);
}
