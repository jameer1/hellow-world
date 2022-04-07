package com.rjtech.register.material.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.dto.RegisterProjPurchaseOrderTO;

public class MaterialProjDtlTO extends RegisterProjPurchaseOrderTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long materialClassId;
    private String comments;
    private BigDecimal totalQty;
    private BigDecimal cumulativeQty;
    private String supplyDeliveryDate;
    private String deliveryPlace;
    private Long ledgerId;
    private Long schLocId;
    private Long projStockId;
    private Long stockId;
    private BigDecimal schLocWiseCount;
    private BigDecimal issueQty;
    private BigDecimal workDairyCunsumptionQty;
    private Long mapId;
    private Long schItemCmpId;

    public Long getSchItemCmpId() {
        return schItemCmpId;
    }

    public void setSchItemCmpId(Long schItemCmpId) {
        this.schItemCmpId = schItemCmpId;
    }

    private List<MaterialPODeliveryDocketTO> materialPODeliveryDocketTOs = new ArrayList<MaterialPODeliveryDocketTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialClassId() {
        return materialClassId;
    }

    public void setMaterialClassId(Long materialClassId) {
        this.materialClassId = materialClassId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<MaterialPODeliveryDocketTO> getMaterialPODeliveryDocketTOs() {
        return materialPODeliveryDocketTOs;
    }

    public void setMaterialPODeliveryDocketTOs(List<MaterialPODeliveryDocketTO> materialPODeliveryDocketTOs) {
        this.materialPODeliveryDocketTOs = materialPODeliveryDocketTOs;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getCumulativeQty() {
        return cumulativeQty;
    }

    public void setCumulativeQty(BigDecimal cumulativeQty) {
        this.cumulativeQty = cumulativeQty;
    }

    public BigDecimal getWorkDairyCunsumptionQty() {
        return workDairyCunsumptionQty;
    }

    public void setWorkDairyCunsumptionQty(BigDecimal workDairyCunsumptionQty) {
        this.workDairyCunsumptionQty = workDairyCunsumptionQty;
    }

    public String getSupplyDeliveryDate() {
        return supplyDeliveryDate;
    }

    public void setSupplyDeliveryDate(String supplyDeliveryDate) {
        this.supplyDeliveryDate = supplyDeliveryDate;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Long getProjStockId() {
        return projStockId;
    }

    public void setProjStockId(Long projStockId) {
        this.projStockId = projStockId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getSchLocId() {
        return schLocId;
    }

    public void setSchLocId(Long schLocId) {
        this.schLocId = schLocId;
    }

    public BigDecimal getSchLocWiseCount() {
        return schLocWiseCount;
    }

    public void setSchLocWiseCount(BigDecimal schLocWiseCount) {
        this.schLocWiseCount = schLocWiseCount;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String toString() {
    	return "schItemCmpId:"+schItemCmpId+" schLocId:"+schLocId;
    }
 }
