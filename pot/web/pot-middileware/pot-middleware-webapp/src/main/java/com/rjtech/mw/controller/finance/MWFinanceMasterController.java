package com.rjtech.mw.controller.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.common.dto.CountryTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.CountryGetReq;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.finance.constants.FinanceURLConstants;
import com.rjtech.finance.dto.PayRollCycleTO;
import com.rjtech.finance.dto.VendorBankDetailsTO;
import com.rjtech.finance.req.CodeTypesSaveReq;
import com.rjtech.finance.req.CompanyTaxSaveReq;
import com.rjtech.finance.req.EmployeePayrollSaveReq;
import com.rjtech.finance.req.EmployeeTypeSaveReq;
import com.rjtech.finance.req.FinanceOnLoadReq;
import com.rjtech.finance.req.FinanceTaxCodesGetReq;
import com.rjtech.finance.req.FinanceTaxDelReq;
import com.rjtech.finance.req.FinanceTaxGetReq;
import com.rjtech.finance.req.GetCostCodesReq;
import com.rjtech.finance.req.GetVendorPostInvoiceRequest;
import com.rjtech.finance.req.MedicalLeaveSaveReq;
import com.rjtech.finance.req.NonRegularAllowanceSaveReq;
import com.rjtech.finance.req.PayDeductionSaveReq;
import com.rjtech.finance.req.PayPeriodCycleDelReq;
import com.rjtech.finance.req.PayPeriodCycleGetReq;
import com.rjtech.finance.req.PayPeriodCycleSaveReq;
import com.rjtech.finance.req.PaymentReceiverSaveReq;
import com.rjtech.finance.req.PersonalTaxSaveReq;
import com.rjtech.finance.req.ProfitCentreDelReq;
import com.rjtech.finance.req.ProfitCentreGetReq;
import com.rjtech.finance.req.ProfitCentreSaveReq;
import com.rjtech.finance.req.ProvidentFundSaveReq;
import com.rjtech.finance.req.RegularAllowanceSaveReq;
import com.rjtech.finance.req.ServiceTaxSaveReq;
import com.rjtech.finance.req.SuperFundTaxSaveReq;
import com.rjtech.finance.req.TaxCodeCountryProvisionSaveReq;
import com.rjtech.finance.req.TaxCodesSaveReq;
import com.rjtech.finance.req.TaxCountryProvisionGetReq;
import com.rjtech.finance.req.TaxCountryProvisionSaveReq;
import com.rjtech.finance.req.TaxReq;
import com.rjtech.finance.req.UnitPayRateDelReq;
import com.rjtech.finance.req.UnitPayRateGetReq;
import com.rjtech.finance.req.UnitPayRateSaveReq;
import com.rjtech.finance.req.VendorBankDetailsReq;
import com.rjtech.finance.req.VendorInvoiceRequest;
import com.rjtech.finance.resp.CodeTypesResp;
import com.rjtech.finance.resp.CompanyTaxResp;
import com.rjtech.finance.resp.EmpPayrollTypeResp;
import com.rjtech.finance.resp.EmployeePayrollTaxResp;
import com.rjtech.finance.resp.FinanceOnLoadResp;
import com.rjtech.finance.resp.MedicalLeaveTaxResp;
import com.rjtech.finance.resp.NonRegularAllowanceResp;
import com.rjtech.finance.resp.PayDeductionResp;
import com.rjtech.finance.resp.PayPeriodCycleResp;
import com.rjtech.finance.resp.PayRollCycleResp;
import com.rjtech.finance.resp.PaymentReceiverResp;
import com.rjtech.finance.resp.PersonalTaxResp;
import com.rjtech.finance.resp.ProfitCentreResp;
import com.rjtech.finance.resp.ProvidentFundResp;
import com.rjtech.finance.resp.RegularAllowanceResp;
import com.rjtech.finance.resp.ServiceTaxResp;
import com.rjtech.finance.resp.SuperFundTaxResp;
import com.rjtech.finance.resp.TaxCodeCountryProviSionRespMap;
import com.rjtech.finance.resp.TaxCodeCountryProvisionResp;
import com.rjtech.finance.resp.TaxCodesResp;
import com.rjtech.finance.resp.TaxCountryProvisionResp;
import com.rjtech.finance.resp.UnitPayRateResp;
import com.rjtech.finance.resp.VendorInvocieRecordResponse;
import com.rjtech.finance.resp.VendorInvocieResponse;
import com.rjtech.finance.resp.YearsResp;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.common.MWCommonService;
import com.rjtech.mw.service.finance.MWFinanceMasterService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

@RestController
@RequestMapping(FinanceURLConstants.PARH_URL)
public class MWFinanceMasterController {

    @Autowired
    private MWFinanceMasterService mwFinanceMasterService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @Autowired
    private MWCommonService mwCommonService;

    @RequestMapping(value = FinanceURLConstants.GET_UNIT_OF_RATES, method = RequestMethod.POST)
    public ResponseEntity<UnitPayRateResp> getUnitOfRates(@RequestBody UnitPayRateGetReq unitPayRateGetReq) {
        return new ResponseEntity<UnitPayRateResp>(mwFinanceMasterService.getUnitOfRates(unitPayRateGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_UNIT_OF_RATES, method = RequestMethod.POST)
    public ResponseEntity<UnitPayRateResp> saveUnitOfRates(@RequestBody UnitPayRateSaveReq unitPayRateSaveReq) {
        return new ResponseEntity<UnitPayRateResp>(mwFinanceMasterService.saveUnitOfRates(unitPayRateSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_UNIT_OF_RATES, method = RequestMethod.POST)
    public ResponseEntity<UnitPayRateResp> deleteUnitOfRates(@RequestBody UnitPayRateDelReq unitPayRateDelReq) {
        return new ResponseEntity<UnitPayRateResp>(mwFinanceMasterService.deleteUnitOfRates(unitPayRateDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_PAY_PERIOD_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<PayRollCycleResp> getPayPeriodCycle(@RequestBody PayPeriodCycleGetReq payPeriodCycleGetReq) {
        return new ResponseEntity<PayRollCycleResp>(mwFinanceMasterService.getPayPeriodCycle(payPeriodCycleGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_PAY_PERIOD_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<PayRollCycleResp> savePayPeriodCycle(@RequestBody PayPeriodCycleSaveReq periodCycleSaveReq) {
        return new ResponseEntity<PayRollCycleResp>(mwFinanceMasterService.savePayPeriodCycle(periodCycleSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_PAY_PERIOD_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<PayRollCycleResp> deletePayPeriodCycle(@RequestBody PayPeriodCycleDelReq periodCycleDelReq) {
        return new ResponseEntity<PayRollCycleResp>(mwFinanceMasterService.deletePayPeriodCycle(periodCycleDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_EMP_PAY_TYPE_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<EmpPayrollTypeResp> getEmpPayTypeCycle(@RequestBody FinanceTaxGetReq financeTaxGetReq) {

        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> projEmpClassMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO projEmpclassLabelKeyTO = null;
        //List<ProjEmpCatgTO> projEmpCatgTOs = mwProjLibService.getProjEmpTypes(projGetReq).getProjEmpCatgTOs();
        /*for (ProjEmpCatgTO projEmpCatgTO : projEmpCatgTOs) {
        	projEmpclassLabelKeyTO = new LabelKeyTO();
        	projEmpclassLabelKeyTO.setId(projEmpCatgTO.getId());
        	projEmpclassLabelKeyTO.setCode(projEmpCatgTO.getCode());
        	projEmpclassLabelKeyTO.setName(projEmpCatgTO.getName());
        	projEmpClassMap.put(projEmpCatgTO.getId(), projEmpclassLabelKeyTO);
        }*/

        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(StatusCodes.ACTIVE.getValue());

        Map<Long, LabelKeyTO> procureCategoryMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO procureCategoryLabelKeyTO = null;
        List<ProcureMentCatgTO> procureMentCatgTOs = mwCentralLiblService.getProcureCatgs(procureCatgFilterReq)
                .getProcureMentCatgTOs();

        for (ProcureMentCatgTO procureMentCatgTO : procureMentCatgTOs) {

            procureCategoryLabelKeyTO = new LabelKeyTO();
            procureCategoryLabelKeyTO.setId(procureMentCatgTO.getProCatgId());
            procureCategoryLabelKeyTO.setCode(procureMentCatgTO.getCode());
            procureCategoryLabelKeyTO.setName(procureMentCatgTO.getDesc());

            procureCategoryMap.put(procureMentCatgTO.getProCatgId(), procureCategoryLabelKeyTO);
        }

        PayPeriodCycleGetReq periodCycleGetReq = new PayPeriodCycleGetReq();
        periodCycleGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        periodCycleGetReq.setClientId(AppUserUtils.getClientId());

        Map<Long, LabelKeyTO> payPeriodCycleMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO payRollCycleLabelKeyTO = null;
        List<PayRollCycleTO> payRollCycleTOs = mwFinanceMasterService.getPayPeriodCycle(periodCycleGetReq)
                .getPayRollCycleTOs();

        for (PayRollCycleTO payRollCycleTO : payRollCycleTOs) {

            payRollCycleLabelKeyTO = new LabelKeyTO();
            payRollCycleLabelKeyTO.setId(payRollCycleTO.getId());
            payRollCycleLabelKeyTO.setCode(payRollCycleTO.getPayRollCycle());
            payRollCycleLabelKeyTO.setName(payRollCycleTO.getSelectYear());

            payPeriodCycleMap.put(payRollCycleTO.getId(), payRollCycleLabelKeyTO);
        }
        EmpPayrollTypeResp payrollTypeResp = mwFinanceMasterService.getEmpPayTypeCycle(financeTaxGetReq);
        payrollTypeResp.setProjEmpClassMap(projEmpClassMap);
        payrollTypeResp.setProcureCategoryMap(procureCategoryMap);
        payrollTypeResp.setPayPeriodCycleMap(payPeriodCycleMap);

        return new ResponseEntity<EmpPayrollTypeResp>(payrollTypeResp, HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_EMP_TYPE_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<EmpPayrollTypeResp> saveEmpPayTypeCycle(
            @RequestBody EmployeeTypeSaveReq employeeTypeSaveReq) {
        return new ResponseEntity<EmpPayrollTypeResp>(mwFinanceMasterService.saveEmpPayTypeCycle(employeeTypeSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_EMP_PAY_TYPE_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<EmpPayrollTypeResp> deleteEmpPayTypeCycle(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<EmpPayrollTypeResp>(mwFinanceMasterService.deleteEmpPayTypeCycle(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODES, method = RequestMethod.POST)
    public ResponseEntity<TaxCodesResp> getTaxCodes(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<TaxCodesResp>(mwFinanceMasterService.getTaxCodes(financeTaxGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_CODE_TYPES, method = RequestMethod.POST)
    public ResponseEntity<CodeTypesResp> getCodeTypes(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<CodeTypesResp>(mwFinanceMasterService.getCodeTypes(financeTaxGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_CODE_TYPES, method = RequestMethod.POST)
    public ResponseEntity<CodeTypesResp> saveCodeTypes(@RequestBody CodeTypesSaveReq codeTypesSaveReq) {
        return new ResponseEntity<CodeTypesResp>(mwFinanceMasterService.saveCodeTypes(codeTypesSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.SAVE_TAX_CODES, method = RequestMethod.POST)
    public ResponseEntity<TaxCodesResp> saveTaxCodes(@RequestBody TaxCodesSaveReq taxCodesSaveReq) {
        return new ResponseEntity<TaxCodesResp>(mwFinanceMasterService.saveTaxCodes(taxCodesSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_TAX_CODES, method = RequestMethod.POST)
    public ResponseEntity<TaxCodesResp> deleteTaxCodes(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<TaxCodesResp>(mwFinanceMasterService.deleteTaxCodes(financeTaxDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODES_BY_COUNTRY, method = RequestMethod.POST)
    public ResponseEntity<TaxCountryProvisionResp> getTaxCodesByCountry(
            @RequestBody TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
        return new ResponseEntity<TaxCountryProvisionResp>(
                mwFinanceMasterService.getTaxCodesByCountry(taxCountryProvisionGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_TAX_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<TaxCountryProvisionResp> getTaxCountryProvision(
            @RequestBody TaxCountryProvisionGetReq taxCountryProvisionGetReq) {

        /*CountryGetReq countryGetReq = new CountryGetReq();
        countryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        countryGetReq.setClientId(AppUserUtils.getClientId());
        countryGetReq.setCountryId(taxCountryProvisionGetReq.getCountryId());
        */
        //Map<Long, LabelKeyTO> countryMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO countryLabelKeyTO = null;
        //List<CountryTO> countryTOs = mwCommonService.getCountries(countryGetReq).getCountryTOs();

        /*for (CountryTO countryTO : countryTOs) {
        
        	countryLabelKeyTO = new LabelKeyTO();
        	countryLabelKeyTO.setId(countryTO.getId());
        	countryLabelKeyTO.setCode(countryTO.getCode());
        	countryLabelKeyTO.setName(countryTO.getName());
        	countryMap.put(countryTO.getId(), countryLabelKeyTO);
        }*/

        //Map<Long, LabelKeyTO> provisionMap = new HashMap<Long, LabelKeyTO>();
        /*LabelKeyTO provisionLabelKeyTO = null;
        List<CountryTO> countrypTOs = mwCommonService.getCountryDetailsById(countryGetReq).getCountryTOs();
        
        for (CountryTO countryTO : countrypTOs) {
        	for (ProvisionTO provisionTO : countryTO.getProvisionTOs()) {
        		provisionLabelKeyTO = new LabelKeyTO();
        		provisionLabelKeyTO.setId(provisionTO.getId());
        		provisionLabelKeyTO.setCode(provisionTO.getCode());
        		provisionLabelKeyTO.setName(provisionTO.getName());
        		provisionMap.put(provisionTO.getId(), provisionLabelKeyTO);
        	}
        }*/

        TaxCountryProvisionResp countryProvisionResp = mwFinanceMasterService
                .getTaxCountryProvision(taxCountryProvisionGetReq);
        //countryProvisionResp.setCountryMap(countryMap);
        //countryProvisionResp.setProvisionMap(provisionMap);
        return new ResponseEntity<TaxCountryProvisionResp>(countryProvisionResp, HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_TAX_COUNTRY_PROVISIONS_MAP, method = RequestMethod.POST)
    public ResponseEntity<TaxCodeCountryProviSionRespMap> getTaxCountryProvisionMap(@RequestBody TaxReq taxReq) {
        return new ResponseEntity<TaxCodeCountryProviSionRespMap>(
                mwFinanceMasterService.getTaxCountryProvisionMap(taxReq), HttpStatus.OK);
    }

    public Map<Long, LabelKeyTO> getCountryMap() {
        CountryGetReq countryGetReq = new CountryGetReq();
        countryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        countryGetReq.setClientId(AppUserUtils.getClientId());
        Map<Long, LabelKeyTO> countryMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO countryLabelKeyTO = null;
        List<CountryTO> countryTOs = mwCommonService.getCountries(countryGetReq).getCountryTOs();

        for (CountryTO countryTO : countryTOs) {
            countryLabelKeyTO = new LabelKeyTO();
            countryLabelKeyTO.setId(countryTO.getId());
            countryLabelKeyTO.setCode(countryTO.getCode());
            countryLabelKeyTO.setName(countryTO.getName());
            countryMap.put(countryTO.getId(), countryLabelKeyTO);
        }
        return countryMap;
    }

    public Map<Long, LabelKeyTO> getProvisionMap() {

        CountryGetReq countryGetReq = new CountryGetReq();
        countryGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        countryGetReq.setClientId(AppUserUtils.getClientId());

        Map<Long, LabelKeyTO> provisionMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO provisionLabelKeyTO = null;
        List<CountryTO> countrypTOs = mwCommonService.getCountryDetailsById(countryGetReq).getCountryTOs();

        for (CountryTO countryTO : countrypTOs) {
            provisionLabelKeyTO = new LabelKeyTO();
            provisionLabelKeyTO.setId(countryTO.getProvisionTO().getId());
            provisionLabelKeyTO.setCode(countryTO.getProvisionTO().getCode());
            provisionLabelKeyTO.setName(countryTO.getProvisionTO().getName());
            provisionMap.put(countryTO.getId(), provisionLabelKeyTO);
        }
        return provisionMap;
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_TAX_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<TaxCountryProvisionResp> saveTaxCountryProvision(
            @RequestBody TaxCountryProvisionSaveReq countryProvisionSaveReq) {

        TaxCountryProvisionResp taxCountryProvisionResp = mwFinanceMasterService
                .saveTaxCountryProvision(countryProvisionSaveReq);
        return new ResponseEntity<TaxCountryProvisionResp>(taxCountryProvisionResp, HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_TAX_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<TaxCountryProvisionResp> deleteTaxCountryProvision(
            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<TaxCountryProvisionResp>(
                mwFinanceMasterService.deleteTaxCountryProvision(financeTaxDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODE_COUNTRY_PROVISIONS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<TaxCodeCountryProvisionResp> getTaxCodeCountryProvisionsOnload(
            @RequestBody FinanceTaxGetReq financeTaxGetReq) {

        TaxCodeCountryProvisionResp codeCountryProvisionResp = new TaxCodeCountryProvisionResp();

        codeCountryProvisionResp = mwFinanceMasterService.getTaxCodeCountryProvisionOnLoad(financeTaxGetReq);

        return new ResponseEntity<TaxCodeCountryProvisionResp>(codeCountryProvisionResp, HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<TaxCodeCountryProvisionResp> getTaxCodeCountryProvisions(
            @RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<TaxCodeCountryProvisionResp>(
                mwFinanceMasterService.getTaxCodeCountryProvision(financeTaxGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_TAX_CODE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<TaxCodeCountryProvisionResp> saveTaxCodeCountryProvision(
            @RequestBody TaxCodeCountryProvisionSaveReq taxCodeCountryProvisionSaveReq) {
        return new ResponseEntity<TaxCodeCountryProvisionResp>(
                mwFinanceMasterService.saveTaxCodeCountryProvision(taxCodeCountryProvisionSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_TAX_CODE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
    public ResponseEntity<TaxCodeCountryProvisionResp> deleteTaxCodeCountryProvision(
            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<TaxCodeCountryProvisionResp>(
                mwFinanceMasterService.deleteTaxCodeCountryProvision(financeTaxDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_REGULAR_ALLOWANCE, method = RequestMethod.POST)
    public ResponseEntity<RegularAllowanceResp> getRegularAllowance(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<RegularAllowanceResp>(mwFinanceMasterService.getRegularAllowance(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_REGULAR_ALLOWANCE, method = RequestMethod.POST)
    public ResponseEntity<RegularAllowanceResp> saveRegularAllowance(
            @RequestBody RegularAllowanceSaveReq regularAllowanceSaveReq) {
        return new ResponseEntity<RegularAllowanceResp>(
                mwFinanceMasterService.saveRegularAllowance(regularAllowanceSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_REGULAR_ALLOWANCE, method = RequestMethod.POST)
    public ResponseEntity<RegularAllowanceResp> deleteRegularAllowance(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<RegularAllowanceResp>(mwFinanceMasterService.deleteRegularAllowance(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_NON_REGULAR_ALLOWANCE, method = RequestMethod.POST)
    public ResponseEntity<NonRegularAllowanceResp> getNonRegularAllowance(
            @RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<NonRegularAllowanceResp>(
                mwFinanceMasterService.getNonRegularAllowance(financeTaxGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_NON_REGULAR_ALLOWANCE, method = RequestMethod.POST)
    public ResponseEntity<NonRegularAllowanceResp> saveNonRegularAllowance(
            @RequestBody NonRegularAllowanceSaveReq nonRegularAllowanceSaveReq) {
        return new ResponseEntity<NonRegularAllowanceResp>(
                mwFinanceMasterService.saveNonRegularAllowance(nonRegularAllowanceSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_NON_REGULAR_ALLOWANCE, method = RequestMethod.POST)
    public ResponseEntity<NonRegularAllowanceResp> deleteNonRegularAllowance(
            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<NonRegularAllowanceResp>(
                mwFinanceMasterService.deleteNonRegularAllowance(financeTaxDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_PROVIDENT_FUND, method = RequestMethod.POST)
    public ResponseEntity<ProvidentFundResp> getProvidentFund(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<ProvidentFundResp>(mwFinanceMasterService.getProvidentFund(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_PROVIDENT_FUND, method = RequestMethod.POST)
    public ResponseEntity<ProvidentFundResp> saveProvidentFund(@RequestBody ProvidentFundSaveReq providentFundSaveReq) {
        return new ResponseEntity<ProvidentFundResp>(mwFinanceMasterService.saveProvidentFund(providentFundSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_PROVIDENT_FUND, method = RequestMethod.POST)
    public ResponseEntity<ProvidentFundResp> deleteProvidentFund(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<ProvidentFundResp>(mwFinanceMasterService.deleteProvidentFund(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_PERSONAL_TAX_RATES, method = RequestMethod.POST)
    public ResponseEntity<PersonalTaxResp> getPersonalTaxRates(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<PersonalTaxResp>(mwFinanceMasterService.getPersonalTaxRates(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_PERSONAL_TAX_RATES, method = RequestMethod.POST)
    public ResponseEntity<PersonalTaxResp> savePersonalTaxRates(@RequestBody PersonalTaxSaveReq personalTaxSaveReq) {
        return new ResponseEntity<PersonalTaxResp>(mwFinanceMasterService.savePersonalTaxRates(personalTaxSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_PERSONAL_TAX_RATES, method = RequestMethod.POST)
    public ResponseEntity<PersonalTaxResp> deletePersonalTaxRates(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<PersonalTaxResp>(mwFinanceMasterService.deletePersonalTaxRates(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_COMPANY_TAX, method = RequestMethod.POST)
    public ResponseEntity<CompanyTaxResp> getCompanyTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<CompanyTaxResp>(mwFinanceMasterService.getCompanyTax(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_COMPANY_TAX, method = RequestMethod.POST)
    public ResponseEntity<CompanyTaxResp> saveCompanyTax(@RequestBody CompanyTaxSaveReq companyTaxSaveReq) {
        return new ResponseEntity<CompanyTaxResp>(mwFinanceMasterService.saveCompanyTax(companyTaxSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_COMPANY_TAX, method = RequestMethod.POST)
    public ResponseEntity<CompanyTaxResp> deleteCompanyTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<CompanyTaxResp>(mwFinanceMasterService.deleteCompanyTax(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_MEDICAL_LEAVE_TAX, method = RequestMethod.POST)
    public ResponseEntity<MedicalLeaveTaxResp> getMedicalLeaveTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<MedicalLeaveTaxResp>(mwFinanceMasterService.getMedicalLeaveTax(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_MEDICAL_LEAVE_TAX, method = RequestMethod.POST)
    public ResponseEntity<MedicalLeaveTaxResp> saveMedicalLeaveTax(
            @RequestBody MedicalLeaveSaveReq medicalLeaveSaveReq) {
        return new ResponseEntity<MedicalLeaveTaxResp>(mwFinanceMasterService.saveMedicalLeaveTax(medicalLeaveSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_MEDICAL_LEAVE_TAX, method = RequestMethod.POST)
    public ResponseEntity<MedicalLeaveTaxResp> deleteMediaclLeaveTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<MedicalLeaveTaxResp>(mwFinanceMasterService.deleteMedicalLeaveTax(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_EMPLOYEE_PAYROLL, method = RequestMethod.POST)
    public ResponseEntity<EmployeePayrollTaxResp> getEmployeePayroll(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<EmployeePayrollTaxResp>(
                mwFinanceMasterService.getEmployeePayrollTax(financeTaxGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_EMPLOYEE_PAYROLL, method = RequestMethod.POST)
    public ResponseEntity<EmployeePayrollTaxResp> saveEmployeePayroll(
            @RequestBody EmployeePayrollSaveReq employeePayrollSaveReq) {
        return new ResponseEntity<EmployeePayrollTaxResp>(
                mwFinanceMasterService.saveEmployeePayrollTax(employeePayrollSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_EMPLOYEE_PAYROLL, method = RequestMethod.POST)
    public ResponseEntity<EmployeePayrollTaxResp> deleteEmployeePayrollTax(
            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<EmployeePayrollTaxResp>(
                mwFinanceMasterService.deleteEmployeePayrollTax(financeTaxDelReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_SUPERFUND_TAX, method = RequestMethod.POST)
    public ResponseEntity<SuperFundTaxResp> getSuperfundTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<SuperFundTaxResp>(mwFinanceMasterService.getSuperfundTax(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_SUPERFUND_TAX, method = RequestMethod.POST)
    public ResponseEntity<SuperFundTaxResp> saveSuperfundTax(@RequestBody SuperFundTaxSaveReq superFundTaxSaveReq) {
        return new ResponseEntity<SuperFundTaxResp>(mwFinanceMasterService.saveSuperfundTax(superFundTaxSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_SUPERFUND_TAX, method = RequestMethod.POST)
    public ResponseEntity<SuperFundTaxResp> deleteSuperfundTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<SuperFundTaxResp>(mwFinanceMasterService.deleteSuperfundTax(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_SERVICE_TAX, method = RequestMethod.POST)
    public ResponseEntity<ServiceTaxResp> getServiceTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<ServiceTaxResp>(mwFinanceMasterService.getServiceTax(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_SERVICE_TAX, method = RequestMethod.POST)
    public ResponseEntity<ServiceTaxResp> saveServiceTax(@RequestBody ServiceTaxSaveReq serviceTaxSaveReq) {
        return new ResponseEntity<ServiceTaxResp>(mwFinanceMasterService.saveServiceTax(serviceTaxSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_SERVICE_TAX, method = RequestMethod.POST)
    public ResponseEntity<ServiceTaxResp> deleteServiceTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<ServiceTaxResp>(mwFinanceMasterService.deleteServiceTax(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_PAY_DEDUCTION, method = RequestMethod.POST)
    public ResponseEntity<PayDeductionResp> getPayDeduction(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<PayDeductionResp>(mwFinanceMasterService.getPayDeduction(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_PAY_DEDUCTION, method = RequestMethod.POST)
    public ResponseEntity<PayDeductionResp> savePayDeduction(@RequestBody PayDeductionSaveReq payDeductionSaveReq) {
        return new ResponseEntity<PayDeductionResp>(mwFinanceMasterService.savePayDeduction(payDeductionSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_PAY_DEDUCTION, method = RequestMethod.POST)
    public ResponseEntity<PayDeductionResp> deletePayDeduction(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<PayDeductionResp>(mwFinanceMasterService.deletePayDeduction(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_PAYMENT_RECEIVER, method = RequestMethod.POST)
    public ResponseEntity<PaymentReceiverResp> getPaymentReceiver(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
        return new ResponseEntity<PaymentReceiverResp>(mwFinanceMasterService.getPaymentReceiver(financeTaxGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_PAYMENT_RECEIVER, method = RequestMethod.POST)
    public ResponseEntity<PaymentReceiverResp> savePaymentReceiver(
            @RequestBody PaymentReceiverSaveReq paymentReceiverSaveReq) {
        return new ResponseEntity<PaymentReceiverResp>(
                mwFinanceMasterService.savePaymentReceiver(paymentReceiverSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.DELETE_PAYMENT_RECEIVER, method = RequestMethod.POST)
    public ResponseEntity<PaymentReceiverResp> deletePaymentReceiver(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<PaymentReceiverResp>(mwFinanceMasterService.deletePaymentReceiver(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_ONLOAD_FOR_FINANCE, method = RequestMethod.POST)
    public ResponseEntity<FinanceOnLoadResp> financeOnLoadResp(@RequestBody FinanceOnLoadReq financeOnLoadReq) {
        FinanceOnLoadResp financeOnLoadResp = mwFinanceMasterService.financeOnLoadResp(financeOnLoadReq);
        return new ResponseEntity(AppUtils.toJson(financeOnLoadResp), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_PAY_ROLL_CYCLE, method = RequestMethod.POST)
    public ResponseEntity<PayPeriodCycleResp> getPayRollCycles() {
        return new ResponseEntity(AppUtils.toJson(mwFinanceMasterService.getPayRollCycles()), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_YEARS, method = RequestMethod.POST)
    public ResponseEntity<YearsResp> getFinancialYears() {
        return new ResponseEntity(AppUtils.toJson(mwFinanceMasterService.getFinancialYears()), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_TAXABLE_TYPE, method = RequestMethod.POST)
    public ResponseEntity<PayPeriodCycleResp> getTaxableType() {
        return new ResponseEntity(AppUtils.toJson(mwFinanceMasterService.getTaxableType()), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_CREDIT_CYCLES, method = RequestMethod.POST)
    public ResponseEntity<PayPeriodCycleResp> getCreditCycles() {
        return new ResponseEntity(AppUtils.toJson(mwFinanceMasterService.getCreditCycles()), HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_FINANCE_TAX_CODES, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getFinanceTaxCodes(@RequestBody FinanceTaxCodesGetReq financeTaxCodesGetReq) {

        return new ResponseEntity<LabelKeyTOResp>(mwFinanceMasterService.getFinanceTaxCodes(financeTaxCodesGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_CODE_TYPES, method = RequestMethod.POST)
    public ResponseEntity<CodeTypesResp> deactivateCodeTypes(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
        return new ResponseEntity<CodeTypesResp>(mwFinanceMasterService.deactivateCodeTypes(financeTaxDelReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = FinanceURLConstants.GET_PROFIT_CENTRES, method = RequestMethod.POST)
    public ResponseEntity<ProfitCentreResp> getProfitCentres(@RequestBody ProfitCentreGetReq profitCentreGetReq) {
        return new ResponseEntity<ProfitCentreResp>(mwFinanceMasterService.getProfitCentres(profitCentreGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.GET_PROFIT_ITEMS, method = RequestMethod.POST)
    public ResponseEntity<ProfitCentreResp> getProfitCentreItems(@RequestBody ProfitCentreGetReq profitCentreGetReq) {
        return new ResponseEntity<ProfitCentreResp>(mwFinanceMasterService.getProfitCentreItems(profitCentreGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.SAVE_PROFIT_CENTRES, method = RequestMethod.POST)
    public ResponseEntity<ProfitCentreResp> saveProfitCentres(@RequestBody ProfitCentreSaveReq profitCentreSaveReq) {

        return new ResponseEntity<ProfitCentreResp>(mwFinanceMasterService.saveProfitCentres(profitCentreSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = FinanceURLConstants.DELETE_PROFIT_CENTRES, method = RequestMethod.POST)
    public ResponseEntity<ProfitCentreResp> deleteProfitCentres(@RequestBody ProfitCentreDelReq profitCentreDelReq) {
        return new ResponseEntity<ProfitCentreResp>(mwFinanceMasterService.deleteProfitCentres(profitCentreDelReq),
                HttpStatus.OK);

    }
    
    @RequestMapping(value = FinanceURLConstants.SAVE_VENDOR_POST_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<VendorInvocieResponse> saveVendorPostInvoice(
            @RequestBody VendorInvoiceRequest vendorInvoiceRequest) {
    	System.out.println("save vendor invoce");
    	mwFinanceMasterService.saveVendorPostInvoice(vendorInvoiceRequest);
    	VendorInvocieResponse response = new VendorInvocieResponse();
    	response.setMessage("success");
    	return new ResponseEntity<VendorInvocieResponse>(response, HttpStatus.OK);

    }
    
    @RequestMapping(value = FinanceURLConstants.GET_VENDOR_POST_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<VendorInvocieResponse> getVendorPostInvoice(@RequestBody GetVendorPostInvoiceRequest getVendorPostInvoiceRequest) {
        return new ResponseEntity<VendorInvocieResponse>(mwFinanceMasterService.getVendorPostInvoice(getVendorPostInvoiceRequest),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = FinanceURLConstants.INVOICE_TRACKING_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<VendorInvocieRecordResponse> getVendorInvoiceRecord(@RequestBody GetVendorPostInvoiceRequest getVendorPostInvoiceRequest) {
    	System.out.println("*********** getVendorInvoiceRecord  *******************  ");
        return new ResponseEntity<VendorInvocieRecordResponse>(mwFinanceMasterService.getVendorInvoiceRecord(getVendorPostInvoiceRequest),
                HttpStatus.OK);
    }
    @RequestMapping(value = FinanceURLConstants.GET_COST_CODES_BY_PROJIDS, method = RequestMethod.POST)
    public ResponseEntity<CostCodeResp> getCostCodeByProjIds(@RequestBody GetCostCodesReq getCostCodeReq) {
    	System.out.println("*********** getCostCodeByProjIds  *******************  ");
        return new ResponseEntity<CostCodeResp>(mwFinanceMasterService.getCostCodeByProjIds(getCostCodeReq),
                HttpStatus.OK);
    }
    @RequestMapping(value = FinanceURLConstants.GET_VENDOR_BANK_DETAILS_COMPANY_ID, method = RequestMethod.POST)
    public ResponseEntity<VendorBankDetailsTO> getVendorBankDetailsByPCompanyId(@RequestBody VendorBankDetailsReq vendorBankDetailsReq) {
    	System.out.println("*********** getCostCodeByProjIds  *******************  ");
        return new ResponseEntity<VendorBankDetailsTO>(mwFinanceMasterService.getVendorBankDetailsByPCompanyId(vendorBankDetailsReq),
                HttpStatus.OK);
    }
    

}
