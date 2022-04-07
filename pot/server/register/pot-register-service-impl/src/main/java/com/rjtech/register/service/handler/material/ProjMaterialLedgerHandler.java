package com.rjtech.register.service.handler.material;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.material.model.MaterialProjLedgerEntity;
import com.rjtech.register.utils.RegisterCommonUtils;

public class ProjMaterialLedgerHandler {

    public static MaterialProjLedgerEntity generateProjMaterialLedgerCredit(
            MaterialProjLedgerEntity existProjLedgerEntity, BigDecimal creditQty, String issueDocketRef) {
        MaterialProjLedgerEntity materialProjLedgerEntity = new MaterialProjLedgerEntity();
        if (CommonUtil.objectNotNull(existProjLedgerEntity)) {
            BeanUtils.copyProperties(existProjLedgerEntity, materialProjLedgerEntity);
            existProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_N);

            materialProjLedgerEntity.setId(null);
            materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
            materialProjLedgerEntity.setDebit(null);
            materialProjLedgerEntity.setCredit(creditQty.setScale(4, RoundingMode.UP));
            materialProjLedgerEntity.setOpenBalance(materialProjLedgerEntity.getAvailableBalance());
            materialProjLedgerEntity.setAvailableBalance(
                    materialProjLedgerEntity.getAvailableBalance().add(creditQty).setScale(4, RoundingMode.UP));
            materialProjLedgerEntity
                    .setTotal(materialProjLedgerEntity.getTotal().add(creditQty).setScale(4, RoundingMode.UP));

        } else {
            materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
            materialProjLedgerEntity.setCredit(creditQty.setScale(4, RoundingMode.UP));
            materialProjLedgerEntity.setOpenBalance(new BigDecimal(0));
            materialProjLedgerEntity.setAvailableBalance(creditQty.setScale(4, RoundingMode.UP));
            materialProjLedgerEntity.setTotal(creditQty.setScale(4, RoundingMode.UP));
            materialProjLedgerEntity.setStatus(StatusCodes.ACTIVE.getValue());
        }
        materialProjLedgerEntity.setEffectiveDate(new Date());

        //materialProjLedgerEntity.setDocketNumber(issueDocketRef);

        return materialProjLedgerEntity;
    }

    public static MaterialProjLedgerEntity generateProjMaterialLedgerDebitExternal(
            MaterialProjLedgerEntity existProjLedgerEntity, BigDecimal issueQty, String issueDocketRef) {
        MaterialProjLedgerEntity materialProjLedgerEntity = new MaterialProjLedgerEntity();
        BeanUtils.copyProperties(existProjLedgerEntity, materialProjLedgerEntity);
        existProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_N);

        materialProjLedgerEntity.setId(null);
        materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        materialProjLedgerEntity.setCredit(null);
        materialProjLedgerEntity.setDebit(issueQty.setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setOpenBalance(materialProjLedgerEntity.getAvailableBalance());

        BigDecimal availableBalance = materialProjLedgerEntity.getAvailableBalance().subtract(issueQty);
        materialProjLedgerEntity.setAvailableBalance(availableBalance.setScale(4, RoundingMode.UP));
        materialProjLedgerEntity
                .setTotal(materialProjLedgerEntity.getTotal().subtract(issueQty).setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setStatus(StatusCodes.ACTIVE.getValue());
        materialProjLedgerEntity.setEffectiveDate(new Date());
        //materialProjLedgerEntity.setDocketNumber(issueDocketRef);
        return materialProjLedgerEntity;
    }

    public static MaterialProjLedgerEntity generateProjMaterialLedgerTransitForInternal(
            MaterialProjLedgerEntity existProjLedgerEntity, BigDecimal issueQty, String issueDocketRef) {
        MaterialProjLedgerEntity materialProjLedgerEntity = new MaterialProjLedgerEntity();
        BeanUtils.copyProperties(existProjLedgerEntity, materialProjLedgerEntity);
        existProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_N);

        materialProjLedgerEntity.setId(null);
        materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        materialProjLedgerEntity.setOpenBalance(materialProjLedgerEntity.getAvailableBalance());
        BigDecimal availableBalance = materialProjLedgerEntity.getAvailableBalance().subtract(issueQty);
        materialProjLedgerEntity.setAvailableBalance(availableBalance.setScale(4, RoundingMode.UP));

        materialProjLedgerEntity.setDebit(issueQty.setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setCredit(null);
        BigDecimal transit = materialProjLedgerEntity.getTransit();
        transit = CommonUtil.ifNullGetDefaultValue(transit);
        materialProjLedgerEntity.setTransit(transit.add(issueQty).setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setEffectiveDate(new Date());
        //materialProjLedgerEntity.setDocketNumber(issueDocketRef);
        materialProjLedgerEntity.setStatus(StatusCodes.ACTIVE.getValue());

        return materialProjLedgerEntity;
    }

    public static MaterialProjLedgerEntity generateNewTOProjMaterialLedgerTransit(BigDecimal issueQty,
            String issueDocketRef) {
        MaterialProjLedgerEntity materialProjLedgerEntity = new MaterialProjLedgerEntity();
        materialProjLedgerEntity.setId(null);
        materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        materialProjLedgerEntity.setAvailableBalance(new BigDecimal(0));
        materialProjLedgerEntity.setOpenBalance(new BigDecimal(0));
        materialProjLedgerEntity.setTransit(issueQty.setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setTotal(issueQty.setScale(4, RoundingMode.UP));
        materialProjLedgerEntity.setEffectiveDate(new Date());
        //materialProjLedgerEntity.setDocketNumber(issueDocketRef);
        materialProjLedgerEntity.setStatus(StatusCodes.ACTIVE.getValue());
        return materialProjLedgerEntity;
    }

    public static MaterialProjLedgerEntity generateExistTOProjMaterialLedgerTransitExternal(
            MaterialProjLedgerEntity existProjLedgerEntity, BigDecimal issueQty, String issueDocketRef) {
        MaterialProjLedgerEntity materialProjLedgerEntity = new MaterialProjLedgerEntity();
        BeanUtils.copyProperties(existProjLedgerEntity, materialProjLedgerEntity);
        existProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_N);

        materialProjLedgerEntity.setId(null);
        materialProjLedgerEntity.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        materialProjLedgerEntity.setOpenBalance(materialProjLedgerEntity.getAvailableBalance());
        materialProjLedgerEntity.setCredit(null);
        materialProjLedgerEntity.setDebit(null);

        BigDecimal transit = materialProjLedgerEntity.getTransit();
        transit = CommonUtil.ifNullGetDefaultValue(transit);

        materialProjLedgerEntity.setTransit(transit.add(issueQty).setScale(4, RoundingMode.UP));

        materialProjLedgerEntity
                .setTotal(materialProjLedgerEntity.getTotal().add(issueQty).setScale(4, RoundingMode.UP));

        materialProjLedgerEntity.setEffectiveDate(new Date());
        //materialProjLedgerEntity.setDocketNumber(issueDocketRef);
        materialProjLedgerEntity.setStatus(StatusCodes.ACTIVE.getValue());

        return materialProjLedgerEntity;
    }

}
