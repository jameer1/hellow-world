package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ServiceClassTO;
import com.rjtech.common.resp.AppResp;


public class ServiceResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ServiceClassTO> serviceClassTOs = null;

    public ServiceResp() {
        serviceClassTOs = new ArrayList<ServiceClassTO>(5);
    }

    public List<ServiceClassTO> getServiceClassTOs() {
        return serviceClassTOs;
    }

}
