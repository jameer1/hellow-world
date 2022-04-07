package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;

public class FixedAssetsRegisterOnLoadResp extends AppResp {

    private static final long serialVersionUID = -4643175409489645438L;
    private FixedAssetDtlTO fixedAssetDtlTO = null;
    private List<FixedAssetDtlTO> fixedAssetDtlTOs = null;

    public FixedAssetsRegisterOnLoadResp() {
        fixedAssetDtlTOs = new ArrayList<FixedAssetDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public FixedAssetDtlTO getFixedAssetDtlTO() {
        return fixedAssetDtlTO;
    }

    public void setFixedAssetDtlTO(FixedAssetDtlTO fixedAssetDtlTO) {
        this.fixedAssetDtlTO = fixedAssetDtlTO;
    }

    public List<FixedAssetDtlTO> getFixedAssetDtlTOs() {
        return fixedAssetDtlTOs;
    }

    public void setFixedAssetDtlTOs(List<FixedAssetDtlTO> fixedAssetDtlTOs) {
        this.fixedAssetDtlTOs = fixedAssetDtlTOs;
    }

}
