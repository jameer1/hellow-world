package com.rjtech.material.ledger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LedgerResTo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5006739495197381870L;
    
    private Map<String, MaterialLedgerResTo> docketLedgerMap = new HashMap<String, MaterialLedgerResTo>();
    
    private List<MaterialLedgerResTo> ledgerRes = new ArrayList<MaterialLedgerResTo>();

    public List<MaterialLedgerResTo> getLedgerRes() {
        return ledgerRes;
    }

    public void setLedgerRes(List<MaterialLedgerResTo> ledgerRes) {
        this.ledgerRes = ledgerRes;
    }

    public Map<String, MaterialLedgerResTo> getDocketLedgerMap() {
        return docketLedgerMap;
    }

    public void setDocketLedgerMap(Map<String, MaterialLedgerResTo> docketLedgerMap) {
        this.docketLedgerMap = docketLedgerMap;
    }

}
