package com.rjtech.register.service.material;

import java.util.List;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;

public interface MaterialDeliveryDocketSevice {

    LabelKeyTOResp getMaterialDeliveryDocketDetails(MaterialGetReq materialGetReq);

    LabelKeyTOResp getMaterialSchItemDeliveryDockets(MaterialFilterReq materialFilterReq);

    List<MaterialLedgerResTo> getSupplierDeliveryDockets(MaterialFilterReq materialFilterReq);

    List<MaterialLedgerResTo> getDeliveryDockets(MaterialFilterReq materialFilterReq);

    List<MaterialLedgerResTo> getInTransitDetails(MaterialFilterReq materialFilterReq);

    List<MaterialLedgerResTo> getStockPiledDetails(MaterialFilterReq materialFilterReq);

    MaterialPODeliveryDocketEntity getMaterialProjEntity(Long id);

}
