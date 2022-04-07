package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.CurrencyTO;

public class CurrencySaveReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<CurrencyTO> currencyTOs = new ArrayList<CurrencyTO>();

    public List<CurrencyTO> getCurrencyTOs() {
        return currencyTOs;
    }

    public void setCurrencyTOs(List<CurrencyTO> currencyTOs) {
        this.currencyTOs = currencyTOs;
    }

}