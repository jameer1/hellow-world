package com.rjtech.common.dto;

import java.io.Serializable;
import java.util.Date;

public abstract class AuditLog implements Serializable {

    private static final long serialVersionUID = -3763356942184775462L;

    private Integer status = new Integer(1);
    private Date createdOn;
    private Date updatedOn;
    private String createdBy;
    private String updatedBy;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}