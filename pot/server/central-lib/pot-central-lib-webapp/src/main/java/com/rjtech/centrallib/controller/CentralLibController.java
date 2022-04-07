package com.rjtech.centrallib.controller;

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
import com.rjtech.centrallib.req.ProcureFilterReq;
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
import com.rjtech.centrallib.resp.CountryProvinceCodeResp;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.EmpWageResp;
import com.rjtech.centrallib.resp.FinanceCenterResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.centrallib.resp.PlantServiceClassResp;
import com.rjtech.centrallib.resp.ProcureCatgOnLoadResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.centrallib.resp.ProcurementCompanyResp;
import com.rjtech.centrallib.resp.RegisterOnLoadResp;
import com.rjtech.centrallib.resp.ServiceResp;
import com.rjtech.centrallib.resp.StockResp;
import com.rjtech.centrallib.resp.WeatherResp;
import com.rjtech.common.dto.FinanceCenterRecordTo;
import com.rjtech.common.service.CentralLibService;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ProcurementCatg;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjectResp;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

import com.rjtech.centrallib.req.TangibleClassGetReq;
import com.rjtech.centrallib.req.TangibleClassSaveReq;
import com.rjtech.centrallib.req.TangibleClassDelReq;
import com.rjtech.centrallib.resp.TangibleClassResp;
import com.rjtech.centrallib.req.TangibleClassFilterReq;

import com.rjtech.centrallib.req.TaxRatesRulesCodeDtlSaveReq;
import com.rjtech.centrallib.resp.TaxRatesRulesCodeDtlResp;

import com.rjtech.centrallib.resp.CmpBankDetailsResp;
import com.rjtech.centrallib.req.CompanyBankSaveReq;
import com.rjtech.centrallib.req.BankDelReq;

@RestController
@RequestMapping(CentralLibraryConstants.PARH_URL)
public class CentralLibController {

    @Autowired
    private CentralLibService centralLibService;

    @Autowired
    private EPSProjService epsProjService;

    @RequestMapping(value = CentralLibraryConstants.GET_MEASUREMENTS, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> getMeasurements(@RequestBody MesureFilterReq mesureFilterReq) {
        return new ResponseEntity<MeasureUnitResp>(centralLibService.getMeasurements(mesureFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MEASURES_BY_PROCURE_TYPE, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> getMeasuresByProcureType(@RequestBody MesureFilterReq mesureFilterReq) {
        return new ResponseEntity<MeasureUnitResp>(centralLibService.getMeasuresByProcureType(mesureFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_MEASUREMENTS, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> saveMeasurements(@RequestBody MeasureUnitReq measureUnitReq) {
        centralLibService.saveMeasurements(measureUnitReq);
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        MeasureUnitResp measureUnitResp = centralLibService.getMeasurements(mesureFilterReq);
        measureUnitResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<MeasureUnitResp>(measureUnitResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_MEASUREMENTS, method = RequestMethod.POST)
    public ResponseEntity<MeasureUnitResp> deleteMeasurements(@RequestBody MeasureDelReq measureDelReq) {
        centralLibService.deleteMeasurements(measureDelReq);
        MesureFilterReq mesureFilterReq = new MesureFilterReq();
        mesureFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        MeasureUnitResp measureUnitResp = centralLibService.getMeasurements(mesureFilterReq);
        measureUnitResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<MeasureUnitResp>(measureUnitResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_WEATHERS, method = RequestMethod.POST)
    public ResponseEntity<WeatherResp> getWeatherDetails(@RequestBody WeatherFilterReq weatherFilterReq) {
        return new ResponseEntity<WeatherResp>(centralLibService.getWeatherDetails(weatherFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_WEATHERS, method = RequestMethod.POST)
    public ResponseEntity<WeatherResp> saveWeatherDetails(@RequestBody WeatherReq weatherReq) {
        centralLibService.saveWeatherDetails(weatherReq);
        WeatherFilterReq weatherFilterReq = new WeatherFilterReq();
        weatherFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        WeatherResp weatherResp = centralLibService.getWeatherDetails(weatherFilterReq);
        weatherResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<WeatherResp>(weatherResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_WEATHERS, method = RequestMethod.POST)
    public ResponseEntity<WeatherResp> deleteWeatherDetails(@RequestBody WeatherDelReq weatherDelReq) {
        centralLibService.deleteWeatherDetails(weatherDelReq);
        WeatherFilterReq weatherFilterReq = new WeatherFilterReq();
        weatherFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        WeatherResp weatherResp = centralLibService.getWeatherDetails(weatherFilterReq);
        weatherResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<WeatherResp>(weatherResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMPCLASSES, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> getEmpClasses(@RequestBody EmpClassFilterReq empClassFilterReq) {
        return new ResponseEntity<EmpClassesResp>(centralLibService.getEmpClasses(empClassFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_EMPCLASSES, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> saveEmpClasses(@RequestBody EmpClassesSaveReq empClassesSaveReq) {
        centralLibService.saveEmpClasses(empClassesSaveReq);
        EmpClassFilterReq empClassFilterReq = new EmpClassFilterReq();
        empClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpClassesResp empClassesResp = centralLibService.getEmpClasses(empClassFilterReq);
        empClassesResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpClassesResp>(empClassesResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_EMPCLASSES, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> deleteEmpClasses(@RequestBody EmpClassDelReq empClassDelReq) {
        centralLibService.deleteEmpClasses(empClassDelReq);
        EmpClassFilterReq empClassFilterReq = new EmpClassFilterReq();
        empClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpClassesResp empClassesResp = centralLibService.getEmpClasses(empClassFilterReq);
        empClassesResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<EmpClassesResp>(empClassesResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> getCompanies(@RequestBody CompanyFilterReq companyFilterReq) {
        return new ResponseEntity<CompanyResp>(centralLibService.getCompanies(companyFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES_DETAILS_CMP_IDS, method = RequestMethod.POST)
    public ResponseEntity<ProcurementCompanyResp> getCompaniesDetailsByCmpIds(
            @RequestBody CompanyGetReq companyGetReq) {
        return new ResponseEntity<ProcurementCompanyResp>(centralLibService.getCompaniesDetailsByCmpIds(companyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANIES_WITHDEFAULT_ADDRESS_AND_CONTACT, method = RequestMethod.POST)
    public ResponseEntity<ProcurementCompanyResp> getCompaniesWithDefaultAddressAndContact(
            @RequestBody CompanyFilterReq companyFilterReq) {
        return new ResponseEntity<ProcurementCompanyResp>(
                centralLibService.getCompaniesWithDefaultAddressAndContact(companyFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COMPANY_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> getCompanyDetails(@RequestBody CompanyGetReq companyGetReq) {
        return new ResponseEntity<CompanyResp>(centralLibService.getCompanyDetails(companyGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COMPANY, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> saveCompanyDetails(@RequestBody CompanySaveReq companySaveReq) {
        centralLibService.saveCompany(companySaveReq);

        CompanyFilterReq companyFilterReq = new CompanyFilterReq();
        companyFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        CompanyResp companyResp = centralLibService.getCompanies(companyFilterReq);
        companyResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CompanyResp>(companyResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_COMPANIES, method = RequestMethod.POST)
    public ResponseEntity<CompanyResp> deleteCompanies(@RequestBody CompanyDelReq companyDelReq) {
        centralLibService.deleteCompanies(companyDelReq);
        CompanyFilterReq companyFilterReq = new CompanyFilterReq();
        companyFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        CompanyResp companyResp = centralLibService.getCompanies(companyFilterReq);
        companyResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<CompanyResp>(companyResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_ADDRESS, method = RequestMethod.POST)
    public ResponseEntity<AddressResp> saveAddress(@RequestBody AddressSaveReq addressSaveReq) {
        centralLibService.saveAddress(addressSaveReq);
        CompanyGetReq userReq = new CompanyGetReq();
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        userReq.setCmpId(addressSaveReq.getAddressTOs().get(0).getCompanyId());
        AddressResp addressResp = centralLibService.getAddress(userReq);
        addressResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AddressResp>(addressResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_ADDRESS, method = RequestMethod.POST)
    public ResponseEntity<AddressResp> deleteAddress(@RequestBody AddressDelReq addressDelReq) {
        centralLibService.deleteAddress(addressDelReq);
        CompanyGetReq userReq = new CompanyGetReq();
        userReq.setCmpId(addressDelReq.getCmpId());
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        AddressResp addressResp = centralLibService.getAddress(userReq);
        addressResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<AddressResp>(addressResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_CONTACTS, method = RequestMethod.POST)
    public ResponseEntity<ContactsResp> saveContacts(@RequestBody ContactsSaveReq contactsSaveReq) {
        centralLibService.saveContacts(contactsSaveReq);

        CompanyGetReq userReq = new CompanyGetReq();
        userReq.setCmpId(contactsSaveReq.getContactsTOs().get(0).getCompanyId());
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        ContactsResp contactsResp = centralLibService.getContacts(userReq);
        contactsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ContactsResp>(contactsResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_CONTACTS, method = RequestMethod.POST)
    public ResponseEntity<ContactsResp> deleteContacts(@RequestBody ContactDelReq contactDelReq) {
        centralLibService.deleteContacts(contactDelReq);
        CompanyGetReq userReq = new CompanyGetReq();
        userReq.setCmpId(contactDelReq.getCmpId());
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        ContactsResp contactsResp = centralLibService.getContacts(userReq);
        contactsResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ContactsResp>(contactsResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_COMPANY_CLOSED_PROJS, method = RequestMethod.POST)
    public String deleteCompanyClosedProjs(@RequestBody CompanyProjDelReq companyProjDelReq) {
        try {
            centralLibService.deleteCompanyClosedProjs(companyProjDelReq);
            return CentralLibraryConstants.SUCCESS;
        } catch (Exception e) {
            return CentralLibraryConstants.FAILURE;
        }
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROJECTS_BY_CLIENT, method = RequestMethod.POST)
    public ResponseEntity<ProjectResp> getProjectsByClient(@RequestBody ProjGetReq projGetReq) {
        return new ResponseEntity<ProjectResp>(epsProjService.getProjects(projGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COMPANY_CURRENT_PROJS, method = RequestMethod.POST)
    public ResponseEntity<CmpCurrentProjsResp> saveCompanyCurrentProjs(
            @RequestBody CompanyProjSaveReq companyProjSaveReq) {
        centralLibService.saveCompanyCurrentProjs(companyProjSaveReq);

        CompanyGetReq userReq = new CompanyGetReq();
        userReq.setCmpId(companyProjSaveReq.getCompanyProjectsTOs().get(0).getCmpId());
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        CmpCurrentProjsResp cmpCurrentProjsResp = centralLibService.getCmpCurrentProjs(userReq);
        cmpCurrentProjsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CmpCurrentProjsResp>(cmpCurrentProjsResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DELETE_COMPANY_CURRENT_PROJS, method = RequestMethod.POST)
    public String deleteCompanyCurrentProjs(@RequestBody CompanyProjDelReq companyProjDelReq) {
        try {
            centralLibService.deleteCompanyCurrentProjs(companyProjDelReq);
            return CentralLibraryConstants.SUCCESS;
        } catch (Exception e) {
            return CentralLibraryConstants.FAILURE;
        }
    }

    @RequestMapping(value = CentralLibraryConstants.MOVE_TO_COMPANY_CLOSED_PROJS, method = RequestMethod.POST)
    public ResponseEntity<Void> moveToCmpClosProjs(@RequestBody CompanyProjSaveReq companyProjSaveReq) {
        centralLibService.moveToCmpClosProjs(companyProjSaveReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_STOCKS, method = RequestMethod.POST)
    public ResponseEntity<StockResp> getStock(@RequestBody StockFilterReq stockFilterReq) {
        return new ResponseEntity<StockResp>(centralLibService.getStock(stockFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_STOCKS, method = RequestMethod.POST)
    public ResponseEntity<StockResp> saveStock(@RequestBody StockSaveReq stockSaveReq) {
        centralLibService.saveStock(stockSaveReq);

        StockFilterReq stockFilterReq = new StockFilterReq();
        stockFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        StockResp stockResp = centralLibService.getStock(stockFilterReq);
        stockResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<StockResp>(stockResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_STOCK, method = RequestMethod.POST)
    public ResponseEntity<StockResp> deleteStock(@RequestBody StockDelReq stockDelReq) {
        centralLibService.deleteStock(stockDelReq);
        StockFilterReq stockFilterReq = new StockFilterReq();
        stockFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        StockResp stockResp = centralLibService.getStock(stockFilterReq);
        stockResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<StockResp>(stockResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_EMPWAGES, method = RequestMethod.POST)
    public ResponseEntity<EmpWageResp> getEmpWages(@RequestBody EmpWagesFilterReq empWagesFilterReq) {
        return new ResponseEntity<EmpWageResp>(centralLibService.getEmpWages(empWagesFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_EMPWAGES, method = RequestMethod.POST)
    public ResponseEntity<EmpWageResp> saveEmpWages(@RequestBody EmpWageSaveReq empWageSaveReq) {
        centralLibService.saveEmpWages(empWageSaveReq);

        EmpWagesFilterReq empWagesFilterReq = new EmpWagesFilterReq();
        empWagesFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpWageResp empWageResp = centralLibService.getEmpWages(empWagesFilterReq);
        empWageResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpWageResp>(empWageResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_EMPWAGES, method = RequestMethod.POST)
    public ResponseEntity<EmpWageResp> deleteEmpWages(@RequestBody EmpWageDelReq empWagesDelReq) {
        centralLibService.deleteEmpWages(empWagesDelReq);
        EmpWagesFilterReq empWagesFilterReq = new EmpWagesFilterReq();
        empWagesFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpWageResp empWageResp = centralLibService.getEmpWages(empWagesFilterReq);
        empWageResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<EmpWageResp>(empWageResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> getCostCodes(@RequestBody costCodeFilterReq costCodeFilterReq) {
        return new ResponseEntity<CostCodeResp>(centralLibService.getCostCodes(costCodeFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> saveCostCodes(@RequestBody CostCodeSaveReq CostCodeSaveReq) {
        centralLibService.saveCostCodes(CostCodeSaveReq);

        costCodeFilterReq costCodeReq = new costCodeFilterReq();
        costCodeReq.setStatus(StatusCodes.ACTIVE.getValue());
        CostCodeResp costCodeResp = centralLibService.getCostCodes(costCodeReq);
        costCodeResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CostCodeResp>(costCodeResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> deleteCostCodes(@RequestBody CostCodeDelReq costCodeDelReq) {
        centralLibService.deleteCostCodes(costCodeDelReq);
        costCodeFilterReq costCodeReq = new costCodeFilterReq();
        costCodeReq.setStatus(StatusCodes.ACTIVE.getValue());
        CostCodeResp costCodeResp = centralLibService.getCostCodes(costCodeReq);
        costCodeResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<CostCodeResp>(costCodeResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PROCURECATGS, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgResp> getProcureCatgs(@RequestBody ProcureCatgFilterReq procureCatgFilterReq) {
        return new ResponseEntity<ProcureCatgResp>(centralLibService.getProcureCatgs(procureCatgFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PROCURECATGS, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgResp> saveProcureCatgs(@RequestBody ProcureCatgSaveReq procureCatgSaveReq) {
        centralLibService.saveProcureCatgs(procureCatgSaveReq);

        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProcureCatgResp procureCatgResp = centralLibService.getProcureCatgs(procureCatgFilterReq);
        procureCatgResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProcureCatgResp>(procureCatgResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_PROCURECATGS, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgResp> deleteProcureCatgs(@RequestBody ProcureCatgDelReq procureCatgDelReq) {
        centralLibService.deleteProcureCatgs(procureCatgDelReq);
        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProcureCatgResp procureCatgResp = centralLibService.getProcureCatgs(procureCatgFilterReq);
        procureCatgResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ProcureCatgResp>(procureCatgResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANTCLASSES, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> getPlantClasses(@RequestBody PlantClassFilterReq plantClassFilterReq) {
        return new ResponseEntity<PlantClassResp>(centralLibService.getPlantClasses(plantClassFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PLANTCLASSES, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> savePlantClasses(@RequestBody PlantClassSavereq plantClassSavereq) {
        centralLibService.savePlantClasses(plantClassSavereq);

        PlantClassFilterReq plantClassFilterReq = new PlantClassFilterReq();
        plantClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantClassResp plantClassResp = centralLibService.getPlantClasses(plantClassFilterReq);
        plantClassResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PlantClassResp>(plantClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_PLANTCLASSES, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> deletePlantClasses(@RequestBody PlantClassDelReq plantClassDelReq) {
        centralLibService.deletePlantClasses(plantClassDelReq);
        PlantClassFilterReq plantClassFilterReq = new PlantClassFilterReq();
        plantClassFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantClassResp plantClassResp = centralLibService.getPlantClasses(plantClassFilterReq);
        plantClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<PlantClassResp>(plantClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_SERVICECLASSES, method = RequestMethod.POST)
    public ServiceResp getServiceClasses(@RequestBody ServiceFiltterReq serviceFiltterReq) {
        return centralLibService.getServiceClasses(serviceFiltterReq);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_SERVICECLASSES, method = RequestMethod.POST)
    public ResponseEntity<ServiceResp> saveServiceClasses(@RequestBody ServiceSaveReq serviceSaveReq) {
        centralLibService.saveServiceClasses(serviceSaveReq);

        ServiceFiltterReq serviceFiltterReq = new ServiceFiltterReq();
        serviceFiltterReq.setStatus(StatusCodes.ACTIVE.getValue());
        ServiceResp serviceResp = centralLibService.getServiceClasses(serviceFiltterReq);
        serviceResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ServiceResp>(serviceResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_SERVICECLASSES, method = RequestMethod.POST)
    public ResponseEntity<ServiceResp> deleteServiceClasses(@RequestBody ServiceClassDelReq serviceClassDelReq) {
        centralLibService.deleteServiceClasses(serviceClassDelReq);
        ServiceFiltterReq serviceFiltterReq = new ServiceFiltterReq();
        serviceFiltterReq.setStatus(StatusCodes.ACTIVE.getValue());
        ServiceResp serviceResp = centralLibService.getServiceClasses(serviceFiltterReq);
        serviceResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ServiceResp>(serviceResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_CONTACTS, method = RequestMethod.POST)
    public ResponseEntity<ContactsResp> getContacts(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<ContactsResp>(centralLibService.getContacts(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_ADDRESS, method = RequestMethod.POST)
    public ResponseEntity<AddressResp> getAddress(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<AddressResp>(centralLibService.getAddress(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_CMPCURRENTPROJS, method = RequestMethod.POST)
    public ResponseEntity<CmpCurrentProjsResp> getCmpCurrentProjs(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<CmpCurrentProjsResp>(centralLibService.getCmpCurrentProjs(userReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.ONLOAD_DATA_FOR_PROCURECATG, method = RequestMethod.POST)
    public ResponseEntity<ProcureCatgOnLoadResp> onLoadDataForProcureCatg(
            @RequestBody OnLoadFilterReq onLoadFilterReq) {
        ProcureCatgOnLoadResp procureCatgOnLoadResp = new ProcureCatgOnLoadResp();

        ProcureFilterReq procureFilterReq = new ProcureFilterReq();
        procureFilterReq.setClientId(AppUserUtils.getClientId());
        procureFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        procureCatgOnLoadResp.getProcureMentCatgTO().setClientId(onLoadFilterReq.getClientId());
        return new ResponseEntity<ProcureCatgOnLoadResp>(procureCatgOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASS_SERVICE, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceClassResp> getPlantServiceClass(
            @RequestBody PlantServiceClassGetReq plantServiceClassGetReq) {
        return new ResponseEntity<PlantServiceClassResp>(
                centralLibService.getPlantServiceClass(plantServiceClassGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_PLANT_CLASS_SERVICE, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceClassResp> savePlantServiceClass(
            @RequestBody PlantServiceClassSaveReq plantServiceClassSaveReq) {
        centralLibService.savePlantServiceClass(plantServiceClassSaveReq);

        PlantServiceClassGetReq plantServiceClassGetReq = new PlantServiceClassGetReq();
        plantServiceClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantServiceClassResp plantServiceClassResp = centralLibService.getPlantServiceClass(plantServiceClassGetReq);
        plantServiceClassResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PlantServiceClassResp>(plantServiceClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DEACTIVATE_PLANT_CLASS_SERVICE, method = RequestMethod.POST)
    public ResponseEntity<PlantServiceClassResp> deactivatePlantServiceClass(
            @RequestBody PlantServiceClassDeactivateReq plantServiceClassDeactivateReq) {
        centralLibService.deactivatePlantServiceClass(plantServiceClassDeactivateReq);

        PlantServiceClassGetReq plantServiceClassGetReq = new PlantServiceClassGetReq();
        plantServiceClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantServiceClassResp plantServiceClassResp = centralLibService.getPlantServiceClass(plantServiceClassGetReq);
        plantServiceClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<PlantServiceClassResp>(plantServiceClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_CLASS_SERVICE_ITENS_ONLY, method = RequestMethod.POST)
    public ResponseEntity<String> getPlantServiceClassItemsOnly(@RequestBody RegisterOnLoadReq registerOnLoadReq) {
        CentLibPlantRepairsOnLoadResp resp = new CentLibPlantRepairsOnLoadResp();
        resp.setPlantServiceMap(centralLibService.getPlantServiceClassItemsMap());
        return new ResponseEntity<String>(AppUtils.toJson(resp), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_REGISTER_ONLOAD_CMP_PROCATG_CLASS, method = RequestMethod.POST)
    public ResponseEntity<String> getRegisterOnLoadCmpCatgProCatgClass(
            @RequestBody RegisterOnLoadReq registerOnLoadReq) {
        RegisterOnLoadResp resp = new RegisterOnLoadResp();
        resp.getRegisterOnLoadTO().setCompanyMap(centralLibService.getCompanyMap());
        resp.getRegisterOnLoadTO().setProcureCatgMap(centralLibService.getProcureCatgMap(registerOnLoadReq));
        if (ProcurementCatg.MAN_POWER.getDesc().equalsIgnoreCase(registerOnLoadReq.getProcureCatg())) {
            resp.getRegisterOnLoadTO().setClassificationMap(centralLibService.getEmpClassMap());
        } else if (ProcurementCatg.PLANT.getDesc().equalsIgnoreCase(registerOnLoadReq.getProcureCatg())) {
            resp.getRegisterOnLoadTO().setClassificationMap(centralLibService.getPlantClassMap());
        } else if (ProcurementCatg.MATERIAL.getDesc().equalsIgnoreCase(registerOnLoadReq.getProcureCatg())) {
            resp.getRegisterOnLoadTO().setClassificationMap(centralLibService.getMaterialClassMap());
        }
        return new ResponseEntity<String>(AppUtils.toJson(resp), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_PLANT_SERVICE_MATERIAL_CLASS, method = RequestMethod.POST)
    public ResponseEntity<CentLibPlantRepairsOnLoadResp> getPlantServiceMaterialClassMap(
            @RequestBody RegisterOnLoadReq registerOnLoadReq) {
        CentLibPlantRepairsOnLoadResp plantRepairsOnLoadResp = new CentLibPlantRepairsOnLoadResp();
        plantRepairsOnLoadResp.setClassificationMap(centralLibService.getMaterialClassMap());
        plantRepairsOnLoadResp.setPlantServiceMap(centralLibService.getPlantServiceClassItemsMap());
        return new ResponseEntity<CentLibPlantRepairsOnLoadResp>(plantRepairsOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MATERIAL_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getMaterialItems(@RequestBody MaterialClassGetReq materialClassGetReq) {
        return new ResponseEntity<MaterialClassResp>(centralLibService.getMaterialItems(materialClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_MATERIAL_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getMaterialGroups(@RequestBody MaterialClassGetReq materialClassGetReq) {
        return new ResponseEntity<MaterialClassResp>(centralLibService.getMaterialGroups(materialClassGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_CENTRALMATERIAL, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getCentralMaterial(
            @RequestBody MaterialClassFilterReq materialClassFilterReq) {
        return new ResponseEntity<MaterialClassResp>(centralLibService.getCentralMaterial(materialClassFilterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_MATERIAL_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> saveMaterialGroups(
            @RequestBody MaterialClassSaveReq materialClassSaveReq) {
        centralLibService.saveMaterialGroups(materialClassSaveReq);

        MaterialClassGetReq materialClassGetReq = new MaterialClassGetReq();
        materialClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        MaterialClassResp materialClassResp = centralLibService.getMaterialGroups(materialClassGetReq);
        materialClassResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<MaterialClassResp>(materialClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_MATERIAL_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> deleteMaterialGroups(
            @RequestBody MaterialClassDelReq materialClassDelReq) {
        centralLibService.deleteMaterialGroups(materialClassDelReq);
        MaterialClassGetReq materialClassGetReq = new MaterialClassGetReq();
        materialClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        MaterialClassResp materialClassResp = centralLibService.getMaterialGroups(materialClassGetReq);
        materialClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<MaterialClassResp>(materialClassResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_ASSET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetCategoryResp> getAssetCategory(@RequestBody AssetCategoryGetReq assetCategoryGetReq) {
        return new ResponseEntity<AssetCategoryResp>(centralLibService.getAssetCategory(assetCategoryGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_ASSET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetCategoryResp> saveAssetCategory(@RequestBody AssetCategorySaveReq assetCategorySaveReq) {
        centralLibService.saveAssetCategory(assetCategorySaveReq);

        AssetCategoryGetReq assetCategoryGetReq = new AssetCategoryGetReq();
        assetCategoryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        AssetCategoryResp assetCategoryResp = centralLibService.getAssetCategory(assetCategoryGetReq);
        assetCategoryResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AssetCategoryResp>(assetCategoryResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_ASSET_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetCategoryResp> deleteAssetCategory(@RequestBody AssetCategoryDelReq assetCategoryDelReq) {
        centralLibService.deleteAssetCategory(assetCategoryDelReq);
        AssetCategoryGetReq assetCategoryGetReq = new AssetCategoryGetReq();
        assetCategoryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        AssetCategoryResp assetCategoryResp = centralLibService.getAssetCategory(assetCategoryGetReq);
        assetCategoryResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<AssetCategoryResp>(assetCategoryResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_ASSET_MAINTENANCE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetMaintenanceCategoryResp> getAssetMaintenanceCategory(
            @RequestBody AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq) {
        return new ResponseEntity<AssetMaintenanceCategoryResp>(
                centralLibService.getAssetMaintenanceCategory(assetMaintenanceCategoryGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_ASSET_MAINTENANCE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetMaintenanceCategoryResp> saveAssetMaintenanceCategory(
            @RequestBody AssetMaintenanceCategorySaveReq assetMaintenanceCategorySaveReq) {
        centralLibService.saveAssetMaintenanceCategory(assetMaintenanceCategorySaveReq);
        AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq = new AssetMaintenanceCategoryGetReq();
        assetMaintenanceCategoryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        AssetMaintenanceCategoryResp assetMaintenanceCategoryResp = centralLibService
                .getAssetMaintenanceCategory(assetMaintenanceCategoryGetReq);
        assetMaintenanceCategoryResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AssetMaintenanceCategoryResp>(assetMaintenanceCategoryResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DELETE_ASSET_MAINTENANCE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<AssetMaintenanceCategoryResp> deleteAssetMaintenanceCategory(
            @RequestBody AssetMaintenanceCategoryDelReq assetMaintenanceCategoryDelReq) {
        centralLibService.deleteAssetMaintenanceCategory(assetMaintenanceCategoryDelReq);
        AssetMaintenanceCategoryGetReq assetMaintenanceCategoryGetReq = new AssetMaintenanceCategoryGetReq();
        assetMaintenanceCategoryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        AssetMaintenanceCategoryResp assetMaintenanceCategoryResp = centralLibService
                .getAssetMaintenanceCategory(assetMaintenanceCategoryGetReq);
        assetMaintenanceCategoryResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<AssetMaintenanceCategoryResp>(assetMaintenanceCategoryResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.SAVE_COUNTRY_PROVISION_CODES, method = RequestMethod.POST)
    public ResponseEntity<CountryProvinceCodeResp> saveCountryProvinceCodes(
            @RequestBody CountryProvinceCodeSaveReq countryProvinceCodeSaveReq) {
        centralLibService.saveCountryProvinceCode(countryProvinceCodeSaveReq);
        CountryProvinceCodeGetReq countryProvinceCodeGetReq = new CountryProvinceCodeGetReq();
        CountryProvinceCodeResp countryProvinceCodeResp = centralLibService
                .getCountryProvinceCodes(countryProvinceCodeGetReq);
        countryProvinceCodeResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CountryProvinceCodeResp>(countryProvinceCodeResp, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_COUNTRY_PROVISION_CODES, method = RequestMethod.POST)
    public ResponseEntity<CountryProvinceCodeResp> getCountryProvinceCodes(
            @RequestBody CountryProvinceCodeGetReq countryProvinceCodeGetReq) {
        return new ResponseEntity<CountryProvinceCodeResp>(
                centralLibService.getCountryProvinceCodes(countryProvinceCodeGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.DEACTIVATE_COUNTRY_PROVISION_CODES, method = RequestMethod.POST)
    public ResponseEntity<CountryProvinceCodeResp> deactivateCountryProvinceCodes(
            @RequestBody CountryProvinceCodeDeactiveReq countryProvinceCodeDeactiveReq) {
        centralLibService.deactivateCountryProvinceCodes(countryProvinceCodeDeactiveReq);
        CountryProvinceCodeGetReq countryProvinceCodeGetReq = new CountryProvinceCodeGetReq();
        CountryProvinceCodeResp countryProvinceCodeResp = centralLibService
                .getCountryProvinceCodes(countryProvinceCodeGetReq);
        countryProvinceCodeResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CountryProvinceCodeResp>(countryProvinceCodeResp, HttpStatus.OK);
    }
    
    // finance center master
    
    @PostMapping(value = CentralLibraryConstants.SAVE_FINANCE_CENTER)
    public ResponseEntity<FinanceCenterRecordTo> savefinanceCenterRecords(
            @RequestBody FinanceCenterSaveReq financeCenterSaveReq) {
    	//System.out.println("controller=====getfinanceCenterRecords");
        FinanceCenterRecordTo financeCenterRecordTo = centralLibService.savefinanceCenterRecords(financeCenterSaveReq);
       // System.out.println("controller=====financeCenterRecordTo",financeCenterRecordTo);
        return new ResponseEntity<FinanceCenterRecordTo>(financeCenterRecordTo, HttpStatus.OK);
    }

    @RequestMapping(value = CentralLibraryConstants.GET_FINANCE_CENTER, method = RequestMethod.POST)
    public ResponseEntity<FinanceCenterResp> getfinanceCenterRecords(@RequestBody FinanceCenterCodeGetReq financeCenterCodeGetReq) {
    	// System.out.println("controller=====getfinanceCenterRecords");
        return new ResponseEntity<FinanceCenterResp>(
                centralLibService.getfinanceCenterRecords(financeCenterCodeGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_UNT_OF_RATE, method = RequestMethod.POST)
    public ResponseEntity<FinanceCenterResp> getUnitOfRate(@RequestBody FinanceCenterFilterReq financeCenterFilterReq) {
    	// System.out.println("controller=====getUnitOfRate");
        return new ResponseEntity<FinanceCenterResp>(
                centralLibService.getUnitOfRate(financeCenterFilterReq), HttpStatus.OK);
    }
    
    
    @PostMapping(value = CentralLibraryConstants.GET_EMPLOYEE_TYPES)
    public ResponseEntity<RegisterOnLoadResp> getEmployeeTypes(@RequestBody FinanceCenterCodeGetReq financeCenterCodeGetReq) {
        RegisterOnLoadResp resp = new RegisterOnLoadResp();
        //System.out.println("controller=====getEmployeeTypes");
        resp.getRegisterOnLoadTO().setProcureCatgMap(centralLibService.getEmployeeTypes(financeCenterCodeGetReq));
        return new ResponseEntity<RegisterOnLoadResp>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DEACTIVATE_FINANCE_CENTER, method = RequestMethod.POST)
    public ResponseEntity<FinanceCenterResp> deactivatefinanceCenterRecords(
            @RequestBody FinanceCenterDeactiveReq financeCenterDeactiveReq) {
        centralLibService.deactivateFinanceCenterRecords(financeCenterDeactiveReq);
        FinanceCenterCodeGetReq financeCenterCodeGetReq = new FinanceCenterCodeGetReq();
        FinanceCenterResp financeCenterResp = centralLibService
                .getfinanceCenterRecords(financeCenterCodeGetReq);
        financeCenterResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<FinanceCenterResp>(financeCenterResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.SAVE_TANGIBLE_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> saveTangibleGroups(
            @RequestBody TangibleClassSaveReq tangibleClassSaveReq) {
        centralLibService.saveTangibleGroups(tangibleClassSaveReq);

        TangibleClassGetReq tangibleClassGetReq = new TangibleClassGetReq();
        tangibleClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        TangibleClassResp tangibleClassResp = 
        		centralLibService.getTangibleGroups(tangibleClassGetReq);
        tangibleClassResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<TangibleClassResp>(tangibleClassResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_CENTRAL_TANGIBLE, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> getCentralTangible(
            @RequestBody TangibleClassFilterReq tangibleClassFilterReq) {
        return new ResponseEntity<TangibleClassResp>(centralLibService.getCentralTangible(tangibleClassFilterReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DELETE_TANGIBLE_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> deleteTangibleGroups(
            @RequestBody TangibleClassDelReq tangibleClassDelReq) {
        centralLibService.deleteTangibleGroups(tangibleClassDelReq);
        TangibleClassGetReq tangibleClassGetReq = new TangibleClassGetReq();
        tangibleClassGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        TangibleClassResp tangibleClassResp = centralLibService.getTangibleGroups(tangibleClassGetReq);
        tangibleClassResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<TangibleClassResp>(tangibleClassResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_TANGIBLE_GROUPS, method = RequestMethod.POST)
    public ResponseEntity<TangibleClassResp> getTangibleGroups(@RequestBody TangibleClassGetReq tangibleClassGetReq) {
        return new ResponseEntity<TangibleClassResp>(centralLibService.getTangibleGroups(tangibleClassGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = CentralLibraryConstants.SAVE_TAX_AND_RULES)
    public ResponseEntity<TaxRatesRulesCodeDtlResp> saveTaxAndRules(
    		 @RequestBody TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq) {
        centralLibService.saveTaxAndRules(taxRatesRulesCodeDtlSaveReq);
        TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlGetReq = new TaxRatesRulesCodeDtlSaveReq();
       /* TaxRatesRulesCodeDtlResp taxRatesRulesCodeDtlResp = centralLibService
                .getTaxRatesRulesCodeDtl(taxRatesRulesCodeDtlGetReq);*/
        TaxRatesRulesCodeDtlResp taxRatesRulesCodeDtlResp = new TaxRatesRulesCodeDtlResp();
        taxRatesRulesCodeDtlResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<TaxRatesRulesCodeDtlResp>(taxRatesRulesCodeDtlResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.SAVE_COMPANY_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CmpBankDetailsResp> saveCompanyBankDetails(
            @RequestBody CompanyBankSaveReq companyBankSaveReq) {
        centralLibService.saveCompanyBankDetails(companyBankSaveReq);
        System.out.println("companyBankSaveReqqqqqqqqqqqqqqqqqqqqqqq"+companyBankSaveReq);
          CompanyGetReq userReq = new CompanyGetReq();
         userReq.setStatus(StatusCodes.ACTIVE.getValue());
         userReq.setCmpId(companyBankSaveReq.getBankTOs().get(0).getCompanyId());
        CmpBankDetailsResp cmpBankDetailsResp = new CmpBankDetailsResp();
        cmpBankDetailsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CmpBankDetailsResp>(cmpBankDetailsResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.GET_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CmpBankDetailsResp> getBank(@RequestBody CompanyGetReq userReq) {
        return new ResponseEntity<CmpBankDetailsResp>(centralLibService.getBank(userReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = CentralLibraryConstants.DELETE_BANK_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<CmpBankDetailsResp> deleteBank(@RequestBody BankDelReq bankDelReq) {
        centralLibService.deleteBank(bankDelReq);
        CompanyGetReq userReq = new CompanyGetReq();
        userReq.setCmpId(bankDelReq.getCmpId());
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        CmpBankDetailsResp cmpBankDetailsResp = centralLibService.getBank(userReq);
        cmpBankDetailsResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<CmpBankDetailsResp>(cmpBankDetailsResp, HttpStatus.OK);
    }
    
    
}