package com.rjtech.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class ProfitCentreTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private String code;
    private String name;
    private boolean item;
    private boolean itemParent = false;
    private boolean expand = false;
    private Long parentId;
    private String parentName;
    private String vendorPayCycle;
    private String cyclePeriodStartFrom;
    private String cycleDueDate; 
    private boolean projAssigned;
    private List<ProfitCentreTO> childProfitCentreTOs = new ArrayList<ProfitCentreTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean itemParent) {
        this.itemParent = itemParent;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    public String getVendorPayCycle() {
        return vendorPayCycle;
    }

    public void setVendorPayCycle(String vendorPayCycle) {
        this.vendorPayCycle = vendorPayCycle;
    }
    
    public String getCyclePeriodStartFrom() {
        return cyclePeriodStartFrom;
    }

    public void setCyclePeriodStartFrom(String cyclePeriodStartFrom) {
        this.cyclePeriodStartFrom = cyclePeriodStartFrom;
    }
    
    public String getCycleDueDate() {
        return cycleDueDate;
    }

    public void setCycleDueDate(String cycleDueDate) {
        this.cycleDueDate = cycleDueDate;
    }

    public List<ProfitCentreTO> getChildProfitCentreTOs() {
        return childProfitCentreTOs;
    }

    public void setChildProfitCentreTOs(List<ProfitCentreTO> childProfitCentreTOs) {
        this.childProfitCentreTOs = childProfitCentreTOs;
    }


    public boolean isProjAssigned() {
        return projAssigned;
    }

    public void setProjAssigned(boolean projAssigned) {
        this.projAssigned = projAssigned;
    }
}
