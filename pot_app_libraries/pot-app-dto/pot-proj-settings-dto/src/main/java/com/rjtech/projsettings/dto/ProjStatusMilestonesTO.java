package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjStatusMilestonesTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -4567482312275511930L;
    /**
     * 
     */
    private Long id;
    private String mileStones;
    private String mileStoneOriginal;
    private String mileStoneForeCast;
    private String mileStoneActual;
    private String mileStoneVariance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMileStones() {
        return mileStones;
    }

    public void setMileStones(String mileStones) {
        this.mileStones = mileStones;
    }

    public String getMileStoneOriginal() {
        return mileStoneOriginal;
    }

    public void setMileStoneOriginal(String mileStoneOriginal) {
        this.mileStoneOriginal = mileStoneOriginal;
    }

    public String getMileStoneForeCast() {
        return mileStoneForeCast;
    }

    public void setMileStoneForeCast(String mileStoneForeCast) {
        this.mileStoneForeCast = mileStoneForeCast;
    }

    public String getMileStoneActual() {
        return mileStoneActual;
    }

    public void setMileStoneActual(String mileStoneActual) {
        this.mileStoneActual = mileStoneActual;
    }

    public String getMileStoneVariance() {
        return mileStoneVariance;
    }

    public void setMileStoneVariance(String mileStoneVariance) {
        this.mileStoneVariance = mileStoneVariance;
    }

}
