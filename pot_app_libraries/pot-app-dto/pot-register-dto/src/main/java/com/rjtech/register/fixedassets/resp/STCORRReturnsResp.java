package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.STCORRReturnsDtlTO;

public class STCORRReturnsResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<STCORRReturnsDtlTO> stcorrReturnsDtlTOs = new ArrayList<STCORRReturnsDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    
    public List<STCORRReturnsDtlTO> getStcorrReturnsDtlTOs() {
        return stcorrReturnsDtlTOs;
        }

    public void setStcorrReturnsDtlTOs(List<STCORRReturnsDtlTO> stcorrReturnsDtlTOs) {
        this.stcorrReturnsDtlTOs = stcorrReturnsDtlTOs;
    }
}
