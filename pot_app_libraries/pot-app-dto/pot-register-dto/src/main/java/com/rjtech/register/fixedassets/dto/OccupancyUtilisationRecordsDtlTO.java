package com.rjtech.register.fixedassets.dto;

public class OccupancyUtilisationRecordsDtlTO extends SubAssetDtlTO {
    private static final long serialVersionUID = 7950084862079755849L;
    private Long id;
    private String pastYear;
    private String unit;
    private String occupied;
    private String vacant;
    private String underRepair;
    private Long Total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPastYear() {
        return pastYear;
    }

    public void setPastYear(String pastYear) {
        this.pastYear = pastYear;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOccupied() {
        return occupied;
    }

    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }

    public String getVacant() {
        return vacant;
    }

    public void setVacant(String vacant) {
        this.vacant = vacant;
    }

    public String getUnderRepair() {
        return underRepair;
    }

    public void setUnderRepair(String underRepair) {
        this.underRepair = underRepair;
    }

    public Long getTotal() {
        return Total;
    }

    public void setTotal(Long total) {
        Total = total;
    }

}
