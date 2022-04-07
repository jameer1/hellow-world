package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class ManpowerInvoiceDocketItemTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6489879516755955248L;
    private Long id;
    private Long purId;
    private Long empId;
    private Long empPOId;
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

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
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

    public Long getEmpPOId() {
        return empPOId;
    }

    public void setEmpPOId(Long empPOId) {
        this.empPOId = empPOId;
    }

}
