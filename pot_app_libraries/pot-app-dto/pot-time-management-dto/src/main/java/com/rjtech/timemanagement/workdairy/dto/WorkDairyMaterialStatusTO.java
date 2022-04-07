package com.rjtech.timemanagement.workdairy.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkDairyMaterialStatusTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long materialDtlId;
    private Long parentId;
    private String comments;
    private String apprStatus;
    private Long userId;
    private Integer status;
    private double total;

    private List<WorkDairyMaterialCostTO> workDairyMaterialCostTOs = new ArrayList<WorkDairyMaterialCostTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialDtlId() {
        return materialDtlId;
    }

    public void setMaterialDtlId(Long materialDtlId) {
        this.materialDtlId = materialDtlId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WorkDairyMaterialCostTO> getWorkDairyMaterialCostTOs() {
        return workDairyMaterialCostTOs;
    }

    public void setWorkDairyMaterialCostTOs(List<WorkDairyMaterialCostTO> workDairyMaterialCostTOs) {
        this.workDairyMaterialCostTOs = workDairyMaterialCostTOs;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}