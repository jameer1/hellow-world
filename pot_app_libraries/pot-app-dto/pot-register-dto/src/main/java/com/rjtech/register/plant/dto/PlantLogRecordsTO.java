package com.rjtech.register.plant.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.rjtech.common.dto.LabelKeyTO;

public class PlantLogRecordsTO implements Serializable {

    private static final long serialVersionUID = 658713127709079559L;

    private Long id;
    private String fromDate;
    private String toDate;
    private BigDecimal startMeter;
    private BigDecimal endMeter;
    private BigDecimal netUnits;
    private String comments;
    private boolean latest;
    private PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
    private LabelKeyTO empLabelKeyTO = new LabelKeyTO();
    private BigDecimal latestMeter;

    public PlantLogRecordsTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getNetUnits() {
        return netUnits;
    }

    public void setNetUnits(BigDecimal netUnits) {
        this.netUnits = netUnits;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LabelKeyTO getEmpLabelKeyTO() {
        return empLabelKeyTO;
    }

    public void setEmpLabelKeyTO(LabelKeyTO empLabelKeyTO) {
        this.empLabelKeyTO = empLabelKeyTO;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public BigDecimal getStartMeter() {
        return startMeter;
    }

    public void setStartMeter(BigDecimal startMeter) {
        this.startMeter = startMeter;
    }

    public BigDecimal getEndMeter() {
        return endMeter;
    }

    public void setEndMeter(BigDecimal endMeter) {
        this.endMeter = endMeter;
    }

    public PlantRegProjTO getPlantRegProjTO() {
        return plantRegProjTO;
    }

    public void setPlantRegProjTO(PlantRegProjTO plantRegProjTO) {
        this.plantRegProjTO = plantRegProjTO;
    }

    public BigDecimal getLatestMeter() {
        return latestMeter;
    }

    public void setLatestMeter(BigDecimal latestMeter) {
        this.latestMeter = latestMeter;
    }

}
