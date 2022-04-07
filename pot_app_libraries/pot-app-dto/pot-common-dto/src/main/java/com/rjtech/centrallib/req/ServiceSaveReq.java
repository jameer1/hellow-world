package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ServiceClassTO;
import com.rjtech.common.dto.ClientTO;


public class ServiceSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ServiceClassTO> serviceClassTOs = new ArrayList<ServiceClassTO>(
            5);

    public List<ServiceClassTO> getServiceClassTOs() {
        return serviceClassTOs;
    }

    public void setServiceClassTOs(List<ServiceClassTO> serviceClassTOs) {
        this.serviceClassTOs = serviceClassTOs;
    }

}
