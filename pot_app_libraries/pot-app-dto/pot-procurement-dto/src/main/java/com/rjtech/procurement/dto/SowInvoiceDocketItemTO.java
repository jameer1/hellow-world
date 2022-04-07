package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class SowInvoiceDocketItemTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8528139318194647601L;
    private Long id;
    private Long purId;
    private Long sowId;
    private Long sowPOId;
    private String desc;
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSowId() {
        return sowId;
    }

    public void setSowId(Long sowId) {
        this.sowId = sowId;
    }

    public Long getSowPOId() {
        return sowPOId;
    }

    public void setSowPOId(Long sowPOId) {
        this.sowPOId = sowPOId;
    }

}
