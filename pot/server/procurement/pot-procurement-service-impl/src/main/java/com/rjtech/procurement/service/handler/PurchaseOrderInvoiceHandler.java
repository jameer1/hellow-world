package com.rjtech.procurement.service.handler;

import java.util.List;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.dto.PurchaseOrderInvoiceDtlTO;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.model.PurchaseOrderInvoiceDtlEntity;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.procurement.resp.PurchaseOrderInvoiceResp;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.copy.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PurchaseOrderInvoiceHandler {

    public static PurchaseOrderInvoiceDtlEntity convertPOJOToEntity(PurchaseOrderInvoiceDtlTO invoiceDtlTO,
            PurchaseOrderRepository purchaseOrderRepository, EPSProjRepository epsProjRepository,
            ProjCostItemRepositoryCopy projCostItemRepository, LoginRepository loginRepository) {
        PurchaseOrderInvoiceDtlEntity entity = new PurchaseOrderInvoiceDtlEntity();
        if (CommonUtil.isNonBlankLong(invoiceDtlTO.getId())) {
            entity.setId(invoiceDtlTO.getId());
        }

        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository.findOne(invoiceDtlTO.getPurId());
        if (null != purchaseOrderEntity) {
            entity.setPurId(purchaseOrderEntity);
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(invoiceDtlTO.getProjId());

        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        if (CommonUtil.isNotBlankStr(invoiceDtlTO.getInvoiceDate())) {
            entity.setInvoiceDate(CommonUtil.convertStringToDate(invoiceDtlTO.getInvoiceDate()));
        }
        if (CommonUtil.isNotBlankStr(invoiceDtlTO.getReceivedDate())) {
            entity.setReceivedDate(CommonUtil.convertStringToDate(invoiceDtlTO.getReceivedDate()));
        }
        if (CommonUtil.isNotBlankStr(invoiceDtlTO.getPayDueDate())) {
            entity.setPayDueDate(CommonUtil.convertStringToDate(invoiceDtlTO.getPayDueDate()));
        }
        if (CommonUtil.isNotBlankStr(invoiceDtlTO.getProposedDueDate())) {
            entity.setProposedDueDate(CommonUtil.convertStringToDate(invoiceDtlTO.getProposedDueDate()));
        }
        entity.setPaymentInDays(invoiceDtlTO.getPaymentInDays());
        entity.setNetAmount(invoiceDtlTO.getNetAmount());
        entity.setTaxAmount(invoiceDtlTO.getTaxAmount());
        entity.setRetainedNetAmount(invoiceDtlTO.getRetainedNetAmount());
        entity.setRetainedTaxAmount(invoiceDtlTO.getRetainedNetAmount());
        ProjCostItemEntity projCostItemEntity = projCostItemRepository.findOne(invoiceDtlTO.getCostId());
        if (null != projCostItemEntity) {
            entity.setCostId(projCostItemEntity);
        }
        entity.setSplit(invoiceDtlTO.getSplit());
        entity.setCostAmount(invoiceDtlTO.getCostAmount());
        entity.setBankName(invoiceDtlTO.getBankName());
        entity.setAccName(invoiceDtlTO.getAccName());
        entity.setBankCode(invoiceDtlTO.getBankCode());
        entity.setAccNum(invoiceDtlTO.getAccNum());
        entity.setAccDtlsVerified(invoiceDtlTO.getAccDtlsVerified());
        entity.setCurrentStatus(invoiceDtlTO.getCurrentStatus());
        entity.setPaymentStatus(invoiceDtlTO.getPaymentStatus());
        entity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        entity.setApprUserId(loginRepository.findOne(invoiceDtlTO.getApprUserId()));
        entity.setFinanceUserId(loginRepository.findOne(invoiceDtlTO.getFinanceUserId()));
        entity.setComments(invoiceDtlTO.getComments());

        entity.setStatus(invoiceDtlTO.getStatus());

        return entity;
    }

    public static PurchaseOrderInvoiceResp populatePostInvoices(List<PurchaseOrderInvoiceDtlEntity> entities) {
        PurchaseOrderInvoiceResp purchaseOrderInvoiceResp = new PurchaseOrderInvoiceResp();
        for (PurchaseOrderInvoiceDtlEntity purchaseOrderInvoiceDtlEntity : entities) {
            purchaseOrderInvoiceResp.getPurchaseOrderInvoiceDtlTOs()
                    .add(convertEntityToPOJO(purchaseOrderInvoiceDtlEntity));
        }
        return purchaseOrderInvoiceResp;
    }

    public static PurchaseOrderInvoiceDtlTO convertEntityToPOJO(PurchaseOrderInvoiceDtlEntity entity) {
        PurchaseOrderInvoiceDtlTO invoiceDtlTO = new PurchaseOrderInvoiceDtlTO();
        if (CommonUtil.isNonBlankLong(entity.getId())) {
            invoiceDtlTO.setId(entity.getId());
        }
        PurchaseOrderEntity purId = entity.getPurId();
        ProjMstrEntity projMstr = entity.getProjId();
        ProjCostItemEntity projCostItemEntity = entity.getCostId();
        if (null != purId) {
            invoiceDtlTO.setPurId(purId.getId());
        }
        if (null != projMstr) {
            invoiceDtlTO.setProjId(projMstr.getProjectId());
            invoiceDtlTO.setInvoiceNum(ModuleCodesPrefixes.PROJ_INVOICE_PREFIX.getDesc() + "-" + projMstr.getCode()
                    + "-" + AppUtils.formatNumberToString(entity.getId()));
        }
        if (CommonUtil.isNotBlankDate(entity.getInvoiceDate())) {
            invoiceDtlTO.setInvoiceDate(CommonUtil.convertDateToString(entity.getInvoiceDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getReceivedDate())) {
            invoiceDtlTO.setReceivedDate(CommonUtil.convertDateToString(entity.getReceivedDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getPayDueDate())) {
            invoiceDtlTO.setPayDueDate(CommonUtil.convertDateToString(entity.getPayDueDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getProposedDueDate())) {
            invoiceDtlTO.setProposedDueDate(CommonUtil.convertDateToString(entity.getProposedDueDate()));
        }
        invoiceDtlTO.setPaymentInDays(entity.getPaymentInDays());
        invoiceDtlTO.setNetAmount(entity.getNetAmount());
        invoiceDtlTO.setTaxAmount(entity.getTaxAmount());
        invoiceDtlTO.setRetainedNetAmount(entity.getRetainedNetAmount());
        invoiceDtlTO.setRetainedTaxAmount(entity.getRetainedNetAmount());
        if (null != projCostItemEntity) {
            invoiceDtlTO.setCostId(projCostItemEntity.getId());
        }
        invoiceDtlTO.setSplit(entity.getSplit());
        invoiceDtlTO.setCostAmount(entity.getCostAmount());
        invoiceDtlTO.setBankName(entity.getBankName());
        invoiceDtlTO.setAccName(entity.getAccName());
        invoiceDtlTO.setBankCode(entity.getBankCode());
        invoiceDtlTO.setAccNum(entity.getAccNum());
        invoiceDtlTO.setAccDtlsVerified(entity.getAccDtlsVerified());
        invoiceDtlTO.setCurrentStatus(entity.getCurrentStatus());
        invoiceDtlTO.setPaymentStatus(entity.getPaymentStatus());
        UserMstrEntity reqUser = entity.getReqUserId();
        if (reqUser != null)
            invoiceDtlTO.setReqUserId(reqUser.getUserId());
        UserMstrEntity apprUser = entity.getApprUserId();
        if (apprUser != null)
            invoiceDtlTO.setApprUserId(apprUser.getUserId());
        UserMstrEntity financeUser = entity.getFinanceUserId();
        if (financeUser != null)
            invoiceDtlTO.setFinanceUserId(financeUser.getUserId());
        invoiceDtlTO.setComments(entity.getComments());

        invoiceDtlTO.setStatus(entity.getStatus());

        return invoiceDtlTO;
    }

}
