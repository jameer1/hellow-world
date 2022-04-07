package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.BusinessActivityTO;
import com.rjtech.centrallib.dto.BusinessCategoryTO;
import com.rjtech.common.dto.ClientTO;


public class BusinessCatgSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static List<BusinessCategoryTO> businessCategoryTOs = new ArrayList<BusinessCategoryTO>(
            5);

    public static List<BusinessCategoryTO> getBusinessCategoryTOs() {
        return businessCategoryTOs;
    }

    public void setBusinessActivityTOs(List<BusinessCategoryTO> businessCategoryTOs) {
        BusinessCatgSaveReq.businessCategoryTOs = businessCategoryTOs;
    }

}
