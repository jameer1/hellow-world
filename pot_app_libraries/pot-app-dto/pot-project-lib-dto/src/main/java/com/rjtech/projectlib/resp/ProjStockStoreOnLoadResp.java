package com.rjtech.projectlib.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjStoreStockTO;

public class ProjStockStoreOnLoadResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ProjStoreStockTO projStoreStockTO = null;

    public ProjStockStoreOnLoadResp() {
        projStoreStockTO = new ProjStoreStockTO();
    }

    public ProjStoreStockTO getProjStoreStockTO() {
        return projStoreStockTO;
    }

    public void setProjStoreStockTO(ProjStoreStockTO projStoreStockTO) {
        this.projStoreStockTO = projStoreStockTO;
    }

}
