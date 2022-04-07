package com.rjtech.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.finance.constants.FinanceURLConstants;
import com.rjtech.finance.req.CodeTypesSaveReq;
import com.rjtech.finance.req.CompanyTaxSaveReq;
import com.rjtech.finance.req.EmployeePayrollSaveReq;
import com.rjtech.finance.req.EmployeeTypeSaveReq;
import com.rjtech.finance.req.FinanceOnLoadReq;
import com.rjtech.finance.req.FinanceTaxCodesGetReq;
import com.rjtech.finance.req.FinanceTaxDelReq;
import com.rjtech.finance.req.FinanceTaxGetReq;
import com.rjtech.finance.req.MedicalLeaveSaveReq;
import com.rjtech.finance.req.NonRegularAllowanceSaveReq;
import com.rjtech.finance.req.PayDeductionSaveReq;
import com.rjtech.finance.req.PayPeriodCycleDelReq;
import com.rjtech.finance.req.PayPeriodCycleGetReq;
import com.rjtech.finance.req.PayPeriodCycleSaveReq;
import com.rjtech.finance.req.PaymentReceiverSaveReq;
import com.rjtech.finance.req.PersonalTaxSaveReq;
import com.rjtech.finance.req.PostInvoiceReq;
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
import com.rjtech.finance.resp.YearsResp;
import com.rjtech.finance.service.FinanceMasterService;

public class PostInvoiceController {

	 @Autowired
	    private FinanceMasterService financeMasterService;

	    @RequestMapping(value ="GET_POST_INVOCIE_DETAILS", method = RequestMethod.POST)
	    public ResponseEntity<UnitPayRateResp> getUnitOfRates(@RequestBody UnitPayRateGetReq unitPayRateGetReq) {
	        return new ResponseEntity<UnitPayRateResp>(financeMasterService.getUnitOfRates(unitPayRateGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = "SAVE_POST_INVOICE", method = RequestMethod.POST)
	    public ResponseEntity<UnitPayRateResp> saveUnitOfRates(@RequestBody PostInvoiceReq savePostInvoice) {
	       financeMasterService.savePostInvoiceDetails(savePostInvoice);
	         return new ResponseEntity<UnitPayRateResp>(HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_UNIT_OF_RATES, method = RequestMethod.POST)
	    public ResponseEntity<UnitPayRateResp> deleteUnitOfRates(@RequestBody UnitPayRateDelReq unitPayRateDelReq) {
	        financeMasterService.deleteUnitOfRates(unitPayRateDelReq);
	        UnitPayRateGetReq unitPayRateGetReq = new UnitPayRateGetReq();
	        unitPayRateGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        UnitPayRateResp unitPayRateResp = financeMasterService.getUnitOfRates(unitPayRateGetReq);
	        unitPayRateResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<UnitPayRateResp>(unitPayRateResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PAY_PERIOD_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<PayRollCycleResp> getPayPeriodCycle(@RequestBody PayPeriodCycleGetReq payPeriodCycleGetReq) {
	        return new ResponseEntity<PayRollCycleResp>(financeMasterService.getPayPeriodCycle(payPeriodCycleGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_PAY_PERIOD_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<PayRollCycleResp> savePayPeriodCycle(@RequestBody PayPeriodCycleSaveReq periodCycleSaveReq) {
	        financeMasterService.savePayPeriodCycle(periodCycleSaveReq);

	        PayPeriodCycleGetReq payPeriodCycleGetReq = new PayPeriodCycleGetReq();
	        payPeriodCycleGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PayRollCycleResp payRollCycleResp = financeMasterService.getPayPeriodCycle(payPeriodCycleGetReq);
	        payRollCycleResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<PayRollCycleResp>(payRollCycleResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_PAY_PERIOD_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<PayRollCycleResp> deletePayPeriodCycle(@RequestBody PayPeriodCycleDelReq periodCycleDelReq) {
	        financeMasterService.deletePayPeriodCycle(periodCycleDelReq);

	        PayPeriodCycleGetReq payPeriodCycleGetReq = new PayPeriodCycleGetReq();
	        payPeriodCycleGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PayRollCycleResp payRollCycleResp = financeMasterService.getPayPeriodCycle(payPeriodCycleGetReq);
	        payRollCycleResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<PayRollCycleResp>(payRollCycleResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_EMP_PAY_TYPE_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<EmpPayrollTypeResp> getEmpPayTypeCycle(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<EmpPayrollTypeResp>(financeMasterService.getEmpPayTypeCycle(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_EMP_TYPE_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<EmpPayrollTypeResp> saveEmpPayTypeCycle(
	            @RequestBody EmployeeTypeSaveReq employeeTypeSaveReq) {
	        financeMasterService.saveEmpPayTypeCycle(employeeTypeSaveReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        EmpPayrollTypeResp empPayrollTypeResp = financeMasterService.getEmpPayTypeCycle(financeTaxGetReq);
	        empPayrollTypeResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<EmpPayrollTypeResp>(empPayrollTypeResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_EMP_PAY_TYPE_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<EmpPayrollTypeResp> deleteEmpPayTypeCycle(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteEmpPayTypeCycle(financeTaxDelReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        EmpPayrollTypeResp empPayrollTypeResp = financeMasterService.getEmpPayTypeCycle(financeTaxGetReq);
	        empPayrollTypeResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<EmpPayrollTypeResp>(empPayrollTypeResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODES, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodesResp> getTaxCodes(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<TaxCodesResp>(financeMasterService.getTaxCodes(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_CODE_TYPES, method = RequestMethod.POST)
	    public ResponseEntity<CodeTypesResp> getCodeTypes(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<CodeTypesResp>(financeMasterService.getCodeTypes(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_CODE_TYPES, method = RequestMethod.POST)
	    public ResponseEntity<CodeTypesResp> saveCodeTypes(@RequestBody CodeTypesSaveReq codeTypesSaveReq) {
	        financeMasterService.saveCodeTypes(codeTypesSaveReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setTaxId(codeTypesSaveReq.getTaxId());
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        CodeTypesResp codeTypesResp = financeMasterService.getCodeTypes(financeTaxGetReq);
	        codeTypesResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<CodeTypesResp>(codeTypesResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_TAX_CODES, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodesResp> saveTaxCodes(@RequestBody TaxCodesSaveReq taxCodesSaveReq) {
	        financeMasterService.saveTaxCodes(taxCodesSaveReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TaxCodesResp taxCodesResp = financeMasterService.getTaxCodes(financeTaxGetReq);
	        taxCodesResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TaxCodesResp>(taxCodesResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_TAX_CODES, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodesResp> deactivateTaxCodes(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deactivateTaxCodes(financeTaxDelReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TaxCodesResp taxCodesResp = financeMasterService.getTaxCodes(financeTaxGetReq);
	        taxCodesResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TaxCodesResp>(taxCodesResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODES_BY_COUNTRY, method = RequestMethod.POST)
	    public ResponseEntity<TaxCountryProvisionResp> getTaxCodesByCountry(
	            @RequestBody TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
	        return new ResponseEntity<TaxCountryProvisionResp>(
	                financeMasterService.getTaxCodesByCountry(taxCountryProvisionGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAX_COUNTRY_PROVISIONS, method = RequestMethod.POST)
	    public ResponseEntity<TaxCountryProvisionResp> getTaxCountryProvision(
	            @RequestBody TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
	        return new ResponseEntity<TaxCountryProvisionResp>(
	                financeMasterService.getCountryProvision(taxCountryProvisionGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAX_COUNTRY_PROVISIONS_MAP, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodeCountryProviSionRespMap> getTaxCountryProvisionMap(@RequestBody TaxReq taxreq) {
	        return new ResponseEntity<TaxCodeCountryProviSionRespMap>(
	                financeMasterService.getTaxCountryProvisionMap(taxreq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_TAX_COUNTRY_PROVISIONS, method = RequestMethod.POST)
	    public ResponseEntity<TaxCountryProvisionResp> saveTaxCountryProvision(
	            @RequestBody TaxCountryProvisionSaveReq taxCountryProvisionSaveReq) {
	        Long id = financeMasterService.saveCountryProvision(taxCountryProvisionSaveReq);

	        TaxCountryProvisionGetReq taxCountryProvisionGetReq = new TaxCountryProvisionGetReq();
	        taxCountryProvisionGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        taxCountryProvisionGetReq.setId(id);
	        taxCountryProvisionGetReq
	                .setCountryName(taxCountryProvisionSaveReq.getTaxCountryProvisionTO().getCountryLabelKeyTO().getName());
	        TaxCountryProvisionResp taxCountryProvisionResp = financeMasterService
	                .getCountryProvision(taxCountryProvisionGetReq);
	        taxCountryProvisionResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TaxCountryProvisionResp>(taxCountryProvisionResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_TAX_COUNTRY_PROVISIONS, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodesResp> deactivateTaxCountryProvision(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deactivateTaxCodes(financeTaxDelReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TaxCodesResp taxCodesResp = financeMasterService.getTaxCodes(financeTaxGetReq);
	        taxCodesResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TaxCodesResp>(taxCodesResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODE_COUNTRY_PROVISIONS_ONLOAD, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodeCountryProvisionResp> getTaxCodeCountryProvisionsOnload(
	            @RequestBody FinanceTaxGetReq financeTaxGetReq) {

	        return new ResponseEntity<TaxCodeCountryProvisionResp>(
	                financeMasterService.getTaxCodeCountryProvisionOnLoad(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAX_CODE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodeCountryProvisionResp> getTaxCodeCountryProvisions(
	            @RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<TaxCodeCountryProvisionResp>(
	                financeMasterService.getTaxCodeCountryProvision(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_TAX_CODE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodeCountryProvisionResp> saveTaxCodeCountryProvision(
	            @RequestBody TaxCodeCountryProvisionSaveReq taxCodeCountryProvisionSaveReq) {
	        financeMasterService.saveTaxCodeCountryProvision(taxCodeCountryProvisionSaveReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setTaxId(taxCodeCountryProvisionSaveReq.getTaxId());
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TaxCodeCountryProvisionResp taxCodeCountryProvisionResp = financeMasterService
	                .getTaxCodeCountryProvision(financeTaxGetReq);
	        taxCodeCountryProvisionResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TaxCodeCountryProvisionResp>(taxCodeCountryProvisionResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_TAX_CODE_COUNTRY_PROVISIONS, method = RequestMethod.POST)
	    public ResponseEntity<TaxCodeCountryProvisionResp> deactivateTaxCodeCountryProvision(
	            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deactivateTaxCodeCountryProvision(financeTaxDelReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        TaxCodeCountryProvisionResp taxCodeCountryProvisionResp = financeMasterService
	                .getTaxCodeCountryProvision(financeTaxGetReq);
	        taxCodeCountryProvisionResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<TaxCodeCountryProvisionResp>(taxCodeCountryProvisionResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_REGULAR_ALLOWANCE, method = RequestMethod.POST)
	    public ResponseEntity<RegularAllowanceResp> getRegularAllowance(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<RegularAllowanceResp>(financeMasterService.getRegularAllowance(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_REGULAR_ALLOWANCE, method = RequestMethod.POST)
	    public ResponseEntity<RegularAllowanceResp> saveRegularAllowance(
	            @RequestBody RegularAllowanceSaveReq regularAllowanceSaveReq) {
	        financeMasterService.saveRegularAllowance(regularAllowanceSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        RegularAllowanceResp regularAllowanceResp = financeMasterService.getRegularAllowance(financeTaxGetReq);
	        regularAllowanceResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<RegularAllowanceResp>(regularAllowanceResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_REGULAR_ALLOWANCE, method = RequestMethod.POST)
	    public ResponseEntity<RegularAllowanceResp> deleteRegularAllowance(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteRegularAllowance(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        RegularAllowanceResp regularAllowanceResp = financeMasterService.getRegularAllowance(financeTaxGetReq);
	        regularAllowanceResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<RegularAllowanceResp>(regularAllowanceResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_NON_REGULAR_ALLOWANCE, method = RequestMethod.POST)
	    public ResponseEntity<NonRegularAllowanceResp> getNonRegularAllowance(
	            @RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<NonRegularAllowanceResp>(
	                financeMasterService.getNonRegularAllowance(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_NON_REGULAR_ALLOWANCE, method = RequestMethod.POST)
	    public ResponseEntity<NonRegularAllowanceResp> saveNonRegularAllowance(
	            @RequestBody NonRegularAllowanceSaveReq nonRegularAllowanceSaveReq) {
	        financeMasterService.saveNonRegularAllowance(nonRegularAllowanceSaveReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        NonRegularAllowanceResp nonRegularAllowanceResp = financeMasterService.getNonRegularAllowance(financeTaxGetReq);
	        nonRegularAllowanceResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<NonRegularAllowanceResp>(nonRegularAllowanceResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_NON_REGULAR_ALLOWANCE, method = RequestMethod.POST)
	    public ResponseEntity<NonRegularAllowanceResp> deleteNonRegularAllowance(
	            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteNonRegularAllowance(financeTaxDelReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        NonRegularAllowanceResp nonRegularAllowanceResp = financeMasterService.getNonRegularAllowance(financeTaxGetReq);
	        nonRegularAllowanceResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<NonRegularAllowanceResp>(nonRegularAllowanceResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PROVIDENT_FUND, method = RequestMethod.POST)
	    public ResponseEntity<ProvidentFundResp> getProvidentFund(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<ProvidentFundResp>(financeMasterService.getProvidentFund(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_PROVIDENT_FUND, method = RequestMethod.POST)
	    public ResponseEntity<ProvidentFundResp> saveProvidentFund(@RequestBody ProvidentFundSaveReq providentFundSaveReq) {
	        financeMasterService.saveProvidentFund(providentFundSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        ProvidentFundResp providentFundResp = financeMasterService.getProvidentFund(financeTaxGetReq);
	        providentFundResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<ProvidentFundResp>(providentFundResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_PROVIDENT_FUND, method = RequestMethod.POST)
	    public ResponseEntity<ProvidentFundResp> deleteProvidentFund(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteProvidentFund(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        ProvidentFundResp providentFundResp = financeMasterService.getProvidentFund(financeTaxGetReq);
	        providentFundResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<ProvidentFundResp>(providentFundResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PERSONAL_TAX_RATES, method = RequestMethod.POST)
	    public ResponseEntity<PersonalTaxResp> getPersonalTaxRates(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<PersonalTaxResp>(financeMasterService.getPersonalTaxRates(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_PERSONAL_TAX_RATES, method = RequestMethod.POST)
	    public ResponseEntity<PersonalTaxResp> savePersonalTaxRates(@RequestBody PersonalTaxSaveReq personalTaxSaveReq) {
	        financeMasterService.savePersonalTaxRates(personalTaxSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PersonalTaxResp personalTaxResp = financeMasterService.getPersonalTaxRates(financeTaxGetReq);
	        personalTaxResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<PersonalTaxResp>(personalTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_PERSONAL_TAX_RATES, method = RequestMethod.POST)
	    public ResponseEntity<PersonalTaxResp> deletePersonalTaxRates(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deletePersonalTaxRates(financeTaxDelReq);

	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PersonalTaxResp personalTaxResp = financeMasterService.getPersonalTaxRates(financeTaxGetReq);
	        personalTaxResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<PersonalTaxResp>(personalTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_COMPANY_TAX, method = RequestMethod.POST)
	    public ResponseEntity<CompanyTaxResp> getCompanyTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<CompanyTaxResp>(financeMasterService.getCompanyTax(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_COMPANY_TAX, method = RequestMethod.POST)
	    public ResponseEntity<CompanyTaxResp> saveCompanyTax(@RequestBody CompanyTaxSaveReq companyTaxSaveReq) {
	        financeMasterService.saveCompanyTax(companyTaxSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        CompanyTaxResp companyTaxResp = financeMasterService.getCompanyTax(financeTaxGetReq);
	        companyTaxResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<CompanyTaxResp>(companyTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_COMPANY_TAX, method = RequestMethod.POST)
	    public ResponseEntity<CompanyTaxResp> deleteCompanyTax(@RequestBody FinanceTaxDelReq companyTaxDelReq) {
	        financeMasterService.deleteCompanyTax(companyTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        CompanyTaxResp companyTaxResp = financeMasterService.getCompanyTax(financeTaxGetReq);
	        companyTaxResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<CompanyTaxResp>(companyTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_MEDICAL_LEAVE_TAX, method = RequestMethod.POST)
	    public ResponseEntity<MedicalLeaveTaxResp> getMedicalLeaveTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<MedicalLeaveTaxResp>(financeMasterService.getMedicalLeaveTax(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_MEDICAL_LEAVE_TAX, method = RequestMethod.POST)
	    public ResponseEntity<MedicalLeaveTaxResp> saveMedicalLeaveTax(
	            @RequestBody MedicalLeaveSaveReq medicalLeaveSaveReq) {
	        financeMasterService.saveMedicalLeaveTax(medicalLeaveSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        MedicalLeaveTaxResp medicalLeaveResp = financeMasterService.getMedicalLeaveTax(financeTaxGetReq);
	        medicalLeaveResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<MedicalLeaveTaxResp>(medicalLeaveResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_MEDICAL_LEAVE_TAX, method = RequestMethod.POST)
	    public ResponseEntity<MedicalLeaveTaxResp> deleteMedicalLeaveTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteMedicalLeaveTax(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        MedicalLeaveTaxResp medicalLeaveResp = financeMasterService.getMedicalLeaveTax(financeTaxGetReq);
	        medicalLeaveResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<MedicalLeaveTaxResp>(medicalLeaveResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_EMPLOYEE_PAYROLL, method = RequestMethod.POST)
	    public ResponseEntity<EmployeePayrollTaxResp> getEmployeePayroll(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<EmployeePayrollTaxResp>(financeMasterService.getEmployeePayrollTax(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_EMPLOYEE_PAYROLL, method = RequestMethod.POST)
	    public ResponseEntity<EmployeePayrollTaxResp> saveEmployeePayroll(
	            @RequestBody EmployeePayrollSaveReq employeePayrollSaveReq) {
	        financeMasterService.saveEmployeePayroll(employeePayrollSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        EmployeePayrollTaxResp employeePayrollResp = financeMasterService.getEmployeePayrollTax(financeTaxGetReq);
	        employeePayrollResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<EmployeePayrollTaxResp>(employeePayrollResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_EMPLOYEE_PAYROLL, method = RequestMethod.POST)
	    public ResponseEntity<EmployeePayrollTaxResp> deleteEmployeePayroll(
	            @RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteEmployeePayroll(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        EmployeePayrollTaxResp employeePayrollResp = financeMasterService.getEmployeePayrollTax(financeTaxGetReq);
	        employeePayrollResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<EmployeePayrollTaxResp>(employeePayrollResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PAY_DEDUCTION, method = RequestMethod.POST)
	    public ResponseEntity<PayDeductionResp> getPayDeduction(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<PayDeductionResp>(financeMasterService.getPayDeduction(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_PAY_DEDUCTION, method = RequestMethod.POST)
	    public ResponseEntity<PayDeductionResp> savePayDeduction(@RequestBody PayDeductionSaveReq payDeductionSaveReq) {
	        financeMasterService.savePayDeduction(payDeductionSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PayDeductionResp deductionResp = financeMasterService.getPayDeduction(financeTaxGetReq);
	        deductionResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<PayDeductionResp>(deductionResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_PAY_DEDUCTION, method = RequestMethod.POST)
	    public ResponseEntity<PayDeductionResp> deletePayDeduction(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deletePayDeduction(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PayDeductionResp payDeductionResp = financeMasterService.getPayDeduction(financeTaxGetReq);
	        payDeductionResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<PayDeductionResp>(payDeductionResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_SUPERFUND_TAX, method = RequestMethod.POST)
	    public ResponseEntity<SuperFundTaxResp> getSuperfundTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<SuperFundTaxResp>(financeMasterService.getSuperfundTax(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_SUPERFUND_TAX, method = RequestMethod.POST)
	    public ResponseEntity<SuperFundTaxResp> saveSuperfundTax(@RequestBody SuperFundTaxSaveReq superFundTaxSaveReq) {
	        financeMasterService.saveSuperfundTax(superFundTaxSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        SuperFundTaxResp superFundTaxResp = financeMasterService.getSuperfundTax(financeTaxGetReq);
	        superFundTaxResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<SuperFundTaxResp>(superFundTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_SUPERFUND_TAX, method = RequestMethod.POST)
	    public ResponseEntity<SuperFundTaxResp> deleteSuperfundTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteSuperfundTax(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        SuperFundTaxResp superFundTaxResp = financeMasterService.getSuperfundTax(financeTaxGetReq);
	        superFundTaxResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<SuperFundTaxResp>(superFundTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_SERVICE_TAX, method = RequestMethod.POST)
	    public ResponseEntity<ServiceTaxResp> getServiceTax(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<ServiceTaxResp>(financeMasterService.getServiceTax(financeTaxGetReq), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_SERVICE_TAX, method = RequestMethod.POST)
	    public ResponseEntity<ServiceTaxResp> saveServiceTax(@RequestBody ServiceTaxSaveReq serviceTaxSaveReq) {
	        financeMasterService.saveServiceTax(serviceTaxSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        ServiceTaxResp serviceTaxResp = financeMasterService.getServiceTax(financeTaxGetReq);
	        serviceTaxResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<ServiceTaxResp>(serviceTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_SERVICE_TAX, method = RequestMethod.POST)
	    public ResponseEntity<ServiceTaxResp> deleteServiceTax(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deleteServiceTax(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        ServiceTaxResp serviceTaxResp = financeMasterService.getServiceTax(financeTaxGetReq);
	        serviceTaxResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<ServiceTaxResp>(serviceTaxResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PAYMENT_RECEIVER, method = RequestMethod.POST)
	    public ResponseEntity<PaymentReceiverResp> getPaymentReceiver(@RequestBody FinanceTaxGetReq financeTaxGetReq) {
	        return new ResponseEntity<PaymentReceiverResp>(financeMasterService.getPaymentReceiver(financeTaxGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_PAYMENT_RECEIVER, method = RequestMethod.POST)
	    public ResponseEntity<PaymentReceiverResp> savePaymentReceiver(
	            @RequestBody PaymentReceiverSaveReq paymentReceiverSaveReq) {
	        financeMasterService.savePaymentReceiver(paymentReceiverSaveReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PaymentReceiverResp paymentReceiverResp = financeMasterService.getPaymentReceiver(financeTaxGetReq);
	        paymentReceiverResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<PaymentReceiverResp>(paymentReceiverResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_PAYMENT_RECEIVER, method = RequestMethod.POST)
	    public ResponseEntity<PaymentReceiverResp> deletePaymentReceiver(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deletePaymentReceiver(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        PaymentReceiverResp paymentReceiverResp = financeMasterService.getPaymentReceiver(financeTaxGetReq);
	        paymentReceiverResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<PaymentReceiverResp>(paymentReceiverResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_ONLOAD_FOR_FINANCE, method = RequestMethod.POST)
	    public ResponseEntity<FinanceOnLoadResp> getOnLoadForFinance(@RequestBody FinanceOnLoadReq financeOnLoadReq) {
	        FinanceOnLoadResp financeOnLoadResp = new FinanceOnLoadResp();

	        return new ResponseEntity<FinanceOnLoadResp>(financeOnLoadResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PAY_ROLL_CYCLE, method = RequestMethod.POST)
	    public ResponseEntity<PayPeriodCycleResp> getPayRollCycle() {
	        return new ResponseEntity<PayPeriodCycleResp>(financeMasterService.getPayRollCycle(), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_YEARS, method = RequestMethod.POST)
	    public ResponseEntity<YearsResp> getFinancialYears() {
	        return new ResponseEntity<YearsResp>(financeMasterService.getFinancialYears(), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_TAXABLE_TYPE, method = RequestMethod.POST)
	    public ResponseEntity<PayPeriodCycleResp> getTaxableType() {
	        return new ResponseEntity<PayPeriodCycleResp>(financeMasterService.getTaxableType(), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_CREDIT_CYCLES, method = RequestMethod.POST)
	    public ResponseEntity<PayPeriodCycleResp> getCreditCycles() {
	        return new ResponseEntity<PayPeriodCycleResp>(financeMasterService.getCreditCycles(), HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_FINANCE_TAX_CODES, method = RequestMethod.POST)
	    public ResponseEntity<LabelKeyTOResp> getFinanceTaxCodes(@RequestBody FinanceTaxCodesGetReq financeTaxCodesGetReq) {

	        return new ResponseEntity<LabelKeyTOResp>(financeMasterService.getFinanceTaxCodes(financeTaxCodesGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DEACTIVATE_CODE_TYPES, method = RequestMethod.POST)
	    public ResponseEntity<CodeTypesResp> deactivateCodeTypes(@RequestBody FinanceTaxDelReq financeTaxDelReq) {
	        financeMasterService.deactivateCodeTypes(financeTaxDelReq);
	        FinanceTaxGetReq financeTaxGetReq = new FinanceTaxGetReq();
	        financeTaxGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        CodeTypesResp codeTypesResp = financeMasterService.getCodeTypes(financeTaxGetReq);
	        codeTypesResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<CodeTypesResp>(codeTypesResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PROFIT_CENTRES, method = RequestMethod.POST)
	    public ResponseEntity<ProfitCentreResp> getProfitCentres(@RequestBody ProfitCentreGetReq profitCentreGetReq) {
	        return new ResponseEntity<ProfitCentreResp>(financeMasterService.getProfitCentres(profitCentreGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.GET_PROFIT_ITEMS, method = RequestMethod.POST)
	    public ResponseEntity<ProfitCentreResp> getProfitCentreItems(@RequestBody ProfitCentreGetReq profitCentreGetReq) {
	        return new ResponseEntity<ProfitCentreResp>(financeMasterService.getProfitCentreItems(profitCentreGetReq),
	                HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.SAVE_PROFIT_CENTRES, method = RequestMethod.POST)
	    public ResponseEntity<ProfitCentreResp> saveProfitCentres(@RequestBody ProfitCentreSaveReq profitCentreSaveReq) {
	        financeMasterService.saveProfitCentres(profitCentreSaveReq);
	        ProfitCentreGetReq profitCentreGetReq = new ProfitCentreGetReq();
	        profitCentreGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        ProfitCentreResp profitCentreResp = financeMasterService.getProfitCentres(profitCentreGetReq);
	        profitCentreResp.cloneAppResp(CommonUtil.getSaveAppResp());
	        return new ResponseEntity<ProfitCentreResp>(profitCentreResp, HttpStatus.OK);
	    }

	    @RequestMapping(value = FinanceURLConstants.DELETE_PROFIT_CENTRES, method = RequestMethod.POST)
	    public ResponseEntity<ProfitCentreResp> deleteProfitCentres(@RequestBody ProfitCentreDelReq profitCentreDelReq) {
	        financeMasterService.deleteProfitCentres(profitCentreDelReq);
	        ProfitCentreGetReq profitCentreGetReq = new ProfitCentreGetReq();
	        profitCentreGetReq.setStatus(StatusCodes.ACTIVE.getValue());
	        ProfitCentreResp profitCentreResp = financeMasterService.getProfitCentres(profitCentreGetReq);
	        profitCentreResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
	        return new ResponseEntity<ProfitCentreResp>(profitCentreResp, HttpStatus.OK);
	    }
}
