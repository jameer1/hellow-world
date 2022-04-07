package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class MaterialInvoiceDocketItemTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3855038022828197320L;
    /**
     * 
     */

    private Long id;
    private Long purId;
    private Long matId;
    private Long matPOId;
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

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
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

    public Long getMatPOId() {
        return matPOId;
    }

    public void setMatPOId(Long matPOId) {
        this.matPOId = matPOId;
    }

}
