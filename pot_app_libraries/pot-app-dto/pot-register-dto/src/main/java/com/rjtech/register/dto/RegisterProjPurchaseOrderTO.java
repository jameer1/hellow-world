package com.rjtech.register.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterProjPurchaseOrderTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private BigDecimal usedItems;
    private BigDecimal quantity;
    private BigDecimal cumulative;
    private LabelKeyTO purchaseLabelKeyTO;
    private LabelKeyTO purchaseSchLabelKeyTO;

    private Long projPOId;

    public RegisterProjPurchaseOrderTO() {
        purchaseLabelKeyTO = new LabelKeyTO();
        purchaseSchLabelKeyTO = new LabelKeyTO();
    }

    public LabelKeyTO getPurchaseLabelKeyTO() {
        return purchaseLabelKeyTO;
    }

    public void setPurchaseLabelKeyTO(LabelKeyTO purchaseLabelKeyTO) {
        this.purchaseLabelKeyTO = purchaseLabelKeyTO;
    }

    public LabelKeyTO getPurchaseSchLabelKeyTO() {
        return purchaseSchLabelKeyTO;
    }

    public void setPurchaseSchLabelKeyTO(LabelKeyTO purchaseSchLabelKeyTO) {
        this.purchaseSchLabelKeyTO = purchaseSchLabelKeyTO;
    }

    public BigDecimal getCumulative() {
        return cumulative;
    }

    public BigDecimal getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(BigDecimal usedItems) {
        this.usedItems = usedItems;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getProjPOId() {
        return projPOId;
    }

    public void setProjPOId(Long projPOId) {
        this.projPOId = projPOId;
    }

    public void setCumulative(BigDecimal cumulative) {
        this.cumulative = cumulative;
    }

}
