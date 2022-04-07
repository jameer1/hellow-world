package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.SubAssetDtlTO;

public class SubAssetDeleteReq extends SubAssetDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> subAssetIds = new ArrayList<Long>();

    public List<Long> getSubAssetIds() {
        return subAssetIds;
    }

    public void setSubAssetIds(List<Long> subAssetIds) {
        this.subAssetIds = subAssetIds;
    }

}
