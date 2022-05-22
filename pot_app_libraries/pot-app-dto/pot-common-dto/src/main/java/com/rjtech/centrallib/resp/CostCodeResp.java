package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CostCodeTO;
import com.rjtech.common.resp.AppResp;


public class CostCodeResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<CostCodeTO> costCodeTOs = null;

    public CostCodeResp() {
        costCodeTOs = new ArrayList<CostCodeTO>(5);
    }

    
    public void setCostCodeTOs(List<CostCodeTO> costCodeTOs) {
		this.costCodeTOs = costCodeTOs;
	}


	public List<CostCodeTO> getCostCodeTOs() {
        return costCodeTOs;
    }

}
