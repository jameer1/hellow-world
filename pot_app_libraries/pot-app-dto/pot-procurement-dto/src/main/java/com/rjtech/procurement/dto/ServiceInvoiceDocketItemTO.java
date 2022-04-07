package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class ServiceInvoiceDocketItemTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 9099398337979952030L;
    private Long id;
    private Long purId;
    private Long serviceId;
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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
