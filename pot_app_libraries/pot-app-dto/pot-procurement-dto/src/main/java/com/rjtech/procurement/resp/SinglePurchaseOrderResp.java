package com.rjtech.procurement.resp;

import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.procurement.dto.MultiPurchaseOrderTO;
import com.rjtech.procurement.dto.SinglePurchaseOrderTO;

public class SinglePurchaseOrderResp extends AppResp {

    private static final long serialVersionUID = 2331218655814518146L;
    private List<SinglePurchaseOrderTO> singlePurchaseOrderTOs = null;
    private List<MultiPurchaseOrderTO> multiPurchaseOrderTOs = null;

    public List<SinglePurchaseOrderTO> getSinglePurchaseOrderTOs() {
        return singlePurchaseOrderTOs;
    }

    public void setSinglePurchaseOrderTOs(List<SinglePurchaseOrderTO> singlePurchaseOrderTOs) {
        this.singlePurchaseOrderTOs = singlePurchaseOrderTOs;
    }

    public List<MultiPurchaseOrderTO> getMultiPurchaseOrderTOs() {
        return multiPurchaseOrderTOs;
    }

    public void setMultiPurchaseOrderTOs(List<MultiPurchaseOrderTO> multiPurchaseOrderTOs) {
        this.multiPurchaseOrderTOs = multiPurchaseOrderTOs;
    }

}
