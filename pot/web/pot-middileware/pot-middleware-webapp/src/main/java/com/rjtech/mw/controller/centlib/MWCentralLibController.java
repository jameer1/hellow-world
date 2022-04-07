package com.rjtech.mw.controller.centlib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.constants.CentralLibraryConstants;
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
import com.rjtech.centrallib.resp.ServiceResp;
import com.rjtech.centrallib.resp.StockResp;
import com.rjtech.centrallib.resp.WeatherResp;
import com.rjtech.mw.service.centlib.MWCentralLibService;
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

import com.rjtech.common.dto.FinanceCenterRecordTo;

import com.rjtech.centrallib.resp.CmpBankDetailsResp;
import com.rjtech.centrallib.req.CompanyBankSaveReq;
import com.rjtech.centrallib.req.BankDelReq;
@RestController
@RequestMapping(CentralLibraryConstants.PARH_URL)
public class MWCentralLibController {

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @RequestMapping(value = CentralLibraryConstants.GET_MEASUREMENTS, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> getMeasurements(@RequestBody MesureFilterReq mesureFilterReq) {
        return new ResponseEntity<MeasureUnitResp>(mwCentralLiblService.getMeasurements(mesureFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MEASURES_BY_PROCURE_TYPE, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> getMeasuresByProcureType(@RequestBody MesureFilterReq mesureFilterReq) {
        return new ResponseEntity<MeasureUnitResp>(mwCentralLiblService.getMeasuresByProcureType(mesureFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_CENTRALMATERIAL, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getCentralMaterial(
            @RequestBody MaterialClassFilterReq materialClassFilterReq) {
        return new ResponseEntity<MaterialClassResp>(mwCentralLiblService.getCentralMaterial(materialClassFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_MEASUREMENTS, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> saveMeasurements(@RequestBody MeasureUnitReq measureUnitReq) {
        return new ResponseEntity<MeasureUnitResp>(mwCentralLiblService.saveMeasurements(measureUnitReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_MEASUREMENTS, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> deleteMeasurements(@RequestBody MeasureDelReq measureDelReq) {
        return new ResponseEntity<MeasureUnitResp>(mwCentralLiblService.deleteMeasurements(measureDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_WEATHERS, method = RequestMethod.POST)
    public ResponseEntity<WeatherResp> getWeatherDetails(@RequestBody WeatherFilterReq weatherFilterReq) {
        return new ResponseEntity<WeatherResp>(mwCentralLiblService.getWeatherDetails(weatherFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_WEATHERS, method = RequestMethod.POST)
    public ResponseEntity<WeatherResp> saveWeatherDetails(@RequestBody WeatherReq weatherReq) {
        return new ResponseEntity<WeatherResp>(mwCentralLiblService.saveWeatherDetails(weatherReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_WEATHERS, method = RequestMethod.POST)
    public ResponseEntity<WeatherResp> deleteWeatherDetails(@RequestBody WeatherDelReq weatherDelReq) {
        return new ResponseEntity<WeatherResp>(mwCentralLiblService.deleteWeatherDetails(weatherDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMPCLASSES, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> getEmpClasses(@RequestBody EmpClassFilterReq empClassFilterReq) {
        return new ResponseEntity<EmpClassesResp>(mwCentralLiblService.getEmpClasses(empClassFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_EMPCLASSES, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> saveEmpClasses(@RequestBody EmpClassesSaveReq empClassesSaveReq) {
        return new ResponseEntity<EmpClassesResp>(mwCentralLiblService.saveEmpClasses(empClassesSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_EMPCLASSES, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> deleteEmpClasses(@RequestBody EmpClassDelReq empClassDelReq) {
        return new ResponseEntity<EmpClassesResp>(mwCentralLiblService.deleteEmpClasses(empClassDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> getCompanies(@RequestBody CompanyFilterReq companyFilterReq) {
        return new ResponseEntity<CompanyResp>(mwCentralLiblService.getCompanies(companyFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES_DETAILS_CMP_IDS, method = RequestMethod.POST)
    public ResponseEntity<ProcurementCompanyResp> getCompaniesWithDefaultAddressAndContact(
            @RequestBody CompanyGetReq companyGetReq) {
        return new ResponseEntity<ProcurementCompanyResp>(
                mwCentralLiblService.getCompaniesDetailsByCmpIds(companyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES_WITHDEFAULT_ADDRESS_AND_CONTACT, method = RequestMethod.POST)
    public ResponseEntity<ProcurementCompanyResp> getCompaniesWithDefaultAddressAndContact(
            @RequestBody CompanyFilterReq companyFilterReq) {
        return new ResponseEntity<ProcurementCompanyResp>(
                mwCentralLiblService.getCompaniesWithDefaultAddressAndContact(companyFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> getCompanyDetails(@RequestBody CompanyGetReq companyGetReq) {
        return new ResponseEntity<CompanyResp>(mwCentralLiblService.getCompanyDetails(companyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COMPANY, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> saveCompanyDetails(@RequestBody CompanySaveReq companySaveReq) {
        return new ResponseEntity<CompanyResp>(mwCentralLiblService.saveCompanyDetails(companySaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> deleteCompanies(@RequestBody CompanyDelReq companyDelReq) {
        return new ResponseEntity<CompanyResp>(mwCentralLiblService.deleteCompanies(companyDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_ADDRESS, method = RequestMethod.POST)
    public ResponseEntity<AddressResp> saveAddress(@RequestBody AddressSaveReq addressSaveReq) {
        return new ResponseEntity<AddressResp>(mwCentralLiblService.saveAddress(addressSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_ADDRESS, method = RequestMethod.POST)
    public ResponseEntity<AddressResp> deleteAddress(@RequestBody AddressDelReq deleteAddress) {
        return new ResponseEntity<AddressResp>(mwCentralLiblService.deleteAddress(deleteAddress), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_CONTACTS, method = RequestMethod.POST)
    public ResponseEntity<ContactsResp> saveContacts(@RequestBody ContactsSaveReq contactsSaveReq) {
        return new ResponseEntity<ContactsResp>(mwCentralLiblService.saveContacts(contactsSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_CONTACTS, method = RequestMethod.POST)
    public ResponseEntity<ContactsResp> deleteContacts(@RequestBody ContactDelReq contactDelReq) {
        return new ResponseEntity<ContactsResp>(mwCentralLiblService.deleteContacts(contactDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_COMPANY_CURRENT_PROJS, method = RequestMethod.POST)
    public ResponseEntity<CmpCurrentProjsResp> deleteCurrentProjs(@RequestBody CompanyProjDelReq companyProjDelReq) {
        return new ResponseEntity<CmpCurrentProjsResp>(mwCentralLiblService.deleteCurrentProjs(companyProjDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROJECTS_BY_CLIENT, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> getProjectsByClient(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<ProjectResp>(mwCentralLiblService.getProjectsByClient(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COMPANY_CURRENT_PROJS, method = RequestMethod.POST)
    public ResponseEntity<CmpCurrentProjsResp> saveCompanyCurrentProjs(
            @RequestBody CompanyProjSaveReq companyProjSaveReq) {
        return new ResponseEntity<CmpCurrentProjsResp>(mwCentralLiblService.saveCompanyCurrentProjs(companyProjSaveReq),
                HttpStatus.OK);

    }

    @PostMapping(value = CentralLibraryConstants.MOVE_TO_COMPANY_CLOSED_PROJS)
    public ResponseEntity<Void> moveToCmpClosProjs(@RequestBody CompanyProjSaveReq companyProjSaveReq) {
        mwCentralLiblService.moveToCmpCloseProjs(companyProjSaveReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_STOCKS, method = RequestMethod.POST)
    public ResponseEntity<StockResp> getStock(@RequestBody StockFilterReq stockFilterReq) {
        return new ResponseEntity<StockResp>(mwCentralLiblService.getStock(stockFilterReq), HttpStatus.OK);
    }

    /*
     * @RequestMapping(value =
     * CentralLibraryConstants.ONLOAD_DATA_FOR_STOCK_CATEGORY, method =
     * RequestMethod.POST) public ResponseEntity<StockCategoryOnLoadResp>
     * stockCategoryOnLoad(
     * 
     * @RequestBody OnLoadFilterReq cmpOnLoadFilterReq) {
     * 
     * StockCategoryOnLoadResp stockCategoryOnLoadResp = new
     * StockCategoryOnLoadResp(); List<String> categorys = new
     * ArrayList<String>(); for (StockCategory stockCategory :
     * StockCategory.values()) { categorys.add(stockCategory.getCategory()); }
     * return new
     * ResponseEntity<StockCategoryOnLoadResp>(stockCategoryOnLoadResp,
     * HttpStatus.OK); }
     */

    @RequestMapping(value = CentralLibraryConstants.SAVE_STOCKS, method = RequestMethod.POST)
    public ResponseEntity<StockResp> saveStock(@RequestBody StockSaveReq stockSaveReq) {

        return new ResponseEntity<StockResp>(mwCentralLiblService.saveStock(stockSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_STOCK, method = RequestMethod.POST)
    public ResponseEntity<StockResp> deleteStock(@RequestBody StockDelReq stockDelReq) {
        return new ResponseEntity<StockResp>(mwCentralLiblService.deleteStock(stockDelReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROCURES, method = RequestMethod.POST)
    public ResponseEntity<ProcureResp> getprocures(@RequestBody ProcureFilterReq procureFilterReq) {
        return new ResponseEntity<ProcureResp>(mwCentralLiblService.getprocures(procureFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PROCURES, method = RequestMethod.POST)
    public ResponseEntity<ProcureResp> saveProcures(@RequestBody ProcureSaveReq procureSaveReq) {
        return new ResponseEntity<ProcureResp>(mwCentralLiblService.saveProcures(procureSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_PROCURES, method = RequestMethod.POST)
    public ResponseEntity<ProcureResp> deleteProcures(@RequestBody ProcureDelReq procureDelReq) {
        return new ResponseEntity<ProcureResp>(mwCentralLiblService.deleteProcures(procureDelReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMPWAGES, method = RequestMethod.POST)
    public ResponseEntity<EmpWageResp> getEmpWages(@RequestBody EmpWagesFilterReq empWagesFilterReq) {
        return new ResponseEntity<EmpWageResp>(mwCentralLiblService.getEmpWages(empWagesFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_EMPWAGES, method = RequestMethod.POST)
    public ResponseEntity<EmpWageResp> saveEmpWages(@RequestBody EmpWageSaveReq empWageSaveReq) {
        return new ResponseEntity<EmpWageResp>(mwCentralLiblService.saveEmpWages(empWageSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_EMPWAGES, method = RequestMethod.POST)
    public ResponseEntity<EmpWageResp> deleteEmpWages(@RequestBody EmpWageDelReq empWagesDelReq) {
        return new ResponseEntity<EmpWageResp>(mwCentralLiblService.deleteEmpWages(empWagesDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> getCostCodes(@RequestBody costCodeFilterReq costCodeFilterReq) {
        return new ResponseEntity<CostCodeResp>(mwCentralLiblService.getCostCodes(costCodeFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> saveCostCodes(@RequestBody CostCodeSaveReq costCodeSaveReq) {
        return new ResponseEntity<CostCodeResp>(mwCentralLiblService.saveCostCodes(costCodeSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> deleteCostCodes(@RequestBody CostCodeDelReq costCodeDelReq) {
        return new ResponseEntity<CostCodeResp>(mwCentralLiblService.deleteCostCodes(costCodeDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROCURECATGS, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgResp> getProcureCatgs(@RequestBody ProcureCatgFilterReq procureCatgFilterReq) {
        return new ResponseEntity<ProcureCatgResp>(mwCentralLiblService.getProcureCatgs(procureCatgFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PROCURECATGS, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgResp> saveProcureCatgs(@RequestBody ProcureCatgSaveReq procureCatgSaveReq) {
        return new ResponseEntity<ProcureCatgResp>(mwCentralLiblService.saveProcureCatgs(procureCatgSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_PROCURECATGS, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgResp> deleteProcureCatgs(@RequestBody ProcureCatgDelReq procureCatgDelReq) {
        return new ResponseEntity<ProcureCatgResp>(mwCentralLiblService.deleteProcureCatgs(procureCatgDelReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANTCLASSES, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> getPlantClasses(@RequestBody PlantClassFilterReq plantClassFilterReq) {
        return new ResponseEntity<PlantClassResp>(mwCentralLiblService.getPlantClasses(plantClassFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PLANTCLASSES, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> savePlantClasses(@RequestBody PlantClassSavereq plantClassSavereq) {
        return new ResponseEntity<PlantClassResp>(mwCentralLiblService.savePlantClasses(plantClassSavereq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_PLANTCLASSES, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> deletePlantClasses(@RequestBody PlantClassDelReq plantClassDelReq) {
        return new ResponseEntity<PlantClassResp>(mwCentralLiblService.deletePlantClasses(plantClassDelReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_SERVICECLASSES, method = RequestMethod.POST)
    public ServiceResp getServiceClasses(@RequestBody ServiceFiltterReq serviceFiltterReq) {
        return mwCentralLiblService.getServiceClasses(serviceFiltterReq);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_SERVICECLASSES, method = RequestMethod.POST)
    public ResponseEntity<ServiceResp> saveServiceClasses(@RequestBody ServiceSaveReq serviceSaveReq) {
        return new ResponseEntity<ServiceResp>(mwCentralLiblService.saveServiceClasses(serviceSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_SERVICECLASSES, method = RequestMethod.POST)
    public ResponseEntity<ServiceResp> deleteServiceClasses(@RequestBody ServiceClassDelReq serviceClassDelReq) {
        return new ResponseEntity<ServiceResp>(mwCentralLiblService.deleteServiceClasses(serviceClassDelReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_CONTACTS, method = RequestMethod.POST)
    public ResponseEntity<ContactsResp> getContacts(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<ContactsResp>(mwCentralLiblService.getContacts(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_ADDRESS, method = RequestMethod.POST)
    public ResponseEntity<AddressResp> getAddress(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<AddressResp>(mwCentralLiblService.getAddress(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_CMPCURRENTPROJS, method = RequestMethod.POST)
    public ResponseEntity<CmpCurrentProjsResp> getCmpCurrentProjs(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<CmpCurrentProjsResp>(mwCentralLiblService.getCmpCurrentProjs(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.ONLOAD_DATA_FOR_PROCURECATG, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgOnLoadResp> onLoadDataForProcureCatg(
            @RequestBody OnLoadFilterReq onLoadFilterReq) {
        return new ResponseEntity<ProcureCatgOnLoadResp>(mwCentralLiblService.onLoadDataForProcureCatg(onLoadFilterReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASS_SERVICE, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceClassResp> getPlantServiceClass(
            @RequestBody PlantServiceClassGetReq plantServiceClassGetReq) {
        return new ResponseEntity<PlantServiceClassResp>(
                mwCentralLiblService.getPlantServiceClass(plantServiceClassGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PLANT_CLASS_SERVICE, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceClassResp> savePlantServiceClass(
            @RequestBody PlantServiceClassSaveReq plantServiceClassSaveReq) {

        return new ResponseEntity<PlantServiceClassResp>(
                mwCentralLiblService.savePlantServiceClass(plantServiceClassSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DEACTIVATE_PLANT_CLASS_SERVICE, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceClassResp> deactivatePlantServiceClass(
            @RequestBody PlantServiceClassDeactivateReq plantServiceClassDeactivateReq) {

        return new ResponseEntity<PlantServiceClassResp>(
                mwCentralLiblService.deactivatePlantServiceClass(plantServiceClassDeactivateReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_SERVICE_MATERIAL_CLASS, method = RequestMethod.POST)
    public ResponseEntity<CentLibPlantRepairsOnLoadResp> getPlantServiceMaterialClassMap(
            @RequestBody RegisterOnLoadReq registerOnLoadReq) {
        return new ResponseEntity<CentLibPlantRepairsOnLoadResp>(
                mwCentralLiblService.getPlantServiceMaterialClassMap(registerOnLoadReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MATERIAL_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getMaterialGroups(@RequestBody MaterialClassGetReq materialClassGetReq) {
        return new ResponseEntity<MaterialClassResp>(mwCentralLiblService.getMaterialGroups(materialClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MATERIAL_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getMaterialItems(@RequestBody MaterialClassGetReq materialClassGetReq) {
        return new ResponseEntity<MaterialClassResp>(mwCentralLiblService.getMaterialItems(materialClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_MATERIAL_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> saveMaterialGroups(@RequestBody MaterialClassSaveReq materialClassGetReq) {
        return new ResponseEntity<MaterialClassResp>(mwCentralLiblService.saveMaterialGroups(materialClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_MATERIAL_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> deleteMaterialGroups(
            @RequestBody MaterialClassDelReq materialClassDelReq) {

        return new ResponseEntity<MaterialClassResp>(mwCentralLiblService.deleteMaterialGroups(materialClassDelReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_ASSET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetCategoryResp> getAssetCategory(@RequestBody AssetCategoryGetReq assetCategoryGetReq) {
        return new ResponseEntity<AssetCategoryResp>(mwCentralLiblService.getAssetCategory(assetCategoryGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_ASSET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetCategoryResp> saveAssetCategory(@RequestBody AssetCategorySaveReq assetCategorySaveReq) {

        return new ResponseEntity<AssetCategoryResp>(mwCentralLiblService.saveAssetCategory(assetCategorySaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_ASSET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetCategoryResp> deleteAssetCategory(@RequestBody AssetCategoryDelReq assetCategoryDelReq) {

        return new ResponseEntity<AssetCategoryResp>(mwCentralLiblService.deleteAssetCategory(assetCategoryDelReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_ASSET_MAINTENANCE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetMaintenanceCategoryResp> getAssetMaintenanceCategory(
            @RequestBody AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq) {
        return new ResponseEntity<AssetMaintenanceCategoryResp>(
                mwCentralLiblService.getAssetMaintenanceCategory(assetMaintenanceCategoryGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_ASSET_MAINTENANCE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetMaintenanceCategoryResp> saveAssetMaintenanceCategory(
            @RequestBody AssetMaintenanceCategorySaveReq assetMaintenanceCategorySaveReq) {

        return new ResponseEntity<AssetMaintenanceCategoryResp>(
                mwCentralLiblService.saveAssetMaintenanceCategory(assetMaintenanceCategorySaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_ASSET_MAINTENANCE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetMaintenanceCategoryResp> deleteAssetMaintenanceCategory(
            @RequestBody AssetMaintenanceCategoryDelReq assetMaintenanceCategoryDelReq) {

        return new ResponseEntity<AssetMaintenanceCategoryResp>(
                mwCentralLiblService.deleteAssetMaintenanceCategory(assetMaintenanceCategoryDelReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.SAVE_TANGIBLE_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> saveTangibleGroups(@RequestBody TangibleClassSaveReq tangibleClassSaveReq) {
        return new ResponseEntity<TangibleClassResp>(mwCentralLiblService.saveTangibleGroups(tangibleClassSaveReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DELETE_TANGIBLE_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> deleteTangibleGroups(
            @RequestBody TangibleClassDelReq tangibleClassDelReq) {

        return new ResponseEntity<TangibleClassResp>(mwCentralLiblService.deleteTangibleGroups(tangibleClassDelReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_TANGIBLE_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> getTangibleGroups(@RequestBody TangibleClassGetReq tangibleClassGetReq) {
        return new ResponseEntity<TangibleClassResp>(mwCentralLiblService.getTangibleGroups(tangibleClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_TANGIBLE_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> getTangibleItems(@RequestBody TangibleClassGetReq tangibleClassGetReq) {
        return new ResponseEntity<TangibleClassResp>(mwCentralLiblService.getTangibleItems(tangibleClassGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_CENTRAL_TANGIBLE, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> getCentralTangible(
            @RequestBody TangibleClassFilterReq tangibleClassFilterReq) {
        return new ResponseEntity<TangibleClassResp>(mwCentralLiblService.getCentralTangible(tangibleClassFilterReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.SAVE_COUNTRY_PROVISION_CODES, method = RequestMethod.POST)
    public ResponseEntity<CountryProvinceCodeResp> saveCountryProvinceCodes(
            @RequestBody CountryProvinceCodeSaveReq countryProvinceCodeSaveReq) {
        return new ResponseEntity<CountryProvinceCodeResp>(mwCentralLiblService.saveCountryProvinceCodes(countryProvinceCodeSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_COUNTRY_PROVISION_CODES, method = RequestMethod.POST)
    public ResponseEntity<CountryProvinceCodeResp> getCountryProvinceCodes(
            @RequestBody CountryProvinceCodeGetReq countryProvinceCodeGetReq) {
        return new ResponseEntity<CountryProvinceCodeResp>(
        		mwCentralLiblService.getCountryProvinceCodes(countryProvinceCodeGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DEACTIVATE_COUNTRY_PROVISION_CODES, method = RequestMethod.POST)
    public ResponseEntity<CountryProvinceCodeResp> deactivateCountryProvinceCodes(
            @RequestBody CountryProvinceCodeDeactiveReq countryProvinceCodeDeactiveReq) {
        return new ResponseEntity<CountryProvinceCodeResp>(mwCentralLiblService.deactivateCountryProvinceCodes(countryProvinceCodeDeactiveReq), HttpStatus.OK);
    }
    
    //finance center changes 
    
    @RequestMapping(value = CentralLibraryConstants.GET_FINANCE_CENTER, method = RequestMethod.POST)
    public ResponseEntity<FinanceCenterResp> getfinanceCenterRecords(@RequestBody FinanceCenterCodeGetReq financeCenterCodeGetReq) {
    	 // System.out.println("MW controller=====getfinanceCenterRecords");
        return new ResponseEntity<FinanceCenterResp>(
        		mwCentralLiblService.getfinanceCenterRecords(financeCenterCodeGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = CentralLibraryConstants.SAVE_FINANCE_CENTER)
    public ResponseEntity<FinanceCenterResp> savefinanceCenterRecords(
            @RequestBody FinanceCenterSaveReq financeCenterSaveReq) {
        return new ResponseEntity<FinanceCenterResp>(mwCentralLiblService.savefinanceCenterRecords(financeCenterSaveReq), HttpStatus.OK);
    }
    
  @PostMapping(value = CentralLibraryConstants.GET_EMPLOYEE_TYPES)
    public ResponseEntity<FinanceCenterResp> getEmployeeTypes(@RequestBody FinanceCenterCodeGetReq financeCenterCodeGetReq) {
        return new ResponseEntity<FinanceCenterResp>(mwCentralLiblService.getEmployeeTypes(financeCenterCodeGetReq), HttpStatus.OK);
    }
  
    @RequestMapping(value = CentralLibraryConstants.DEACTIVATE_FINANCE_CENTER, method = RequestMethod.POST)
    public ResponseEntity<FinanceCenterResp> deactivatefinanceCenterRecords(
            @RequestBody FinanceCenterDeactiveReq financeCenterDeactiveReq) {
        return new ResponseEntity<FinanceCenterResp>(mwCentralLiblService.deactivatefinanceCenterRecords(financeCenterDeactiveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_UNT_OF_RATE, method = RequestMethod.POST)
    public ResponseEntity<FinanceCenterResp> getUnitOfRate(@RequestBody FinanceCenterFilterReq financeCenterFilterReq) {
    	  //System.out.println("MW controller=====getUnitOfRate");
        return new ResponseEntity<FinanceCenterResp>(
        		mwCentralLiblService.getUnitOfRate(financeCenterFilterReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.SAVE_TAX_AND_RULES, method = RequestMethod.POST)
    public ResponseEntity<TaxRatesRulesCodeDtlResp> saveTaxAndRules(@RequestBody TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq) {
    	  //System.out.println("MW controller=====saveTaxAndRules");
        return new ResponseEntity<TaxRatesRulesCodeDtlResp>(
        		mwCentralLiblService.saveTaxAndRules(taxRatesRulesCodeDtlSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.SAVE_COMPANY_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CmpBankDetailsResp> saveCompanyBankDetails(
            @RequestBody CompanyBankSaveReq companyBankSaveReq) {
    	System.out.println("companyBankSaveReq111111111111"+companyBankSaveReq);
        return new ResponseEntity<CmpBankDetailsResp>(mwCentralLiblService.saveCompanyBankDetails(companyBankSaveReq),
                HttpStatus.OK);

    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CmpBankDetailsResp> getBank(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<CmpBankDetailsResp>(mwCentralLiblService.getBank(userReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DELETE_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CmpBankDetailsResp> deleteBank(@RequestBody BankDelReq bankDelReq) {
        return new ResponseEntity<CmpBankDetailsResp>(mwCentralLiblService.deleteBank(bankDelReq), HttpStatus.OK);

    }

}