package com.rjtech.register.material.dto;

import java.math.BigDecimal;

import com.rjtech.register.dto.RegisterProjPurchaseOrderTO;

public class MaterialProjSchItemTO extends RegisterProjPurchaseOrderTO {

    private static final long serialVersionUID = 8595409519091350845L;

    private Long id;
    private Long materialClassId;
    private String comments;
    private BigDecimal totalQty;
    private BigDecimal cumulativeQty;
    private String supplyDeliveryDate;
    private Long ledgerId;
    private Long schCountByLocId;
    private BigDecimal issueQty;
    private Long schItemId;
    private String projName;
    private Long projDockId;
    private BigDecimal transitQty;
    private Boolean qtyMismatch;
    private BigDecimal currentAvaiableQty;
    private boolean issueExist;
    private BigDecimal openingStock;
    private BigDecimal closingStock;
    private long materialTransReqId;
    private double workDairyAvlQty;
    
    public double getWorkDairyAvlQty() {
        return workDairyAvlQty;
    }

    public void setWorkDairyAvlQty(double workDairyAvlQty) {
        this.workDairyAvlQty = workDairyAvlQty;
    }

    public long getMaterialTransReqId() {
        return materialTransReqId;
    }

    public void setMaterialTransReqId(long materialTransReqId) {
        this.materialTransReqId = materialTransReqId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
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

    public String getSupplyDeliveryDate() {
        return supplyDeliveryDate;
    }

    public void setSupplyDeliveryDate(String supplyDeliveryDate) {
        this.supplyDeliveryDate = supplyDeliveryDate;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public Long getSchCountByLocId() {
        return schCountByLocId;
    }

    public BigDecimal getIssueQty() {
        return issueQty;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public void setSchCountByLocId(Long schCountByLocId) {
        this.schCountByLocId = schCountByLocId;
    }

    public void setIssueQty(BigDecimal issueQty) {
        this.issueQty = issueQty;
    }

    public Long getSchItemId() {
        return schItemId;
    }

    public void setSchItemId(Long schItemId) {
        this.schItemId = schItemId;
    }

    public Long getProjDockId() {
        return projDockId;
    }

    public void setProjDockId(Long projDockId) {
        this.projDockId = projDockId;
    }

    public BigDecimal getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(BigDecimal transitQty) {
        this.transitQty = transitQty;
    }

    public Boolean getQtyMismatch() {
        return qtyMismatch;
    }

    public void setQtyMismatch(Boolean qtyMismatch) {
        this.qtyMismatch = qtyMismatch;
    }

    public BigDecimal getCurrentAvaiableQty() {
        return currentAvaiableQty;
    }

    public void setCurrentAvaiableQty(BigDecimal currentAvaiableQty) {
        this.currentAvaiableQty = currentAvaiableQty;
    }

    public boolean isIssueExist() {
        return issueExist;
    }

    public void setIssueExist(boolean issueExist) {
        this.issueExist = issueExist;
    }

    public BigDecimal getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(BigDecimal openingStock) {
        this.openingStock = openingStock;
    }

    public BigDecimal getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(BigDecimal closingStock) {
        this.closingStock = closingStock;
    }

}
