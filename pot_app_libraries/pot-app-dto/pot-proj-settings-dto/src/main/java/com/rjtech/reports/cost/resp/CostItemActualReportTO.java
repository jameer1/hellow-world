package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import com.rjtech.common.utils.CommonUtil;

public class CostItemActualReportTO implements Serializable {

    private static final long serialVersionUID = -2369919929011345957L;
    private String date;
    private Long epsId;
    private String epsName;
    private Long projId;
    private String projName;
    private String currencyCode;
    private Double labourActual = Double.valueOf(0);
    private Double plantActual = Double.valueOf(0);
    private Double matActual = Double.valueOf(0);
    private Double otherActual = Double.valueOf(0);
    private Double totalActual = Double.valueOf(0);
    private Double labourPlanned = Double.valueOf(0);
    private Double plantPlanned = Double.valueOf(0);
    private Double matPlanned = Double.valueOf(0);
    private Double otherPlanned = Double.valueOf(0);
    private Double totalPlanned = Double.valueOf(0);

    public String getDate() {
        return date;
    }

    public Date getDateObj() {
        return CommonUtil.convertStringToDate(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getEpsId() {
        return epsId;
    }

    public void setEpsId(Long epsId) {
        this.epsId = epsId;
    }

    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getLabourActual() {
        return Double.valueOf(new DecimalFormat("#.##").format(labourActual));
    }

    public void setLabourActual(Double labourActual) {
        this.labourActual = labourActual;
    }

    public void addLabourActual(Double labourActual) {
        this.labourActual += labourActual;
        this.totalActual += labourActual;
    }

    public Double getPlantActual() {
        return Double.valueOf(new DecimalFormat("#.##").format(plantActual));
    }

    public void setPlantActual(Double plantActual) {
        this.plantActual = plantActual;
    }

    public void addPlantActual(Double plantActual) {
        this.plantActual += plantActual;
        this.totalActual += plantActual;
    }

    public Double getMatActual() {
        return Double.valueOf(new DecimalFormat("#.##").format(matActual));
    }

    public void setMatActual(Double matActual) {
        this.matActual = matActual;
    }

    public void addMatActual(Double matActual) {
        this.matActual += matActual;
        this.totalActual += matActual;
    }

    public Double getOtherActual() {
        return Double.valueOf(new DecimalFormat("#.##").format(otherActual));
    }

    public void setOtherActual(Double otherActual) {
        this.otherActual = otherActual;
    }

    public void addOtherActual(Double otherActual) {
        this.otherActual += otherActual;
        this.totalActual += otherActual;
    }

    public Double getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(Double totalActual) {
        this.totalActual = totalActual;
    }

    public Double getLabourPlanned() {
        return labourPlanned;
    }

    public void setLabourPlanned(Double labourPlanned) {
        this.labourPlanned = labourPlanned;
    }

    public Double getPlantPlanned() {
        return plantPlanned;
    }

    public void setPlantPlanned(Double plantPlanned) {
        this.plantPlanned = plantPlanned;
    }

    public Double getMatPlanned() {
        return matPlanned;
    }

    public void setMatPlanned(Double matPlanned) {
        this.matPlanned = matPlanned;
    }

    public Double getOtherPlanned() {
        return otherPlanned;
    }

    public void setOtherPlanned(Double otherPlanned) {
        this.otherPlanned = otherPlanned;
    }

    public Double getTotalPlanned() {
        return totalPlanned;
    }

    public void setTotalPlanned(Double totalPlanned) {
        this.totalPlanned = totalPlanned;
    }

}
