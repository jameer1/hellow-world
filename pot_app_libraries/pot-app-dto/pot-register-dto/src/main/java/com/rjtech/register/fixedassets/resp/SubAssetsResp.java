package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;
import com.rjtech.register.fixedassets.dto.SubAssetDtlTO;

public class SubAssetsResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private FixedAssetDtlTO fixedAssetDtlTO = new FixedAssetDtlTO();
    private List<SubAssetDtlTO> subAssetDtlTOs = new ArrayList<SubAssetDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public FixedAssetDtlTO getFixedAssetDtlTO() {
        return fixedAssetDtlTO;
    }

    public void setFixedAssetDtlTO(FixedAssetDtlTO fixedAssetDtlTO) {
        this.fixedAssetDtlTO = fixedAssetDtlTO;
    }

    public List<SubAssetDtlTO> getSubAssetDtlTOs() {
        return subAssetDtlTOs;
    }

    public void setSubAssetDtlTOs(List<SubAssetDtlTO> subAssetDtlTOs) {
        this.subAssetDtlTOs = subAssetDtlTOs;
    }

}
