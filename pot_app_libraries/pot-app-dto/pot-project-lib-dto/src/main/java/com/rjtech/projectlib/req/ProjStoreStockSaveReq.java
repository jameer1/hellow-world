package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjStoreStockTO;

public class ProjStoreStockSaveReq extends ProjectTO {
    private static final long serialVersionUID = 6526217036270683215L;
    private List<ProjStoreStockTO> projStoreStockTOs = new ArrayList<ProjStoreStockTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjStoreStockTO> getProjStoreStockTOs() {
        return projStoreStockTOs;
    }

    public void setProjStoreStockTO(List<ProjStoreStockTO> projStoreStockTO) {
        this.projStoreStockTOs = projStoreStockTO;
    }

}
