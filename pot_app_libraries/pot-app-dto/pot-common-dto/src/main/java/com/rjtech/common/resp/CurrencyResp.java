package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.CurrencyTO;

public class CurrencyResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<CurrencyTO> currencyTOs = null;

    public CurrencyResp() {
        currencyTOs = new ArrayList<CurrencyTO>();
    }

    public List<CurrencyTO> getCurrencyTOs() {
        return currencyTOs;
    }

}