package com.rjtech.register.service.material;

import java.util.List;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.register.material.req.MaterialFilterReq;

public interface MaterialProjConsumptionSevice {

    List<MaterialLedgerResTo> getMaterialStoreTransitConsumption(MaterialFilterReq materialGetReq);

    List<MaterialLedgerResTo> getMaterialStockPiledConsumption(MaterialFilterReq materialGetReq);

    LabelKeyTOResp getMaterialDailyIssueSchItems(MaterialFilterReq materialGetReq);

    LabelKeyTOResp getMaterialProjLedgers(MaterialFilterReq materialFilterReq);
}
