package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.finance.dto.ApproverValidateDetailsTO;
import com.rjtech.finance.dto.AssignCostCodesTO;
import com.rjtech.finance.dto.InvoiceSectionTO;
import com.rjtech.finance.dto.InvoiceparticularsTO;
import com.rjtech.finance.dto.ManpowerDeliveryDocketTO;
import com.rjtech.finance.dto.MaterialDeliveryDocketTO;
import com.rjtech.finance.dto.PlantsDeliveryDocketTO;
import com.rjtech.finance.dto.ServiceDeliveryDocketTO;
import com.rjtech.finance.dto.SubDeliveryDocketTO;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;
import com.rjtech.finance.model.VendorInvoiceAmountEntity;
import com.rjtech.finance.model.VendorPostInvoiceAssignCastCodesEntity;
import com.rjtech.finance.model.VendorPostInvoiceEntity;
import com.rjtech.finance.model.VendorPostInvoiceManpowerDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoiceMaterialDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoicePlantsDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoiceServiceDeliveryDocketEntity;
import com.rjtech.finance.model.VendorPostInvoiceSubDeliveryDocketEntity;
import com.rjtech.finance.req.VendorInvoiceRequest;
import com.rjtech.finance.resp.VendorInvocieResponse;

public class VendorPostInvoiceHandler {
	
	public static VendorPostInvoiceEntity prepareVendorPostInvoiceEntity(InvoiceparticularsTO invoiceParticularsTO, VendorBankAccountDetailsTO bankAccountDetailsTO, ApproverValidateDetailsTO approverDetailTO) {
		VendorPostInvoiceEntity postInvoiceEntity = new VendorPostInvoiceEntity();
		postInvoiceEntity.setEpsName(invoiceParticularsTO.getEpsName());
		postInvoiceEntity.setProjectName(invoiceParticularsTO.getProjectName());
		postInvoiceEntity.setProjectId(invoiceParticularsTO.getProjectId());
		postInvoiceEntity.setPoNumber(invoiceParticularsTO.getPoNumber());
		postInvoiceEntity.setPoDescription(invoiceParticularsTO.getPoDescription());
		postInvoiceEntity.setVendorName(invoiceParticularsTO.getVendorName());
		postInvoiceEntity.setVendorId(invoiceParticularsTO.getVendorId());
		postInvoiceEntity.setOriginatorEmployeeId(invoiceParticularsTO.getOriginatorEmployeeId());
		postInvoiceEntity.setOriginatorEmployeeName(invoiceParticularsTO.getOriginatorEmployeeName());
		postInvoiceEntity.setInvocieDate(invoiceParticularsTO.getInvocieDate());
		postInvoiceEntity.setInvoiceNumber(invoiceParticularsTO.getInvoiceNumber());
		postInvoiceEntity.setInvoicePeriodFromDate(invoiceParticularsTO.getInvoicePeriodFromDate());
		postInvoiceEntity.setInvoicePeriodFromDate(invoiceParticularsTO.getInvoicePeriodFromDate());
		postInvoiceEntity.setInvoicePeriodToDate(invoiceParticularsTO.getInvoicePeriodToDate());
		postInvoiceEntity.setInvoiceReceivedDate(invoiceParticularsTO.getInvoiceReceivedDate());
		postInvoiceEntity.setPaymentDueDate(invoiceParticularsTO.getPaymentDueDate());
		postInvoiceEntity.setProposedPaymentDate(invoiceParticularsTO.getProposedPaymentDate());
		postInvoiceEntity.setExpenditureType(invoiceParticularsTO.getExpenditureType());
		postInvoiceEntity.setCreatedOn(new Date());
		
		postInvoiceEntity.setApproverName(approverDetailTO.getApproverName());
		postInvoiceEntity.setApproverName(approverDetailTO.getApproverId());
		postInvoiceEntity.setSubmitForApprover(postInvoiceEntity.isSubmitForApprover());
		postInvoiceEntity.setAccountDetailsVerified(postInvoiceEntity.isAccountDetailsVerified());
		
		postInvoiceEntity.setBankName(bankAccountDetailsTO.getBankName());
		postInvoiceEntity.setAccountNumber(bankAccountDetailsTO.getAccountNumber());
		postInvoiceEntity.setBankCode(bankAccountDetailsTO.getBankCode());
		postInvoiceEntity.setSwiftCode(postInvoiceEntity.getSwiftCode());
		postInvoiceEntity.setAccountName(postInvoiceEntity.getAccountName());
		
		return postInvoiceEntity;
		
	}
	
	public static void prepareBankAccountDetailsVendorPostInvoiceEntity(VendorPostInvoiceEntity postInvoiceEntity, VendorBankAccountDetailsTO bankAccountDetailsTO) {
		postInvoiceEntity.setBankName(bankAccountDetailsTO.getBankName());
		postInvoiceEntity.setBankCode(bankAccountDetailsTO.getBankCode());
		postInvoiceEntity.setAccountName(bankAccountDetailsTO.getAccountName());
		postInvoiceEntity.setAccountNumber(bankAccountDetailsTO.getAccountNumber());
		postInvoiceEntity.setSwiftCode(bankAccountDetailsTO.getSwiftCode());
	}
	
	
	public static void prepareApproverDetails(VendorPostInvoiceEntity postInvoiceEntity, ApproverValidateDetailsTO approverDetailTO) {
		postInvoiceEntity.setAccountDetailsVerified(approverDetailTO.isAccountDetailsVerified());
		postInvoiceEntity.setSubmitForApprover(approverDetailTO.isSubmitForApprover());
		postInvoiceEntity.setApproverId(approverDetailTO.getApproverId());
		postInvoiceEntity.setApproverName(approverDetailTO.getApproverName());
	}
	
	public static List<VendorInvoiceAmountEntity> prepareInvoiceAmount(VendorInvoiceRequest vendorInvoiceRequest) {
		List<VendorInvoiceAmountEntity> VendorInvoiceEntityList = new ArrayList<>();
		InvoiceSectionTO asPerInvoiceTO = vendorInvoiceRequest.getAsPerInvoice();
		VendorInvoiceEntityList.add(prepareInvoiceSectionEtity(asPerInvoiceTO, "AS_PER_INVOICE"));
		InvoiceSectionTO  amountRetainedTO = vendorInvoiceRequest.getAmountRetained();
		VendorInvoiceEntityList.add(prepareInvoiceSectionEtity(amountRetainedTO, "INVOICE_AMOUNT_RETINED"));
		InvoiceSectionTO  netAmountPayableTO = vendorInvoiceRequest.getNetAmountPayable();
		VendorInvoiceEntityList.add(prepareInvoiceSectionEtity(netAmountPayableTO, "INVOICE_NET_AMOUNT"));
		InvoiceSectionTO  asPerSystemVerificationTO = vendorInvoiceRequest.getAsPerSystemVerification();
		VendorInvoiceEntityList.add(prepareInvoiceSectionEtity(asPerSystemVerificationTO, "INVOICE_SYSTEM_VERIFICATION"));
		return VendorInvoiceEntityList;
	}
	
	public static VendorInvoiceAmountEntity prepareInvoiceSectionEtity(InvoiceSectionTO invoiceSectionTO, String type) {
		VendorInvoiceAmountEntity vendorInvoiceEntity = new VendorInvoiceAmountEntity();
		vendorInvoiceEntity.setExpenditureType(invoiceSectionTO.getExpenditureType());
		vendorInvoiceEntity.setNetInvoiceAmount(invoiceSectionTO.getNetInvoiceAmount());
		vendorInvoiceEntity.setGstInvoiceAmount(invoiceSectionTO.getGstInvoiceAmount());
		vendorInvoiceEntity.setTotalInvoiceAmount(invoiceSectionTO.getTotalInvoiceAmount());
		vendorInvoiceEntity.setInvoiceAmountType(type);
		return vendorInvoiceEntity;
	}
	
	
	public static List<VendorPostInvoiceMaterialDeliveryDocketEntity> preapreVendorMaterialDocketEntityRecords(List<MaterialDeliveryDocketTO> materialDeliveryDocket) {
		List<VendorPostInvoiceMaterialDeliveryDocketEntity> vendorPostInvoiceMaterialDeliveryDocketEntityList = new ArrayList<>();
		
		if(materialDeliveryDocket != null && materialDeliveryDocket.size() > 0) {
			
			for(MaterialDeliveryDocketTO materialDocketTO : materialDeliveryDocket) {
				VendorPostInvoiceMaterialDeliveryDocketEntity vendorPostInvoiceMaterialDeliveryDocketEntity = new VendorPostInvoiceMaterialDeliveryDocketEntity();
				vendorPostInvoiceMaterialDeliveryDocketEntity.setClaimedQtyToCurrentPeriod(materialDocketTO.getClaimedQtyToPreviousPeriod());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setProcessQtyUptoInvoicePeriod(materialDocketTO.getProcessQtyUptoInvoicePeriod());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setClaimedQtyToPreviousPeriod(materialDocketTO.getClaimedQtyToPreviousPeriod());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setRateAmount(materialDocketTO.getRateAmount());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setQtyAsPerContract(materialDocketTO.getQtyAsPerContract());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setDocketNumber(materialDocketTO.getDocketNumber());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setDocketDate(materialDocketTO.getDocketDate());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setMaterialReleasedDate(materialDocketTO.getMaterialReleasedDate());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setScheduleItemId(materialDocketTO.getScheduleItemId());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setScheduleItemDescription(materialDocketTO.getScheduleItemDescription());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setUnitOfMeasure(materialDocketTO.getUnitOfMeasure());
				vendorPostInvoiceMaterialDeliveryDocketEntity.setReceiverComments(materialDocketTO.getReceiverComments());
				vendorPostInvoiceMaterialDeliveryDocketEntityList.add(vendorPostInvoiceMaterialDeliveryDocketEntity);
			}
		}
		return vendorPostInvoiceMaterialDeliveryDocketEntityList;
	}
	
	
	public static List<VendorPostInvoiceAssignCastCodesEntity> preapreassignCostCodeTOToassignCostCodeEntity(List<AssignCostCodesTO>  assignCostCodeTOList){
		List<VendorPostInvoiceAssignCastCodesEntity> vendorPostInvoiceAssignCastCodesEntityList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(assignCostCodeTOList)) {
						
			for(AssignCostCodesTO assignCostCodesTO: assignCostCodeTOList) {
				VendorPostInvoiceAssignCastCodesEntity vendorPostInvoiceAssignCastCodesEntity = new VendorPostInvoiceAssignCastCodesEntity();
				vendorPostInvoiceAssignCastCodesEntity.setCostCodePercentage(assignCostCodesTO.getCostCodePercentage());
				if(assignCostCodesTO.getLabelKeyTO() != null) {
					vendorPostInvoiceAssignCastCodesEntity.setCostCodeId(assignCostCodesTO.getLabelKeyTO().getId());
					vendorPostInvoiceAssignCastCodesEntity.setCostCodeDescription(assignCostCodesTO.getLabelKeyTO().getName());
				}
				vendorPostInvoiceAssignCastCodesEntity.setCostCodeAmount(assignCostCodesTO.getCostCodeAmount());
				vendorPostInvoiceAssignCastCodesEntity.setCreatedOn(new Date());
				vendorPostInvoiceAssignCastCodesEntity.setUpdatedOn(new Date());
				vendorPostInvoiceAssignCastCodesEntityList.add(vendorPostInvoiceAssignCastCodesEntity);
			}
		}
		return vendorPostInvoiceAssignCastCodesEntityList;
	}
	
	
	public static List<VendorPostInvoiceManpowerDeliveryDocketEntity> preapreVendorManpowerDocketEntityRecords(List<ManpowerDeliveryDocketTO> manpowerDeliveryDocket) {
		List<VendorPostInvoiceManpowerDeliveryDocketEntity> vendorPostInvoiceManpowerDeliveryDocketEntityList = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(manpowerDeliveryDocket)) {
			for(ManpowerDeliveryDocketTO manpowerDeliveryDocketTO: manpowerDeliveryDocket) {
				VendorPostInvoiceManpowerDeliveryDocketEntity vendorPostInvoiceManpowerDeliveryDocketEntity = new VendorPostInvoiceManpowerDeliveryDocketEntity();
				
				vendorPostInvoiceManpowerDeliveryDocketEntity.setDocumentPeriod(manpowerDeliveryDocketTO.getDocumentPeriod());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setDocumentType(manpowerDeliveryDocketTO.getDocumentType());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setDocumentNumber(manpowerDeliveryDocketTO.getDocumentNumber());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setEmployeeId(manpowerDeliveryDocketTO.getEmployeeId());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setEmployeeFirstName(manpowerDeliveryDocketTO.getEmployeeFirstName());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setEmployeeLastName(manpowerDeliveryDocketTO.getEmployeeLastName());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setTrade(manpowerDeliveryDocketTO.getTrade());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setDateOfEntrollment(manpowerDeliveryDocketTO.getDateOfEnrollment());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setDateOfMobilisation(manpowerDeliveryDocketTO.getDateOfMobilisation());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setActualDateOfDeMobilisation(manpowerDeliveryDocketTO.getActualDateOfDeMobilisation());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setScheduleItemId(manpowerDeliveryDocketTO.getScheduleItemId());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setScheduleItemDescription(manpowerDeliveryDocketTO.getScheduleItemDescription());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setUnitOfMeasure(manpowerDeliveryDocketTO.getUnitOfMeasure());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setWageFactor(manpowerDeliveryDocketTO.getWageFactor());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setGrossUtilisationRatio(manpowerDeliveryDocketTO.getGrossUtilisationRatio());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setNetUtilisationRaio(manpowerDeliveryDocketTO.getNetUtilisationRaio());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setQty(manpowerDeliveryDocketTO.getQty());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setPresentAndPaidLeaveDays(manpowerDeliveryDocketTO.getPresentAndPaidLeaveDays());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setAbsentAndUnpaidLeaveDays(manpowerDeliveryDocketTO.getAbsentAndUnpaidLeaveDays());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setNonworkingDays(manpowerDeliveryDocketTO.getNonworkingDays());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setGrossBillablePeriodInDays(manpowerDeliveryDocketTO.getGrossBillablePeriodInDays());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setNonBillablePeriodInDays(manpowerDeliveryDocketTO.getNonBillablePeriodInDays());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setCalendarDaysCountAsInvoiceperiod(manpowerDeliveryDocketTO.getCalendarDaysCountAsInvoiceperiod());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setRate(manpowerDeliveryDocketTO.getRate());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setAmount(manpowerDeliveryDocketTO.getAmount());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setComments(manpowerDeliveryDocketTO.getComments());
				vendorPostInvoiceManpowerDeliveryDocketEntity.setTypeOfSection(manpowerDeliveryDocketTO.getTypeOfSection());
			
				vendorPostInvoiceManpowerDeliveryDocketEntityList.add(vendorPostInvoiceManpowerDeliveryDocketEntity);
			}
		}
		
		return vendorPostInvoiceManpowerDeliveryDocketEntityList;
	}
	
	public static List<VendorPostInvoicePlantsDeliveryDocketEntity> preapreVendorPostInvoicePlantsDeliveryDocketEntityRecords(List<PlantsDeliveryDocketTO> plantsDeliveryDocketList) {
		List<VendorPostInvoicePlantsDeliveryDocketEntity> vendorPostInvoicePlantsDeliveryDocketEntityList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(plantsDeliveryDocketList)) {
			
			for(PlantsDeliveryDocketTO plantsDeliveryDocketTO : plantsDeliveryDocketList) {
				VendorPostInvoicePlantsDeliveryDocketEntity vendorPostInvoicePlantsDeliveryDocketEntity = new VendorPostInvoicePlantsDeliveryDocketEntity();
				
				vendorPostInvoicePlantsDeliveryDocketEntity.setWorkDairyNumber(plantsDeliveryDocketTO.getWorkDairyNumber());
				vendorPostInvoicePlantsDeliveryDocketEntity.setWorkDairyDate(plantsDeliveryDocketTO.getWorkDairyDate());
				vendorPostInvoicePlantsDeliveryDocketEntity.setWorkStatusCategory(plantsDeliveryDocketTO.getWorkStatusCategory());
				vendorPostInvoicePlantsDeliveryDocketEntity.setPlantWorkStatusRecordId(plantsDeliveryDocketTO.getPlantWorkStatusRecordId());
				vendorPostInvoicePlantsDeliveryDocketEntity.setPlantWorkStatusRecordMonth(plantsDeliveryDocketTO.getPlantWorkStatusRecordMonth());
				vendorPostInvoicePlantsDeliveryDocketEntity.setDocketNumber(plantsDeliveryDocketTO.getDocketNumber());
				vendorPostInvoicePlantsDeliveryDocketEntity.setDocketDate(plantsDeliveryDocketTO.getDocketDate());
				vendorPostInvoicePlantsDeliveryDocketEntity.setPlantsReceivedDate(plantsDeliveryDocketTO.getPlantsReceivedDate());
				vendorPostInvoicePlantsDeliveryDocketEntity.setPlantId(plantsDeliveryDocketTO.getPlantId());
				vendorPostInvoicePlantsDeliveryDocketEntity.setPlantOwner(plantsDeliveryDocketTO.getPlantOwner());
				vendorPostInvoicePlantsDeliveryDocketEntity.setShiftCategory(plantsDeliveryDocketTO.getShiftCategory());
				vendorPostInvoicePlantsDeliveryDocketEntity.setUtilisationCategory(plantsDeliveryDocketTO.getUtilisationCategory());
				vendorPostInvoicePlantsDeliveryDocketEntity.setMobilisation(plantsDeliveryDocketTO.getMobilisation());
				vendorPostInvoicePlantsDeliveryDocketEntity.setActualDateOfDeMobilisation(plantsDeliveryDocketTO.getActualDateOfDeMobilisation());
				vendorPostInvoicePlantsDeliveryDocketEntity.setScheduleItemId(plantsDeliveryDocketTO.getScheduleItemId());
				vendorPostInvoicePlantsDeliveryDocketEntity.setScheduleItemDescription(plantsDeliveryDocketTO.getScheduleItemDescription());
				vendorPostInvoicePlantsDeliveryDocketEntity.setUnitOfMeasure(plantsDeliveryDocketTO.getUnitOfMeasure());
				vendorPostInvoicePlantsDeliveryDocketEntity.setQty(plantsDeliveryDocketTO.getQty());
				vendorPostInvoicePlantsDeliveryDocketEntity.setRate(plantsDeliveryDocketTO.getRate());
				vendorPostInvoicePlantsDeliveryDocketEntity.setAmount(plantsDeliveryDocketTO.getAmount());
				vendorPostInvoicePlantsDeliveryDocketEntity.setComments(plantsDeliveryDocketTO.getComments());
				vendorPostInvoicePlantsDeliveryDocketEntity.setTypeOfSection(plantsDeliveryDocketTO.getTypeOfSection());
			
				vendorPostInvoicePlantsDeliveryDocketEntityList.add(vendorPostInvoicePlantsDeliveryDocketEntity);
			}
			
		}
		return vendorPostInvoicePlantsDeliveryDocketEntityList;
		
	}
	
	
	public static List<VendorPostInvoiceServiceDeliveryDocketEntity> preapreVendorPostInvoiceServiceDeliveryDocketEntityRecords(List<ServiceDeliveryDocketTO> serviceDeliveryDocket) {
		List<VendorPostInvoiceServiceDeliveryDocketEntity> vendorPostInvoiceServiceDeliveryDocketEntityList = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(serviceDeliveryDocket)) {
			
			for(ServiceDeliveryDocketTO serviceDeliveryDocketTO : serviceDeliveryDocket) {
				VendorPostInvoiceServiceDeliveryDocketEntity vendorPostInvoiceServiceDeliveryDocketEntity = new VendorPostInvoiceServiceDeliveryDocketEntity();
				
				vendorPostInvoiceServiceDeliveryDocketEntity.setScheduleItemDescription(serviceDeliveryDocketTO.getScheduleItemDescription());
				vendorPostInvoiceServiceDeliveryDocketEntity.setScheduleItemId(serviceDeliveryDocketTO.getScheduleItemId());
				vendorPostInvoiceServiceDeliveryDocketEntity.setAmount(serviceDeliveryDocketTO.getAmount());
				vendorPostInvoiceServiceDeliveryDocketEntity.setMeasuringUnit(serviceDeliveryDocketTO.getMeasuringUnit());
				vendorPostInvoiceServiceDeliveryDocketEntity.setCumulativeProgressQTYToInvoicePeriod(serviceDeliveryDocketTO.getCumulativeProgressQTYToInvoicePeriod());
				vendorPostInvoiceServiceDeliveryDocketEntity.setNetProgressQTY(serviceDeliveryDocketTO.getNetProgressQTY());
				vendorPostInvoiceServiceDeliveryDocketEntity.setQtyCumulativeClaimedForperviousPeriod(serviceDeliveryDocketTO.getQtyCumulativeClaimedForperviousPeriod());
				vendorPostInvoiceServiceDeliveryDocketEntity.setRate(serviceDeliveryDocketTO.getRate());
				vendorPostInvoiceServiceDeliveryDocketEntity.setAgreementQTY(serviceDeliveryDocketTO.getAgreementQTY());
				vendorPostInvoiceServiceDeliveryDocketEntity.setComments(serviceDeliveryDocketTO.getComments());
				vendorPostInvoiceServiceDeliveryDocketEntity.setCreatedOn(new Date());
				vendorPostInvoiceServiceDeliveryDocketEntity.setUpdatedOn(new Date());
				
				vendorPostInvoiceServiceDeliveryDocketEntityList.add(vendorPostInvoiceServiceDeliveryDocketEntity);
			}
			
		}
		return vendorPostInvoiceServiceDeliveryDocketEntityList;
	}
	
	
	public static List<VendorPostInvoiceSubDeliveryDocketEntity> preapreVendorPostInvoiceSubDeliveryDocketEntityRecords(List<SubDeliveryDocketTO> subDeliveryDocket) {
		List<VendorPostInvoiceSubDeliveryDocketEntity> vendorPostInvoiceSubDeliveryDocketEntityList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(subDeliveryDocket)) {
			
			for(SubDeliveryDocketTO subDeliveryDocketTO : subDeliveryDocket) {
				VendorPostInvoiceSubDeliveryDocketEntity vendorPostInvoiceSubDeliveryDocketEntity = new VendorPostInvoiceSubDeliveryDocketEntity();
				vendorPostInvoiceSubDeliveryDocketEntity.setSowItemId(subDeliveryDocketTO.getSowItemId());
				vendorPostInvoiceSubDeliveryDocketEntity.setSowItemIdDescription(subDeliveryDocketTO.getSowItemIdDescription());
				vendorPostInvoiceSubDeliveryDocketEntity.setMeasuringUnit(subDeliveryDocketTO.getMeasuringUnit());
				vendorPostInvoiceSubDeliveryDocketEntity.setContractQTY(subDeliveryDocketTO.getContractQTY());
				vendorPostInvoiceSubDeliveryDocketEntity.setCumulativeProgressQTYToInvoicePeriod(subDeliveryDocketTO.getCumulativeProgressQTYToInvoicePeriod());
				vendorPostInvoiceSubDeliveryDocketEntity.setQtyClaimedForPreviousPeriod(subDeliveryDocketTO.getQtyClaimedForPreviousPeriod());
				vendorPostInvoiceSubDeliveryDocketEntity.setNetProgressQTY(subDeliveryDocketTO.getNetProgressQTY());
				vendorPostInvoiceSubDeliveryDocketEntity.setRate(subDeliveryDocketTO.getRate());
				vendorPostInvoiceSubDeliveryDocketEntity.setAmount(subDeliveryDocketTO.getAmount());
				vendorPostInvoiceSubDeliveryDocketEntity.setComments(subDeliveryDocketTO.getComments());
				
				vendorPostInvoiceSubDeliveryDocketEntityList.add(vendorPostInvoiceSubDeliveryDocketEntity);
			}
			
		}
		
		return vendorPostInvoiceSubDeliveryDocketEntityList;
	}
	
	
	public static VendorInvocieResponse convertVendorPostInvoiceEntityToInvocieParticulars(VendorPostInvoiceEntity vendorInvoiceEtity) {
		//ModelMapper mapper = new ModelMapper();
		
		VendorInvocieResponse vendorInvocieResponse = new VendorInvocieResponse();
		
		InvoiceparticularsTO invoiceparticularsTO = new InvoiceparticularsTO();
		invoiceparticularsTO.setEpsName(vendorInvoiceEtity.getEpsName());
		invoiceparticularsTO.setProjectName(vendorInvoiceEtity.getProjectName());
		invoiceparticularsTO.setProjectId(vendorInvoiceEtity.getProjectId());
		invoiceparticularsTO.setEpsId(vendorInvoiceEtity.getEpsId());
		invoiceparticularsTO.setPoNumber(vendorInvoiceEtity.getPoDescription());
		invoiceparticularsTO.setPoDescription(vendorInvoiceEtity.getPoDescription());
		invoiceparticularsTO.setVendorId(vendorInvoiceEtity.getVendorId());
		invoiceparticularsTO.setVendorName(vendorInvoiceEtity.getVendorName());
		invoiceparticularsTO.setOriginatorEmployeeId(vendorInvoiceEtity.getOriginatorEmployeeId());
		invoiceparticularsTO.setOriginatorEmployeeName(vendorInvoiceEtity.getOriginatorEmployeeName());
		invoiceparticularsTO.setInvocieDate(vendorInvoiceEtity.getInvocieDate());
		invoiceparticularsTO.setInvoiceNumber(vendorInvoiceEtity.getInvoiceNumber());
		invoiceparticularsTO.setInvoicePeriodFromDate(vendorInvoiceEtity.getInvoicePeriodFromDate());
		invoiceparticularsTO.setInvoicePeriodToDate(vendorInvoiceEtity.getInvoicePeriodToDate());
		invoiceparticularsTO.setInvoiceReceivedDate(vendorInvoiceEtity.getInvoiceReceivedDate());
		invoiceparticularsTO.setPaymentDueDate(vendorInvoiceEtity.getPaymentDueDate());
		invoiceparticularsTO.setProposedPaymentDate(vendorInvoiceEtity.getProposedPaymentDate());
		invoiceparticularsTO.setExpenditureType(vendorInvoiceEtity.getExpenditureType());
		
		VendorBankAccountDetailsTO vendorBankAccountDetailsTO = new VendorBankAccountDetailsTO();
		vendorBankAccountDetailsTO.setAccountName(vendorInvoiceEtity.getAccountName());
		vendorBankAccountDetailsTO.setAccountNumber(vendorInvoiceEtity.getAccountNumber());
		vendorBankAccountDetailsTO.setBankCode(vendorInvoiceEtity.getBankCode());
		vendorBankAccountDetailsTO.setBankName(vendorInvoiceEtity.getBankName());
		vendorBankAccountDetailsTO.setSwiftCode(vendorInvoiceEtity.getSwiftCode());
		
		ApproverValidateDetailsTO approverValidateDetailsTO = new ApproverValidateDetailsTO();
		approverValidateDetailsTO.setAccountDetailsVerified(vendorInvoiceEtity.isAccountDetailsVerified());
		approverValidateDetailsTO.setSubmitForApprover(vendorInvoiceEtity.isSubmitForApprover());
		approverValidateDetailsTO.setApproverId(vendorInvoiceEtity.getApproverId());
		approverValidateDetailsTO.setApproverName(vendorInvoiceEtity.getApproverName());
		
		vendorInvocieResponse.setInvoiceparticularsTO(invoiceparticularsTO);
		vendorInvocieResponse.setVendorBankAccountDetailsTO(vendorBankAccountDetailsTO);
		vendorInvocieResponse.setApproverValidateDetailsTO(approverValidateDetailsTO);
		
		
		return vendorInvocieResponse;
	}
	
	
	public static List<AssignCostCodesTO> convertvendorPostInvoiceAssignCastCodesEntityAssignCodesTO(List<VendorPostInvoiceAssignCastCodesEntity> vendorPostInvoiceAssignCastCodesEntityList) {
		List<AssignCostCodesTO> assignCostCodesTOList = new ArrayList<>();
		
		for(VendorPostInvoiceAssignCastCodesEntity vendorPostInvoiceAssignCastCodesEntity: vendorPostInvoiceAssignCastCodesEntityList) {
			AssignCostCodesTO assignCostCodesTO = new AssignCostCodesTO();
			LabelKeyTO labelKeyTO = new LabelKeyTO();
			labelKeyTO.setId(vendorPostInvoiceAssignCastCodesEntity.getCostCodeId());
			labelKeyTO.setName(vendorPostInvoiceAssignCastCodesEntity.getCostCodeDescription());
			assignCostCodesTO.setLabelKeyTO(labelKeyTO);
			assignCostCodesTO.setCostCodePercentage(vendorPostInvoiceAssignCastCodesEntity.getCostCodePercentage());
			assignCostCodesTO.setCostCodeAmount(vendorPostInvoiceAssignCastCodesEntity.getCostCodeAmount());
			assignCostCodesTOList.add(assignCostCodesTO);
		}
		
		return assignCostCodesTOList;
		
	}
	
	public static void convertVendorInvoiceAmountEntityToInvoiceSectionTO(List<VendorInvoiceAmountEntity>  vendorInvoiceAmountEntityList,
			VendorInvocieResponse response) {
				
		for(VendorInvoiceAmountEntity vendorInvoiceAmountEntity: vendorInvoiceAmountEntityList) {
			InvoiceSectionTO invoiceSectionTO = new InvoiceSectionTO();
			invoiceSectionTO.setNetInvoiceAmount(vendorInvoiceAmountEntity.getNetInvoiceAmount());
			invoiceSectionTO.setGstInvoiceAmount(vendorInvoiceAmountEntity.getGstInvoiceAmount());
			invoiceSectionTO.setTotalInvoiceAmount(vendorInvoiceAmountEntity.getTotalInvoiceAmount());
			invoiceSectionTO.setExpenditureType(vendorInvoiceAmountEntity.getExpenditureType());
			
			if(vendorInvoiceAmountEntity.getInvoiceAmountType() != null && 
					StringUtils.equals(vendorInvoiceAmountEntity.getInvoiceAmountType(), "INVOICE_SYSTEM_VERIFICATION")) {
				response.setAsPerSystemVerification(invoiceSectionTO);
			}
			else if(vendorInvoiceAmountEntity.getInvoiceAmountType() != null && 
					StringUtils.equals(vendorInvoiceAmountEntity.getInvoiceAmountType(), "AS_PER_INVOICE")) {
				response.setAsPerInvoice(invoiceSectionTO);
			}
			else if(vendorInvoiceAmountEntity.getInvoiceAmountType() != null && 
					StringUtils.equals(vendorInvoiceAmountEntity.getInvoiceAmountType(), "INVOICE_NET_AMOUNT")) {
				response.setNetAmountPayable(invoiceSectionTO);
			}
			else if(vendorInvoiceAmountEntity.getInvoiceAmountType() != null && 
					StringUtils.equals(vendorInvoiceAmountEntity.getInvoiceAmountType(), "INVOICE_AMOUNT_RETINED")) {
				response.setAmountRetained(invoiceSectionTO);
			}
		}
		
	}
	
	/**
	 * @param vendorPostInvoiceMaterialDeliveryDocketEntityList
	 */
	public static List<MaterialDeliveryDocketTO> convertVendorPostInvoiceMaterialDeliveryDocketEntityToMaterialDeliveryDocketTO(List<VendorPostInvoiceMaterialDeliveryDocketEntity> vendorPostInvoiceMaterialDeliveryDocketEntityList) {
		List<MaterialDeliveryDocketTO> materialDeliveryDocketTOList = new ArrayList<>();
		for(VendorPostInvoiceMaterialDeliveryDocketEntity vendorPostInvoiceMaterialDeliveryDocketEntity: vendorPostInvoiceMaterialDeliveryDocketEntityList) {
			MaterialDeliveryDocketTO materialDeliveryDocketTO = new MaterialDeliveryDocketTO();
			materialDeliveryDocketTO.setDocketNumber(vendorPostInvoiceMaterialDeliveryDocketEntity.getDocketNumber());
			materialDeliveryDocketTO.setDocketDate(vendorPostInvoiceMaterialDeliveryDocketEntity.getDocketDate());
			materialDeliveryDocketTO.setMaterialReleasedDate(vendorPostInvoiceMaterialDeliveryDocketEntity.getMaterialReleasedDate());
			materialDeliveryDocketTO.setScheduleItemId(vendorPostInvoiceMaterialDeliveryDocketEntity.getScheduleItemId());
			materialDeliveryDocketTO.setScheduleItemDescription(vendorPostInvoiceMaterialDeliveryDocketEntity.getScheduleItemDescription());
			materialDeliveryDocketTO.setUnitOfMeasure(vendorPostInvoiceMaterialDeliveryDocketEntity.getUnitOfMeasure());
			materialDeliveryDocketTO.setQtyAsPerContract(vendorPostInvoiceMaterialDeliveryDocketEntity.getQtyAsPerContract());
			materialDeliveryDocketTO.setProcessQtyUptoInvoicePeriod(vendorPostInvoiceMaterialDeliveryDocketEntity.getProcessQtyUptoInvoicePeriod());
			materialDeliveryDocketTO.setClaimedQtyToPreviousPeriod(vendorPostInvoiceMaterialDeliveryDocketEntity.getClaimedQtyToPreviousPeriod());
			materialDeliveryDocketTO.setClaimedQtyToCurrentPeriod(vendorPostInvoiceMaterialDeliveryDocketEntity.getClaimedQtyToCurrentPeriod());
			materialDeliveryDocketTO.setRateAmount(vendorPostInvoiceMaterialDeliveryDocketEntity.getRateAmount());
			materialDeliveryDocketTO.setReceiverComments(vendorPostInvoiceMaterialDeliveryDocketEntity.getReceiverComments());
			materialDeliveryDocketTOList.add(materialDeliveryDocketTO);
		}
		return materialDeliveryDocketTOList;
		
	}
	
	
	/**
	 * @param vendorPostInvoiceManpowerDeliveryDocketEntityList
	 */
	public static List<ManpowerDeliveryDocketTO> convertVendorPostInvoiceManpowerDeliveryDocketEntityToManpowerDeliveryDocketTO(List<VendorPostInvoiceManpowerDeliveryDocketEntity> vendorPostInvoiceManpowerDeliveryDocketEntityList) {
		List<ManpowerDeliveryDocketTO> manpowerDeliveryDocketList = new ArrayList<>();
		
		for(VendorPostInvoiceManpowerDeliveryDocketEntity vendorPostInvoiceManpowerDeliveryDocketEntity: vendorPostInvoiceManpowerDeliveryDocketEntityList) {
			ManpowerDeliveryDocketTO manpowerDeliveryDocketTO = new ManpowerDeliveryDocketTO();
			
			manpowerDeliveryDocketTO.setDocumentType(vendorPostInvoiceManpowerDeliveryDocketEntity.getDocumentType());
			manpowerDeliveryDocketTO.setDocumentNumber(vendorPostInvoiceManpowerDeliveryDocketEntity.getDocumentNumber());
			manpowerDeliveryDocketTO.setDocumentPeriod(vendorPostInvoiceManpowerDeliveryDocketEntity.getDocumentPeriod());
			manpowerDeliveryDocketTO.setEmployeeId(vendorPostInvoiceManpowerDeliveryDocketEntity.getEmployeeId());
			manpowerDeliveryDocketTO.setEmployeeFirstName(vendorPostInvoiceManpowerDeliveryDocketEntity.getEmployeeFirstName());
			manpowerDeliveryDocketTO.setEmployeeLastName(vendorPostInvoiceManpowerDeliveryDocketEntity.getEmployeeLastName());
			manpowerDeliveryDocketTO.setTrade(vendorPostInvoiceManpowerDeliveryDocketEntity.getTrade());
			manpowerDeliveryDocketTO.setDateOfEnrollment(vendorPostInvoiceManpowerDeliveryDocketEntity.getDateOfEntrollment());
			manpowerDeliveryDocketTO.setDateOfMobilisation(vendorPostInvoiceManpowerDeliveryDocketEntity.getDateOfMobilisation());
			manpowerDeliveryDocketTO.setActualDateOfDeMobilisation(vendorPostInvoiceManpowerDeliveryDocketEntity.getActualDateOfDeMobilisation());
			manpowerDeliveryDocketTO.setScheduleItemId(vendorPostInvoiceManpowerDeliveryDocketEntity.getScheduleItemId());
			manpowerDeliveryDocketTO.setScheduleItemDescription(vendorPostInvoiceManpowerDeliveryDocketEntity.getScheduleItemDescription());
			manpowerDeliveryDocketTO.setUnitOfMeasure(vendorPostInvoiceManpowerDeliveryDocketEntity.getUnitOfMeasure());
			manpowerDeliveryDocketTO.setWageFactor(vendorPostInvoiceManpowerDeliveryDocketEntity.getWageFactor());
			manpowerDeliveryDocketTO.setGrossUtilisationRatio(vendorPostInvoiceManpowerDeliveryDocketEntity.getGrossUtilisationRatio());
			manpowerDeliveryDocketTO.setNetUtilisationRaio(vendorPostInvoiceManpowerDeliveryDocketEntity.getNetUtilisationRaio());
			manpowerDeliveryDocketTO.setQty(vendorPostInvoiceManpowerDeliveryDocketEntity.getQty());
			manpowerDeliveryDocketTO.setPresentAndPaidLeaveDays(vendorPostInvoiceManpowerDeliveryDocketEntity.getPresentAndPaidLeaveDays());
			manpowerDeliveryDocketTO.setAbsentAndUnpaidLeaveDays(vendorPostInvoiceManpowerDeliveryDocketEntity.getAbsentAndUnpaidLeaveDays());
			manpowerDeliveryDocketTO.setNonworkingDays(vendorPostInvoiceManpowerDeliveryDocketEntity.getNonworkingDays());
			manpowerDeliveryDocketTO.setGrossBillablePeriodInDays(vendorPostInvoiceManpowerDeliveryDocketEntity.getGrossBillablePeriodInDays());
			manpowerDeliveryDocketTO.setGrossUtilisationRatio(vendorPostInvoiceManpowerDeliveryDocketEntity.getGrossUtilisationRatio());
			manpowerDeliveryDocketTO.setNonBillablePeriodInDays(vendorPostInvoiceManpowerDeliveryDocketEntity.getNonBillablePeriodInDays());
			manpowerDeliveryDocketTO.setCalendarDaysCountAsInvoiceperiod(vendorPostInvoiceManpowerDeliveryDocketEntity.getCalendarDaysCountAsInvoiceperiod());
			manpowerDeliveryDocketTO.setRate(vendorPostInvoiceManpowerDeliveryDocketEntity.getRate());
			manpowerDeliveryDocketTO.setComments(vendorPostInvoiceManpowerDeliveryDocketEntity.getComments());
			manpowerDeliveryDocketTO.setAmount(vendorPostInvoiceManpowerDeliveryDocketEntity.getAmount());
			manpowerDeliveryDocketTO.setTypeOfSection(vendorPostInvoiceManpowerDeliveryDocketEntity.getTypeOfSection());
			manpowerDeliveryDocketList.add(manpowerDeliveryDocketTO);
		}
		return manpowerDeliveryDocketList;
		
	}
	
	
	/**
	 * @param vendorPostInvoicePlantsDeliveryDocketEntityList
	 * @return
	 */
	public static List<PlantsDeliveryDocketTO> convertVendorPostInvoicePlantsDeliveryDocketEntityToPlantsDeliveryDocketTO(List<VendorPostInvoicePlantsDeliveryDocketEntity> vendorPostInvoicePlantsDeliveryDocketEntityList){
		List<PlantsDeliveryDocketTO> plantsDeliveryDocket  =  new ArrayList<>();
		for(VendorPostInvoicePlantsDeliveryDocketEntity vendorPostInvoicePlantsDeliveryDocketEntity: vendorPostInvoicePlantsDeliveryDocketEntityList) {
			PlantsDeliveryDocketTO plantsDeliveryDocketTO = new PlantsDeliveryDocketTO();
			plantsDeliveryDocketTO.setWorkDairyNumber(vendorPostInvoicePlantsDeliveryDocketEntity.getWorkDairyNumber());
			plantsDeliveryDocketTO.setPlantWorkStatusRecordId(vendorPostInvoicePlantsDeliveryDocketEntity.getPlantWorkStatusRecordId());
			plantsDeliveryDocketTO.setDocketNumber(vendorPostInvoicePlantsDeliveryDocketEntity.getDocketNumber());
			plantsDeliveryDocketTO.setDocketDate(vendorPostInvoicePlantsDeliveryDocketEntity.getDocketDate());
			plantsDeliveryDocketTO.setPlantsReceivedDate(vendorPostInvoicePlantsDeliveryDocketEntity.getPlantsReceivedDate());
			plantsDeliveryDocketTO.setPlantId(vendorPostInvoicePlantsDeliveryDocketEntity.getPlantId());
			plantsDeliveryDocketTO.setPlantOwner(vendorPostInvoicePlantsDeliveryDocketEntity.getPlantOwner());
			plantsDeliveryDocketTO.setWorkStatusCategory(vendorPostInvoicePlantsDeliveryDocketEntity.getWorkStatusCategory());
			plantsDeliveryDocketTO.setShiftCategory(vendorPostInvoicePlantsDeliveryDocketEntity.getShiftCategory());
			plantsDeliveryDocketTO.setUtilisationCategory(vendorPostInvoicePlantsDeliveryDocketEntity.getUtilisationCategory());
			plantsDeliveryDocketTO.setMobilisation(vendorPostInvoicePlantsDeliveryDocketEntity.getMobilisation());
			plantsDeliveryDocketTO.setActualDateOfDeMobilisation(vendorPostInvoicePlantsDeliveryDocketEntity.getActualDateOfDeMobilisation());
			plantsDeliveryDocketTO.setPlantDescription(vendorPostInvoicePlantsDeliveryDocketEntity.getPlantDescription());
			plantsDeliveryDocketTO.setScheduleItemId(vendorPostInvoicePlantsDeliveryDocketEntity.getScheduleItemId());
			plantsDeliveryDocketTO.setScheduleItemDescription(vendorPostInvoicePlantsDeliveryDocketEntity.getScheduleItemDescription());
			plantsDeliveryDocketTO.setUnitOfMeasure(vendorPostInvoicePlantsDeliveryDocketEntity.getUnitOfMeasure());
			plantsDeliveryDocketTO.setQty(vendorPostInvoicePlantsDeliveryDocketEntity.getQty());
			plantsDeliveryDocketTO.setRate(vendorPostInvoicePlantsDeliveryDocketEntity.getRate());
			plantsDeliveryDocketTO.setAmount(vendorPostInvoicePlantsDeliveryDocketEntity.getAmount());
			plantsDeliveryDocketTO.setComments(vendorPostInvoicePlantsDeliveryDocketEntity.getComments());
			plantsDeliveryDocketTO.setTypeOfSection(vendorPostInvoicePlantsDeliveryDocketEntity.getTypeOfSection());
			plantsDeliveryDocket.add(plantsDeliveryDocketTO);
		}
		return plantsDeliveryDocket;
		
	}
	
	
	/**
	 * @param vendorPostInvoiceServiceDeliveryDocketEntityList
	 */
	public static List<ServiceDeliveryDocketTO> convertVendorPostInvoiceServiceDeliveryDocketEntityToServiceDeliveryDocketTO(List<VendorPostInvoiceServiceDeliveryDocketEntity> vendorPostInvoiceServiceDeliveryDocketEntityList) {
		List<ServiceDeliveryDocketTO> serviceDeliveryDocketTOList = new ArrayList<>();
		for(VendorPostInvoiceServiceDeliveryDocketEntity vendorPostInvoiceServiceDeliveryDocketEntity: vendorPostInvoiceServiceDeliveryDocketEntityList) {
			ServiceDeliveryDocketTO serviceDeliveryDocketTO = new ServiceDeliveryDocketTO();
			serviceDeliveryDocketTO.setScheduleItemId(vendorPostInvoiceServiceDeliveryDocketEntity.getScheduleItemId());
			serviceDeliveryDocketTO.setScheduleItemDescription(vendorPostInvoiceServiceDeliveryDocketEntity.getScheduleItemDescription());
			serviceDeliveryDocketTO.setMeasuringUnit(vendorPostInvoiceServiceDeliveryDocketEntity.getMeasuringUnit());
			serviceDeliveryDocketTO.setAgreementQTY(vendorPostInvoiceServiceDeliveryDocketEntity.getAgreementQTY());
			serviceDeliveryDocketTO.setCumulativeProgressQTYToInvoicePeriod(vendorPostInvoiceServiceDeliveryDocketEntity.getCumulativeProgressQTYToInvoicePeriod());
			serviceDeliveryDocketTO.setQtyCumulativeClaimedForperviousPeriod(vendorPostInvoiceServiceDeliveryDocketEntity.getQtyCumulativeClaimedForperviousPeriod());
			serviceDeliveryDocketTO.setNetProgressQTY(vendorPostInvoiceServiceDeliveryDocketEntity.getNetProgressQTY());
			serviceDeliveryDocketTO.setRate(vendorPostInvoiceServiceDeliveryDocketEntity.getRate());
			serviceDeliveryDocketTO.setAmount(vendorPostInvoiceServiceDeliveryDocketEntity.getAmount());
			serviceDeliveryDocketTO.setComments(vendorPostInvoiceServiceDeliveryDocketEntity.getComments());
			serviceDeliveryDocketTOList.add(serviceDeliveryDocketTO);
		}
		return serviceDeliveryDocketTOList;
	}
	
	
	public static List<SubDeliveryDocketTO> convertVendorPostInvoiceSubDeliveryDocketEntityToSubDeliveryDocketTO(List<VendorPostInvoiceSubDeliveryDocketEntity> vendorPostInvoiceSubDeliveryDocketEntityList) {
		List<SubDeliveryDocketTO> subDeliveryDocketTOList = new ArrayList<>();
		for(VendorPostInvoiceSubDeliveryDocketEntity vendorPostInvoiceSubDeliveryDocketEntity: vendorPostInvoiceSubDeliveryDocketEntityList) {
			SubDeliveryDocketTO  subDeliveryDocketTO = new SubDeliveryDocketTO();
			subDeliveryDocketTO.setSowItemId(vendorPostInvoiceSubDeliveryDocketEntity.getSowItemId());
			subDeliveryDocketTO.setSowItemIdDescription(vendorPostInvoiceSubDeliveryDocketEntity.getSowItemIdDescription());
			subDeliveryDocketTO.setMeasuringUnit(vendorPostInvoiceSubDeliveryDocketEntity.getMeasuringUnit());
			subDeliveryDocketTO.setCumulativeProgressQTYToInvoicePeriod(vendorPostInvoiceSubDeliveryDocketEntity.getCumulativeProgressQTYToInvoicePeriod());
			subDeliveryDocketTO.setQtyClaimedForPreviousPeriod(vendorPostInvoiceSubDeliveryDocketEntity.getQtyClaimedForPreviousPeriod());
			subDeliveryDocketTO.setNetProgressQTY(vendorPostInvoiceSubDeliveryDocketEntity.getNetProgressQTY());
			subDeliveryDocketTO.setRate(vendorPostInvoiceSubDeliveryDocketEntity.getRate());
			subDeliveryDocketTO.setAmount(vendorPostInvoiceSubDeliveryDocketEntity.getAmount());
			subDeliveryDocketTO.setComments(vendorPostInvoiceSubDeliveryDocketEntity.getComments());
			subDeliveryDocketTOList.add(subDeliveryDocketTO);
		}
		return subDeliveryDocketTOList;
	}
	
	
	

}
