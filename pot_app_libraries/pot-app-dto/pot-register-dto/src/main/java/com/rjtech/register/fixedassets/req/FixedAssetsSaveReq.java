package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;

public class FixedAssetsSaveReq extends FixedAssetDtlTO {

    private static final long serialVersionUID = 7526217036270683215L;

    private List<FixedAssetDtlTO> fixedAssetRegisterTOs = new ArrayList<FixedAssetDtlTO>();

    public List<FixedAssetDtlTO> getFixedAssetRegisterTOs() {
        return fixedAssetRegisterTOs;
    }

    public void setFixedAssetRegisterTOs(List<FixedAssetDtlTO> fixedAssetRegisterTOs) {
        this.fixedAssetRegisterTOs = fixedAssetRegisterTOs;
    }

}
