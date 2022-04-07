package com.rjtech.register.controller.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.material.ledger.LedgerResTo;
import com.rjtech.material.ledger.MaterialLedgerResTo;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.service.material.MaterialDeliveryDocketSevice;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MaterialProjLedgerController {

    @Autowired
    private MaterialDeliveryDocketSevice materialDeliveryDocketSevice;

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<LedgerResTo> getProjectDockets(@RequestBody MaterialFilterReq materialFilterReq) {
        List<MaterialLedgerResTo> materialDockets = materialDeliveryDocketSevice
                .getSupplierDeliveryDockets(materialFilterReq);
        materialDockets.addAll(materialDeliveryDocketSevice.getDeliveryDockets(materialFilterReq));
        Map<String, MaterialLedgerResTo> docketsMap = new HashMap<String, MaterialLedgerResTo>();
        materialDockets.forEach((materialDocket) -> {
            docketsMap.put(materialDocket.getDocketId(), materialDocket);
        });
        LedgerResTo ledgerResTo = new LedgerResTo();
        ledgerResTo.setDocketLedgerMap(docketsMap);
        return new ResponseEntity<LedgerResTo>(ledgerResTo, HttpStatus.OK);
    }
}
