package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CostBudgetRatioTO implements Serializable {

    private static final long serialVersionUID = 2396538904278064527L;
    private Long costId;
    private Double labour;
    private Double plant;
    private Double mat;
    private Double other;

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Double getLabour() {
        return Double.valueOf(new DecimalFormat("#.##").format(labour));
    }

    public void setLabour(Double labour) {
        this.labour = labour;
    }

    public Double getPlant() {
        return Double.valueOf(new DecimalFormat("#.##").format(plant));
    }

    public void setPlant(Double plant) {
        this.plant = plant;
    }

    public Double getMat() {
        return Double.valueOf(new DecimalFormat("#.##").format(mat));
    }

    public void setMat(Double mat) {
        this.mat = mat;
    }

    public Double getOther() {
        return Double.valueOf(new DecimalFormat("#.##").format(other));
    }

    public void setOther(Double other) {
        this.other = other;
    }

}
