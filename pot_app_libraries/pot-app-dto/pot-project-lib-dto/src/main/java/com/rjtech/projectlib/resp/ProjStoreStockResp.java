package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjStoreStockTO;

public class ProjStoreStockResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ProjStoreStockTO> projStoreStockTOs = null;

    public ProjStoreStockResp() {
        projStoreStockTOs = new ArrayList<ProjStoreStockTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjStoreStockTO> getProjStoreStockTOs() {
        return projStoreStockTOs;
    }

}
