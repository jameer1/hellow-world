package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;
import com.rjtech.register.fixedassets.dto.SubAssetDtlTO;

public class SubAssetsSaveReq extends SubAssetsGetReq {

    private static final long serialVersionUID = 1L;

    public SubAssetsSaveReq() {

    }

    private FixedAssetDtlTO fixedAssetDtlTO;
    private List<SubAssetDtlTO> subAssetDtlTO = new ArrayList<SubAssetDtlTO>();

    public FixedAssetDtlTO getFixedAssetDtlTO() {
        return fixedAssetDtlTO;
    }

    public void setFixedAssetDtlTO(FixedAssetDtlTO fixedAssetDtlTO) {
        this.fixedAssetDtlTO = fixedAssetDtlTO;
    }

    public List<SubAssetDtlTO> getSubAssetDtlTO() {
        return subAssetDtlTO;
    }

    public void setSubAssetDtlTO(List<SubAssetDtlTO> subAssetDtlTO) {
        this.subAssetDtlTO = subAssetDtlTO;
    }

}
