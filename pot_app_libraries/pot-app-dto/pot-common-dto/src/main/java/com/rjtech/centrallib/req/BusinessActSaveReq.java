package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.BusinessActivityTO;
import com.rjtech.common.dto.ClientTO;


public class BusinessActSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<BusinessActivityTO> businessActivityTOs = new ArrayList<BusinessActivityTO>(
            5);

    public List<BusinessActivityTO> getBusinessActivityTOs() {
        return businessActivityTOs;
    }

    public void setBusinessActivityTOs(List<BusinessActivityTO> businessActivityTOs) {
        this.businessActivityTOs = businessActivityTOs;
    }

}
