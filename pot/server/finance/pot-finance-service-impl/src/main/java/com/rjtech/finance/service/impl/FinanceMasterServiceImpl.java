package com.rjtech.finance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.rjtech.centrallib.dto.CostCodeTO;
import com.rjtech.centrallib.model.CmpBankAccountEntity;
import com.rjtech.centrallib.repository.BankAccountRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.resp.CostCodeResp;
import com.rjtech.common.dto.CreditCycleTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.PayPeriodCycleTO;
import com.rjtech.common.dto.TaxableTypeTO;
import com.rjtech.common.dto.YearsTO;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.CreditCycle;
import com.rjtech.common.utils.PayPeriodCycles;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.TaxableTypes;
import com.rjtech.common.utils.Years;
import com.rjtech.finance.dto.ApproverValidateDetailsTO;
import com.rjtech.finance.dto.AssignCostCodesTO;
import com.rjtech.finance.dto.CodeTypesTO;
import com.rjtech.finance.dto.InvoiceparticularsTO;
import com.rjtech.finance.dto.ManpowerDeliveryDocketTO;
import com.rjtech.finance.dto.MaterialDeliveryDocketTO;
import com.rjtech.finance.dto.PlantsDeliveryDocketTO;
import com.rjtech.finance.dto.ProfitCentreTO;
import com.rjtech.finance.dto.ServiceDeliveryDocketTO;
import com.rjtech.finance.dto.SubDeliveryDocketTO;
import com.rjtech.finance.dto.TaxCodeCountryProvisionTO;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;
import com.rjtech.finance.dto.VendorBankDetailsTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.CompanyTaxEntity;
import com.rjtech.finance.model.EmployeePayRollTaxEntity;
import com.rjtech.finance.model.EmployeeWiseCycleEntity;
import com.rjtech.finance.model.InvoiceRecordsHistory;
import com.rjtech.finance.model.MedicalTaxEntity;
import com.rjtech.finance.model.NonRegularPayAllowanceEntity;
import com.rjtech.finance.model.PayDeductionEntity;
import com.rjtech.finance.model.PayRollEntity;
import com.rjtech.finance.model.PersonalTaxEntity;
import com.rjtech.finance.model.ProfitCentreEntity;
import com.rjtech.finance.model.ProjGeneralMstrEntityCopy;
import com.rjtech.finance.model.ProvidentFundEntity;
import com.rjtech.finance.model.RegularPayAllowanceEntity;
import com.rjtech.finance.model.ServiceTaxEntity;
import com.rjtech.finance.model.TaxCodeCountryProvisionEntity;
import com.rjtech.finance.model.TaxCodesEntity;
import com.rjtech.finance.model.TaxCountryProvisionEntity;
import com.rjtech.finance.model.TaxOnSuperFundEntity;
import com.rjtech.finance.model.TaxPaymentReceiverEntity;
import com.rjtech.finance.model.VendorInvoiceAmountEntity;
import com.rjtech.finance.model.VendorPostInvoiceAssignCastCodesEntity;
import com.rjtech.finance.model.VendorPostInvoiceEntity;
import com.rjtech.finance.model.VendorPostInvoiceManpowerDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoiceMaterialDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoicePlantsDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoiceServiceDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoiceSubDeliveryDocketEntity;
import com.rjtech.finance.repository.CodeTypesRepository;
import com.rjtech.finance.repository.CompanyTaxRepository;
import com.rjtech.finance.repository.EmployeePayrollTaxRepository;
import com.rjtech.finance.repository.EmployeeTypeRepository;
import com.rjtech.finance.repository.FinanceTaxCodesProcRepository;
import com.rjtech.finance.repository.InvoiceRecordHistoryRepository;
import com.rjtech.finance.repository.MedicalLeaveTaxRepository;
import com.rjtech.finance.repository.NonRegularAllowanceRepository;
import com.rjtech.finance.repository.PayDeductionRepository;
import com.rjtech.finance.repository.PayPeriodCycleRepository;
import com.rjtech.finance.repository.PaymentReceiverRepository;
import com.rjtech.finance.repository.PersonalTaxRepository;
import com.rjtech.finance.repository.ProfitCentreRepository;
//import com.rjtech.finance.repository.ProjGeneralRepositoryCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.finance.repository.ProvidentFundRepository;
import com.rjtech.finance.repository.RegularAllowanceRepository;
import com.rjtech.finance.repository.ServiceTaxRepository;
import com.rjtech.finance.repository.SuperFundTaxRepository;
import com.rjtech.finance.repository.TaxCodeCountryProvisionRepository;
import com.rjtech.finance.repository.TaxCodesRepository;
import com.rjtech.finance.repository.TaxCountryProvisionRepository;
import com.rjtech.finance.repository.UnitPayRateRepository;
import com.rjtech.finance.repository.VendorInvoiceAmountRepository;
import com.rjtech.finance.repository.VendorPostInvoiceAssignCastCodesRepository;
import com.rjtech.finance.repository.VendorPostInvoiceManpowerDeliveryDocketRepository;
import com.rjtech.finance.repository.VendorPostInvoiceMaterialDeliveryDocketRepository;
import com.rjtech.finance.repository.VendorPostInvoiceParticularsRepository;
import com.rjtech.finance.repository.VendorPostInvoicePlantsDeliveryDocketRepository;
import com.rjtech.finance.repository.VendorPostInvoiceServiceDeliveryDocketRepository;
import com.rjtech.finance.repository.VendorPostInvoiceSubDeliveryDocketRepository;
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
import com.rjtech.finance.service.FinanceMasterService;
import com.rjtech.finance.service.handler.CompanyTaxHandler;
import com.rjtech.finance.service.handler.EmployeePayrollTaxHandler;
import com.rjtech.finance.service.handler.EmployeeTypeHandler;
import com.rjtech.finance.service.handler.MedicalLeaveTaxHandler;
import com.rjtech.finance.service.handler.NonRegularAllowanceHandler;
import com.rjtech.finance.service.handler.PayDeductionHandler;
import com.rjtech.finance.service.handler.PayPeriodCycleHandler;
import com.rjtech.finance.service.handler.PaymentReceiverHandler;
import com.rjtech.finance.service.handler.PersonalTaxHandler;
import com.rjtech.finance.service.handler.ProfitCentreHandler;
import com.rjtech.finance.service.handler.ProvidentFundHandler;
import com.rjtech.finance.service.handler.RegularAllowanceHandler;
import com.rjtech.finance.service.handler.ServiceTaxHandler;
import com.rjtech.finance.service.handler.SuperFundTaxHandler;
import com.rjtech.finance.service.handler.TaxCalculationHandler;
import com.rjtech.finance.service.handler.UnitPayRateHandler;
import com.rjtech.finance.service.handler.VendorPostInvoiceHandler;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.repository.ProjCostItemRepository;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "centralLibFinanceService")
@RJSService(modulecode = "centralLibFinanceService")
@Transactional
public class FinanceMasterServiceImpl implements FinanceMasterService {

    @Autowired
    private UnitPayRateRepository unitPayRateRepository;

    @Autowired
    private PayPeriodCycleRepository payPeriodCycleRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Autowired
    private RegularAllowanceRepository regularAllowanceRepository;

    @Autowired
    private NonRegularAllowanceRepository nonRegularAllowanceRepository;

    @Autowired
    private ProvidentFundRepository providentFundRepository;

    @Autowired
    private PersonalTaxRepository personalTaxRepository;

    @Autowired
    private CompanyTaxRepository companyTaxRepository;

    @Autowired
    private MedicalLeaveTaxRepository medicalLeaveTaxRepository;

    @Autowired
    private EmployeePayrollTaxRepository employeePayrollRepository;

    @Autowired
    private PayDeductionRepository payDeductionRepository;

    @Autowired
    private PaymentReceiverRepository paymentReceiverRepository;

    @Autowired
    private SuperFundTaxRepository superFundRepository;

    @Autowired
    private ServiceTaxRepository serviceTaxRepository;

    @Autowired
    private TaxCodesRepository taxCodesRepository;

    @Autowired
    private CodeTypesRepository codeTypesRepository;

    @Autowired
    private TaxCountryProvisionRepository taxCountryProvisionRepository;

    @Autowired
    private TaxCodeCountryProvisionRepository taxCodeCountryProvisionRepository;

    @Autowired
    private FinanceTaxCodesProcRepository financeTaxCodesProcRepository;

    @Autowired
    private ProfitCentreRepository profitCentreRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private ProcureCatgRepository procureCatgRepository;
    
    @Autowired
    private VendorInvoiceAmountRepository vendorInvoiceAmountRepository;
    
    @Autowired
    private VendorPostInvoiceParticularsRepository  vendorPostInvoiceParticularsRepository;
    
    @Autowired
    private VendorPostInvoiceAssignCastCodesRepository vendorPostInvoiceAssignCastCodesRepository;
    
    @Autowired
    private VendorPostInvoiceMaterialDeliveryDocketRepository vendorPostInvoiceMaterialDeliveryDocketRepository;
    
    @Autowired
    private VendorPostInvoiceSubDeliveryDocketRepository vendorPostInvoiceSubDeliveryDocketRepository;
    
    @Autowired
    private VendorPostInvoiceServiceDeliveryDocketRepository vendorPostInvoiceServiceDeliveryDocketRepository;
    
    @Autowired
    private VendorPostInvoicePlantsDeliveryDocketRepository vendorPostInvoicePlantsDeliveryDocketRepository;
    
    @Autowired
    private VendorPostInvoiceManpowerDeliveryDocketRepository vendorPostInvoiceManpowerDeliveryDocketRepository;
    
    @Autowired
	private ProjGeneralRepositoryCopy projGeneralRepositoryCopy;
    
    @Autowired
    private InvoiceRecordHistoryRepository invoiceRecordHistoryRepository;
    
    @Autowired
    private ProjCostItemRepository projCostItemRepospitory;
    
    @Autowired
    private BankAccountRepository bankAccountRepository;
    

    public UnitPayRateResp getUnitOfRates(UnitPayRateGetReq unitPayRateGetReq) {
        String code = unitPayRateGetReq.getCode();
        String name = unitPayRateGetReq.getName();
        if (CommonUtil.isNotBlankStr(code)) {
            code = CommonUtil.appendLikeOperator(code);
        }

        if (CommonUtil.isNotBlankStr(name)) {
            name = CommonUtil.appendLikeOperator(name);
        }
        return UnitPayRateHandler.convertEntityToPOJO(unitPayRateRepository.findUnitPayRate(AppUserUtils.getClientId(),
                code, name, unitPayRateGetReq.getStatus()));
    }

    public void saveUnitOfRates(UnitPayRateSaveReq unitPayRateSaveReq) {
        unitPayRateRepository.save(
                UnitPayRateHandler.convertPOJOToEntity(unitPayRateSaveReq.getUnitPayRateTOs(), clientRegRepository));
    }

    public void deleteUnitOfRates(UnitPayRateDelReq unitPayRateDelReq) {
        if (CommonUtil.isListHasData(unitPayRateDelReq.getUnitPayRateIds())) {
            unitPayRateRepository.deactivateUnitOfRates(unitPayRateDelReq.getUnitPayRateIds(),
                    unitPayRateDelReq.getStatus());
        }
    }

    public TaxCodesResp getTaxCodes(FinanceTaxGetReq financeTaxGetReq) {
        TaxCodesResp taxCodesResp = new TaxCodesResp();
        List<TaxCodesEntity> taxCodesEntities = taxCodesRepository.findTaxCodes(AppUserUtils.getClientId(),
                financeTaxGetReq.getStatus());
        for (TaxCodesEntity taxCodesEntity : taxCodesEntities) {
            taxCodesResp.getTaxCodesTOs().add(TaxCalculationHandler.convertTaxCodeEntityToPOJO(taxCodesEntity));
        }
        return taxCodesResp;
    }

    public void saveTaxCodes(TaxCodesSaveReq taxCodesSaveReq) {
        taxCodesRepository.save(
                TaxCalculationHandler.populateTaxCodeEntities(taxCodesSaveReq.getTaxCodesTOs(), clientRegRepository));

    }

    public void deactivateTaxCodes(FinanceTaxDelReq financeTaxDelReq) {
        if (CommonUtil.isListHasData(financeTaxDelReq.getTaxIds())) {
            taxCodesRepository.deactivateTaxCodes(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
        }
    }

    public TaxCountryProvisionResp getTaxCodesByCountry(TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
        TaxCountryProvisionResp taxCountryProvisionResp = new TaxCountryProvisionResp();
        List<TaxCountryProvisionEntity> taxCountryProvisionEntities = taxCountryProvisionRepository
                .findTaxCodesByCountry(taxCountryProvisionGetReq.getCountryName(), AppUserUtils.getClientId(),
                        taxCountryProvisionGetReq.getStatus());
        for (TaxCountryProvisionEntity taxCountryProvisionEntity : taxCountryProvisionEntities) {
            taxCountryProvisionResp.getTaxCountryProvisionTOs()
                    .add(TaxCalculationHandler.convertTaxCountryProvisionEntityToPOJO(taxCountryProvisionEntity));

        }
        return taxCountryProvisionResp;
    }

    public TaxCountryProvisionResp getCountryProvision(TaxCountryProvisionGetReq taxCountryProvisionGetReq) {
        TaxCountryProvisionResp taxCountryProvisionResp = new TaxCountryProvisionResp();
        List<TaxCountryProvisionEntity> taxCountryProvisionEntities = taxCountryProvisionRepository
                .findTaxCountryProvision(taxCountryProvisionGetReq.getCountryName(), taxCountryProvisionGetReq.getId(),
                        AppUserUtils.getClientId(), taxCountryProvisionGetReq.getStatus());
        for (TaxCountryProvisionEntity taxCountryProvisionEntity : taxCountryProvisionEntities) {
            taxCountryProvisionResp.getTaxCountryProvisionTOs()
                    .add(TaxCalculationHandler.convertTaxCountryProvisionEntityToPOJO(taxCountryProvisionEntity));

        }
        return taxCountryProvisionResp;
    }

    public TaxCodeCountryProviSionRespMap getTaxCountryProvisionMap(TaxReq taxreq) {
        TaxCodeCountryProviSionRespMap taxCountryProvinceMap = new TaxCodeCountryProviSionRespMap();
        List<TaxCountryProvisionEntity> taxCountryProvinceEntities = taxCountryProvisionRepository
                .findAllCountryProvinces(AppUserUtils.getClientId(), taxreq.getStatus());

        if (CommonUtil.isListHasData(taxCountryProvinceEntities)) {
            for (TaxCountryProvisionEntity taxCountryProvinceEntity : taxCountryProvinceEntities) {

                if ((CommonUtil.objectNotNull(taxCountryProvinceEntity.getCountryName())
                        && (CommonUtil.objectNotNull(taxCountryProvinceEntity.getProvision())
                                && (CommonUtil.isNotBlankDate(taxCountryProvinceEntity.getEffectiveDate()))))) {
                    String date = CommonUtil.convertDateToString(taxCountryProvinceEntity.getEffectiveDate());
                    taxCountryProvinceMap.getTaxCodeCountryUniqueCodeMap()
                            .put(taxCountryProvinceEntity.getCountryName() + "  ,  "
                                    + taxCountryProvinceEntity.getProvision() + "  , " + date,
                                    taxCountryProvinceEntity.getStatus());

                }

            }

        }
        return taxCountryProvinceMap;
    }

    public Long saveCountryProvision(TaxCountryProvisionSaveReq taxCountryProvisionSaveReq) {
        TaxCountryProvisionEntity taxCountryProvisionEntity = taxCountryProvisionRepository
                .save(TaxCalculationHandler.convertTaxCountryProvisionPOJOToEntity(
                        taxCountryProvisionSaveReq.getTaxCountryProvisionTO(), clientRegRepository));

        return taxCountryProvisionEntity.getId();
    }

    public void deleteCountryProvision(FinanceTaxDelReq financeTaxDelReq) {
        taxCountryProvisionRepository.deactivateTaxCountryProvision(financeTaxDelReq.getTaxIds(),
                financeTaxDelReq.getStatus());
    }

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvisionOnLoad(FinanceTaxGetReq financeTaxGetReq) {
        TaxCodeCountryProvisionResp taxCodeCountryProvisionResp = new TaxCodeCountryProvisionResp();

        List<TaxCodesEntity> taxCodesEntities = taxCodesRepository.findTaxCodes(AppUserUtils.getClientId(),
                financeTaxGetReq.getStatus());
        TaxCodeCountryProvisionTO taxCodeCountryProvisionTO = null;
        for (TaxCodesEntity taxCodesEntity : taxCodesEntities) {
            taxCodeCountryProvisionTO = new TaxCodeCountryProvisionTO();
            taxCodeCountryProvisionTO.setTaxCountryProvsionId(financeTaxGetReq.getTaxId());
            taxCodeCountryProvisionTO.setStatus(StatusCodes.ACTIVE.getValue());
            taxCodeCountryProvisionTO.setTaxCodesTO(TaxCalculationHandler.convertTaxCodeEntityToPOJO(taxCodesEntity));
            taxCodeCountryProvisionResp.getTaxCodeCountryProvisionTOs().add(taxCodeCountryProvisionTO);
        }
        taxCodeCountryProvisionResp.setPeriodCycles(getPeriodCycles());
        return taxCodeCountryProvisionResp;
    }

    public TaxCodeCountryProvisionResp getTaxCodeCountryProvision(FinanceTaxGetReq financeTaxGetReq) {
        TaxCodeCountryProvisionResp taxCodeCountryProvisionResp = new TaxCodeCountryProvisionResp();
        List<TaxCodeCountryProvisionEntity> taxCodeCountryProvisionEntities = taxCodeCountryProvisionRepository
                .findTaxCodeCountryProvisions(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (TaxCodeCountryProvisionEntity taxCodeCountryProvisionEntity : taxCodeCountryProvisionEntities) {
            taxCodeCountryProvisionResp.getTaxCodeCountryProvisionTOs().add(
                    TaxCalculationHandler.convertTaxCodeCountryProvisionEntityToPOJO(taxCodeCountryProvisionEntity));
        }
        return taxCodeCountryProvisionResp;
    }

    public void saveTaxCodeCountryProvision(TaxCodeCountryProvisionSaveReq taxCodeCountryProvisionSaveReq) {
        List<TaxCodeCountryProvisionEntity> taxCodeCountryProvisionEntities = new ArrayList<TaxCodeCountryProvisionEntity>();
        for (TaxCodeCountryProvisionTO taxCodeCountryProvisionTO : taxCodeCountryProvisionSaveReq
                .getTaxCodeCountryProvisionTOs()) {
            taxCodeCountryProvisionEntities
                    .add(TaxCalculationHandler.convertTaxCodeCountryProvisionPOJOToEntity(taxCodeCountryProvisionTO));
        }
        taxCodeCountryProvisionRepository.save(taxCodeCountryProvisionEntities);
    }

    public void deactivateTaxCodeCountryProvision(FinanceTaxDelReq financeTaxDelReq) {
        taxCodeCountryProvisionRepository.deactivateTaxCodeCountryProvision(financeTaxDelReq.getTaxIds(),
                financeTaxDelReq.getStatus());
    }

    public RegularAllowanceResp getRegularAllowance(FinanceTaxGetReq financeTaxGetReq) {
        RegularAllowanceResp regularAllowanceResp = new RegularAllowanceResp();
        List<RegularPayAllowanceEntity> regularPayAllowanceEntities = regularAllowanceRepository
                .findRegularAllowance(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (RegularPayAllowanceEntity payAllowanceEntity : regularPayAllowanceEntities) {
            regularAllowanceResp.getRegularPayAllowanceTOs()
                    .add(RegularAllowanceHandler.convertEntityToPOJO(payAllowanceEntity));
        }
        return regularAllowanceResp;
    }

    public void saveRegularAllowance(RegularAllowanceSaveReq regularAllowanceSaveReq) {
        regularAllowanceRepository
                .save(RegularAllowanceHandler.convertPOJOToEntity(regularAllowanceSaveReq.getRegularPayAllowanceTOs()));

    }

    public void deleteRegularAllowance(FinanceTaxDelReq financeTaxDelReq) {
        regularAllowanceRepository.deleteRegularAllowance(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public NonRegularAllowanceResp getNonRegularAllowance(FinanceTaxGetReq financeTaxGetReq) {
        NonRegularAllowanceResp nonRegularAllowanceResp = new NonRegularAllowanceResp();
        List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntities = nonRegularAllowanceRepository
                .findNonRegularAllowance(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity : nonRegularPayAllowanceEntities) {
            nonRegularAllowanceResp.getNonRegularPayAllowanceTOs()
                    .add(NonRegularAllowanceHandler.convertNonRegularMstrEntityToPOJO(nonRegularPayAllowanceEntity));
        }
        return nonRegularAllowanceResp;
    }

    public void saveNonRegularAllowance(NonRegularAllowanceSaveReq regularAllowanceSaveReq) {
        nonRegularAllowanceRepository.save(
                NonRegularAllowanceHandler.convertPOJOToEntity(regularAllowanceSaveReq.getNonRegularPayAllowanceTOs()));

    }

    public void deleteNonRegularAllowance(FinanceTaxDelReq financeTaxDelReq) {
        nonRegularAllowanceRepository.deleteNonRegularAllowance(financeTaxDelReq.getTaxIds(),
                financeTaxDelReq.getStatus());
    }

    public ProvidentFundResp getProvidentFund(FinanceTaxGetReq financeTaxGetReq) {
        ProvidentFundResp providentFundResp = new ProvidentFundResp();
        List<ProvidentFundEntity> providentFundEntities = providentFundRepository
                .findProvidentFund(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (ProvidentFundEntity providentFundEntity : providentFundEntities) {
            providentFundResp.getProvidentFundTOs().add(ProvidentFundHandler.convertEntityToPOJO(providentFundEntity));
        }
        return providentFundResp;
    }

    public void saveProvidentFund(ProvidentFundSaveReq providentFundSaveReq) {
        providentFundRepository
                .save(ProvidentFundHandler.convertPOJOToEntity(providentFundSaveReq.getProvidentFundTOs()));

    }

    public void deleteProvidentFund(FinanceTaxDelReq financeTaxDelReq) {
        providentFundRepository.deleteProvidentFund(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public PersonalTaxResp getPersonalTaxRates(FinanceTaxGetReq financeTaxGetReq) {
        PersonalTaxResp personalTaxResp = new PersonalTaxResp();
        List<PersonalTaxEntity> personalTaxEntities = personalTaxRepository.findPersonalTax(financeTaxGetReq.getTaxId(),
                financeTaxGetReq.getStatus());
        for (PersonalTaxEntity personalTaxEntity : personalTaxEntities) {
            personalTaxResp.getPersonalTaxTOs().add(PersonalTaxHandler.convertEntityToPOJO(personalTaxEntity));
        }
        return personalTaxResp;
    }

    public void savePersonalTaxRates(PersonalTaxSaveReq personalTaxSaveReq) {
        personalTaxRepository.save(PersonalTaxHandler.convertPOJOToEntity(personalTaxSaveReq.getPersonalTaxTOs()));
    }

    public void deletePersonalTaxRates(FinanceTaxDelReq financeTaxDelReq) {
        personalTaxRepository.deletePersonalTaxRates(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public CompanyTaxResp getCompanyTax(FinanceTaxGetReq financeTaxGetReq) {
        CompanyTaxResp companyTaxResp = new CompanyTaxResp();
        List<CompanyTaxEntity> companyTaxEntities = companyTaxRepository.findCompanyTax(financeTaxGetReq.getTaxId(),
                financeTaxGetReq.getStatus());
        for (CompanyTaxEntity companyTaxEntity : companyTaxEntities) {
            companyTaxResp.getCompanyTaxTOs().add(CompanyTaxHandler.convertEntityToPOJO(companyTaxEntity));
        }
        return companyTaxResp;
    }

    public void saveCompanyTax(CompanyTaxSaveReq companyTaxSaveReq) {
        companyTaxRepository.save(CompanyTaxHandler.convertPOJOToEntity(companyTaxSaveReq.getCompanyTaxTOs()));

    }

    public void deleteCompanyTax(FinanceTaxDelReq financeTaxDelReq) {
        companyTaxRepository.deleteCompanyTax(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public MedicalLeaveTaxResp getMedicalLeaveTax(FinanceTaxGetReq financeTaxGetReq) {
        MedicalLeaveTaxResp medicalLeaveResp = new MedicalLeaveTaxResp();
        List<MedicalTaxEntity> medicalTaxEntities = medicalLeaveTaxRepository
                .findMedicalLeaveTax(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (MedicalTaxEntity medicalTaxEntity : medicalTaxEntities) {
            medicalLeaveResp.getMedicalLeaveTaxTOs().add(MedicalLeaveTaxHandler.convertEntityToPOJO(medicalTaxEntity));
        }
        return medicalLeaveResp;
    }

    public void saveMedicalLeaveTax(MedicalLeaveSaveReq medicalLeaveSaveReq) {
        medicalLeaveTaxRepository
                .save(MedicalLeaveTaxHandler.convertPOJOToEntity(medicalLeaveSaveReq.getMedicalLeaveTaxTOs()));

    }

    public void deleteMedicalLeaveTax(FinanceTaxDelReq financeTaxDelReq) {
        medicalLeaveTaxRepository.deleteMedicalLeaveTax(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public EmployeePayrollTaxResp getEmployeePayrollTax(FinanceTaxGetReq financeTaxGetReq) {
        EmployeePayrollTaxResp employeePayrollResp = new EmployeePayrollTaxResp();
        List<EmployeePayRollTaxEntity> employeePayRollEntities = employeePayrollRepository
                .findEmployeePayrollTax(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (EmployeePayRollTaxEntity employeePayRollEntity : employeePayRollEntities) {
            employeePayrollResp.getEmployeePayRollTaxTOs()
                    .add(EmployeePayrollTaxHandler.convertEntityToPOJO(employeePayRollEntity));
        }
        return employeePayrollResp;
    }

    public void saveEmployeePayroll(EmployeePayrollSaveReq employeePayrollSaveReq) {
        employeePayrollRepository
                .save(EmployeePayrollTaxHandler.convertPOJOToEntity(employeePayrollSaveReq.getEmployeePayRollTaxTOs()));

    }

    public void deleteEmployeePayroll(FinanceTaxDelReq financeTaxDelReq) {
        employeePayrollRepository.deleteEmployeePayroll(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public SuperFundTaxResp getSuperfundTax(FinanceTaxGetReq financeTaxGetReq) {
        SuperFundTaxResp superFundTaxResp = new SuperFundTaxResp();
        List<TaxOnSuperFundEntity> superFundEntities = superFundRepository.findSuperFund(financeTaxGetReq.getTaxId(),
                financeTaxGetReq.getStatus());
        for (TaxOnSuperFundEntity taxOnSuperFundEntity : superFundEntities) {
            superFundTaxResp.getTaxOnSuperFundTOs().add(SuperFundTaxHandler.convertEntityToPOJO(taxOnSuperFundEntity));
        }
        return superFundTaxResp;
    }

    public void saveSuperfundTax(SuperFundTaxSaveReq superFundTaxSaveReq) {
        superFundRepository.save(SuperFundTaxHandler.convertPOJOToEntity(superFundTaxSaveReq.getTaxOnSuperFundTOs()));

    }

    public void deleteSuperfundTax(FinanceTaxDelReq financeTaxDelReq) {
        superFundRepository.deleteSuperfundTax(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public ServiceTaxResp getServiceTax(FinanceTaxGetReq financeTaxGetReq) {
        ServiceTaxResp serviceTaxResp = new ServiceTaxResp();
        List<ServiceTaxEntity> serviceTaxEntities = serviceTaxRepository.findServiceTax(financeTaxGetReq.getTaxId(),
                financeTaxGetReq.getStatus());
        for (ServiceTaxEntity serviceTaxEntity : serviceTaxEntities) {
            serviceTaxResp.getServiceTaxTOs().add(ServiceTaxHandler.convertEntityToPOJO(serviceTaxEntity));
        }
        return serviceTaxResp;
    }

    public void saveServiceTax(ServiceTaxSaveReq serviceTaxSaveReq) {
        serviceTaxRepository.save(ServiceTaxHandler.convertPOJOToEntity(serviceTaxSaveReq.getServiceTaxTOs()));

    }

    public void deleteServiceTax(FinanceTaxDelReq financeTaxDelReq) {
        serviceTaxRepository.deleteServiceTax(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public PayDeductionResp getPayDeduction(FinanceTaxGetReq financeTaxGetReq) {
        PayDeductionResp payDeductionResp = new PayDeductionResp();
        List<PayDeductionEntity> payDeductionEntities = payDeductionRepository
                .findPayDeduction(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (PayDeductionEntity payDeductionEntity : payDeductionEntities) {
            payDeductionResp.getPayDeductionTOs().add(PayDeductionHandler.convertEntityToPOJO(payDeductionEntity));
        }
        return payDeductionResp;
    }

    public void savePayDeduction(PayDeductionSaveReq payDeductionSaveReq) {
        payDeductionRepository.save(PayDeductionHandler.convertPOJOToEntity(payDeductionSaveReq.getPayDeductionTOs()));
    }

    public void deletePayDeduction(FinanceTaxDelReq financeTaxDelReq) {
        payDeductionRepository.deletePayDeduction(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public PaymentReceiverResp getPaymentReceiver(FinanceTaxGetReq financeTaxGetReq) {
        PaymentReceiverResp paymentReceiverResp = new PaymentReceiverResp();
        List<TaxPaymentReceiverEntity> paymentReceiverEntities = paymentReceiverRepository
                .findPaymentReceiver(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (TaxPaymentReceiverEntity paymentReceiverEntity : paymentReceiverEntities) {
            paymentReceiverResp.getTaxPaymentDetailsTOs()
                    .add(PaymentReceiverHandler.convertEntityToPOJO(paymentReceiverEntity));
        }
        return paymentReceiverResp;
    }

    public void savePaymentReceiver(PaymentReceiverSaveReq paymentReceiverSaveReq) {
        paymentReceiverRepository
                .save(PaymentReceiverHandler.convertPOJOToEntity(paymentReceiverSaveReq.getTaxPaymentDetailsTOs()));

    }

    public void deletePaymentReceiver(FinanceTaxDelReq financeTaxDelReq) {
        paymentReceiverRepository.deletePaymentReceiver(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());
    }

    public PayPeriodCycleResp getPayRollCycle() {
        PayPeriodCycleResp payPeriodCycleResp = new PayPeriodCycleResp();
        PayPeriodCycleTO payPeriodCycleTO = null;
        for (PayPeriodCycles cycle : PayPeriodCycles.values()) {
            payPeriodCycleTO = new PayPeriodCycleTO();
            payPeriodCycleTO.setValue(cycle.getValue());
            payPeriodCycleResp.getPeriodCycleTOs().add(payPeriodCycleTO);
        }
        YearsTO yearsTO = null;
        for (Years years : Years.values()) {
            yearsTO = new YearsTO();
            yearsTO.setValue(years.getValue());
            yearsTO.setName(years.getName());
            payPeriodCycleResp.getYearsTOs().add(yearsTO);
        }
        TaxableTypeTO taxableTypeTO = null;
        for (TaxableTypes taxableTypes : TaxableTypes.values()) {
            taxableTypeTO = new TaxableTypeTO();
            taxableTypeTO.setName(taxableTypes.getName());
            payPeriodCycleResp.getTaxableTypeTOs().add(taxableTypeTO);
        }
        CreditCycleTO creditCycleTO = null;
        for (CreditCycle creditCycle : CreditCycle.values()) {
            creditCycleTO = new CreditCycleTO();
            creditCycleTO.setCreditCycle(creditCycle.getName());
            payPeriodCycleResp.getCreditCycleTOs().add(creditCycleTO);
        }
        return payPeriodCycleResp;

    }

    public YearsResp getFinancialYears() {
        YearsResp yearsResp = new YearsResp();
        YearsTO yearsTO = null;
        for (Years years : Years.values()) {
            yearsTO = new YearsTO();
            yearsTO.setValue(years.getValue());
            yearsTO.setName(years.getName());
            yearsResp.getYearsTOs().add(yearsTO);
        }
        return yearsResp;
    }

    public PayRollCycleResp getPayPeriodCycle(PayPeriodCycleGetReq periodCycleGetReq) {
        PayRollCycleResp payRollCycleResp = new PayRollCycleResp();
        List<PayRollEntity> payRollEntities = payPeriodCycleRepository
                .findPayPeriodCycle(periodCycleGetReq.getClientId(), periodCycleGetReq.getStatus());
        for (PayRollEntity payRollEntity : payRollEntities) {
            payRollCycleResp.getPayRollCycleTOs().add(PayPeriodCycleHandler.convertEntityToPOJO(payRollEntity));
        }
        return payRollCycleResp;
    }

    public void savePayPeriodCycle(PayPeriodCycleSaveReq periodCycleSaveReq) {
        payPeriodCycleRepository.save(PayPeriodCycleHandler.convertPOJOToEntity(periodCycleSaveReq.getPayRollCycleTOs(),
                clientRegRepository));

    }

    public void deletePayPeriodCycle(PayPeriodCycleDelReq periodCycleDelReq) {
        payPeriodCycleRepository.deactivatePayPeriodCycle(periodCycleDelReq.getPayPeriodIds(),
                periodCycleDelReq.getStatus());
    }

    public EmpPayrollTypeResp getEmpPayTypeCycle(FinanceTaxGetReq financeTaxGetReq) {
        EmpPayrollTypeResp empPayrollTypeResp = new EmpPayrollTypeResp();
        List<EmployeeWiseCycleEntity> employeeWiseCycleEntities = employeeTypeRepository
                .findEmployeeTypes(financeTaxGetReq.getTaxId(), financeTaxGetReq.getStatus());
        for (EmployeeWiseCycleEntity employeeWiseCycleEntity : employeeWiseCycleEntities) {
            empPayrollTypeResp.getEmployeeWiseCycleTOs()
                    .add(EmployeeTypeHandler.convertEntityToPOJO(employeeWiseCycleEntity));
        }
        return empPayrollTypeResp;
    }

    public void saveEmpPayTypeCycle(EmployeeTypeSaveReq employeeTypeSaveReq) {
        employeeTypeRepository.save(EmployeeTypeHandler.convertPOJOToEntity(
                employeeTypeSaveReq.getEmployeeWiseCycleTOs(), procureCatgRepository, payPeriodCycleRepository));
    }

    public void deleteEmpPayTypeCycle(FinanceTaxDelReq financeTaxDelReq) {
        employeeTypeRepository.deleteEmployeeType(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());

    }

    public PayPeriodCycleResp getTaxableType() {
        PayPeriodCycleResp payPeriodCycleResp = new PayPeriodCycleResp();
        TaxableTypeTO taxableTypeTO = null;
        for (TaxableTypes taxableTypes : TaxableTypes.values()) {
            taxableTypeTO = new TaxableTypeTO();
            taxableTypeTO.setName(taxableTypes.getName());
            payPeriodCycleResp.getTaxableTypeTOs().add(taxableTypeTO);
        }
        return payPeriodCycleResp;
    }

    public CodeTypesResp getCodeTypes(FinanceTaxGetReq financeTaxGetReq) {
        CodeTypesResp codeTypesResp = new CodeTypesResp();
        List<CodeTypesEntity> codeTypesEntities = codeTypesRepository.findCodeTypes(financeTaxGetReq.getTaxId(),
                financeTaxGetReq.getStatus());
        for (CodeTypesEntity codeTypesEntity : codeTypesEntities) {
            codeTypesResp.getCodeTypesTOs().add(TaxCalculationHandler.convertCodeTypesEntityToPOJO(codeTypesEntity));

        }
        return codeTypesResp;
    }

    public PayPeriodCycleResp getCreditCycles() {
        PayPeriodCycleResp payPeriodCycleResp = new PayPeriodCycleResp();
        CreditCycleTO creditCycleTO = null;
        for (CreditCycle creditCycle : CreditCycle.values()) {
            creditCycleTO = new CreditCycleTO();
            creditCycleTO.setCreditCycle(creditCycle.getName());
            payPeriodCycleResp.getCreditCycleTOs().add(creditCycleTO);
        }
        return payPeriodCycleResp;
    }

    public void saveCodeTypes(CodeTypesSaveReq codeTypesSaveReq) {
        List<CodeTypesEntity> codeTypesEntities = new ArrayList<CodeTypesEntity>();
        for (CodeTypesTO codeTypesTO : codeTypesSaveReq.getCodeTypesTOs()) {
            codeTypesEntities.add(TaxCalculationHandler.convertCodeTypePOJOToEntity(codeTypesTO));
        }
        codeTypesRepository.save(codeTypesEntities);
    }

    public LabelKeyTOResp getFinanceTaxCodes(FinanceTaxCodesGetReq financeTaxCodesGetReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();

        List<LabelKeyTO> labelKeyTOs = financeTaxCodesProcRepository.getFinanceTaxCodes(
                financeTaxCodesGetReq.getProjId(), AppUserUtils.getClientId(), new Date(),
                financeTaxCodesGetReq.getFinanceType());
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public void deactivateCodeTypes(FinanceTaxDelReq financeTaxDelReq) {
        codeTypesRepository.deactivateCodeTypes(financeTaxDelReq.getTaxIds(), financeTaxDelReq.getStatus());

    }

    public List<String> getPeriodCycles() {
        List<String> periodCycles = new ArrayList<String>();
        for (PayPeriodCycles payPeriodCycle : PayPeriodCycles.values()) {
            periodCycles.add(payPeriodCycle.getValue());
        }
        return periodCycles;
    }

    public ProfitCentreResp getProfitCentres(ProfitCentreGetReq profitCentreGetReq) {
        ProfitCentreResp profitCentreResp = new ProfitCentreResp();
        List<ProfitCentreEntity> profitCentreEntities = null;
        if (AppUtils.hasText(profitCentreGetReq.getName()) || AppUtils.hasText(profitCentreGetReq.getCode())) {
            profitCentreEntities = profitCentreRepository.findProfitCentresByName(profitCentreGetReq.getCode(),
                    profitCentreGetReq.getName(), AppUserUtils.getClientId(), profitCentreGetReq.getStatus());
        } else {
            profitCentreEntities = profitCentreRepository.findAllProfitCentres(AppUserUtils.getClientId(),
                    profitCentreGetReq.getStatus());
        }

        for (ProfitCentreEntity profitCentreEntity : profitCentreEntities) {
            profitCentreResp.getProfitCentreTOs().add(ProfitCentreHandler.populateProfitCentre(profitCentreEntity));            
        }
		for (ProfitCentreTO profitCentreTOParent : profitCentreResp.getProfitCentreTOs()) {

			List<ProjGeneralMstrEntity> projGeneralMstrEntity = projGeneralRepositoryCopy
					.findProfitCentresByProfitCenter(profitCentreTOParent.getId());
			if (projGeneralMstrEntity.size() > 0) {
				profitCentreTOParent.setProjAssigned(true);
			} else {
				profitCentreTOParent.setProjAssigned(false);
			}
		}
		for (ProfitCentreTO profitCentreTOParent : profitCentreResp.getProfitCentreTOs()) {
			for (ProfitCentreTO profitCentreTOChild : profitCentreTOParent.getChildProfitCentreTOs()) {
				List<ProjGeneralMstrEntity> projGeneralMstrEntity = projGeneralRepositoryCopy
						.findProfitCentresByProfitCenter(profitCentreTOChild.getId());
				if (projGeneralMstrEntity.size() > 0) {
					profitCentreTOChild.setProjAssigned(true);
				} else {
					profitCentreTOChild.setProjAssigned(false);
				}
			}
		}
		for (ProfitCentreTO profitCentreTOParent : profitCentreResp.getProfitCentreTOs()) {
			for (ProfitCentreTO profitCentreTOChild : profitCentreTOParent.getChildProfitCentreTOs()) {
				for (ProfitCentreTO profitCentreTOSubChild : profitCentreTOChild.getChildProfitCentreTOs()) {
					List<ProjGeneralMstrEntity> projGeneralMstrEntity = projGeneralRepositoryCopy
							.findProfitCentresByProfitCenter(profitCentreTOSubChild.getId());
					if (projGeneralMstrEntity.size() > 0) {
						profitCentreTOSubChild.setProjAssigned(true);
					} else {
						profitCentreTOSubChild.setProjAssigned(false);
					}
				}
			}
		}
        return profitCentreResp;
    }

    public ProfitCentreResp getProfitCentreItems(ProfitCentreGetReq profitCentreGetReq) {
        ProfitCentreResp ProfitCentreResp = new ProfitCentreResp();
        List<ProfitCentreEntity> profitCentreEntities = profitCentreRepository
                .findAllProfitItems(AppUserUtils.getClientId(), profitCentreGetReq.getStatus());
        for (ProfitCentreEntity profitCentreEntity : profitCentreEntities) {
            ProfitCentreResp.getProfitCentreTOs().add(ProfitCentreHandler.populateProfitCentre(profitCentreEntity));
        }
        return ProfitCentreResp;
    }

    public void saveProfitCentres(ProfitCentreSaveReq profitCentreSaveReq) {
        profitCentreRepository.save(ProfitCentreHandler
                .populateEntitiesFromPOJO(profitCentreSaveReq.getProfitCentreTOs(), clientRegRepository));
    }

    public void deleteProfitCentres(ProfitCentreDelReq profitCentreDelReq) {
        profitCentreRepository.deactivateProfitCentres(profitCentreDelReq.getProfitIds(),
                profitCentreDelReq.getStatus());

    }
    
	
	  public void savePostInvoiceDetails(PostInvoiceReq postInvoiceDetails) {
	  
	  }
	  
	  @Transactional
	  public Object saveVendorPostInvoice(VendorInvoiceRequest vendorInvoiceRequest) {
		  InvoiceparticularsTO invoiceParticularsTO =  vendorInvoiceRequest.getInvoiceparticularsTO();
		  VendorBankAccountDetailsTO bankAccountDetailsTO = vendorInvoiceRequest.getVendorBankAccountDetailsTO();
		  ApproverValidateDetailsTO approverDetailTO = vendorInvoiceRequest.getApproverValidateDetailsTO();
		  VendorPostInvoiceEntity postInvoiceEntity = VendorPostInvoiceHandler.prepareVendorPostInvoiceEntity(invoiceParticularsTO, bankAccountDetailsTO, approverDetailTO);
		  postInvoiceEntity.setPreContractId(vendorInvoiceRequest.getPreContractId());
		  vendorPostInvoiceParticularsRepository.save(postInvoiceEntity);
		  List<VendorInvoiceAmountEntity> VendorInvoiceAmountEntityList = VendorPostInvoiceHandler.prepareInvoiceAmount(vendorInvoiceRequest);
		  if(!CollectionUtils.isEmpty(VendorInvoiceAmountEntityList)) {
			  
			  for(VendorInvoiceAmountEntity vendorInvoiceAmountEntity: 
				  VendorInvoiceAmountEntityList) {
				  vendorInvoiceAmountEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorInvoiceAmountRepository.save(vendorInvoiceAmountEntity);
			  }
		  }
		  List<AssignCostCodesTO>  assignCostCodeTOList = vendorInvoiceRequest.getAssignCostCodesTOList();
		  List<VendorPostInvoiceAssignCastCodesEntity> vendorPostInvoiceAssignCastCodesEntityList = VendorPostInvoiceHandler.preapreassignCostCodeTOToassignCostCodeEntity(assignCostCodeTOList);
		  if(CollectionUtils.isNotEmpty(vendorPostInvoiceAssignCastCodesEntityList)) {
			  for(VendorPostInvoiceAssignCastCodesEntity vendorPostInvoiceAssignCastCodesEntity : vendorPostInvoiceAssignCastCodesEntityList) {
				  vendorPostInvoiceAssignCastCodesEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorPostInvoiceAssignCastCodesRepository.save(vendorPostInvoiceAssignCastCodesEntity);
			  }
		  }
		  List<MaterialDeliveryDocketTO> materialDeliveryDocket = vendorInvoiceRequest.getMaterialDeliveryDocket();
		  List<VendorPostInvoiceMaterialDeliveryDocketEntity> materialDocketEntityList =  VendorPostInvoiceHandler.preapreVendorMaterialDocketEntityRecords(materialDeliveryDocket);
		  if(CollectionUtils.isNotEmpty(materialDocketEntityList)) {
			  for(VendorPostInvoiceMaterialDeliveryDocketEntity vendorPostInvoiceMaterialDeliveryDocketEntity: materialDocketEntityList) {
				  vendorPostInvoiceMaterialDeliveryDocketEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorPostInvoiceMaterialDeliveryDocketRepository.save(vendorPostInvoiceMaterialDeliveryDocketEntity);
			  }
		  }
		  List<ManpowerDeliveryDocketTO> manpowerDeliveryDocket = vendorInvoiceRequest.getManpowerDeliveryDocketTO();
		  List<VendorPostInvoiceManpowerDeliveryDocketEntity> vendorPostInvoiceManpowerDeliveryDocketEntityList =  VendorPostInvoiceHandler.preapreVendorManpowerDocketEntityRecords(manpowerDeliveryDocket);
		  if(CollectionUtils.isNotEmpty(vendorPostInvoiceManpowerDeliveryDocketEntityList)) {
			  
			  for(VendorPostInvoiceManpowerDeliveryDocketEntity vendorPostInvoiceManpowerDeliveryDocketEntity: vendorPostInvoiceManpowerDeliveryDocketEntityList) {
				  vendorPostInvoiceManpowerDeliveryDocketEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorPostInvoiceManpowerDeliveryDocketRepository.save(vendorPostInvoiceManpowerDeliveryDocketEntity);
			  }
		  }
		  
		  List<PlantsDeliveryDocketTO> plantsDeliveryDocket = vendorInvoiceRequest.getPlantsDeliveryDocketTO();
		  List<VendorPostInvoicePlantsDeliveryDocketEntity> vendorPostInvoicePlantsDeliveryDocketEntityList =  VendorPostInvoiceHandler.preapreVendorPostInvoicePlantsDeliveryDocketEntityRecords(plantsDeliveryDocket);
		  if(CollectionUtils.isNotEmpty(vendorPostInvoicePlantsDeliveryDocketEntityList)) {
			  
			  for(VendorPostInvoicePlantsDeliveryDocketEntity vendorPostInvoicePlantsDeliveryDocketEntity : vendorPostInvoicePlantsDeliveryDocketEntityList) {
				  vendorPostInvoicePlantsDeliveryDocketEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorPostInvoicePlantsDeliveryDocketRepository.save(vendorPostInvoicePlantsDeliveryDocketEntity);
			  }
		  }
		  
		  List<ServiceDeliveryDocketTO> serviceDeliveryDocket = vendorInvoiceRequest.getServiceDeliveryDocketTO();
		  List<VendorPostInvoiceServiceDeliveryDocketEntity> vendorPostInvoiceServiceDeliveryDocketEntityList =  VendorPostInvoiceHandler.preapreVendorPostInvoiceServiceDeliveryDocketEntityRecords(serviceDeliveryDocket);
		  if(CollectionUtils.isNotEmpty(vendorPostInvoiceServiceDeliveryDocketEntityList)) {
			  for(VendorPostInvoiceServiceDeliveryDocketEntity vendorPostInvoiceServiceDeliveryDocketEntity : vendorPostInvoiceServiceDeliveryDocketEntityList) {
				  vendorPostInvoiceServiceDeliveryDocketEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorPostInvoiceServiceDeliveryDocketRepository.save(vendorPostInvoiceServiceDeliveryDocketEntity);
			  }
		  }
		  
		  List<SubDeliveryDocketTO> subDeliveryDocket = vendorInvoiceRequest.getSubDeliveryDocket();
		  List<VendorPostInvoiceSubDeliveryDocketEntity> vendorPostInvoiceSubDeliveryDocketEntityList =  VendorPostInvoiceHandler.preapreVendorPostInvoiceSubDeliveryDocketEntityRecords(subDeliveryDocket);
		  if(CollectionUtils.isNotEmpty(vendorPostInvoiceSubDeliveryDocketEntityList)) {
			  
			  for(VendorPostInvoiceSubDeliveryDocketEntity vendorPostInvoiceSubDeliveryDocketEntity: vendorPostInvoiceSubDeliveryDocketEntityList) {
				  vendorPostInvoiceSubDeliveryDocketEntity.setVendorPostInvocieId(postInvoiceEntity.getId());
				  vendorPostInvoiceSubDeliveryDocketRepository.save(vendorPostInvoiceSubDeliveryDocketEntity);
			  }
		  }
		  return "success";
	  }

	  //getVendorPostInvoice(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest);
	@Override
	public VendorInvocieResponse getVendorPostInvoice(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest) {
		System.out.println("*********** getVendorPostInvoice serviceImpl *******************  ");
		boolean flagMateial = false;
		boolean flagManpower = false;
		boolean flagPlant = false;
		boolean flagservice = false;
		boolean flagsubcontract = false;
		
		int  invoiceId = getVendorPostInvoiceRequest.getPreContractId();
		VendorInvocieResponse  response = new VendorInvocieResponse();
		List<VendorPostInvoiceEntity> vendorInvoiceEtityList = vendorPostInvoiceParticularsRepository.findByPreContractId(invoiceId);
		if(vendorInvoiceEtityList != null && vendorInvoiceEtityList.size() > 0) {
			VendorPostInvoiceEntity vendorInvoiceEtity = vendorInvoiceEtityList.get(0);
			long vendorInvoicId = vendorInvoiceEtity.getId();
			response = VendorPostInvoiceHandler.convertVendorPostInvoiceEntityToInvocieParticulars(vendorInvoiceEtity);
			List<VendorPostInvoiceAssignCastCodesEntity> vendorPostInvoiceAssignCastCodesEntity = vendorPostInvoiceAssignCastCodesRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorPostInvoiceAssignCastCodesEntity != null && vendorPostInvoiceAssignCastCodesEntity.size() > 0) {
				List<AssignCostCodesTO> assignCostCodesTOList = VendorPostInvoiceHandler.convertvendorPostInvoiceAssignCastCodesEntityAssignCodesTO(vendorPostInvoiceAssignCastCodesEntity);
				response.setAssignCostCodesTOList(assignCostCodesTOList);
			}
			List<VendorInvoiceAmountEntity>  vendorInvoiceAmountEntityList = vendorInvoiceAmountRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorInvoiceAmountEntityList != null && vendorInvoiceAmountEntityList.size() > 0) {
				VendorPostInvoiceHandler.convertVendorInvoiceAmountEntityToInvoiceSectionTO(vendorInvoiceAmountEntityList,response);
			}
			
			List<VendorPostInvoiceMaterialDeliveryDocketEntity> vendorPostInvoiceMaterialDeliveryDocketEntityList = vendorPostInvoiceMaterialDeliveryDocketRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorPostInvoiceMaterialDeliveryDocketEntityList != null && vendorPostInvoiceMaterialDeliveryDocketEntityList.size() > 0) {
				List<MaterialDeliveryDocketTO> materialDeliveryDocketTOList = VendorPostInvoiceHandler.convertVendorPostInvoiceMaterialDeliveryDocketEntityToMaterialDeliveryDocketTO(vendorPostInvoiceMaterialDeliveryDocketEntityList);
				response.setMaterialDeliveryDocket(materialDeliveryDocketTOList);
				response.getInvoiceparticularsTO().setProcurmentCategory("Material");
				flagMateial = true;
			}
			
			List<VendorPostInvoiceManpowerDeliveryDocketEntity> vendorPostInvoiceManpowerDeliveryDocketEntityList  = vendorPostInvoiceManpowerDeliveryDocketRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorPostInvoiceManpowerDeliveryDocketEntityList != null && vendorPostInvoiceManpowerDeliveryDocketEntityList.size() > 0) {
				List<ManpowerDeliveryDocketTO> manpowerDeliveryDocketList = VendorPostInvoiceHandler.convertVendorPostInvoiceManpowerDeliveryDocketEntityToManpowerDeliveryDocketTO(vendorPostInvoiceManpowerDeliveryDocketEntityList);
				response.setManpowerDeliveryDocketTO(manpowerDeliveryDocketList);
				flagManpower = true;
				if(flagMateial) { 
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower");
				} else {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower");
				}
			}
			
			//plants
			 List<VendorPostInvoicePlantsDeliveryDocketEntity> vendorPostInvoicePlantsDeliveryDocketEntityList   = vendorPostInvoicePlantsDeliveryDocketRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorPostInvoicePlantsDeliveryDocketEntityList != null && vendorPostInvoicePlantsDeliveryDocketEntityList.size() > 0) {
				List<PlantsDeliveryDocketTO> plantsDeliveryDocket  = VendorPostInvoiceHandler.convertVendorPostInvoicePlantsDeliveryDocketEntityToPlantsDeliveryDocketTO(vendorPostInvoicePlantsDeliveryDocketEntityList);
				response.setPlantsDeliveryDocketTO(plantsDeliveryDocket);
				flagPlant = true;
				if(flagMateial && flagManpower) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Plant");
				} else if(flagManpower) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Plant");
				}
				else {
					response.getInvoiceparticularsTO().setProcurmentCategory("Plant");
				}
			}
			
			//services
			List<VendorPostInvoiceServiceDeliveryDocketEntity> vendorPostInvoiceServiceDeliveryDocketEntityList = vendorPostInvoiceServiceDeliveryDocketRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorPostInvoiceServiceDeliveryDocketEntityList != null && vendorPostInvoiceServiceDeliveryDocketEntityList.size() > 0) {
				List<ServiceDeliveryDocketTO> serviceDeliveryDocketTOList = VendorPostInvoiceHandler.convertVendorPostInvoiceServiceDeliveryDocketEntityToServiceDeliveryDocketTO(vendorPostInvoiceServiceDeliveryDocketEntityList);
				response.setServiceDeliveryDocketTO(serviceDeliveryDocketTOList);
				flagservice = true;
				if(flagMateial && flagManpower && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Plant Service");
				} else if(flagManpower && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Plant Service");
				
				} else if(flagMateial && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Plant Service");
				} else if(flagMateial && flagManpower) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Service");
				}
				else if(flagMateial) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Service");
				}
				else if(flagManpower) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Service");
				}
				else if(flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Plant Service");
				}
				else {
					response.getInvoiceparticularsTO().setProcurmentCategory("Plant");
				}
			}
			
			//sub
			List<VendorPostInvoiceSubDeliveryDocketEntity> vendorPostInvoiceSubDeliveryDocketEntityList = vendorPostInvoiceSubDeliveryDocketRepository.findByVendorPostInvocieId(vendorInvoicId);
			if(vendorPostInvoiceSubDeliveryDocketEntityList != null && vendorPostInvoiceSubDeliveryDocketEntityList.size() > 0) {
				List<SubDeliveryDocketTO> subDeliveryDocketTOList = VendorPostInvoiceHandler.convertVendorPostInvoiceSubDeliveryDocketEntityToSubDeliveryDocketTO(vendorPostInvoiceSubDeliveryDocketEntityList);
				response.setSubDeliveryDocket(subDeliveryDocketTOList);
				
				flagsubcontract = true;
				if(flagMateial && flagManpower && flagPlant && flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Plant Service Sub");
				}
				else if(flagMateial && flagManpower && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Plant Sub");
				}
				else if(flagMateial && flagManpower && flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Service Sub");
				}
				else if(flagMateial && flagPlant && flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Plant Service Sub");
				}
				else if(flagManpower && flagPlant && flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Plant Service Sub");
				}
				else if(flagManpower && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Plant Sub");
				}
				else if(flagManpower && flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Service Sub");
				}
				else if(flagMateial && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Plant Sub");
				}
				else if(flagMateial && flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Service Sub");
				}
				else if(flagMateial && flagManpower) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Manpower Sub");
				}
				else if(flagservice && flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Plant Service Sub");
				} 
				else if(flagMateial) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Material Sub");
				}
				else if(flagManpower) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Manpower Sub");
				}
				else if(flagPlant) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Plant Sub");
				}
				else if(flagservice) {
					response.getInvoiceparticularsTO().setProcurmentCategory("Service Sub");
				}
				else {
					response.getInvoiceparticularsTO().setProcurmentCategory("Sub");
				}
			}
		}
		return response;
	}
	@Override
	public VendorInvocieRecordResponse getInvoiceTrackingRecords(GetVendorPostInvoiceRequest getVendorPostInvoiceRequest) {
		System.out.println("Entered into getInvoiceTrackingRecords() ");
		String  invoiceId = getVendorPostInvoiceRequest.getInvoiceNumber();
		VendorInvocieRecordResponse  response = null;
		List<InvoiceRecordsHistory> invoiceList = invoiceRecordHistoryRepository.findByVendorPostInvocieId(invoiceId);
		for(InvoiceRecordsHistory recordsHistory : invoiceList) {
			if(response!=null) {
				response = new VendorInvocieRecordResponse();
			response.setInvoiceNumber(recordsHistory.getInvoiceNum());
			response.setDate(recordsHistory.getApprovedDate()!=null ? recordsHistory.getApprovedDate().toString() :"");
			response.setStatus(recordsHistory.getStatus());
			response.setComments(recordsHistory.getComments());
			response.setCompletedBy(recordsHistory.getApprovedBy().getDisplayName());
			response.setResponisblePersonForPendingSteps(recordsHistory.getResponsiblePersonForPending().getDisplayName());
			
				}
			invoiceList.add(recordsHistory);
			}
		
	return response;
	}

	@Override
	public CostCodeResp getCostCodeByProjIds(GetCostCodesReq getCostCodeReq) {
		CostCodeResp codeCodeResp = new CostCodeResp();
		List<CostCodeTO> listOfcosts = new ArrayList<CostCodeTO>();
		List<Long> projIds = getCostCodeReq.getProjIds();
		int status = 1;
		List<ProjCostItemEntity> listOfProjCostItems = projCostItemRepospitory.findMultiProjCostItems(projIds,status);
		if(CollectionUtils.isEmpty(listOfProjCostItems)) {
		for(ProjCostItemEntity projCostItemEntity : listOfProjCostItems) {
			CostCodeTO costCodeTO = new CostCodeTO();
			if(!ObjectUtils.isEmpty(projCostItemEntity.getCostMstrEntity())) {
				costCodeTO.setId(projCostItemEntity.getCostMstrEntity().getId());
				costCodeTO.setCode(projCostItemEntity.getCostMstrEntity().getCode());
				costCodeTO.setName(projCostItemEntity.getCostMstrEntity().getName());
				costCodeTO.setDispName(projCostItemEntity.getCostMstrEntity().getName());
			}
			
			listOfcosts.add(costCodeTO);
			}
		codeCodeResp.setMessage("Fetched successfully from costCodeMaster");
		codeCodeResp.setMsgCode(HttpStatus.SC_OK+"");
		codeCodeResp.setCostCodeTOs(listOfcosts);
		codeCodeResp.setStatus("SUCCESS");
		}else {
			codeCodeResp.setCostCodeTOs(listOfcosts);
			codeCodeResp.setStatus("Failed");
		}
		
		return codeCodeResp;
		
		}

	@Override
	public VendorBankDetailsTO getVendorBankDetailsByPCompanyId(VendorBankDetailsReq vendorBankDetailsReq) {
		VendorBankDetailsTO VendorBankDetailsTO = new VendorBankDetailsTO();
		List<VendorBankAccountDetailsTO> venAccountDetailsTOs = new ArrayList<>();
		List<ApproverValidateDetailsTO> approverValidateDetailsTOs = new ArrayList<>();
		try {
			Integer status=1;
			List<CmpBankAccountEntity> bankList= bankAccountRepository.findByClientId(vendorBankDetailsReq.getCompanyId(),status);
			for(CmpBankAccountEntity cmpBankAccountEntity : bankList) {
				VendorBankAccountDetailsTO vendorBankAccountDetailsTO = new VendorBankAccountDetailsTO();
				ApproverValidateDetailsTO approverValidateDetailsTO = new ApproverValidateDetailsTO();
				vendorBankAccountDetailsTO.setId(cmpBankAccountEntity.getBankAccountId().intValue());
				vendorBankAccountDetailsTO.setAccountName(cmpBankAccountEntity.getAccName());
				vendorBankAccountDetailsTO.setBankName(cmpBankAccountEntity.getBankName());
				vendorBankAccountDetailsTO.setBankCode(cmpBankAccountEntity.getBankCode());
				vendorBankAccountDetailsTO.setSwiftCode("");
				vendorBankAccountDetailsTO.setAccountNumber(cmpBankAccountEntity.getBankAccNo()!=null ? Long.parseLong( cmpBankAccountEntity.getBankAccNo()): null);
				 venAccountDetailsTOs.add(vendorBankAccountDetailsTO);
				 approverValidateDetailsTO.setApproverId(null);
				 approverValidateDetailsTO.setAccountDetailsVerified(false);
				 approverValidateDetailsTO.setApproverName(null);
				 approverValidateDetailsTO.setSubmitForApprover(false);
				 approverValidateDetailsTOs.add(approverValidateDetailsTO);
				 
			}
			VendorBankDetailsTO.setApproverValidateDetailsTOs(approverValidateDetailsTOs);
			VendorBankDetailsTO.setVendorBankDetailsTOs(venAccountDetailsTOs);
		}
		catch (Exception e) {
			System.out.print("Error occured while fetching bank details ");
		}
		return VendorBankDetailsTO;
	}
	 
}
