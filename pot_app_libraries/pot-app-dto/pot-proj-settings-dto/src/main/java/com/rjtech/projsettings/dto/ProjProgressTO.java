package com.rjtech.projsettings.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projectlib.dto.ProjSOWItemTO;

public class ProjProgressTO extends ProjectTO {

    private static final long serialVersionUID = 3144078622357811766L;
    private Long id;
    private String startDate;
    private String finishDate;
    private Long sowId;
    private boolean item;
    private boolean expand = true;
    private String name;
    private String code;
    private Long parentId;
    private BigDecimal originalQuantity;
    private BigDecimal revicedquantity;
    private String unitofmeasure;
    private Long resourceCurveId;

    private ProjSOWItemTO projSOWItemTO;
    private List<ProjProgressTO> childProjProgressTOs = new ArrayList<ProjProgressTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceCurveId() {
        return resourceCurveId;
    }

    public void setResourceCurveId(Long resourceCurveId) {
        this.resourceCurveId = resourceCurveId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public ProjSOWItemTO getProjSOWItemTO() {
        return projSOWItemTO;
    }

    public void setProjSOWItemTO(ProjSOWItemTO projSOWItemTO) {
        this.projSOWItemTO = projSOWItemTO;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ProjProgressTO> getChildProjProgressTOs() {
        return childProjProgressTOs;
    }

    public void setChildProjProgressTOs(List<ProjProgressTO> childProjProgressTOs) {
        this.childProjProgressTOs = childProjProgressTOs;
    }

    public BigDecimal getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public BigDecimal getRevicedquantity() {
        return revicedquantity;
    }

    public void setRevicedquantity(BigDecimal revicedquantity) {
        this.revicedquantity = revicedquantity;
    }

    public String getUnitofmeasure() {
        return unitofmeasure;
    }

    public void setUnitofmeasure(String unitofmeasure) {
        this.unitofmeasure = unitofmeasure;
    }
}
