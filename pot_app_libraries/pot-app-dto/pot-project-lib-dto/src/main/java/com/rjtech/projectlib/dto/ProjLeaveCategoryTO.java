package com.rjtech.projectlib.dto;

import java.io.Serializable;

public class ProjLeaveCategoryTO implements Serializable {

    private static final long serialVersionUID = -7795487354719812896L;
    private Long id;
    private Long procureId;
    private String procureType;
    private String payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcureId() {
        return procureId;
    }

    public void setProcureId(Long procureId) {
        this.procureId = procureId;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
