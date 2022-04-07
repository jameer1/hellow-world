package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.BankTO;


public class CompanyBankSaveReq extends CompanyGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<BankTO> bankTOs = new ArrayList<BankTO>(
            5);

    public List<BankTO> getBankTOs() {
        return bankTOs;
    }

    public void setBankTOs(List<BankTO> bankTOs) {
        this.bankTOs = bankTOs;
    }

}
