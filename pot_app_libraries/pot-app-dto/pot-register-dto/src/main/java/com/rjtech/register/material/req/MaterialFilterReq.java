package com.rjtech.register.material.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MaterialFilterReq implements Serializable {

    private static final long serialVersionUID = 2138487510156870435L;

    private List<Long> projList = new ArrayList<Long>();
    private String fromDate;
    private String toDate;
    private List<Long> materialIds = new ArrayList<Long>();
    private List<Long> costCodeIds = new ArrayList<Long>();
    private List<Long> storeIds = new ArrayList<Long>();
    private List<Long> projStoreIds = new ArrayList<Long>();

    public List<Long> getProjList() {
        return projList;
    }

    public void setProjList(List<Long> projList) {
        this.projList = projList;
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

    public List<Long> getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(List<Long> materialIds) {
        this.materialIds = materialIds;
    }

    public List<Long> getCostCodeIds() {
        return costCodeIds;
    }

    public void setCostCodeIds(List<Long> costCodeIds) {
        this.costCodeIds = costCodeIds;
    }

    public List<Long> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<Long> storeIds) {
        this.storeIds = storeIds;
    }

    public List<Long> getProjStoreIds() {
        return projStoreIds;
    }

    public void setProjStoreIds(List<Long> projStoreIds) {
        this.projStoreIds = projStoreIds;
    }
}
