package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.MultiPurchaseOrderTO;

public class MultiPurchaseOrderSaveReq extends ProjectTO {

    private static final long serialVersionUID = -1287508473925552248L;
    private List<MultiPurchaseOrderTO> multiPurchaseOrderTOs = new ArrayList<MultiPurchaseOrderTO>();

    public List<MultiPurchaseOrderTO> getMultiPurchaseOrderTOs() {
        return multiPurchaseOrderTOs;
    }

    public void setMultiPurchaseOrderTOs(List<MultiPurchaseOrderTO> multiPurchaseOrderTOs) {
        this.multiPurchaseOrderTOs = multiPurchaseOrderTOs;
    }

}
