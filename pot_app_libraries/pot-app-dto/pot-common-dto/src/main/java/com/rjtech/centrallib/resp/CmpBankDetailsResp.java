package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.BankTO;
import com.rjtech.common.resp.AppResp;


public class CmpBankDetailsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<BankTO> bankTOs = null;

    public CmpBankDetailsResp() {
    	bankTOs = new ArrayList<BankTO>(5);
    }

    public List<BankTO> getBankTOs() {
        return bankTOs;
    }

    public void setBankTOs(List<BankTO> bankTOs) {
        this.bankTOs = bankTOs;
    }

}
