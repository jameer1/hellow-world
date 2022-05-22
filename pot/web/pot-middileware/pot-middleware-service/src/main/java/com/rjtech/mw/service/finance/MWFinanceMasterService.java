package com.rjtech.mw.service.finance;

import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.common.resp.LabelKeyTOResp;
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

public interface MWFinanceMasterService {

    public PayPeriodCycleResp getPayRollCycles();

    public YearsResp getFinancialYears();

    public PayPeriodCycleResp getTaxableType();

    public PayPeriodCycleResp getCreditCycles();

    public UnitPayRateResp getUnitOfRates(UnitPayRateGetReq unitPayRateGetReq);

    public UnitPayRateResp saveUnitOfRates(UnitPayRateSaveReq unitPayRateSaveReq);

    public UnitPayRateResp deleteUnitOfRates(UnitPayRateDelReq unitPayRateDelReq);

    public PayRollCycleResp getPayPeriodCycle(PayPeriodCycleGetReq periodCycleGetReq);

    public PayRollCycleResp savePayPeriodCycle(PayPeriodCycleSaveReq periodCycleSaveReq);

    public PayRollCycleResp deletePayPeriodCycle(PayPeriodCycleDelReq periodCycleDelReq);

    public EmpPayrollTypeResp getEmpPayTypeCycle(FinanceTaxGetReq financeTaxGetReq);

    public EmpPayrollTypeResp saveEmpPayTypeCycle(EmployeeTypeSaveReq employeeTypeSaveReq);

    public EmpPayrollTypeResp deleteEmpPayTypeCycle(FinanceTaxDelReq financeTaxDelReq);

    public TaxCodesResp getTaxCodes(FinanceTaxGetReq financeTaxGetReq);

    public CodeTypesResp getCodeTypes(FinanceTaxGetReq financeTaxGetReq);

    public TaxCodesResp saveTaxCodes(TaxCodesSaveReq taxCodesSaveReq);

    public CodeTypesResp saveCodeTypes(CodeTypesSaveReq codeTypesSaveReq);

    public CodeTypesResp deactivateCodeTypes(FinanceTaxDelReq financeTaxDelReq);

    public TaxCodesResp deleteTaxCodes(FinanceTaxDelReq financeTaxDelReq);

    public TaxCountryProvisionResp getTaxCodesByCountry(TaxCountryProvisionGetReq taxCountryProvisionGetReq);

    public TaxCountryProvisionResp getTaxCountryProvision(TaxCountryProvisionGetReq taxCountryProvisionGetReq);

    public TaxCodeCountryProviSionRespMap getTaxCountryProvisionMap(TaxReq taxReq);

    public TaxCountryProvisionResp saveTaxCountryProvision(TaxCountryProvisionSaveReq countryProvisionSaveReq);

    public TaxCountryProvisionResp deleteTaxCountryProvision(FinanceTaxDelReq financeTaxDelReq);

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvisionOnLoad(FinanceTaxGetReq financeTaxGetReq);

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvision(FinanceTaxGetReq financeTaxGetReq);

    public TaxCodeCountryProvisionResp saveTaxCodeCountryProvision(
            TaxCodeCountryProvisionSaveReq taxCodeCountryProvisionSaveReq);

    public TaxCodeCountryProvisionResp deleteTaxCodeCountryProvision(FinanceTaxDelReq financeTaxDelReq);

    public RegularAllowanceResp getRegularAllowance(FinanceTaxGetReq financeTaxGetReq);

    public RegularAllowanceResp saveRegularAllowance(RegularAllowanceSaveReq regularAllowanceSaveReq);

    public RegularAllowanceResp deleteRegularAllowance(FinanceTaxDelReq financeTaxDelReq);

    public NonRegularAllowanceResp getNonRegularAllowance(FinanceTaxGetReq financeTaxGetReq);

    public NonRegularAllowanceResp saveNonRegularAllowance(NonRegularAllowanceSaveReq nonRegularAllowanceSaveReq);

    public NonRegularAllowanceResp deleteNonRegularAllowance(FinanceTaxDelReq financeTaxDelReq);

    public ProvidentFundResp getProvidentFund(FinanceTaxGetReq financeTaxGetReq);

    public ProvidentFundResp saveProvidentFund(ProvidentFundSaveReq providentFundSaveReq);

    public ProvidentFundResp deleteProvidentFund(FinanceTaxDelReq financeTaxDelReq);

    public PersonalTaxResp getPersonalTaxRates(FinanceTaxGetReq financeTaxGetReq);

    public PersonalTaxResp savePersonalTaxRates(PersonalTaxSaveReq personalTaxSaveReq);

    public PersonalTaxResp deletePersonalTaxRates(FinanceTaxDelReq financeTaxDelReq);

    public CompanyTaxResp getCompanyTax(FinanceTaxGetReq financeTaxGetReq);

    public CompanyTaxResp saveCompanyTax(CompanyTaxSaveReq companyTaxSaveReq);

    public CompanyTaxResp deleteCompanyTax(FinanceTaxDelReq companyTaxDelReq);

    public MedicalLeaveTaxResp getMedicalLeaveTax(FinanceTaxGetReq financeTaxGetReq);

    public MedicalLeaveTaxResp saveMedicalLeaveTax(MedicalLeaveSaveReq medicalLeaveSaveReq);

    public MedicalLeaveTaxResp deleteMedicalLeaveTax(FinanceTaxDelReq financeTaxDelReq);

    public EmployeePayrollTaxResp getEmployeePayrollTax(FinanceTaxGetReq financeTaxGetReq);

    public EmployeePayrollTaxResp saveEmployeePayrollTax(EmployeePayrollSaveReq employeePayrollSaveReq);

    public EmployeePayrollTaxResp deleteEmployeePayrollTax(FinanceTaxDelReq financeTaxDelReq);

    public ServiceTaxResp getServiceTax(FinanceTaxGetReq financeTaxGetReq);

    public ServiceTaxResp saveServiceTax(ServiceTaxSaveReq serviceTaxSaveReq);

    public ServiceTaxResp deleteServiceTax(FinanceTaxDelReq financeTaxDelReq);

    public SuperFundTaxResp getSuperfundTax(FinanceTaxGetReq financeTaxGetReq);

    public SuperFundTaxResp saveSuperfundTax(SuperFundTaxSaveReq superFundTaxSaveReq);

    public SuperFundTaxResp deleteSuperfundTax(FinanceTaxDelReq financeTaxDelReq);

    public PayDeductionResp getPayDeduction(FinanceTaxGetReq financeTaxGetReq);

    public PayDeductionResp savePayDeduction(PayDeductionSaveReq payDeductionSaveReq);

    public PayDeductionResp deletePayDeduction(FinanceTaxDelReq financeTaxDelReq);

    public PaymentReceiverResp getPaymentReceiver(FinanceTaxGetReq financeTaxGetReq);

    public PaymentReceiverResp savePaymentReceiver(PaymentReceiverSaveReq paymentReceiverSaveReq);

    public PaymentReceiverResp deletePaymentReceiver(FinanceTaxDelReq financeTaxDelReq);

    public FinanceOnLoadResp financeOnLoadResp(FinanceOnLoadReq financeOnLoadReq);

    public LabelKeyTOResp getFinanceTaxCodes(FinanceTaxCodesGetReq financeTaxCodesGetReq);

    public ProfitCentreResp getProfitCentres(ProfitCentreGetReq profitCentreGetReq);

    public ProfitCentreResp getProfitCentreItems(ProfitCentreGetReq profitCentreGetReq);

    public ProfitCentreResp saveProfitCentres(ProfitCentreSaveReq profitCentreSaveReq);

    public ProfitCentreResp deleteProfitCentres(ProfitCentreDelReq profitCentreDelReq);
    
    public Object saveVendorPostInvoice(VendorInvoiceRequest vendorInvoiceRequest);
    
    public VendorInvocieResponse getVendorPostInvoice(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest);

    VendorInvocieRecordResponse getVendorInvoiceRecord(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest);

    CostCodeResp getCostCodeByProjIds(GetCostCodesReq getCostCodeReq);

	public VendorBankDetailsTO getVendorBankDetailsByPCompanyId(VendorBankDetailsReq vendorBankDetailsReq);
}
