package com.rjtech.mw.service.impl.centlib;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.constants.CentralLibraryConstants;
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
import com.rjtech.centrallib.req.WeatherDelReq;
import com.rjtech.centrallib.req.WeatherFilterReq;
import com.rjtech.centrallib.req.WeatherReq;
import com.rjtech.centrallib.req.costCodeFilterReq;
import com.rjtech.centrallib.resp.AddressResp;
import com.rjtech.centrallib.resp.AssetCategoryResp;
import com.rjtech.centrallib.resp.AssetMaintenanceCategoryResp;
import com.rjtech.centrallib.resp.CentLibPlantRepairsOnLoadResp;
import com.rjtech.centrallib.resp.CmpCurrentProjsResp;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.ContactsResp;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.EmpWageResp;
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
import com.rjtech.common.utils.AppUtils;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.mw.controller.central.handler.PlantClassHandler;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjectResp;
import com.rjtech.rjs.core.annotations.RJSService;

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
@Service(value = "mwCentralLiblService")
@RJSService(modulecode = "mwCentralLiblService")
@Transactional
public class MWCentralLibServiceImpl extends RestConfigServiceImpl implements MWCentralLibService {

    public MeasureUnitResp getMeasurements(MesureFilterReq mesureFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_MEASUREMENTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(mesureFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), MeasureUnitResp.class);
    }

    public MeasureUnitResp getMeasuresByProcureType(MesureFilterReq mesureFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_MEASURES_BY_PROCURE_TYPE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(mesureFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), MeasureUnitResp.class);
    }

    public MeasureUnitResp saveMeasurements(MeasureUnitReq measureUnitReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_MEASUREMENTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(measureUnitReq));
        return AppUtils.fromJson(strResponse.getBody(), MeasureUnitResp.class);
    }

    public MeasureUnitResp deleteMeasurements(MeasureDelReq measureDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_MEASUREMENTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(measureDelReq));
        return AppUtils.fromJson(strResponse.getBody(), MeasureUnitResp.class);
    }

    public WeatherResp getWeatherDetails(WeatherFilterReq weatherFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_WEATHERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(weatherFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), WeatherResp.class);
    }

    public WeatherResp saveWeatherDetails(WeatherReq weatherReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_WEATHERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(weatherReq));
        return AppUtils.fromJson(strResponse.getBody(), WeatherResp.class);
    }

    public WeatherResp deleteWeatherDetails(WeatherDelReq weatherDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_WEATHERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(weatherDelReq));
        return AppUtils.fromJson(strResponse.getBody(), WeatherResp.class);
    }

    public EmpClassesResp getEmpClasses(EmpClassFilterReq empClassFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_EMPCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empClassFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpClassesResp.class);
    }

    public EmpClassesResp saveEmpClasses(EmpClassesSaveReq empClassesSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_EMPCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empClassesSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpClassesResp.class);
    }

    public EmpClassesResp deleteEmpClasses(EmpClassDelReq empClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_EMPCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpClassesResp.class);
    }

    public CompanyResp getCompanies(CompanyFilterReq companyFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COMPANIES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), CompanyResp.class);
    }

    public ProcurementCompanyResp getCompaniesDetailsByCmpIds(CompanyGetReq companyGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COMPANIES_DETAILS_CMP_IDS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcurementCompanyResp.class);
    }

    public ProcurementCompanyResp getCompaniesWithDefaultAddressAndContact(CompanyFilterReq companyFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL
                + CentralLibraryConstants.GET_COMPANIES_WITHDEFAULT_ADDRESS_AND_CONTACT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcurementCompanyResp.class);
    }

    public CompanyResp getCompanyDetails(CompanyGetReq companyGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COMPANY_DETAILS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyGetReq));
        return AppUtils.fromJson(strResponse.getBody(), CompanyResp.class);
    }

    public CompanyResp saveCompanyDetails(CompanySaveReq companySaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_COMPANY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companySaveReq));
        return AppUtils.fromJson(strResponse.getBody(), CompanyResp.class);
    }

    public CompanyResp deleteCompanies(CompanyDelReq companyDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_COMPANIES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyDelReq));
        return AppUtils.fromJson(strResponse.getBody(), CompanyResp.class);
    }

    public AddressResp saveAddress(AddressSaveReq addressSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_ADDRESS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(addressSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), AddressResp.class);
    }

    public AddressResp deleteAddress(AddressDelReq addressDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_ADDRESS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(addressDelReq));
        return AppUtils.fromJson(strResponse.getBody(), AddressResp.class);
    }

    public ContactsResp saveContacts(ContactsSaveReq contactsSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_CONTACTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(contactsSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ContactsResp.class);
    }

    public ContactsResp deleteContacts(ContactDelReq contactDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_CONTACTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(contactDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ContactsResp.class);
    }

    public CmpCurrentProjsResp deleteCurrentProjs(CompanyProjDelReq companyProjDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_COMPANY_CURRENT_PROJS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyProjDelReq));
        return AppUtils.fromJson(strResponse.getBody(), CmpCurrentProjsResp.class);
    }

    public ProjectResp getProjectsByClient(ProjGetReq projGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PROJECTS_BY_CLIENT);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjectResp.class);
    }

    public CmpCurrentProjsResp saveCompanyCurrentProjs(CompanyProjSaveReq companyProjSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_COMPANY_CURRENT_PROJS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyProjSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), CmpCurrentProjsResp.class);
    }

    public ResponseEntity<Void> moveToCmpCloseProjs(CompanyProjSaveReq companyProjSaveReq) {
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.MOVE_TO_COMPANY_CLOSED_PROJS);
        return constructPOSTRestTemplate(url, AppUtils.toJson(companyProjSaveReq), Void.class);
    }

    public StockResp getStock(StockFilterReq stockFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_STOCKS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(stockFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), StockResp.class);
    }

    public StockResp saveStock(StockSaveReq stockSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_STOCKS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(stockSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), StockResp.class);
    }

    public StockResp deleteStock(StockDelReq stockDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_STOCK);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(stockDelReq));
        return AppUtils.fromJson(strResponse.getBody(), StockResp.class);
    }

    public ProcureResp getprocures(ProcureFilterReq procureFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PROCURES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(procureFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureResp.class);
    }

    public ProcureResp saveProcures(ProcureSaveReq ProcureSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_PROCURES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(ProcureSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureResp.class);
    }

    public ProcureResp deleteProcures(ProcureDelReq procureDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_PROCURES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(procureDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureResp.class);
    }

    public EmpWageResp getEmpWages(EmpWagesFilterReq empWagesFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_EMPWAGES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empWagesFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpWageResp.class);
    }

    public EmpWageResp saveEmpWages(EmpWageSaveReq empWageSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_EMPWAGES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empWageSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpWageResp.class);
    }

    public EmpWageResp deleteEmpWages(EmpWageDelReq empWagesDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_EMPWAGES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(empWagesDelReq));
        return AppUtils.fromJson(strResponse.getBody(), EmpWageResp.class);
    }

    public CostCodeResp getCostCodes(costCodeFilterReq costCodeFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COSTCODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(costCodeFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), CostCodeResp.class);
    }

    public CostCodeResp saveCostCodes(CostCodeSaveReq costCodeSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_COSTCODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(costCodeSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), CostCodeResp.class);
    }

    public CostCodeResp deleteCostCodes(CostCodeDelReq costCodeDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_COSTCODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(costCodeDelReq));
        return AppUtils.fromJson(strResponse.getBody(), CostCodeResp.class);
    }

    public ProcureCatgResp getProcureCatgs(ProcureCatgFilterReq procureCatgFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PROCURECATGS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(procureCatgFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureCatgResp.class);
    }

    public ProcureCatgResp saveProcureCatgs(ProcureCatgSaveReq procureCatgSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_PROCURECATGS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(procureCatgSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureCatgResp.class);
    }

    public ProcureCatgResp deleteProcureCatgs(ProcureCatgDelReq procureCatgDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_PROCURECATGS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(procureCatgDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureCatgResp.class);
    }

    public PlantClassResp getPlantClasses(PlantClassFilterReq plantClassFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANTCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantClassFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), PlantClassResp.class);
    }

    public PlantClassResp savePlantClasses(PlantClassSavereq plantClassSavereq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_PLANTCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantClassSavereq));
        return AppUtils.fromJson(strResponse.getBody(), PlantClassResp.class);
    }

    public PlantClassResp deletePlantClasses(PlantClassDelReq plantClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_PLANTCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), PlantClassResp.class);
    }

    public ServiceResp getServiceClasses(ServiceFiltterReq serviceFiltterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_SERVICECLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(serviceFiltterReq));
        return AppUtils.fromJson(strResponse.getBody(), ServiceResp.class);
    }

    public ServiceResp saveServiceClasses(ServiceSaveReq serviceSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_SERVICECLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(serviceSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ServiceResp.class);
    }

    public ServiceResp deleteServiceClasses(ServiceClassDelReq serviceClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_SERVICECLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(serviceClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), ServiceResp.class);
    }

    public ContactsResp getContacts(CompanyGetReq companyGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_CONTACTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ContactsResp.class);
    }

    public AddressResp getAddress(CompanyGetReq companyGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_ADDRESS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyGetReq));
        return AppUtils.fromJson(strResponse.getBody(), AddressResp.class);
    }

    public CmpCurrentProjsResp getCmpCurrentProjs(CompanyGetReq companyGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_CMPCURRENTPROJS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyGetReq));
        return AppUtils.fromJson(strResponse.getBody(), CmpCurrentProjsResp.class);
    }

    public ProcureCatgOnLoadResp onLoadDataForProcureCatg(OnLoadFilterReq onLoadFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.ONLOAD_DATA_FOR_PROCURECATG);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(onLoadFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), ProcureCatgOnLoadResp.class);
    }

    public PlantServiceClassResp getPlantServiceClass(PlantServiceClassGetReq plantServiceClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANT_CLASS_SERVICE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantServiceClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), PlantServiceClassResp.class);
    }

    public PlantServiceClassResp savePlantServiceClass(PlantServiceClassSaveReq plantServiceClassSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_PLANT_CLASS_SERVICE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantServiceClassSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), PlantServiceClassResp.class);
    }

    public CentLibPlantRepairsOnLoadResp getPlantServiceClassItemsOnly(RegisterOnLoadReq registerOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANT_CLASS_SERVICE_ITENS_ONLY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(registerOnLoadReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibPlantRepairsOnLoadResp.class);
    }

    public PlantServiceClassResp deactivatePlantServiceClass(
            PlantServiceClassDeactivateReq plantServiceClassDeactivateReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DEACTIVATE_PLANT_CLASS_SERVICE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantServiceClassDeactivateReq));
        return AppUtils.fromJson(strResponse.getBody(), PlantServiceClassResp.class);
    }

    public Map<Long, LabelKeyTO> getPlantClasses() {
        PlantClassFilterReq plantClassFilterReq = new PlantClassFilterReq();
        plantClassFilterReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANTCLASSES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(plantClassFilterReq));
        PlantClassResp plantClassResp = AppUtils.fromJson(strResponse.getBody(), PlantClassResp.class);
        return PlantClassHandler.getLableKeyTO(plantClassResp.getPlantClassTOs());
    }

    public RegisterOnLoadResp getRegisterOnLoadCmpCatgProCatgClass(RegisterOnLoadReq registerOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_REGISTER_ONLOAD_CMP_PROCATG_CLASS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(registerOnLoadReq));
        RegisterOnLoadResp resp = AppUtils.fromJson(strResponse.getBody(), RegisterOnLoadResp.class);
        return resp;
    }

    public CentLibPlantRepairsOnLoadResp getPlantServiceMaterialClassMap(RegisterOnLoadReq registerOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_PLANT_SERVICE_MATERIAL_CLASS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(registerOnLoadReq));
        return AppUtils.fromJson(strResponse.getBody(), CentLibPlantRepairsOnLoadResp.class);
    }

    public MaterialClassResp getMaterialGroups(MaterialClassGetReq MaterialClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_MATERIAL_GROUPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(MaterialClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), MaterialClassResp.class);
    }

    public MaterialClassResp getMaterialItems(MaterialClassGetReq MaterialClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_MATERIAL_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(MaterialClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), MaterialClassResp.class);
    }

    public MaterialClassResp saveMaterialGroups(MaterialClassSaveReq materialClassSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_MATERIAL_GROUPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(materialClassSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), MaterialClassResp.class);
    }

    public MaterialClassResp deleteMaterialGroups(MaterialClassDelReq materialClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_MATERIAL_GROUPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(materialClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), MaterialClassResp.class);
    }

    public AssetCategoryResp getAssetCategory(AssetCategoryGetReq assetCategoryGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_ASSET_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(assetCategoryGetReq));
        return AppUtils.fromJson(strResponse.getBody(), AssetCategoryResp.class);
    }

    public AssetCategoryResp saveAssetCategory(AssetCategorySaveReq assetCategorySaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_ASSET_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(assetCategorySaveReq));
        return AppUtils.fromJson(strResponse.getBody(), AssetCategoryResp.class);
    }

    public AssetCategoryResp deleteAssetCategory(AssetCategoryDelReq assetCategoryDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_ASSET_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(assetCategoryDelReq));
        return AppUtils.fromJson(strResponse.getBody(), AssetCategoryResp.class);
    }

    public AssetMaintenanceCategoryResp getAssetMaintenanceCategory(
            AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_ASSET_MAINTENANCE_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(assetMaintenanceCategoryGetReq));
        return AppUtils.fromJson(strResponse.getBody(), AssetMaintenanceCategoryResp.class);
    }

    public AssetMaintenanceCategoryResp saveAssetMaintenanceCategory(
            AssetMaintenanceCategorySaveReq assetMaintenanceCategorySaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_ASSET_MAINTENANCE_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(assetMaintenanceCategorySaveReq));
        return AppUtils.fromJson(strResponse.getBody(), AssetMaintenanceCategoryResp.class);
    }

    public AssetMaintenanceCategoryResp deleteAssetMaintenanceCategory(
            AssetMaintenanceCategoryDelReq assetMaintenanceCategoryDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_ASSET_MAINTENANCE_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(assetMaintenanceCategoryDelReq));
        return AppUtils.fromJson(strResponse.getBody(), AssetMaintenanceCategoryResp.class);
    }

    public MaterialClassResp getCentralMaterial(MaterialClassFilterReq materialClassFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_CENTRALMATERIAL);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(materialClassFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), MaterialClassResp.class);
    }
    
    public TangibleClassResp getTangibleGroups(TangibleClassGetReq tangibleClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_TANGIBLE_GROUPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(tangibleClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), TangibleClassResp.class);
    }

    public TangibleClassResp getTangibleItems(TangibleClassGetReq tangibleClassGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_TANGIBLE_ITEMS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(tangibleClassGetReq));
        return AppUtils.fromJson(strResponse.getBody(), TangibleClassResp.class);
    }
    
    public TangibleClassResp saveTangibleGroups(TangibleClassSaveReq tangibleClassSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_TANGIBLE_GROUPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(tangibleClassSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), TangibleClassResp.class);
    }
    
    public TangibleClassResp deleteTangibleGroups(TangibleClassDelReq tangibleClassDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_TANGIBLE_GROUPS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(tangibleClassDelReq));
        return AppUtils.fromJson(strResponse.getBody(), TangibleClassResp.class);
    }
    
    public TangibleClassResp getCentralTangible(TangibleClassFilterReq tangibleClassFilterReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_CENTRAL_TANGIBLE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(tangibleClassFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), TangibleClassResp.class);
    }
    
    public CountryProvinceCodeResp saveCountryProvinceCodes(CountryProvinceCodeSaveReq countryProvinceCodeSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_COUNTRY_PROVISION_CODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countryProvinceCodeSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryProvinceCodeResp.class);
    }
    
    public CountryProvinceCodeResp getCountryProvinceCodes(CountryProvinceCodeGetReq countryProvinceCodeGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_COUNTRY_PROVISION_CODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countryProvinceCodeGetReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryProvinceCodeResp.class);
    }
    
    public CountryProvinceCodeResp deactivateCountryProvinceCodes(CountryProvinceCodeDeactiveReq countryProvinceCodeDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DEACTIVATE_COUNTRY_PROVISION_CODES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(countryProvinceCodeDeactiveReq));
        return AppUtils.fromJson(strResponse.getBody(), CountryProvinceCodeResp.class);
    }
    
    public FinanceCenterResp getfinanceCenterRecords(FinanceCenterCodeGetReq financeCenterCodeGetReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("MW service impl=====getfinanceCenterRecords");
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_FINANCE_CENTER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(financeCenterCodeGetReq));
        return AppUtils.fromJson(strResponse.getBody(), FinanceCenterResp.class);
    }
    
    public FinanceCenterResp savefinanceCenterRecords(FinanceCenterSaveReq financeCenterSaveReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_FINANCE_CENTER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(financeCenterSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), FinanceCenterResp.class);
    }
    
    public FinanceCenterResp deactivatefinanceCenterRecords(FinanceCenterDeactiveReq financeCenterDeactiveReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("MW service impl");
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DEACTIVATE_FINANCE_CENTER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(financeCenterDeactiveReq));
        return AppUtils.fromJson(strResponse.getBody(), FinanceCenterResp.class);
    }
    
    public FinanceCenterResp getEmployeeTypes(FinanceCenterCodeGetReq financeCenterCodeGetReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("MW service impl=====getEmployeeTypes");
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_EMPLOYEE_TYPES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(financeCenterCodeGetReq));
        //System.out.println("MW service impl=====strResponse"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), FinanceCenterResp.class);
    }
    
    public FinanceCenterResp getUnitOfRate(FinanceCenterFilterReq financeCenterFilterReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("MW service impl=====getUnitOfRate");
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_UNT_OF_RATE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(financeCenterFilterReq));
        return AppUtils.fromJson(strResponse.getBody(), FinanceCenterResp.class);
    }
    
    public TaxRatesRulesCodeDtlResp saveTaxAndRules(TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        //System.out.println("MW service impl=====saveTaxAndRules");
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_TAX_AND_RULES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(taxRatesRulesCodeDtlSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), TaxRatesRulesCodeDtlResp.class);
    }
    
    public CmpBankDetailsResp saveCompanyBankDetails(CompanyBankSaveReq companyBankSaveReq) {
        ResponseEntity<String> strResponse = null;
        System.out.println("companyBankSaveReqqqqqqqqqqqqqqqqqqqqq"+companyBankSaveReq);
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.SAVE_COMPANY_BANK_DETAILS);
        System.out.println("companyBankSaveReqqqqqqqqqqqqqqqqqqqqq##############"+url);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyBankSaveReq));
        System.out.println("strResponseeeeeeeeeeeeeeeeeeeeeeeeeebankkkk"+strResponse);
        System.out.println("strResponseeeeeeeeeeeeeeeeeeeeeeeeee21212"+AppUtils.toJson(companyBankSaveReq));
        System.out.println("strResponse.getBody()"+strResponse.getBody());
        return AppUtils.fromJson(strResponse.getBody(), CmpBankDetailsResp.class);
    }
   
    public CmpBankDetailsResp getBank(CompanyGetReq companyGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CentralLibraryConstants.PARH_URL + CentralLibraryConstants.GET_BANK_DETAILS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(companyGetReq));
        System.out.println("strResponseeeeeeeeeeeeeeegetttttttttttt"+strResponse);
        return AppUtils.fromJson(strResponse.getBody(), CmpBankDetailsResp.class);
    }
    
    public CmpBankDetailsResp deleteBank(BankDelReq bankDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                CentralLibraryConstants.PARH_URL + CentralLibraryConstants.DELETE_BANK_DETAILS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(bankDelReq));
        return AppUtils.fromJson(strResponse.getBody(), CmpBankDetailsResp.class);
    }


}
