package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class ServiceFiltterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5748380007559175049L;
    private String serviceName;
    private String serviceCode;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

}
