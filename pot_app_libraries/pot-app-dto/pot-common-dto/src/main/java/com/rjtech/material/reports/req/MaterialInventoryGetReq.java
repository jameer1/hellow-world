package com.rjtech.material.reports.req;

import java.util.ArrayList;
import java.util.List;

public class MaterialInventoryGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 597086458869092353L;

    private List<Long> projIds = new ArrayList<Long>();
    private List<String> stockLocations = new ArrayList<String>();
    private List<Long> materialItemIds = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
    private String subReportType;

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<Long> getMaterialItemIds() {
        return materialItemIds;
    }

    public void setMaterialItemIds(List<Long> materialItemIds) {
        this.materialItemIds = materialItemIds;
    }

    public List<String> getStockLocations() {
        return stockLocations;
    }

    public void setStockLocations(List<String> stockLocations) {
        this.stockLocations = stockLocations;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSubReportType() {
        return subReportType;
    }

    public void setSubReportType(String subReportType) {
        this.subReportType = subReportType;
    }

}
