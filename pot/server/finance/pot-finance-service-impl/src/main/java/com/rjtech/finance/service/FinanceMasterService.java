package com.rjtech.finance.service;

import java.util.List;

import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.finance.dto.VendorBankDetailsTO;
import com.rjtech.finance.req.CodeTypesSaveReq;
import com.rjtech.finance.req.CompanyTaxSaveReq;
import com.rjtech.finance.req.EmployeePayrollSaveReq;
import com.rjtech.finance.req.EmployeeTypeSaveReq;
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
import com.rjtech.finance.req.UnitPayRateSaveReq;
import com.rjtech.finance.req.VendorBankDetailsReq;
import com.rjtech.finance.req.VendorInvoiceRequest;
import com.rjtech.finance.resp.CodeTypesResp;
import com.rjtech.finance.resp.CompanyTaxResp;
import com.rjtech.finance.resp.EmpPayrollTypeResp;
import com.rjtech.finance.resp.EmployeePayrollTaxResp;
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

public interface FinanceMasterService {

    public PayPeriodCycleResp getPayRollCycle();

    public YearsResp getFinancialYears();

    public PayPeriodCycleResp getTaxableType();

    public PayPeriodCycleResp getCreditCycles();

    List<String> getPeriodCycles();

    public UnitPayRateResp getUnitOfRates(UnitPayRateGetReq unitPayRateGetReq);

    public void saveUnitOfRates(UnitPayRateSaveReq unitPayRateSaveReq);

    public void deleteUnitOfRates(UnitPayRateDelReq unitPayRateDelReq);

    public PayRollCycleResp getPayPeriodCycle(PayPeriodCycleGetReq periodCycleGetReq);

    public void savePayPeriodCycle(PayPeriodCycleSaveReq periodCycleSaveReq);

    public void deletePayPeriodCycle(PayPeriodCycleDelReq periodCycleDelReq);

    public EmpPayrollTypeResp getEmpPayTypeCycle(FinanceTaxGetReq financeTaxGetReq);

    public void saveEmpPayTypeCycle(EmployeeTypeSaveReq employeeTypeSaveReq);

    public void deleteEmpPayTypeCycle(FinanceTaxDelReq financeTaxDelReq);

    public TaxCodesResp getTaxCodes(FinanceTaxGetReq financeTaxGetReq);

    public void saveTaxCodes(TaxCodesSaveReq taxCodesSaveReq);

    public void deactivateTaxCodes(FinanceTaxDelReq financeTaxDelReq);

    public CodeTypesResp getCodeTypes(FinanceTaxGetReq financeTaxGetReq);

    public void deactivateCodeTypes(FinanceTaxDelReq financeTaxDelReq);

    public void saveCodeTypes(CodeTypesSaveReq codeTypesSaveReq);

    public TaxCountryProvisionResp getTaxCodesByCountry(TaxCountryProvisionGetReq taxCountryProvisionGetReq);

    public TaxCountryProvisionResp getCountryProvision(TaxCountryProvisionGetReq taxCountryProvisionGetReq);

    public TaxCodeCountryProviSionRespMap getTaxCountryProvisionMap(TaxReq taxreq);

    public Long saveCountryProvision(TaxCountryProvisionSaveReq taxCountryProvisionSaveReq);

    public void deleteCountryProvision(FinanceTaxDelReq financeTaxDelReq);

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvisionOnLoad(FinanceTaxGetReq financeTaxGetReq);

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvision(FinanceTaxGetReq financeTaxGetReq);

    public void saveTaxCodeCountryProvision(TaxCodeCountryProvisionSaveReq taxCodeCountryProvisionSaveReq);

    public void deactivateTaxCodeCountryProvision(FinanceTaxDelReq financeTaxDelReq);

    public RegularAllowanceResp getRegularAllowance(FinanceTaxGetReq financeTaxGetReq);

    public void saveRegularAllowance(RegularAllowanceSaveReq regularAllowanceSaveReq);

    public void deleteRegularAllowance(FinanceTaxDelReq financeTaxDelReq);

    public NonRegularAllowanceResp getNonRegularAllowance(FinanceTaxGetReq financeTaxGetReq);

    public void saveNonRegularAllowance(NonRegularAllowanceSaveReq nonRegularAllowanceSaveReq);

    public void deleteNonRegularAllowance(FinanceTaxDelReq financeTaxDelReq);

    public ProvidentFundResp getProvidentFund(FinanceTaxGetReq financeTaxGetReq);

    public void saveProvidentFund(ProvidentFundSaveReq providentFundSaveReq);

    public void deleteProvidentFund(FinanceTaxDelReq financeTaxDelReq);

    public PersonalTaxResp getPersonalTaxRates(FinanceTaxGetReq financeTaxGetReq);

    public void savePersonalTaxRates(PersonalTaxSaveReq personalTaxSaveReq);

    public void deletePersonalTaxRates(FinanceTaxDelReq financeTaxDelReq);

    public CompanyTaxResp getCompanyTax(FinanceTaxGetReq financeTaxGetReq);

    public void saveCompanyTax(CompanyTaxSaveReq companyTaxSaveReq);

    public void deleteCompanyTax(FinanceTaxDelReq companyTaxDelReq);

    public MedicalLeaveTaxResp getMedicalLeaveTax(FinanceTaxGetReq financeTaxGetReq);

    public void saveMedicalLeaveTax(MedicalLeaveSaveReq medicalLeaveSaveReq);

    public void deleteMedicalLeaveTax(FinanceTaxDelReq financeTaxDelReq);

    public EmployeePayrollTaxResp getEmployeePayrollTax(FinanceTaxGetReq financeTaxGetReq);

    public void saveEmployeePayroll(EmployeePayrollSaveReq employeePayrollSaveReq);

    public void deleteEmployeePayroll(FinanceTaxDelReq financeTaxDelReq);

    public SuperFundTaxResp getSuperfundTax(FinanceTaxGetReq financeTaxGetReq);

    public void saveSuperfundTax(SuperFundTaxSaveReq superFundTaxSaveReq);

    public void deleteSuperfundTax(FinanceTaxDelReq financeTaxDelReq);

    public ServiceTaxResp getServiceTax(FinanceTaxGetReq financeTaxGetReq);

    public void saveServiceTax(ServiceTaxSaveReq serviceTaxSaveReq);

    public void deleteServiceTax(FinanceTaxDelReq financeTaxDelReq);

    public PayDeductionResp getPayDeduction(FinanceTaxGetReq financeTaxGetReq);

    public void savePayDeduction(PayDeductionSaveReq payDeductionSaveReq);

    public void deletePayDeduction(FinanceTaxDelReq financeTaxDelReq);

    public PaymentReceiverResp getPaymentReceiver(FinanceTaxGetReq financeTaxGetReq);

    public void savePaymentReceiver(PaymentReceiverSaveReq paymentReceiverSaveReq);

    public void deletePaymentReceiver(FinanceTaxDelReq financeTaxDelReq);

    public LabelKeyTOResp getFinanceTaxCodes(FinanceTaxCodesGetReq financeTaxCodesGetReq);

    public ProfitCentreResp getProfitCentres(ProfitCentreGetReq profitCentreGetReq);

    public ProfitCentreResp getProfitCentreItems(ProfitCentreGetReq profitCentreGetReq);

    public void saveProfitCentres(ProfitCentreSaveReq profitCentreSaveReq);

    public void deleteProfitCentres(ProfitCentreDelReq profitCentreDelReq);
    
    public void savePostInvoiceDetails(PostInvoiceReq postInvoiceDetails);
    
    public Object saveVendorPostInvoice(VendorInvoiceRequest vendorInvoiceRequest);
    
    //public Object fetchInvoiceDetails(int invoiceId);
    public VendorInvocieResponse getVendorPostInvoice(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest);

    public VendorInvocieRecordResponse getInvoiceTrackingRecords(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest);

	public CostCodeResp getCostCodeByProjIds(GetCostCodesReq getCostCodeReq);

	public VendorBankDetailsTO getVendorBankDetailsByPCompanyId(VendorBankDetailsReq vendorBankDetailsReq);
}
