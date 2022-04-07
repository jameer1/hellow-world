package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;

public class FixedAssetRegisterResp extends AppResp {
    private static final long serialVersionUID = 7719597858807086400L;
    private List<FixedAssetDtlTO> empRegisterDtlTOs = new ArrayList<FixedAssetDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FixedAssetDtlTO> getEmpRegisterDtlTOs() {
        return empRegisterDtlTOs;
    }

    public void setEmpRegisterDtlTOs(List<FixedAssetDtlTO> empRegisterDtlTOs) {
        this.empRegisterDtlTOs = empRegisterDtlTOs;
    }

}
