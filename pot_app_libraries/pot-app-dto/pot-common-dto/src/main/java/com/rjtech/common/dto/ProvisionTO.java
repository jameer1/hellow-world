package com.rjtech.common.dto;

import com.rjtech.common.dto.ClientTO;

public class ProvisionTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long id;
    private String code;
    private String name;
    private String startDate;
    private String finishDate;
    private String adminName1;
    private String lat;
    private String lng;
    private ProvinceCodes adminCodes1;

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

    public String getAdminName1() {
        return adminName1;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public ProvinceCodes getAdminCodes1() {
        return adminCodes1;
    }

    public void setAdminCodes1(ProvinceCodes adminCodes1) {
        this.adminCodes1 = adminCodes1;
    }

    
}