package com.rjtech.mw.service.impl.finance;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.finance.constants.FinanceURLConstants;
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
import com.rjtech.mw.service.finance.MWFinanceMasterService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwFinanceMasterService")
@RJSService(modulecode = "mwFinanceMasterService")
@Transactional
public class MWFinanceMasterServiceImpl extends RestConfigServiceImpl implements MWFinanceMasterService {

    public UnitPayRateResp getUnitOfRates(UnitPayRateGetReq unitPayRateGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(unitPayRateGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_UNIT_OF_RATES);
        return AppUtils.fromJson(strResponse.getBody(), UnitPayRateResp.class);
    }

    public UnitPayRateResp saveUnitOfRates(UnitPayRateSaveReq unitPayRateSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(unitPayRateSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_UNIT_OF_RATES);
        return AppUtils.fromJson(strResponse.getBody(), UnitPayRateResp.class);
    }

    public UnitPayRateResp deleteUnitOfRates(UnitPayRateDelReq unitPayRateDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(unitPayRateDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_UNIT_OF_RATES);
        return AppUtils.fromJson(strResponse.getBody(), UnitPayRateResp.class);
    }

    public TaxCodesResp getTaxCodes(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAX_CODES);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodesResp.class);
    }

    public TaxCodesResp saveTaxCodes(TaxCodesSaveReq taxCodesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(taxCodesSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_TAX_CODES);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodesResp.class);
    }

    public TaxCodesResp deleteTaxCodes(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DEACTIVATE_TAX_CODES);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodesResp.class);
    }

    public TaxCountryProvisionResp getTaxCodesByCountry(TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(taxCountryProvisionGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAX_CODES_BY_COUNTRY);
        return AppUtils.fromJson(strResponse.getBody(), TaxCountryProvisionResp.class);
    }

    public TaxCountryProvisionResp getTaxCountryProvision(TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(taxCountryProvisionGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAX_COUNTRY_PROVISIONS);
        return AppUtils.fromJson(strResponse.getBody(), TaxCountryProvisionResp.class);
    }

    public TaxCountryProvisionResp saveTaxCountryProvision(TaxCountryProvisionSaveReq countryProvisionSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(countryProvisionSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_TAX_COUNTRY_PROVISIONS);
        return AppUtils.fromJson(strResponse.getBody(), TaxCountryProvisionResp.class);
    }

    public TaxCountryProvisionResp deleteTaxCountryProvision(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DEACTIVATE_TAX_COUNTRY_PROVISIONS);
        return AppUtils.fromJson(strResponse.getBody(), TaxCountryProvisionResp.class);
    }

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvisionOnLoad(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAX_CODE_COUNTRY_PROVISIONS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodeCountryProvisionResp.class);
    }

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvision(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAX_CODE_COUNTRY_PROVISIONS);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodeCountryProvisionResp.class);
    }

    public TaxCodeCountryProviSionRespMap getTaxCountryProvisionMap(TaxReq taxReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(taxReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAX_COUNTRY_PROVISIONS_MAP);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodeCountryProviSionRespMap.class);
    }

    public TaxCodeCountryProvisionResp saveTaxCodeCountryProvision(
            TaxCodeCountryProvisionSaveReq taxCodeCountryProvisionSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(taxCodeCountryProvisionSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_TAX_CODE_COUNTRY_PROVISIONS);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodeCountryProvisionResp.class);
    }

    public TaxCodeCountryProvisionResp deleteTaxCodeCountryProvision(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DEACTIVATE_TAX_CODE_COUNTRY_PROVISIONS);
        return AppUtils.fromJson(strResponse.getBody(), TaxCodeCountryProvisionResp.class);
    }

    public RegularAllowanceResp getRegularAllowance(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_REGULAR_ALLOWANCE);
        return AppUtils.fromJson(strResponse.getBody(), RegularAllowanceResp.class);
    }

    public RegularAllowanceResp saveRegularAllowance(RegularAllowanceSaveReq regularAllowanceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(regularAllowanceSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_REGULAR_ALLOWANCE);
        return AppUtils.fromJson(strResponse.getBody(), RegularAllowanceResp.class);
    }

    public RegularAllowanceResp deleteRegularAllowance(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_REGULAR_ALLOWANCE);
        return AppUtils.fromJson(strResponse.getBody(), RegularAllowanceResp.class);
    }

    public NonRegularAllowanceResp getNonRegularAllowance(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_NON_REGULAR_ALLOWANCE);
        return AppUtils.fromJson(strResponse.getBody(), NonRegularAllowanceResp.class);
    }

    public NonRegularAllowanceResp saveNonRegularAllowance(NonRegularAllowanceSaveReq nonRegularAllowanceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(nonRegularAllowanceSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_NON_REGULAR_ALLOWANCE);
        return AppUtils.fromJson(strResponse.getBody(), NonRegularAllowanceResp.class);
    }

    public NonRegularAllowanceResp deleteNonRegularAllowance(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_NON_REGULAR_ALLOWANCE);
        return AppUtils.fromJson(strResponse.getBody(), NonRegularAllowanceResp.class);
    }

    public PersonalTaxResp getPersonalTaxRates(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PERSONAL_TAX_RATES);
        return AppUtils.fromJson(strResponse.getBody(), PersonalTaxResp.class);
    }

    public PersonalTaxResp savePersonalTaxRates(PersonalTaxSaveReq personalTaxSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(personalTaxSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_PERSONAL_TAX_RATES);
        return AppUtils.fromJson(strResponse.getBody(), PersonalTaxResp.class);
    }

    public PersonalTaxResp deletePersonalTaxRates(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_PERSONAL_TAX_RATES);
        return AppUtils.fromJson(strResponse.getBody(), PersonalTaxResp.class);
    }

    public CompanyTaxResp getCompanyTax(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_COMPANY_TAX);
        return AppUtils.fromJson(strResponse.getBody(), CompanyTaxResp.class);
    }

    public CompanyTaxResp saveCompanyTax(CompanyTaxSaveReq companyTaxSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(companyTaxSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_COMPANY_TAX);
        return AppUtils.fromJson(strResponse.getBody(), CompanyTaxResp.class);
    }

    public CompanyTaxResp deleteCompanyTax(FinanceTaxDelReq companyTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(companyTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_COMPANY_TAX);
        return AppUtils.fromJson(strResponse.getBody(), CompanyTaxResp.class);
    }

    public MedicalLeaveTaxResp getMedicalLeaveTax(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_MEDICAL_LEAVE_TAX);
        return AppUtils.fromJson(strResponse.getBody(), MedicalLeaveTaxResp.class);
    }

    public MedicalLeaveTaxResp saveMedicalLeaveTax(MedicalLeaveSaveReq medicalLeaveSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(medicalLeaveSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_MEDICAL_LEAVE_TAX);
        return AppUtils.fromJson(strResponse.getBody(), MedicalLeaveTaxResp.class);
    }

    public MedicalLeaveTaxResp deleteMedicalLeaveTax(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_MEDICAL_LEAVE_TAX);
        return AppUtils.fromJson(strResponse.getBody(), MedicalLeaveTaxResp.class);
    }

    public EmployeePayrollTaxResp getEmployeePayrollTax(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_EMPLOYEE_PAYROLL);
        return AppUtils.fromJson(strResponse.getBody(), EmployeePayrollTaxResp.class);
    }

    public EmployeePayrollTaxResp saveEmployeePayrollTax(EmployeePayrollSaveReq employeePayrollSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(employeePayrollSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_EMPLOYEE_PAYROLL);
        return AppUtils.fromJson(strResponse.getBody(), EmployeePayrollTaxResp.class);
    }

    public EmployeePayrollTaxResp deleteEmployeePayrollTax(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_EMPLOYEE_PAYROLL);
        return AppUtils.fromJson(strResponse.getBody(), EmployeePayrollTaxResp.class);
    }

    public ServiceTaxResp getServiceTax(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_SERVICE_TAX);
        return AppUtils.fromJson(strResponse.getBody(), ServiceTaxResp.class);
    }

    public ServiceTaxResp saveServiceTax(ServiceTaxSaveReq serviceTaxSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(serviceTaxSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_SERVICE_TAX);
        return AppUtils.fromJson(strResponse.getBody(), ServiceTaxResp.class);
    }

    public ServiceTaxResp deleteServiceTax(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_SERVICE_TAX);
        return AppUtils.fromJson(strResponse.getBody(), ServiceTaxResp.class);
    }

    public SuperFundTaxResp getSuperfundTax(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_SUPERFUND_TAX);
        return AppUtils.fromJson(strResponse.getBody(), SuperFundTaxResp.class);
    }

    public SuperFundTaxResp saveSuperfundTax(SuperFundTaxSaveReq superFundTaxSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(superFundTaxSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_SUPERFUND_TAX);
        return AppUtils.fromJson(strResponse.getBody(), SuperFundTaxResp.class);
    }

    public SuperFundTaxResp deleteSuperfundTax(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_SUPERFUND_TAX);
        return AppUtils.fromJson(strResponse.getBody(), SuperFundTaxResp.class);
    }

    public PayDeductionResp getPayDeduction(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PAY_DEDUCTION);
        return AppUtils.fromJson(strResponse.getBody(), PayDeductionResp.class);
    }

    public PayDeductionResp savePayDeduction(PayDeductionSaveReq payDeductionSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(payDeductionSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_PAY_DEDUCTION);
        return AppUtils.fromJson(strResponse.getBody(), PayDeductionResp.class);
    }

    public PayDeductionResp deletePayDeduction(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_PAY_DEDUCTION);
        return AppUtils.fromJson(strResponse.getBody(), PayDeductionResp.class);
    }

    public PaymentReceiverResp getPaymentReceiver(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PAYMENT_RECEIVER);
        return AppUtils.fromJson(strResponse.getBody(), PaymentReceiverResp.class);
    }

    public PaymentReceiverResp savePaymentReceiver(PaymentReceiverSaveReq paymentReceiverSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(paymentReceiverSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_PAYMENT_RECEIVER);
        return AppUtils.fromJson(strResponse.getBody(), PaymentReceiverResp.class);
    }

    public PaymentReceiverResp deletePaymentReceiver(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_PAYMENT_RECEIVER);
        return AppUtils.fromJson(strResponse.getBody(), PaymentReceiverResp.class);
    }

    public ProvidentFundResp getProvidentFund(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PROVIDENT_FUND);
        return AppUtils.fromJson(strResponse.getBody(), ProvidentFundResp.class);
    }

    public ProvidentFundResp saveProvidentFund(ProvidentFundSaveReq providentFundSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(providentFundSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_PROVIDENT_FUND);
        return AppUtils.fromJson(strResponse.getBody(), ProvidentFundResp.class);
    }

    public ProvidentFundResp deleteProvidentFund(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_PROVIDENT_FUND);
        return AppUtils.fromJson(strResponse.getBody(), ProvidentFundResp.class);
    }

    public FinanceOnLoadResp financeOnLoadResp(FinanceOnLoadReq financeOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeOnLoadReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_ONLOAD_FOR_FINANCE);
        return AppUtils.fromJson(strResponse.getBody(), FinanceOnLoadResp.class);
    }

    public PayPeriodCycleResp getPayRollCycles() {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(null,
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PAY_ROLL_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), PayPeriodCycleResp.class);
    }

    public YearsResp getFinancialYears()

    {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(null, FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_YEARS);
        return AppUtils.fromJson(strResponse.getBody(), YearsResp.class);

    }

    public PayPeriodCycleResp getTaxableType() {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(null,
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAXABLE_TYPE);
        return AppUtils.fromJson(strResponse.getBody(), PayPeriodCycleResp.class);
    }

    public PayRollCycleResp getPayPeriodCycle(PayPeriodCycleGetReq periodCycleGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(periodCycleGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PAY_PERIOD_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), PayRollCycleResp.class);
    }

    public PayRollCycleResp savePayPeriodCycle(PayPeriodCycleSaveReq periodCycleSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(periodCycleSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_PAY_PERIOD_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), PayRollCycleResp.class);
    }

    public PayRollCycleResp deletePayPeriodCycle(PayPeriodCycleDelReq periodCycleDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(periodCycleDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_PAY_PERIOD_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), PayRollCycleResp.class);
    }

    public EmpPayrollTypeResp getEmpPayTypeCycle(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_EMP_PAY_TYPE_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayrollTypeResp.class);
    }

    public EmpPayrollTypeResp saveEmpPayTypeCycle(EmployeeTypeSaveReq employeeTypeSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(employeeTypeSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_EMP_TYPE_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayrollTypeResp.class);
    }

    public EmpPayrollTypeResp deleteEmpPayTypeCycle(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_EMP_PAY_TYPE_CYCLE);
        return AppUtils.fromJson(strResponse.getBody(), EmpPayrollTypeResp.class);
    }

    public PayPeriodCycleResp getCreditCycles() {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(null,
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_CREDIT_CYCLES);
        return AppUtils.fromJson(strResponse.getBody(), PayPeriodCycleResp.class);
    }

    public CodeTypesResp getCodeTypes(FinanceTaxGetReq financeTaxGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_CODE_TYPES);
        return AppUtils.fromJson(strResponse.getBody(), CodeTypesResp.class);
    }

    public LabelKeyTOResp getFinanceTaxCodes(FinanceTaxCodesGetReq financeTaxCodesGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxCodesGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_FINANCE_TAX_CODES);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public CodeTypesResp saveCodeTypes(CodeTypesSaveReq codeTypesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(codeTypesSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_CODE_TYPES);
        return AppUtils.fromJson(strResponse.getBody(), CodeTypesResp.class);
    }

    public CodeTypesResp deactivateCodeTypes(FinanceTaxDelReq financeTaxDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(financeTaxDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DEACTIVATE_CODE_TYPES);
        return AppUtils.fromJson(strResponse.getBody(), CodeTypesResp.class);
    }

    public ProfitCentreResp getProfitCentres(ProfitCentreGetReq profitCentreGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(profitCentreGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PROFIT_CENTRES);
        return AppUtils.fromJson(strResponse.getBody(), ProfitCentreResp.class);
    }

    public ProfitCentreResp getProfitCentreItems(ProfitCentreGetReq profitCentreGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(profitCentreGetReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_PROFIT_ITEMS);
        return AppUtils.fromJson(strResponse.getBody(), ProfitCentreResp.class);
    }

    public ProfitCentreResp saveProfitCentres(ProfitCentreSaveReq profitCentreSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(profitCentreSaveReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_PROFIT_CENTRES);
        return AppUtils.fromJson(strResponse.getBody(), ProfitCentreResp.class);
    }

    public ProfitCentreResp deleteProfitCentres(ProfitCentreDelReq profitCentreDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(profitCentreDelReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.DELETE_PROFIT_CENTRES);
        return AppUtils.fromJson(strResponse.getBody(), ProfitCentreResp.class);
    }

	@Override
	public Object saveVendorPostInvoice(VendorInvoiceRequest vendorInvoiceRequest) {
		ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(vendorInvoiceRequest),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.SAVE_VENDOR_POST_INVOICE);
		return "success";
	}

	@Override
	public VendorInvocieResponse getVendorPostInvoice(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest) {
		System.out.println("222222 ==>>>   "+ FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_VENDOR_POST_INVOICE);
		ResponseEntity<String> strResponse  = getFinancePOSTRestTemplate(AppUtils.toJson(getVendorPostInvoiceRequest),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_VENDOR_POST_INVOICE);
		System.out.println("strResponse 11111111111111111111111111   "+strResponse);
		VendorInvocieResponse response = new VendorInvocieResponse();
		return AppUtils.fromJson(strResponse.getBody(), VendorInvocieResponse.class);
		//return response;
	}
	@Override
	public VendorInvocieRecordResponse getVendorInvoiceRecord(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest) {
		 ResponseEntity<String> strResponse = null;
	        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(getVendorPostInvoiceRequest),
	                FinanceURLConstants.PARH_URL + FinanceURLConstants.INVOICE_TRACKING_RECORDS);
	        return AppUtils.fromJson(strResponse.getBody(), VendorInvocieRecordResponse.class);
	}

	@Override
	public CostCodeResp getCostCodeByProjIds(GetCostCodesReq getCostCodeReq) {
		 ResponseEntity<String> strResponse = null;
	        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(getCostCodeReq),
	                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_COST_CODES_BY_PROJIDS);
	        return AppUtils.fromJson(strResponse.getBody(), CostCodeResp.class);
	}

	@Override
	public VendorBankDetailsTO getVendorBankDetailsByPCompanyId(VendorBankDetailsReq vendorBankDetailsReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson( vendorBankDetailsReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_VENDOR_BANK_DETAILS_COMPANY_ID);
        return AppUtils.fromJson(strResponse.getBody(), VendorBankDetailsTO.class);
	}
}
