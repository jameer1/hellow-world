package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CostCodeTO;
import com.rjtech.common.dto.ClientTO;


public class CostCodeSaveReq extends ClientTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<CostCodeTO> costCodeTOs = null;

    public CostCodeSaveReq() {
        costCodeTOs = new ArrayList<CostCodeTO>(5);
    }

    public List<CostCodeTO> getCostCodeTOs() {
        return costCodeTOs;
    }

}
