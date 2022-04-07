package com.rjtech.procurement.service.handler;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.dto.InvoiceAmountTO;
import com.rjtech.procurement.dto.InvoiceAssignCostCodesTO;
import com.rjtech.procurement.dto.InvoiceParticularsTO;
import com.rjtech.procurement.dto.InvoiceVendorBankTO;
import com.rjtech.procurement.model.InvoiceAmountEntity;
import com.rjtech.procurement.model.InvoiceAssignCostCodesEntity;
import com.rjtech.procurement.model.InvoiceParticularsEntity;
import com.rjtech.procurement.model.InvoiceVendorBankEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.copy.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;

public class ProjectInvoiceHandler {

    public static InvoiceParticularsEntity convertPOJOToEntity(InvoiceParticularsTO invoiceParticularsTO,
            PurchaseOrderRepository purchaseOrderRepository, EPSProjRepository epsProjRepository) {
        InvoiceParticularsEntity entity = new InvoiceParticularsEntity();
        if (CommonUtil.isNonBlankLong(invoiceParticularsTO.getId())) {
            entity.setId(invoiceParticularsTO.getId());
        }
        if (CommonUtil.isNotBlankStr(invoiceParticularsTO.getInvoiceDate())) {
            entity.setInvoicedate(CommonUtil.convertStringToDate(invoiceParticularsTO.getInvoiceDate()));
        }
        if (CommonUtil.isNotBlankStr(invoiceParticularsTO.getInvoiceReceivedDate())) {
            entity.setReceivedInvoicedate(
                    CommonUtil.convertStringToDate(invoiceParticularsTO.getInvoiceReceivedDate()));
        }
        if (CommonUtil.isNotBlankStr(invoiceParticularsTO.getPayDueDate())) {
            entity.setPayDueDate(CommonUtil.convertStringToDate(invoiceParticularsTO.getPayDueDate()));
        }
        if (CommonUtil.isNotBlankStr(invoiceParticularsTO.getProposedPayDueDate())) {
            entity.setProposedDueDate(CommonUtil.convertStringToDate(invoiceParticularsTO.getProposedPayDueDate()));
        }
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceParticularsTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceParticularsTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        entity.setPaymentInDays(invoiceParticularsTO.getPaymentInDays());
        entity.setStatus(invoiceParticularsTO.getStatus());
        return entity;
    }

    public static InvoiceAmountEntity convertPOJOToEntity(InvoiceAmountTO invoiceAmountTO,
            EPSProjRepository epsProjRepository, PurchaseOrderRepository purchaseOrderRepository) {
        InvoiceAmountEntity entity = new InvoiceAmountEntity();

        if (CommonUtil.isNonBlankLong(invoiceAmountTO.getId())) {
            entity.setId(invoiceAmountTO.getId());
        }
        entity.setInvoiceAmount(invoiceAmountTO.getInvoiceAmount());
        entity.setNetPayableAmount(invoiceAmountTO.getNetPayableAmount());
        entity.setRetainedAmount(invoiceAmountTO.getRetainedAmount());
        entity.setTaxAmount(invoiceAmountTO.getTaxAmount());
        entity.setRetainedTaxAmount(invoiceAmountTO.getRetainedTaxAmount());
        entity.setNetTaxPayableAmount(invoiceAmountTO.getNetTaxPayableAmount());
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceAmountTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceAmountTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        entity.setStatus(invoiceAmountTO.getStatus());
        return entity;
    }

    public static InvoiceAssignCostCodesEntity convertPOJOToEntity(InvoiceAssignCostCodesTO assignCostCodesTO,
            EPSProjRepository epsProjRepository, PurchaseOrderRepository purchaseOrderRepository,
            ProjCostItemRepositoryCopy projCostItemRepository) {
        InvoiceAssignCostCodesEntity entity = new InvoiceAssignCostCodesEntity();

        if (CommonUtil.isNonBlankLong(assignCostCodesTO.getId())) {
            entity.setId(assignCostCodesTO.getId());
        }

        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(assignCostCodesTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }

        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(assignCostCodesTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }

        ProjCostItemEntity projCostItemEntity = projCostItemRepository.findOne(assignCostCodesTO.getCostId());
        if (null != projCostItemEntity) {
            entity.setCostId(projCostItemEntity);
        }

        entity.setAmount(assignCostCodesTO.getAmount());
        entity.setSplit(assignCostCodesTO.getSplit());
        entity.setStatus(assignCostCodesTO.getStatus());
        return entity;
    }

    public static InvoiceVendorBankEntity convertPOJOToEntity(InvoiceVendorBankTO vendorBankTO,
            PurchaseOrderRepository purchaseOrderRepository, EPSProjRepository epsProjRepository,
            LoginRepository loginRepository) {
        InvoiceVendorBankEntity entity = new InvoiceVendorBankEntity();

        if (CommonUtil.isNonBlankLong(vendorBankTO.getId())) {
            entity.setId(vendorBankTO.getId());
        }
        entity.setBankName(vendorBankTO.getBankName());
        entity.setAccountName(vendorBankTO.getAccountName());
        entity.setBankCode(vendorBankTO.getBankCode());
        entity.setAccountNum(vendorBankTO.getAccountNum());
        entity.setAccDetailsVerified(vendorBankTO.getAccDetailsVerified());
        entity.setApprId(loginRepository.findOne(vendorBankTO.getApprId()));
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(vendorBankTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(vendorBankTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        entity.setStatus(vendorBankTO.getStatus());
        return entity;
    }

    public static InvoiceParticularsTO convertEntityToPOJO(InvoiceParticularsEntity entity) {
        InvoiceParticularsTO invoiceDtlTO = new InvoiceParticularsTO();
        PurchaseOrderEntity purId = entity.getPurId();
        ProjMstrEntity projMstr = entity.getProjId();
        if (CommonUtil.isNonBlankLong(entity.getId())) {
            invoiceDtlTO.setId(invoiceDtlTO.getId());
            invoiceDtlTO.setInvoiceNumber(ModuleCodesPrefixes.PROJ_INVOICE_PREFIX.getDesc() + "-" + projMstr.getCode()
                    + "-" + AppUtils.formatNumberToString(entity.getId()));
        }
        if (CommonUtil.isNotBlankDate(entity.getInvoicedate())) {
            invoiceDtlTO.setInvoiceDate(CommonUtil.convertDateToString(entity.getInvoicedate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getReceivedInvoicedate())) {
            invoiceDtlTO.setInvoiceReceivedDate(CommonUtil.convertDateToString(entity.getReceivedInvoicedate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getPayDueDate())) {
            invoiceDtlTO.setPayDueDate(CommonUtil.convertDateToString(entity.getPayDueDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getProposedDueDate())) {
            invoiceDtlTO.setProposedPayDueDate(CommonUtil.convertDateToString(entity.getProposedDueDate()));
        }
        if (null != purId) {
            invoiceDtlTO.setPurId(purId.getId());
        }
        if (null != projMstr) {
            invoiceDtlTO.setProjId(projMstr.getProjectId());
        }
        invoiceDtlTO.setPaymentInDays(entity.getPaymentInDays());
        invoiceDtlTO.setStatus(entity.getStatus());

        return invoiceDtlTO;
    }

    public static InvoiceAmountTO convertEntityToPOJO(InvoiceAmountEntity entity) {
        InvoiceAmountTO amountTO = new InvoiceAmountTO();

        if (CommonUtil.isNonBlankLong(entity.getId())) {
            amountTO.setId(amountTO.getId());
        }
        PurchaseOrderEntity purId = entity.getPurId();
        ProjMstrEntity projMstr = entity.getProjId();
        amountTO.setInvoiceAmount(entity.getInvoiceAmount());
        amountTO.setNetPayableAmount(entity.getNetPayableAmount());
        amountTO.setRetainedAmount(entity.getRetainedAmount());
        amountTO.setTaxAmount(entity.getTaxAmount());
        amountTO.setRetainedTaxAmount(entity.getRetainedTaxAmount());
        amountTO.setNetTaxPayableAmount(entity.getNetTaxPayableAmount());
        if (null != purId) {
            amountTO.setPurId(purId.getId());
        }
        if (null != projMstr) {
            amountTO.setProjId(projMstr.getProjectId());
        }
        amountTO.setStatus(entity.getStatus());
        return amountTO;
    }

    public static InvoiceAssignCostCodesTO convertEntityToPOJO(InvoiceAssignCostCodesEntity entity) {
        InvoiceAssignCostCodesTO costCodesTO = new InvoiceAssignCostCodesTO();

        if (CommonUtil.isNonBlankLong(entity.getId())) {
            costCodesTO.setId(costCodesTO.getId());
        }
        costCodesTO.setAmount(entity.getAmount());

        costCodesTO.setCostId(entity.getCostId().getId());
        costCodesTO.setSplit(entity.getSplit());
        costCodesTO.setPurId(entity.getPurId().getId());
        costCodesTO.setProjId(entity.getProjId().getProjectId());
        costCodesTO.setStatus(entity.getStatus());
        return costCodesTO;
    }

    public static InvoiceVendorBankTO convertEntityToPOJO(InvoiceVendorBankEntity entity) {
        InvoiceVendorBankTO bankTO = new InvoiceVendorBankTO();

        if (CommonUtil.isNonBlankLong(entity.getId())) {
            bankTO.setId(bankTO.getId());
        }
        PurchaseOrderEntity purId = entity.getPurId();
        ProjMstrEntity projMstr = entity.getProjId();
        bankTO.setBankName(entity.getBankName());
        bankTO.setAccountName(entity.getAccountName());
        bankTO.setBankCode(entity.getBankCode());
        bankTO.setAccountNum(entity.getAccountNum());
        bankTO.setAccDetailsVerified(entity.getAccDetailsVerified());
        UserMstrEntity apprUser = entity.getApprId();
        if (apprUser != null)
            bankTO.setApprId(apprUser.getUserId());
        if (null != purId) {
            bankTO.setPurId(purId.getId());
        }
        if (null != projMstr) {
            bankTO.setProjId(projMstr.getProjectId());
        }
        bankTO.setStatus(entity.getStatus());
        return bankTO;
    }

}
