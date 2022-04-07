package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PurchaseOrderTO;

public class PurchaseOrderSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PurchaseOrderTO> purchaseOrderTOs = new ArrayList<PurchaseOrderTO>();

    public List<PurchaseOrderTO> getPurchaseOrderTOs() {
        return purchaseOrderTOs;
    }

    public void setPurchaseOrderTOs(List<PurchaseOrderTO> purchaseOrderTOs) {
        this.purchaseOrderTOs = purchaseOrderTOs;
    }

}
